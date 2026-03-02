package org.herba.plugin.junit.approvaltest.mocks;

import org.eclipse.jdt.junit.model.ITestCaseElement;

public class MockTestCaseElement extends MockTestElement implements ITestCaseElement {

    public MockTestCaseElement(String message, boolean comparisonFailure, String expected, String actual) {
        super(message, comparisonFailure, expected, actual);
    }

    @Override
    public String getTestClassName() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getTestMethodName() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
