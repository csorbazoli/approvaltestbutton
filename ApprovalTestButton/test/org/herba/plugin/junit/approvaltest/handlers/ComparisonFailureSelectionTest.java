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
    public void testGetComparisonFailure() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement("error message", true, "expected value", "actual value");
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(underTest.hasComparisonFailure()).isTrue();
        TestUtils.assertTestFileEquals("selection/ComparisonFailureDto.json", actual);
        assertThat(underTest.getTestElement()).isEqualTo(testElement);
    }

    @Test
    public void testGetComparisonFailure_shouldReturnNullIfNotComparisonFailure() throws Exception {
        // given
        ITestElement testElement = new MockTestCaseElement("error message", false, "expected value", "actual value");
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
        ITestElement testElement = new MockTestCaseElement("java.lang.Error: Failed Approval\n"
                + "                // Approved:" + approvalTestApproved.getAbsolutePath()
                + "                // Received:" + approvalTestReceived.getAbsolutePath(), false, null, null);
        underTest = new ComparisonFailureSelection(testElement);
        // when
        ComparisonFailureDto actual = underTest.getComparisonFailure();
        // then
        assertThat(underTest.hasComparisonFailure()).isTrue();
        TestUtils.assertTestFileEquals("selection/ComparisonFailureDto_fromApprovalError.json", actual);
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