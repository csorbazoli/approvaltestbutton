package org.herba.plugin.junit.approvaltest.mocks;

import org.eclipse.jdt.junit.model.ITestCaseElement;

public class MockTestCaseElement extends MockTestElement implements ITestCaseElement {

    public MockTestCaseElement(Throwable error) {
        super(error);
    }

    public MockTestCaseElement(String message, String expected, String actual) {
        super(message, expected, actual);
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
