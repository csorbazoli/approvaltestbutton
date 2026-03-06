package org.herba.plugin.junit.approvaltest.mocks;

import java.io.File;

import org.eclipse.core.runtime.IPath;

public class MockLocation implements IPath {

    private static String basePath = "";

    public static void setBasePath(String basePath) {
        MockLocation.basePath = basePath;
    }

    @Override
    public IPath addFileExtension(String extension) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath addTrailingSeparator() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath append(String path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath append(IPath path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getDevice() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getFileExtension() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean hasTrailingSeparator() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isAbsolute() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isPrefixOf(IPath anotherPath) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isRoot() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isUNC() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isValidPath(String path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isValidSegment(String segment) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String lastSegment() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath makeAbsolute() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath makeRelative() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath makeRelativeTo(IPath base) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath makeUNC(boolean toUNC) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public int matchingFirstSegments(IPath anotherPath) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath removeFileExtension() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath removeFirstSegments(int count) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath removeLastSegments(int count) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath removeTrailingSeparator() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String segment(int index) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public int segmentCount() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String[] segments() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath setDevice(String device) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public File toFile() {
        return new File(basePath);
    }

    @Override
    public String toOSString() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String toPortableString() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath uptoSegment(int count) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Object clone() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }
}
