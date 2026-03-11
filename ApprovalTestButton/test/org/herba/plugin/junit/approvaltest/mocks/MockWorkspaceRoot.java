package org.herba.plugin.junit.approvaltest.mocks;

import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class MockWorkspaceRoot implements IWorkspaceRoot {

    @Override
    public boolean exists(IPath path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource findMember(String path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource findMember(String path, boolean includePhantoms) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource findMember(IPath path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource findMember(IPath path, boolean includePhantoms) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getDefaultCharset() throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getDefaultCharset(boolean checkImplicit) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFile getFile(IPath path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFolder getFolder(IPath path) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource[] members() throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource[] members(boolean includePhantoms) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResource[] members(int memberFlags) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFile[] findDeletedMembersWithHistory(int depth, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setDefaultCharset(String charset) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setDefaultCharset(String charset, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResourceFilterDescription createFilter(int type, FileInfoMatcherDescription matcherDescription,
            int updateFlags, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResourceFilterDescription[] getFilters() throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void accept(IResourceProxyVisitor visitor, int memberFlags) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void accept(IResourceVisitor visitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void accept(IResourceVisitor visitor, int depth, boolean includePhantoms) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void accept(IResourceVisitor visitor, int depth, int memberFlags) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void clearHistory(IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void copy(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void copy(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void copy(IProjectDescription description, boolean force, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void copy(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IMarker createMarker(String type) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IResourceProxy createProxy() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void deleteMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean exists() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IMarker findMarker(long id) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public int findMaxProblemSeverity(String type, boolean includeSubtypes, int depth) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getFileExtension() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath getFullPath() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public long getLocalTimeStamp() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath getLocation() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public URI getLocationURI() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IMarker getMarker(long id) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public long getModificationStamp() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPathVariableManager getPathVariableManager() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IContainer getParent() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Map<QualifiedName, String> getPersistentProperties() throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public String getPersistentProperty(QualifiedName key) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IProject getProject() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath getProjectRelativePath() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IPath getRawLocation() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public URI getRawLocationURI() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public ResourceAttributes getResourceAttributes() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Map<QualifiedName, Object> getSessionProperties() throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public Object getSessionProperty(QualifiedName key) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public int getType() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IWorkspace getWorkspace() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isAccessible() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isDerived() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isDerived(int options) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isHidden() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isHidden(int options) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isLinked() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isVirtual() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isLinked(int options) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isLocal(int depth) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isPhantom() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isReadOnly() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isSynchronized(int depth) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isTeamPrivateMember() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isTeamPrivateMember(int options) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void move(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void move(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void move(IProjectDescription description, boolean force, boolean keepHistory, IProgressMonitor monitor)
            throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void move(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void revertModificationStamp(long value) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setDerived(boolean isDerived) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setDerived(boolean isDerived, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setHidden(boolean isHidden) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setLocal(boolean flag, int depth, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public long setLocalTimeStamp(long value) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setPersistentProperty(QualifiedName key, String value) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setResourceAttributes(ResourceAttributes attributes) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setSessionProperty(QualifiedName key, Object value) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void setTeamPrivateMember(boolean isTeamPrivate) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void touch(IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean contains(ISchedulingRule rule) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public boolean isConflicting(ISchedulingRule rule) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public void delete(boolean deleteContent, boolean force, IProgressMonitor monitor) throws CoreException {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IContainer[] findContainersForLocation(IPath location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IContainer[] findContainersForLocationURI(URI location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IContainer[] findContainersForLocationURI(URI location, int memberFlags) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFile[] findFilesForLocation(IPath location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFile[] findFilesForLocationURI(URI location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFile[] findFilesForLocationURI(URI location, int memberFlags) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IContainer getContainerForLocation(IPath location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IFile getFileForLocation(IPath location) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IProject getProject(String name) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IProject[] getProjects() {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    @Override
    public IProject[] getProjects(int memberFlags) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

}
