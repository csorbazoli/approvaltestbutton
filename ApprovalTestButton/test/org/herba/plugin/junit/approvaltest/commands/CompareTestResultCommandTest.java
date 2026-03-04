package org.herba.plugin.junit.approvaltest.commands;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.mocks.MockTestCaseElement;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;
import org.junit.Test;

public class CompareTestResultCommandTest {

    private ComparisonFailureDto captureFailureInfo;
    private CompareTestResultCommand underTest = new CompareTestResultCommand() {
        @Override
        protected boolean openComparisonView(ComparisonFailureDto failureInfo) {
            captureFailureInfo = failureInfo;
            return true;
        }
    };

    @Test
    public void testHandleSelection_notTestElement() throws Exception {
        // given
        Object selectedElement = "not a test";
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isFalse();
        assertThat(captureFailureInfo).isNull();
    }

    @Test
    public void testHandleSelection_testElementWithComparisonInfoWithoutFilePath() throws Exception {
        // given
        Object selectedElement = new MockTestCaseElement("something failed", "0", "1");
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isFalse();
        assertThat(captureFailureInfo).isNull();
    }

    @Test
    public void testHandleSelection_testElementWithComparisonInfoWithSingleFilePath() throws Exception {
        // given
        Object selectedElement = new MockTestCaseElement("test result does not match test-resources/result/sample.txt",
                "0", "1");
        // when
        boolean actual = underTest.handleSelection(selectedElement);
        // then
        assertThat(actual).isTrue();
        assertThat(captureFailureInfo.getActual()).isEqualTo("1");
        assertThat(captureFailureInfo.getFilePath()).isEqualTo(new File("test-resources/result/sample.txt"));
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
        assertThat(captureFailureInfo.getActual()).isEqualTo("NEW\n");
        assertThat(captureFailureInfo.getFilePath().getAbsolutePath())
                .isEqualTo(approvalTestApproved.getAbsolutePath());
    }

}