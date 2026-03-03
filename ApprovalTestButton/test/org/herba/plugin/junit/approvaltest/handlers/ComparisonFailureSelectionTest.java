package org.herba.plugin.junit.approvaltest.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.mocks.MockTestCaseElement;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;
import org.junit.Test;

import testutils.TestUtils;

public class ComparisonFailureSelectionTest {

    private ComparisonFailureSelection underTest;

    @Test
    public void testGetComparisonFailure_no_file_path_in_message() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement("error message", "expected value", "actual value");
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(actual).isNull();
        assertThat(underTest.hasComparisonFailure()).isFalse();
    }

    @Test
    public void testGetComparisonFailure_with_path() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement("error message for c:/myproject/src/test/resources/test.txt",
                "expected value", "actual value");
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(underTest.hasComparisonFailure()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("c:/myproject/src/test/resources/test.txt");
        TestUtils.assertTestFileEquals("selection/ComparisonFailureDto.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_uncommon_chars() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement(
                "error message for c:/myproject/src/test-resources/abc-123_{}+()=[],@~#;%$!.txt",
                "expected value", "actual value");
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(underTest.hasComparisonFailure()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("c:/myproject/src/test-resources/abc-123_{}+()=[],@~#;%$!.txt");
    }

    @Test
    public void testGetComparisonFailure_shouldReturnNullIfNotComparisonFailure() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement(new RuntimeException("error message"));
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(actual).isNull();
        assertThat(underTest.hasComparisonFailure()).isFalse();
    }

    @Test
    public void testGetComparisonFailure_shouldReturnTrueIfApprovalTestError() throws Exception {
        // given
        File approvalTestReceived = new File("test-resources/approvaltest", "SampleServiceTest.testCase.received.txt");
        File approvalTestApproved = new File("test-resources/approvaltest", "SampleServiceTest.testCase.approved.txt");
        ITestElement testElement = new MockTestCaseElement(new AssertionError("Failed Approval\r\n"
                + "Approved:" + approvalTestApproved.getAbsolutePath() + "\r\n"
                + "Received:" + approvalTestReceived.getAbsolutePath()));
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(underTest.hasComparisonFailure()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getAbsolutePath()).isEqualTo(approvalTestApproved.getAbsolutePath());
        TestUtils.assertTestFileEquals("selection/ComparisonFailureDto_fromApprovalError.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_shouldReturnTrueIfComparisonFailureWithApprovalErrorMessage()
            throws Exception {
        // given
        File approvalTestReceived = new File("test-resources/approvaltest", "SampleServiceTest.testCase.received.txt");
        File approvalTestApproved = new File("test-resources/approvaltest", "SampleServiceTest.testCase.approved.txt");
        ITestElement testElement = new MockTestCaseElement(
                "Failed Approval\r\n"
                        + "Approved:" + approvalTestApproved.getAbsolutePath() + "\r\n"
                        + "Received:" + approvalTestReceived.getAbsolutePath(),
                "expected text",
                "actual text");
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(underTest.hasComparisonFailure()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getAbsolutePath()).isEqualTo(approvalTestApproved.getAbsolutePath());
        TestUtils.assertTestFileEquals("selection/ComparisonFailureDto_fromApprovalMessage.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_shouldReturnNullIfNotTest() throws Exception {
        // given
        underTest = new ComparisonFailureSelection(null);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(actual).isNull();
        assertThat(underTest.hasComparisonFailure()).isFalse();
        assertThat(underTest.getTestElement()).isNull();
    }

}