package org.herba.plugin.junit.approvaltest.models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(ApprovalTestResultComparisonInput.class.getName());

    private final String actualContent;
    private final File expectedFile;

    public ApprovalTestResultComparisonInput(String actual, File expectedPath) {
        super(initConfiguration(expectedPath));
        actualContent = actual;
        expectedFile = expectedPath;
    }

    private static CompareConfiguration initConfiguration(File path) {
        CompareConfiguration ret = new CompareConfiguration();
        ret.setLeftEditable(true);
        ret.setLeftLabel("Expected: " + path.getAbsolutePath());
        ret.setRightEditable(false);
        ret.setRightLabel("Actual");
        return ret;
    }

    @Override
    protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        // Create the root diff node
        DiffNode root = new DiffNode(
                new FileContentElement("Expected", expectedFile),
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

        FileContentElement(String name, File file) {
            this.name = name;
            this.file = file;
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
            if (this.file.canRead())
                return new ByteArrayInputStream(FileUtils.readFile(this.file).getBytes(StandardCharsets.UTF_8));
            else
                return new ByteArrayInputStream(new byte[0]);
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
            logger.warning("ComparisonInput.replace function is not supported: " + src + " -> " + dest);
            return dest;
        }
    }
}
