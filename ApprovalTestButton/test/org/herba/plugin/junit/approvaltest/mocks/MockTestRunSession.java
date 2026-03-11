package org.herba.plugin.junit.approvaltest.mocks;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;

public class MockTestRunSession implements ITestRunSession {

    private IJavaProject javaProject;

    @Override
    public IJavaProject getLaunchedProject() {
        if (javaProject == null) {
            javaProject = new MockJavaProject();
        }
        return javaProject;
    }

    @Override
    public ITestElement[] getChildren() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public double getElapsedTimeInSeconds() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public FailureTrace getFailureTrace() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
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

    @Override
    public String getTestRunName() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
