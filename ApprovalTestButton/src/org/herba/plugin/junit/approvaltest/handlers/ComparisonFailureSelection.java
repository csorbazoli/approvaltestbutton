package org.herba.plugin.junit.approvaltest.handlers;

import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

import com.spun.util.io.FileUtils;

/**
 * Adapter class that wraps a test element and checks if it contains a
 * ComparisonFailure. Used for command enablement in the Eclipse UI.
 */
public class ComparisonFailureSelection {

    private static final Logger logger = Logger.getLogger(ComparisonFailureSelection.class.getName());

    private static final String PATH_CHAR_GROUP = "[a-zA-Z0-9_\\-\\{\\}+\\(\\)=\\[\\],@~#;%\\$\\!\\.]";
    private static final String FILENAME_REGEXP = PATH_CHAR_GROUP + "+\\.\\w+";
    private static final String SIMPLE_PATH_CHAR_GROUP = "[^:\\s]";
    private static final String PATH_REGEXP = "(\\w:/|/|\\./|\\.\\./|" + SIMPLE_PATH_CHAR_GROUP + "+/)("
            + SIMPLE_PATH_CHAR_GROUP + "+/)*";
    private static Pattern FILE_PATH_PATTERN = Pattern.compile(PATH_REGEXP + FILENAME_REGEXP);

    private ITestElement testElement;
    private ComparisonFailureDto comparisonFailure;

    public ComparisonFailureSelection(ITestElement testElement) {
        this.testElement = testElement;
        comparisonFailure = extractComparisonFailure(testElement);
    }

    /**
     * Extracts the ComparisonFailure from the test element's failure trace
     */
    private ComparisonFailureDto extractComparisonFailure(ITestElement element) {
        if (element == null) {
            return null;
        }
        try {
            String failureTrace = element.getFailureTrace().getTrace();
            if ((failureTrace != null)) {
                if (isComparisonFailure(element)) {
                    ComparisonFailureDto ret = convertToDto(element, failureTrace);
                    return ret.getFilePath() == null ? null : ret;
                } else if (isApprovalTestError(element)) {
                    return convertFromApprovalTestError(element, failureTrace);
                }
            }
        } catch (Exception e) {
            // Silently handle any exceptions during extraction
        }

        return null;
    }

    private boolean isApprovalTestError(ITestElement element) {
        return element.getFailureTrace().getTrace().contains("Approved:");
    }

    private boolean isComparisonFailure(ITestElement element) {
        try {
            Method method = element.getClass().getMethod("isComparisonFailure");
            return (boolean) method.invoke(element);
        } catch (Exception e) {
            // no such method
            return false;
        }
    }

    private ComparisonFailureDto convertToDto(ITestElement element, String failureTrace) {
        return new ComparisonFailureDto(firstLineFrom(failureTrace),
                element.getFailureTrace().getActual(),
                element.getFailureTrace().getExpected(),
                getFilePathFromFailure(failureTrace));
    }

    private File getFilePathFromFailure(String failureTrace) {
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
            logger.info("File path '" + found + "' found in error trace: " + extracted);
            return new File(found);
        } else {
            logger.fine("File path not found in error trace: " + extracted);
        }
        return null;
    }

    private String extractRelevantPartOfTrace(String failureTrace) {
        String ret = failureTrace.substring(failureTrace.indexOf(':') + 1)
                .replace('\\', '/');
        if (ret.contains("expected:") && ret.contains("but was:")) {
            ret = ret.substring(0, ret.indexOf("expected:"));
        }
        return ret;
    }

    private ComparisonFailureDto convertFromApprovalTestError(ITestElement element, String failureTrace) {
        String expectedFilePath = extractPath(failureTrace, "Approved:");
        String actualFilePath = extractPath(failureTrace, "Received:");
        String expectedContent = readFileContent(expectedFilePath);
        String actualContent = readFileContent(actualFilePath);
        return new ComparisonFailureDto(firstLineFrom(element.getFailureTrace().getTrace()),
                actualContent, expectedContent, new File(expectedFilePath));
    }

    private String readFileContent(String filePath) {
        File file = new File(filePath);
        if (file.canRead()) {
            return FileUtils.readFile(file);
        }
        return "";
    }

    private String extractPath(String failureTrace, String prefix) {
        String ret = null;
        int pos = failureTrace.indexOf(prefix);
        if (pos != -1) {
            int end = failureTrace.indexOf('\n', pos);
            ret = failureTrace.substring(pos + prefix.length(), end).trim();
        }
        return ret;
    }

    private String firstLineFrom(String content) {
        String trimmed = content.trim();
        int pos = trimmed.indexOf('\n');
        return pos > 0 ? trimmed.substring(0, pos).trim() : trimmed;
    }

    public ITestElement getTestElement() {
        return testElement;
    }

    public ComparisonFailureDto getComparisonFailure() {
        return comparisonFailure;
    }

    /**
     * Convenience method to check if this selection contains a ComparisonFailure
     */
    public boolean hasComparisonFailure() {
        return comparisonFailure != null;
    }

}
