package org.herba.plugin.junit.approvaltest.handlers;

import java.io.File;
import java.lang.reflect.Method;

import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

import com.spun.util.io.FileUtils;

/**
 * Adapter class that wraps a test element and checks if it contains a
 * ComparisonFailure. Used for command enablement in the Eclipse UI.
 */
public class ComparisonFailureSelection {

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
                    return convertToDto(element, failureTrace);
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
                element.getFailureTrace().getExpected(),
                element.getFailureTrace().getActual(),
                getFilePathFromFailure(element));
    }

    private File getFilePathFromFailure(ITestElement element) {
        // todo: extract file information from original assertion
        // or the error message
        return null;
    }

    private ComparisonFailureDto convertFromApprovalTestError(ITestElement element, String failureTrace) {
        String expectedFilePath = extractPath(failureTrace, "Approved:");
        String actualFilePath = extractPath(failureTrace, "Received:");
        String expectedContent = readFileContent(expectedFilePath);
        String actualContent = readFileContent(actualFilePath);
        return new ComparisonFailureDto(firstLineFrom(element.getFailureTrace().getTrace()),
                expectedContent, actualContent, new File(expectedFilePath));
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
        return pos > 0 ? trimmed.substring(0, pos) : trimmed;
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
