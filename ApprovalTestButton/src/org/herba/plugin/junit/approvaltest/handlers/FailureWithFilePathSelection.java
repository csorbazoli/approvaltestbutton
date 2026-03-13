package org.herba.plugin.junit.approvaltest.handlers;

import java.io.File;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;
import org.herba.plugin.junit.approvaltest.models.FailureWithFilePathDto;

import com.spun.util.io.FileUtils;

/**
 * Adapter class that wraps a test element and checks if it contains a reference
 * to a file (e.g. test resource). Used for command enablement in the Eclipse
 * UI.
 */
public class FailureWithFilePathSelection {

    private static final Logger logger = Logger.getLogger(FailureWithFilePathSelection.class.getName());

    private static final String PATH_CHAR_GROUP = "[a-zA-Z0-9_\\-\\{\\}+\\(\\)=\\[\\],@~#;%\\$\\!\\.]";
    private static final String FILENAME_REGEXP = PATH_CHAR_GROUP + "+\\.\\w+";
    private static final String SIMPLE_PATH_CHAR_GROUP = "[^:\\s]";
    private static final String PATH_REGEXP = "(\\w:/|/|\\./|\\.\\./|" + SIMPLE_PATH_CHAR_GROUP + "+/)("
            + SIMPLE_PATH_CHAR_GROUP + "+/)*";
    private static Pattern FILE_PATH_PATTERN = Pattern.compile(PATH_REGEXP + FILENAME_REGEXP);

    private ITestElement testElement;
    private FailureWithFilePathDto failure;

    public FailureWithFilePathSelection(ITestElement testElement) {
        this.testElement = testElement;
        failure = extractFailureInfo(testElement);
    }

    /**
     * Extracts the ComparisonFailure from the test element's failure trace
     */
    protected FailureWithFilePathDto extractFailureInfo(ITestElement element) {
        if (element == null) {
            return null;
        }
        try {
            String failureTrace = element.getFailureTrace().getTrace();
            if ((failureTrace != null)) {
                if (isApprovalTestError(element)) {
                    return convertFromApprovalTestError(element, failureTrace);
                } else {
                    FailureWithFilePathDto ret = convertToDto(element, failureTrace);
                    return ret.getFilePath() == null ? null : ret;
                }
            }
        } catch (Exception e) {
            // Silently handle any exceptions during extraction
        }

        return null;
    }

    private boolean isApprovalTestError(ITestElement element) {
        String trace = element.getFailureTrace().getTrace();
        return trace.contains("Approved:") || trace.contains("Approvals.verify(");
    }

    private FailureWithFilePathDto convertToDto(ITestElement element, String failureTrace) {
        return new FailureWithFilePathDto(firstLineFrom(failureTrace),
                getFilePathFromFailure(element, failureTrace), null);
    }

    private File getFilePathFromFailure(ITestElement element, String failureTrace) {
        // TODO get patterns or prefixes for assertion types
        // e.g. Approved:PATTERN
        // in file PATTERN
        // what should happen if there are multiple paths? there could be a path in the
        // expected/actual value as well!
        String extracted = extractRelevantPartOfTrace(failureTrace);
        Matcher m = FILE_PATH_PATTERN.matcher(extracted);
        if (m.find()) {
            // might need a default base folder for relative paths
            String found = m.group();
            File fullPath = supplementRelativePath(found, element);
            logger.info("File path '" + found + "' found in error trace: " + extracted);
            return fullPath;
        } else {
            logger.fine("File path not found in error trace: " + extracted);
        }
        return null;
    }

    private File supplementRelativePath(String found, ITestElement element) {
        String normalized = normalizePathSep(found);
        File path = new File(normalized);
        if (path.exists() || normalizePathSep(path.getAbsolutePath()).equals(found))
            return path;
        try {
            File projectBaseFolder = determineProjectBaseFolder(element);
            return supplementTestResourcePrefix(normalized, projectBaseFolder);
        } catch (Exception e) {
            logger.warning("Could not determine project base folder: " + e.getMessage());
            return path;
        }
    }

    private File determineProjectBaseFolder(ITestElement element) {
        IJavaProject launchedProject = element.getParentContainer().getTestRunSession().getLaunchedProject();
        File projectBaseFolder = launchedProject.getResource().getLocation().toFile().getAbsoluteFile();
        return projectBaseFolder;
    }

