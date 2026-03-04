package org.herba.plugin.junit.approvaltest.mocks;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class MockBundleContext implements BundleContext {

    private final Bundle bundle = new MockBundle();

    @Override
    public String getProperty(String key) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public Bundle installBundle(String location, InputStream input) throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Bundle installBundle(String location) throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Bundle getBundle(long id) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Bundle[] getBundles() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void addServiceListener(ServiceListener listener, String filter) throws InvalidSyntaxException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void addServiceListener(ServiceListener listener) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void removeServiceListener(ServiceListener listener) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void addBundleListener(BundleListener listener) {
        // do nothing
    }

    @Override
    public void removeBundleListener(BundleListener listener) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void addFrameworkListener(FrameworkListener listener) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void removeFrameworkListener(FrameworkListener listener) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceRegistration<?> registerService(String[] clazzes, Object service, Dictionary<String, ?> properties) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceRegistration<?> registerService(String clazz, Object service, Dictionary<String, ?> properties) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <S> ServiceRegistration<S> registerService(Class<S> clazz, S service, Dictionary<String, ?> properties) {
        return null;
    }

    @Override
    public <S> ServiceRegistration<S> registerService(Class<S> clazz, ServiceFactory<S> factory,
            Dictionary<String, ?> properties) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceReference<?>[] getServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceReference<?>[] getAllServiceReferences(String clazz, String filter) throws InvalidSyntaxException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceReference<?> getServiceReference(String clazz) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <S> ServiceReference<S> getServiceReference(Class<S> clazz) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <S> Collection<ServiceReference<S>> getServiceReferences(Class<S> clazz, String filter)
            throws InvalidSyntaxException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <S> S getService(ServiceReference<S> reference) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean ungetService(ServiceReference<?> reference) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <S> ServiceObjects<S> getServiceObjects(ServiceReference<S> reference) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public File getDataFile(String filename) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Filter createFilter(String filter) throws InvalidSyntaxException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Bundle getBundle(String location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
