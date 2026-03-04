package org.herba.plugin.junit.approvaltest.mocks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

public class MockBundle implements Bundle {

    @Override
    public int compareTo(Bundle o) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public int getState() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void start(int options) throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void start() throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void stop(int options) throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void stop() throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void update(InputStream input) throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void update() throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void uninstall() throws BundleException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Dictionary<String, String> getHeaders() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public long getBundleId() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getLocation() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceReference<?>[] getRegisteredServices() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ServiceReference<?>[] getServicesInUse() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean hasPermission(Object permission) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public URL getResource(String name) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Dictionary<String, String> getHeaders(String locale) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getSymbolicName() {
        return "TestBundle";
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Enumeration<String> getEntryPaths(String path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public URL getEntry(String path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public long getLastModified() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Enumeration<URL> findEntries(String path, String filePattern, boolean recurse) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public BundleContext getBundleContext() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Map<X509Certificate, List<X509Certificate>> getSignerCertificates(int signersType) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Version getVersion() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <A> A adapt(Class<A> type) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public File getDataFile(String filename) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
