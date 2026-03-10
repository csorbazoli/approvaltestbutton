package org.herba.plugin.junit.approvaltest.mocks;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jdt.junit.model.ITestRunSession;

public class MockTestElementContainer extends MockTestElement implements ITestElementContainer {

    private final List<ITestElement> children = new LinkedList<ITestElement>();
    private ITestRunSession testRunSession;

    public MockTestElementContainer(MockTestElement... elements) {
        super();
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
    public ITestRunSession getTestRunSession() {
        if (testRunSession == null) {
            testRunSession = new MockTestRunSession();
        }
        return testRunSession;
    }

    @Override
    public ITestElement[] getChildren() {
        return children.toArray(new ITestElement[0]);
    }

}
