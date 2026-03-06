package org.herba.plugin.junit.approvaltest.utils;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class FileUtils {

    /**
     * Convert a java.io.File to an IFile
     */
    public static IFile getIFile(File file) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IPath path = new Path(file.getAbsolutePath());
        return root.getFileForLocation(path);
    }

    /**
     * Convert an absolute path string to an IFile
     */
    public static IFile getIFile(String absolutePath) {
        return getIFile(new File(absolutePath));
    }
}