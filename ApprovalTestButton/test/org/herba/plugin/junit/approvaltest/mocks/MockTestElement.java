package org.herba.plugin.junit.approvaltest.mocks;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.junit.ComparisonFailure;

public class MockTestElement implements ITestElement {

    private final FailureTrace failureTrace;
    private boolean comparisonFailure;
    private MockTestElementContainer parentContainer;

    public MockTestElement(Throwable error) {
        StringWriter stringWriter = new StringWriter();
        error.printStackTrace(new PrintWriter(stringWriter));
        failureTrace = new FailureTrace(stringWriter.toString(), null, null);
        this.comparisonFailure = false;
    }

    public MockTestElement(String message, String expected, String actual) {
        StringWriter stringWriter = new StringWriter();
        new ComparisonFailure(message, expected, actual).printStackTrace(new PrintWriter(stringWriter));
        failureTrace = new FailureTrace(stringWriter.toString(), expected, actual);
        this.comparisonFailure = true;
    }

    // this method is implemented in the internal class:
    // org.eclipse.jdt.internal.junit.model.TestElement
    public boolean isComparisonFailure() {
        return comparisonFailure;
    }

    public void setParentContainer(MockTestElementContainer parentContainer) {
        this.parentContainer = parentContainer;
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
        return parentContainer;
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
