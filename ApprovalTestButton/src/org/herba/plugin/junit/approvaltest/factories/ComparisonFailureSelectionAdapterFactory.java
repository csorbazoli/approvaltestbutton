package org.herba.plugin.junit.approvaltest.factories;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelection;

public class ComparisonFailureSelectionAdapterFactory implements IAdapterFactory {

    @Override
    public <T> T getAdapter(Object adaptable, Class<T> adapterType) {
        if ((adapterType == ComparisonFailureSelection.class) && (adaptable instanceof ITestElement)) {
            ITestElement testElement = (ITestElement) adaptable;
            ComparisonFailureSelection selection = new ComparisonFailureSelection(testElement);

            // Only return the adapter if it actually contains a ComparisonFailure
            if (selection.hasComparisonFailure()) {
                return adapterType.cast(selection);
            }
        }
        return null;
    }

    @Override
    public Class<?>[] getAdapterList() {
        return new Class<?>[] { ComparisonFailureSelection.class };
    }

}
