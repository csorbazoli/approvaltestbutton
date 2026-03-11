package org.herba.plugin.junit.approvaltest.utils;

import java.io.File;
import java.io.FileFilter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.spun.util.io.FileUtils;

public class ApprovalTestFileUtils {

    private static IWorkspaceRoot root;

    public static void setRoot(IWorkspaceRoot root) {
        ApprovalTestFileUtils.root = root;
    }

    /**
     * Convert a java.io.File to an IFile
     */
    public static IFile getIFile(File file) {
        IWorkspaceRoot root = getWorkspaceRoot();
        IPath path = new Path(file.getAbsolutePath());
        return root.getFileForLocation(path);
    }

    /**
     * Convert an absolute path string to an IFile
     */
    public static IFile getIFile(String absolutePath) {
        return getIFile(new File(absolutePath));
    }

    public static File findFileByNameInWorkspace(File projectBaseFolder, String fileName) {
        File[] matchingFiles = FileUtils.getRecursiveFileList(projectBaseFolder, new FileFilter() {

            @Override
            public boolean accept(File pathName) {
                return pathName.getName().contains(fileName);
            }
        });
        if (matchingFiles.length > 0) {
            // what if there are more?
            return matchingFiles[0];
        }
        return null;
    }

    private static IWorkspaceRoot getWorkspaceRoot() {
        return root == null ? ResourcesPlugin.getWorkspace().getRoot() : root;
    }

}