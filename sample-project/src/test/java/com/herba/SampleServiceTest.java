package com.herba;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.Junit4Reporter;
import org.approvaltests.reporters.QuietReporter;
import org.junit.Test;

import testutils.CustomJunit4Reporter;
import testutils.TestUtils;

public class SampleServiceTest {

	private SampleService underTest = new SampleService();

	@Test
	public void testDoSomething() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		assertThat(actual).isEqualTo("TestExpected");
	}

	@Test
	public void testDoSomethingWithFileReference() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		assertThat(actual).withFailMessage("There's some problem with samples/SampleService_doSomething.txt")
				.isEqualTo("TestExpected");
	}

	@Test
	public void testDoSomething_assertEquals_relativePathUnderTestResources() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
//		assertThat(TestUtils.readTestFile("samples/SampleService_doSomething.txt"))
//				.withFailMessage("Actual reult does not match content of samples/SampleService_doSomething.txt")
//				.isEqualTo(actual);
		assertEquals("Actual result does not match content of samples/SampleService_doSomething.txt",
				TestUtils.readTestFile("samples/SampleService_doSomething.txt"), actual);
	}

	@Test
	public void testDoSomething_assertEquals_relativePath() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		assertEquals("Actual result does not match content of src/test/resources/samples/SampleService_doSomething.txt",
				TestUtils.readTestFile("samples/SampleService_doSomething.txt"), actual);
//		assertThat(TestUtils.readTestFile("samples/SampleService_doSomething.txt")).withFailMessage(
//				"Actual reult does not match content of src/test/resources/samples/SampleService_doSomething.txt")
//				.isEqualTo(actual);
	}

	@Test
	public void testDoSomething_approvalTest_withJunitReporter() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		// QuietReporter, ReportNothing, AutoApproveWhenEmptyReporter,
		// InlineJavaReporter
		Approvals.verify(actual, new Options(new Junit4Reporter()));
		// default one returns after opening diff (same for
		// AutoApproveWhenEmptyReporter)
		// java.lang.Error: Failed Approval
		// Approved:C:\dev\workspace\sample-project\src\test\java\com\herba\SampleServiceTest.testDoSomething_approvalTest.approved.txt
		// Received:C:\dev\workspace\sample-project\src\test\java\com\herba\SampleServiceTest.testDoSomething_approvalTest.received.txt
		// ReportNothing: just returns the Error above
		// JunitReporter: there is no information available of the file
		// could we use the approvaltests framework to determine what that file would
		// be?
		// we could check the stacktrace if it contains Approvals.verify call!
		// org.junit.ComparisonFailure: expected:<"[TestExpected]"> but was:<"[OK]">
	}

	@Test
	public void testDoSomething_approvalTest_withJunitReporter_differentFileName() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		// QuietReporter, ReportNothing, AutoApproveWhenEmptyReporter,
		// InlineJavaReporter
		Approvals.verify(actual,
				Approvals.NAMES.withParameters("first").and(func -> new Options(new Junit4Reporter())));
	}

	@Test
	public void testDoSomething_approvalTest_default() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		// QuietReporter, ReportNothing, AutoApproveWhenEmptyReporter,
		// InlineJavaReporter
		Approvals.verify(actual, new Options(QuietReporter.INSTANCE));
	}

	@Test
	public void testDoSomething_approvalTest_customReporter() throws Exception {
		// given
		// when
		String actual = underTest.doSomething();
		// then
		// QuietReporter, ReportNothing, AutoApproveWhenEmptyReporter,
		// InlineJavaReporter
		Approvals.verify(actual, new Options(CustomJunit4Reporter.INSTANCE));
		// default one returns after opening diff (same for
		// AutoApproveWhenEmptyReporter)
		// java.lang.Error: Failed Approval
		// Approved:C:\dev\workspace\sample-project\src\test\java\com\herba\SampleServiceTest.testDoSomething_approvalTest.approved.txt
		// Received:C:\dev\workspace\sample-project\src\test\java\com\herba\SampleServiceTest.testDoSomething_approvalTest.received.txt
		// ReportNothing: just returns the Error above
		// JunitReporter
		// org.junit.ComparisonFailure: expected:<"[TestExpected]"> but was:<"[OK]">

	}

}