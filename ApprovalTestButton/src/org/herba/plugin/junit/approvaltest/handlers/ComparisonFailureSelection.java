package org.herba.plugin.junit.approvaltest.handlers;

import java.lang.reflect.Method;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElement.FailureTrace;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;
import org.herba.plugin.junit.approvaltest.models.FailureWithFilePathDto;

/**
 * Adapter class that wraps a test element and checks if it contains a
 * ComparisonFailure. Used for command enablement in the Eclipse UI.
 */
public class ComparisonFailureSelection extends FailureWithFilePathSelection {

//    private static final Logger logger = Logger.getLogger(ComparisonFailureSelection.class.getName());

    private ComparisonFailureDto comparisonFailure;

    public ComparisonFailureSelection(ITestElement testElement) {
        super(testElement);
        comparisonFailure = extractComparisonFailure(testElement);
    }

    /**
     * Extracts the ComparisonFailure from the test element's failure trace
     */
    protected ComparisonFailureDto extractComparisonFailure(ITestElement element) {
        if (element == null) {
            return null;
        }
        ComparisonFailureDto failure = null;
        FailureWithFilePathDto pathInfo = super.extractFailureInfo(element);
        if (pathInfo instanceof ComparisonFailureDto) {
            failure = (ComparisonFailureDto) pathInfo;
        } else if (pathInfo != null && isComparisonFailure(element)) {
            FailureTrace failureTrace = element.getFailureTrace();
            failure = new ComparisonFailureDto(pathInfo.getMessage(), failureTrace.getActual(),
                    failureTrace.getExpected(), pathInfo.getFilePath());
            failure.setFileNameOnly(pathInfo.getFileNameOnly());
        }

        return failure;
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

    @Override
    public ComparisonFailureDto getFailureInfo() {
        return comparisonFailure;
    }

    /**
     * Convenience method to check if this selection contains a ComparisonFailure
     */
    @Override
    public boolean hasFailureInfo() {
        return comparisonFailure != null;
    }

}