    private File supplementTestResourcePrefix(String relativePath, File projectBaseFolder) {
        String prefix = "";
        if (!relativePath.startsWith("src") && !relativePath.startsWith("test")) {
            if (new File(projectBaseFolder, "src/test/resources").exists()) {
                prefix = "src/test/resources/";
            } else if (new File(projectBaseFolder, "test-resources").exists()) {
                prefix = "test-resources/";
            } else if (new File(projectBaseFolder, "test").exists()) {
                prefix = "test/";
            } else { // default, may not exist just yet
                prefix = "src/test/resources/";
            }

        }
        return new File(projectBaseFolder, prefix + relativePath);
    }

    private String extractRelevantPartOfTrace(String failureTrace) {
        String ret = normalizePathSep(failureTrace.substring(failureTrace.indexOf(':') + 1));
        if (ret.contains("expected:") && ret.contains("but was:")) {
            ret = ret.substring(0, ret.indexOf("expected:"));
        }
        return ret;
    }

    private String normalizePathSep(String path) {
        return path.replace('\\', '/');
    }

    private FailureWithFilePathDto convertFromApprovalTestError(ITestElement element, String failureTrace) {
        File actualFilePath = extractReceivedPath(element, failureTrace);
        if (actualFilePath == null) {
            return new FailureWithFilePathDto(firstLineFrom(element.getFailureTrace().getTrace()),
                    null, getDefaultFileNameWithoutExtension(element));
        }
        File expectedFilePath = extractApprovedPath(element, failureTrace, actualFilePath);
        String expectedContent = readFileContent(expectedFilePath);
        String actualContent = readFileContent(actualFilePath);
        return new ComparisonFailureDto(firstLineFrom(element.getFailureTrace().getTrace()),
                actualContent, expectedContent, expectedFilePath);
    }

    private File extractReceivedPath(ITestElement element, String failureTrace) {
        File ret = extractPath(failureTrace, "Received:");
        if (ret == null && element instanceof ITestCaseElement) {
            ret = determineFilePath((ITestCaseElement) element, "received");
        }
        return ret;
    }

    private File extractApprovedPath(ITestElement element, String failureTrace, File receivedFilePath) {
        File ret = extractPath(failureTrace, "Approved:");
        if (ret == null && element instanceof ITestCaseElement) {
            ret = determineFilePath((ITestCaseElement) element, "approved");
        }
        // if approved not exist yet, then it has to be next to the received one
        if (ret == null && receivedFilePath != null) {
            ret = new File(receivedFilePath.getAbsolutePath().replaceFirst("received\\.(\\w+)$", "approved.$1"));
        }
        return ret;
    }

    private File determineFilePath(ITestCaseElement element, String type) {
        // try finding the file in the folder structure just by the name without
        // extension
        return org.herba.plugin.junit.approvaltest.utils.ApprovalTestFileUtils.findFileByNameInWorkspace(
                determineProjectBaseFolder(element),
                getDefaultFileNameWithoutExtension(element, type));
    }

    private String getDefaultFileNameWithoutExtension(ITestElement element) {
        if (element instanceof ITestCaseElement) {
            ITestCaseElement tc = (ITestCaseElement) element;
            return String.join(".", tc.getTestClassName().substring(tc.getTestClassName().lastIndexOf('.') + 1),
                    tc.getTestMethodName(), "*", "approved");
        }
        return null;
    }

    private String getDefaultFileNameWithoutExtension(ITestCaseElement element, String type) {
        return String.join(".", element.getTestClassName().substring(element.getTestClassName().lastIndexOf('.') + 1),
                element.getTestMethodName(), type);
    }

    private String readFileContent(File file) {
        if (file.canRead()) {
            return FileUtils.readFile(file, false);
        }
        return "";
    }

    private File extractPath(String failureTrace, String prefix) {
        String ret = null;
        int pos = failureTrace.indexOf(prefix);
        if (pos != -1) {
            int expected = failureTrace.indexOf(" expected:", pos);
            int newLine = failureTrace.indexOf('\n', pos);
            int end = expected != -1 && expected < newLine ? expected : newLine;
            ret = failureTrace.substring(pos + prefix.length(), end).trim();
        }
        return ret == null ? null : new File(ret);
    }

    private String firstLineFrom(String content) {
        String trimmed = content.trim();
        int pos = trimmed.indexOf('\n');
        return pos > 0 ? trimmed.substring(0, pos).trim() : trimmed;
    }

    public ITestElement getTestElement() {
        return testElement;
    }

    void setTestElement(ITestElement testElement) {
        this.testElement = testElement;
    }

    public FailureWithFilePathDto getFailureInfo() {
        return failure;
    }

    /**
     * Convenience method to check if this selection contains a file reference
     */
    public boolean hasFailureInfo() {
        return failure != null;
    }

}
