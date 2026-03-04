package org.herba.plugin.junit.approvaltest.models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

import com.spun.util.io.FileUtils;

public class ApprovalTestResultComparisonInput extends CompareEditorInput {

    // private static final Logger logger =
    // Logger.getLogger(ApprovalTestResultComparisonInput.class.getName());

    private final String actualContent;
    private final String expectedContent;
    private final File expectedFile;

    public ApprovalTestResultComparisonInput(String actual, File expectedPath, String expected) {
        super(initConfiguration(expectedPath));
        this.actualContent = actual;
        this.expectedFile = expectedPath;
        this.expectedContent = expected;
    }

    private static CompareConfiguration initConfiguration(File path) {
        CompareConfiguration ret = new CompareConfiguration();
        ret.setLeftEditable(true);
        ret.setLeftLabel("Expected (a.k.a. Approved): " + path.getAbsolutePath());
        ret.setRightEditable(false);
        ret.setRightLabel("Actual (a.k.a. Received)");
        return ret;
    }

    @Override
    protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        // Create the root diff node
        DiffNode root = new DiffNode(
                new FileContentElement("Expected", expectedFile, expectedContent),
                new SimpleTextElement("Actual", actualContent));
        return root;
    }

    /**
     * Simple text element implementation for comparing plain text
     */
    private static class SimpleTextElement implements ITypedElement, IStreamContentAccessor {
        private final String name;
        private final String content;

        SimpleTextElement(String name, String content) {
            this.name = name;
            this.content = content != null ? content : "";
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Image getImage() {
            return null;
        }

        @Override
        public String getType() {
            return TEXT_TYPE;
        }

        @Override
        public InputStream getContents() throws CoreException {
            return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static class FileContentElement implements IEditableContent, ITypedElement, IStreamContentAccessor {
        private final String name;
        private final File file;
        private String content;

        FileContentElement(String name, File file, String content) {
            this.name = name;
            this.file = file;
            this.content = content != null ? content : "";
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Image getImage() {
            return null;
        }

        @Override
        public String getType() {
            return TEXT_TYPE;
        }

        @Override
        public InputStream getContents() throws CoreException {
            return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public boolean isEditable() {
            return !file.exists() || file.canWrite();
        }

        @Override
        public void setContent(byte[] newContent) {
            FileUtils.writeFile(file, new ByteArrayInputStream(newContent));
        }

        @Override
        public ITypedElement replace(ITypedElement dest, ITypedElement src) {
            throw new UnsupportedOperationException("NOT_IMPLEMENTED");
        }
    }
}
