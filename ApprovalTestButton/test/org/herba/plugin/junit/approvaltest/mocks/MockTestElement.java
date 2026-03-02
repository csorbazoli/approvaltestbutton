package org.herba.plugin.junit.approvaltest.mocks;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;

public class MockTestElement implements ITestElement {

    private final FailureTrace failureTrace;
    private boolean comparisonFailure;

    public MockTestElement(String message, String expected, String actual) {
        this(message, message.contains("but was:"), expected, actual);
    }

    public MockTestElement(String message, boolean comparisonFailure, String expected, String actual) {
        StringWriter stringWriter = new StringWriter();
        new Exception("SACKTRACE INFO").printStackTrace(new PrintWriter(stringWriter));
        failureTrace = new FailureTrace(message + "\n" + stringWriter.toString(), expected, actual);
        this.comparisonFailure = comparisonFailure;
    }

    // this method is implemented in the internal class:
    // org.eclipse.jdt.internal.junit.model.TestElement
    public boolean isComparisonFailure() {
        return comparisonFailure;
    }

    @Override
    public double getElapsedTimeInSeconds() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public FailureTrace getFailureTrace() {
        return failureTrace;
    }

    @Override
    public ITestElementContainer getParentContainer() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ProgressState getProgressState() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Result getTestResult(boolean arg0) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ITestRunSession getTestRunSession() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
