package org.herba.plugin.junit.approvaltest.mocks;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;

public class MockTestElementContainer implements ITestElementContainer {

    private final List<ITestElement> children = new LinkedList<ITestElement>();

    public MockTestElementContainer(MockTestElement... elements) {
        for (MockTestElement element : elements) {
            addChild(element);
            element.setParentContainer(this);
        }
    }

    public MockTestElementContainer addChild(ITestElement child) {
        this.children.add(child);
        return this;
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
    public ITestElement[] getChildren() {
        return children.toArray(new ITestElement[0]);
    }

}
