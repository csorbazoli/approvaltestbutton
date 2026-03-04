package org.herba.plugin.junit.approvaltest.mocks;

import org.eclipse.core.runtime.IProgressMonitor;

public class MockProgressMonitor implements IProgressMonitor {

    @Override
    public void beginTask(String name, int totalWork) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void done() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void internalWorked(double work) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isCanceled() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setCanceled(boolean value) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setTaskName(String name) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void subTask(String name) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void worked(int work) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
