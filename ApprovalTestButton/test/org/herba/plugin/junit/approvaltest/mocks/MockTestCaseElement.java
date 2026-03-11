package org.herba.plugin.junit.approvaltest.mocks;

import org.eclipse.jdt.junit.model.ITestCaseElement;

public class MockTestCaseElement extends MockTestElement implements ITestCaseElement {

    private String testClassName = "foo.bar.SomeTest";
    private String testMethodName = "testSomething_shouldDo";

    public MockTestCaseElement(Throwable error) {
        super(error);
    }

    public MockTestCaseElement(String message, String expected, String actual) {
        super(message, expected, actual);
    }

    @Override
    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    @Override
    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

}
