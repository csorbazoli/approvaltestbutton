package org.herba.plugin.junit.approvaltest.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.jdt.junit.model.ITestElement;
import org.herba.plugin.junit.approvaltest.mocks.MockLocation;
import org.herba.plugin.junit.approvaltest.mocks.MockTestCaseElement;
import org.herba.plugin.junit.approvaltest.mocks.MockTestElement;
import org.herba.plugin.junit.approvaltest.mocks.MockTestElementContainer;
import org.herba.plugin.junit.approvaltest.models.FailureWithFilePathDto;
import org.herba.plugin.junit.approvaltest.utils.MockFileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testutils.TestUtils;

public class FailureWithFilePathSelectionTest {

    private FailureWithFilePathSelection underTest;

    @Before
    public void setupTest() throws Exception {
        MockFileUtils.initMock();
    }

    @After
    public void tearDown() {
        MockLocation.resetBasePath();
    }

    @Test
    public void testGetComparisonFailure_no_file_path_in_message() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement("error message", "expected value", "actual value");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(actual).isNull();
        assertThat(underTest.hasFailureInfo()).isFalse();
    }

    @Test
    public void testGetComparisonFailure_no_file_path_in_message_but_approvalVerifyWasUsed() throws Exception {
        // given
        File approvalTestApproved = assertApprovalTestFileExists("SomeTest.testSomething_shouldDo.approved.txt");
        MockTestCaseElement testElement = new MockTestCaseElement(
                TestUtils.readTestFile("selection/failureTraceWithApprovalsVerify.txt"), "expected value",
                "actual value");
        new MockTestElementContainer(testElement);
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(actual).isNotNull();
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getAbsolutePath())
                .isEqualTo(approvalTestApproved.getAbsolutePath());
        actual.setFilePath(new File("c:/test/dummy.file"));
        TestUtils.assertTestFileEquals("selection/FailureWithFilePathDto_derivedFromApprovalFailure.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_no_file_path_in_message_but_approvalVerifyWasUsed_fileDoesNotExistYet()
            throws Exception {
        // given
        assertApprovalTestFileExists("SampleServiceTest.testCase.received.txt");
        File approvalTestApproved = assertApprovalTestFileDoesNotExist("SampleServiceTest.testCase.approved.txt");
        MockTestCaseElement testElement = new MockTestCaseElement(
                TestUtils.readTestFile("selection/failureTraceWithApprovalsVerify.txt"), "expected value",
                "actual value");
        testElement.setTestClassName("approvaltest.SampleServiceTest");
        testElement.setTestMethodName("testCase");
        new MockTestElementContainer(testElement);
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(actual).isNotNull();
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getAbsolutePath())
                .isEqualTo(approvalTestApproved.getAbsolutePath());
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_no_path_approvalVerify_noExactMatchForReceived_dontLookForApproved()
            throws Exception {
        // given
        assertApprovalTestFileExists("SampleServiceTest.parameterizedTestCase.first.received.txt");
        assertApprovalTestFileExists("SampleServiceTest.parameterizedTestCase.second.received.txt");
        assertApprovalTestFileExists("SampleServiceTest.parameterizedTestCase.first.approved.txt");
        assertApprovalTestFileDoesNotExist(
                "SampleServiceTest.parameterizedTestCase.second.approved.txt");
        MockTestCaseElement testElement = new MockTestCaseElement(
                TestUtils.readTestFile("selection/failureTraceWithApprovalsVerify.txt"), "expected value",
                "actual value");
        testElement.setTestClassName("approvaltest.SampleServiceTest");
        testElement.setTestMethodName("parameterizedTestCase");
        new MockTestElementContainer(testElement);
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(actual).isNotNull();
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNull();
        assertThat(actual.getFileNameOnly()).isEqualTo("SampleServiceTest.parameterizedTestCase*.approved");
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_with_path() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement("error message for c:/myproject/src/test/resources/test.txt",
                "expected value", "actual value");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("c:/myproject/src/test/resources/test.txt");
        TestUtils.assertTestFileEquals("selection/FailureWithFilePathDto.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_with_relativePath_fallbackIfProjectDetectionFails() throws Exception {
        // given
        MockTestElement testElement = new MockTestCaseElement("error message for src/test/resources/test.txt",
                "expected value", "actual value");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("src/test/resources/test.txt");
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_with_relativePath_shouldResolveFromTestElement() throws Exception {
        // given
        MockTestElement testElement = new MockTestCaseElement("error message for src/test/resources/test.txt",
                "expected value", "actual value");
        new MockTestElementContainer(testElement);
        MockLocation.setBasePath("c:/myproject");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("c:/myproject/src/test/resources/test.txt");
        actual.setFilePath(new File("c:/test/dummy.file"));
        TestUtils.assertTestFileEquals("selection/FailureWithFilePathDto_relative.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_with_relativePathUnderTestResources_shouldResolveFromTestElement()
            throws Exception {
        // given
        MockTestElement testElement = new MockTestCaseElement("error message for com/herba/someService/test.txt",
                "expected value", "actual value");
        new MockTestElementContainer(testElement);
        MockLocation.setBasePath("c:/myproject");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("c:/myproject/src/test/resources/com/herba/someService/test.txt");
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_uncommon_chars() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement(
                "error message for c:/myproject/src/test-resources/abc-123_{}+()=[],@~#;%$!.txt",
                "expected value", "actual value");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getPath().replace('\\', '/'))
                .isEqualTo("c:/myproject/src/test-resources/abc-123_{}+()=[],@~#;%$!.txt");
    }

    @Test
    public void testGetComparisonFailure_shouldReturnNullIfNotComparisonFailure() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement(new RuntimeException("error message"));
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(actual).isNull();
        assertThat(underTest.hasFailureInfo()).isFalse();
    }

    @Test
    public void testGetComparisonFailure_shouldReturnTrueIfApprovalTestError() throws Exception {
        // given
        File approvalTestReceived = assertApprovalTestFileExists("SampleServiceTest.testCase.received.txt");
        File approvalTestApproved = assertApprovalTestFileDoesNotExist("SampleServiceTest.testCase.approved.txt");
        ITestElement testElement = new MockTestCaseElement(new AssertionError("Failed Approval\r\n"
                + "Approved:" + approvalTestApproved.getAbsolutePath() + "\r\n"
                + "Received:" + approvalTestReceived.getAbsolutePath()));
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getAbsolutePath()).isEqualTo(approvalTestApproved.getAbsolutePath());
        actual.setFilePath(new File("c:/test/dummy.file"));
        TestUtils.assertTestFileEquals("selection/FailureWithFilePathDto_fromApprovalError.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_shouldReturnTrueIfComparisonFailureWithApprovalErrorMessage()
            throws Exception {
        // given
        File approvalTestReceived = assertApprovalTestFileExists("SampleServiceTest.testCase.received.txt");
        File approvalTestApproved = assertApprovalTestFileDoesNotExist("SampleServiceTest.testCase.approved.txt");
        ITestElement testElement = new MockTestCaseElement(
                "Failed Approval\r\n"
                        + "Approved:" + approvalTestApproved.getAbsolutePath() + "\r\n"
                        + "Received:" + approvalTestReceived.getAbsolutePath(),
                "expected text",
                "actual text");
        underTest = new FailureWithFilePathSelection(testElement);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(underTest.hasFailureInfo()).isTrue();
        assertThat(actual.getFilePath()).isNotNull();
        assertThat(actual.getFilePath().getAbsolutePath()).isEqualTo(approvalTestApproved.getAbsolutePath());
        actual.setFilePath(new File("c:/test/dummy.file"));
        TestUtils.assertTestFileEquals("selection/FailureWithFilePathDto_fromApprovalMessage.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_shouldReturnNullIfNotTest() throws Exception {
        // given
        underTest = new FailureWithFilePathSelection(null);
        // when
        FailureWithFilePathDto actual = underTest.getFailureInfo();
        // then
        assertThat(actual).isNull();
        assertThat(underTest.hasFailureInfo()).isFalse();
        assertThat(underTest.getTestElement()).isNull();
    }

    private File assertApprovalTestFileExists(String fileName) {
        File approvalTestFile = new File("test-resources/approvaltest", fileName);
        assertThat(approvalTestFile).canRead();
        return approvalTestFile;
    }

    private File assertApprovalTestFileDoesNotExist(String fileName) {
        File approvalTestFile = new File("test-resources/approvaltest", fileName);
        assertThat(approvalTestFile).doesNotExist();
        return approvalTestFile;
    }

}