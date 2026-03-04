package com.herba.plugin.junit.approvaltest;

import org.herba.plugin.junit.approvaltest.mocks.MockTestCaseElement;

public class TestHelper {

    private TestHelper() {
        // private constructor
    }

    public static MockTestCaseElement createTestElementWithPathInMessage() {
        return new MockTestCaseElement(
                "error message for c:/myproject/src/test/resources/test.txt",
                "expected value", "actual value");
    }

    public static MockTestCaseElement createTestElementWithoutPath() {
        return new MockTestCaseElement("error message", "expected value",
                "actual value");
    }

}
