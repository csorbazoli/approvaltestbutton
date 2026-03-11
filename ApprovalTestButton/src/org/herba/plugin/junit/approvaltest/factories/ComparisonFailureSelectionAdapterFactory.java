package org.herba.plugin.junit.approvaltest.factories;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelection;
import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelectionAny;
import org.herba.plugin.junit.approvaltest.handlers.FailureWithFilePathSelection;

public class ComparisonFailureSelectionAdapterFactory implements IAdapterFactory {

    @Override
    public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
        if (adaptable instanceof ITestElement) {
            ITestElement testElement = (ITestElement) adaptable;
            FailureWithFilePathSelection selection = null;
            if (adapterType == ComparisonFailureSelection.class) {
                selection = new ComparisonFailureSelection(testElement);
            } else if (adapterType == ComparisonFailureSelectionAny.class) {
                selection = new ComparisonFailureSelectionAny(testElement);
            } else if (adapterType == FailureWithFilePathSelection.class) {
                selection = new FailureWithFilePathSelection(testElement);
            }
            // Only return the adapter if it actually contains a ComparisonFailure
            if (selection != null && selection.hasFailureInfo()) {
                return adapterType.cast(selection);
            }
        }
        return null;
    }

    @Override
    public Class<?>[] getAdapterList() {
        return new Class<?>[] { ComparisonFailureSelection.class, ComparisonFailureSelectionAny.class,
                FailureWithFilePathSelection.class };
    }

}
