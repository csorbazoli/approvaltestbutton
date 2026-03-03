package org.herba.plugin.junit.approvaltest.commands;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.mocks.MockTestCaseElement;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;
import org.junit.Test;

import testutils.TestUtils;

public class ApproveTestResultCommandTest {

    private boolean confirmationAnswer = true;
    private ApproveTestResultCommand underTest = new ApproveTestResultCommand() {
        @Override
        protected boolean confirmOverwrite(ComparisonFailureDto element) {
            return confirmationAnswer;
        };
    };

    @Test
    public void testHandleSelection_notTestElement() throws Exception {
        // given
        Object selectedElement = "not a test";
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testHandleSelection_notTestElementWithComparisonInfo() throws Exception {
        // given
        Object selectedElement = new MockTestCaseElement(new RuntimeException("something failed"));
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testHandleSelection_testElementWithComparisonInfoWithoutFilePath() throws Exception {
        // given
        Object selectedElement = new MockTestCaseElement("something failed", "0", "1");
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void testHandleSelection_testElementWithComparisonInfoWithRelativeFilePath() throws Exception {
        // given
        Object selectedElement = new MockTestCaseElement("test result does not match test-resources/result/sample.txt",
                "0", "1");
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isTrue();
        File testFile = new File("test-resources/result/sample.txt");
        assertThat(testFile).canRead();
        String content = TestUtils.readTestFile("result/sample.txt");
        testFile.delete();
        assertThat(content).isEqualTo("1");
    }

    @Test
    public void testHandleSelection_testElementWithApprovalError() throws Exception {
        // given
        File approvalTestReceived = new File("test-resources/approvaltest", "SampleServiceTest.testCase.received.txt");
        File approvalTestApproved = new File("test-resources/approvaltest", "SampleServiceTest.testCase.approved.txt");
        ITestElement selectedElement = new MockTestCaseElement(new AssertionError("Failed Approval\n"
                + "Approved:" + approvalTestApproved.getAbsolutePath()
                + "\nReceived:" + approvalTestReceived.getAbsolutePath()));
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isTrue();
        assertThat(approvalTestApproved).canRead();
        String content = TestUtils.readTestFile("approvaltest/SampleServiceTest.testCase.approved.txt");
        approvalTestApproved.delete();
        assertThat(content).isEqualTo("NEW");
    }

}