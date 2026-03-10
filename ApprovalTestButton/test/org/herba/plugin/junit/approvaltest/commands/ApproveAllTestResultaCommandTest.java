package org.herba.plugin.junit.approvaltest.commands;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.eclipse.jdt.junit.model.ITestElement.FailureTrace;
import org.herba.plugin.junit.approvaltest.mocks.MockTestCaseElement;
import org.herba.plugin.junit.approvaltest.mocks.MockTestElementContainer;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;
import org.junit.Test;

import com.spun.util.io.FileUtils;

import testutils.TestUtils;

public class ApproveAllTestResultaCommandTest {

    boolean confirmationAnswer = true;
    List<File> listCapture;
    String errorMessageCapture;
    Throwable errorExceptionCapture;
    MockTestCaseElement firstTestCase;
    MockTestElementContainer testContainer;
    File approvalTestApproved;

    private ApproveAllTestResultsCommand underTest = new ApproveAllTestResultsCommand() {
        @Override
        protected boolean confirmOverwrite(List<File> files) {
            listCapture = files;
            return confirmationAnswer;
        }

        @Override
        protected void showError(ComparisonFailureDto failureInfo, String message, Throwable exc) {
            errorMessageCapture = message;
            errorExceptionCapture = exc;
        }
    };

    @Test
    public void testHandleSelection_shouldHandleTestCasesWithFileComparisonResult() throws Exception {
        // given
        initElements();
        // when
        boolean actual = underTest.handleSelection(firstTestCase);
        // then
        assertThat(actual).isTrue();
        File testFile = new File("test-resources/result/sample.txt");
        assertThat(testFile).canRead();
        String content = TestUtils.readTestFile("result/sample.txt");
        testFile.delete();
        assertThat(approvalTestApproved).canRead();
        String approvedContent = TestUtils.readTestFile("approvaltest/SampleServiceTest.testCase.approved.txt");
        approvalTestApproved.delete();
        assertThat(content).isEqualTo("actual value");
        assertThat(approvedContent).isEqualTo("NEW");
        assertThat(listCapture).hasSize(2);
    }

    @Test
    public void testHandleSelection_shouldHandleTestContainersFailureAsWell() throws Exception {
        // given
        firstTestCase = new MockTestCaseElement(new RuntimeException("something failed"));
        testContainer = new MockTestElementContainer(firstTestCase,
                new MockTestCaseElement("something failed", "0", "1"),
                new MockTestCaseElement("test result does not match test-resources/result/sample.txt",
                        "expected value", "actual value"));
        testContainer.setComparisonFailure(true);
        testContainer.setFailureTrace(
                new FailureTrace("error message for test-resources/result/tcsample.txt", "tc expected", "tc actual"));
        // when
        boolean actual = underTest.handleSelection(firstTestCase);
        // then
        assertThat(actual).isTrue();
        File testFile = new File("test-resources/result/sample.txt");
        assertThat(testFile).canRead();
        testFile.delete();
        File tcFile = new File("test-resources/result/tcsample.txt");
        assertThat(tcFile).canRead();
        String content = TestUtils.readTestFile("result/tcsample.txt");
        tcFile.delete();
        assertThat(content).isEqualTo("tc actual");
        assertThat(listCapture).hasSize(2);
    }

    @Test
    public void testHandleSelection_shouldHandleTestCasesInContainerWithFileComparisonResult() throws Exception {
        // given
        initElements();
        // when
        boolean actual = underTest.handleSelection(testContainer);
        // then
        assertThat(actual).isTrue();
        File testFile = new File("test-resources/result/sample.txt");
        assertThat(testFile).canRead();
        String content = TestUtils.readTestFile("result/sample.txt");
        testFile.delete();
        assertThat(approvalTestApproved).canRead();
        String approvedContent = TestUtils.readTestFile("approvaltest/SampleServiceTest.testCase.approved.txt");
        approvalTestApproved.delete();
        assertThat(content).isEqualTo("actual value");
        assertThat(approvedContent).isEqualTo("NEW");
        assertThat(listCapture).hasSize(2);
    }

    @Test
    public void testHandleSelection_notConfirmed_shouldNotOverwriteFiles() throws Exception {
        // given
        initElements();
        File testFile = new File("test-resources/result/sample.txt");
        testFile.setWritable(true);
        FileUtils.writeFile(testFile, "original content");
        testFile.setWritable(false);
        // when
        boolean actual = underTest.handleSelection(testContainer);
        // then
        assertThat(errorMessageCapture)
                .startsWith("Failed to overwrite test resource");
        assertThat(errorExceptionCapture).isNotNull();
        assertThat(actual).isFalse();
        assertThat(testFile).exists()
                .hasContent("original content");
        testFile.setWritable(true);
        testFile.delete();
        assertThat(approvalTestApproved).doesNotExist();
        assertThat(listCapture).hasSize(2);
    }

    @Test
    public void testHandleSelection_shouldStopAtFirstFailure() throws Exception {
        // given
        initElements();
        confirmationAnswer = false;
        // when
        boolean actual = underTest.handleSelection(testContainer);
        // then
        assertThat(actual).isFalse();
        File testFile = new File("test-resources/result/sample.txt");
        assertThat(testFile).doesNotExist();
        assertThat(approvalTestApproved).doesNotExist();
        assertThat(listCapture).hasSize(2);
    }

    private void initElements() {
        firstTestCase = new MockTestCaseElement(new RuntimeException("something failed"));
        File approvalTestReceived = new File("test-resources/approvaltest", "SampleServiceTest.testCase.received.txt");
        approvalTestApproved = new File("test-resources/approvaltest", "SampleServiceTest.testCase.approved.txt");
        MockTestCaseElement approvalTestElement = new MockTestCaseElement(new AssertionError("Failed Approval\n"
                + "Approved:" + approvalTestApproved.getAbsolutePath()
                + "\nReceived:" + approvalTestReceived.getAbsolutePath()));
        testContainer = new MockTestElementContainer(firstTestCase,
                new MockTestCaseElement("something failed", "0", "1"),
                new MockTestCaseElement("test result does not match test-resources/result/sample.txt",
                        "expected value", "actual value"),
                approvalTestElement);
    }

}