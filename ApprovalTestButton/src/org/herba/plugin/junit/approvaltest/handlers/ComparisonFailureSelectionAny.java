package org.herba.plugin.junit.approvaltest.handlers;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

/**
 * Adapter class that wraps a test element and checks if it contains a
 * ComparisonFailure. Used for command enablement in the Eclipse UI.
 */
public class ComparisonFailureSelectionAny extends ComparisonFailureSelection {

    // private static final Logger logger =
    // Logger.getLogger(ComparisonFailureSelectionAny.class.getName());

    public ComparisonFailureSelectionAny(ITestElement testElement) {
        super(testElement);
    }

    @Override
    protected ComparisonFailureDto extractComparisonFailure(ITestElement element) {
        ITestElementContainer testContainer = null;
        if (element instanceof ITestElementContainer) {
            testContainer = (ITestElementContainer) element;
        } else if (element instanceof ITestElement) {
            testContainer = element.getParentContainer();
        }
        if (testContainer != null) {
            return findFirstElementWithComparisonFailure(testContainer);
        }
        return super.extractComparisonFailure(element);
    }

    private ComparisonFailureDto findFirstElementWithComparisonFailure(ITestElementContainer testContainer) {
        for (ITestElement element : testContainer.getChildren()) {
            ComparisonFailureDto failure = super.extractComparisonFailure(element);
            if (failure != null) {
                setTestElement(element);
                return failure;
            }
        }
        return null;
    }
}
