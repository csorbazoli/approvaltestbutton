package org.herba.plugin.junit.approvaltest.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.spun.util.ArrayUtils;
import com.spun.util.io.SimpleDirectoryFilter;

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
        // only look under src or test subfolders (exclude target, bin, etc)
        List<File> matchingFiles = getRecursiveResourceFileList(projectBaseFolder,
                pathName -> pathName.getName().contains(fileName));
        if (matchingFiles.size() == 1) {
            return matchingFiles.get(0);
        }
        // don't assume the first when there are more it's probably not the right one
        return null;
    }

    private static List<File> getRecursiveResourceFileList(File directory, FileFilter filter) {
        return getRecursiveResourceFileList(directory, filter, Arrays.asList("src", "test"));
    }

    private static List<File> getRecursiveResourceFileList(File directory, FileFilter filter,
            List<String> directoryPrefixes) {
        List<File> list = new LinkedList<File>();
        if (!directory.isDirectory()) {
            throw new Error("File is not a directory: " + directory.getName());
        }
        File[] directories = directory.listFiles(new SimpleDirectoryFilter());
        directories = directories == null ? new File[0] : directories;
        for (File element : directories) {
            if (isMatchingPrefix(element.getName(), directoryPrefixes)) {
                list.addAll(getRecursiveResourceFileList(element, filter, Collections.emptyList()));
            }
        }
        File[] files = directory.listFiles(filter);
        ArrayUtils.addArray(list, files);
        return list;
    }

    private static boolean isMatchingPrefix(String name, List<String> prefixes) {
        boolean ret = true;
        if (!prefixes.isEmpty()) {
            ret = prefixes.stream()
                    .anyMatch(name::startsWith);
        }
        return ret;
    }

    private static IWorkspaceRoot getWorkspaceRoot() {
        return root == null ? ResourcesPlugin.getWorkspace().getRoot() : root;
    }

}