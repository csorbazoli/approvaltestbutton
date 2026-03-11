package org.herba.plugin.junit.approvaltest.commands;

import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelection;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

import com.spun.util.io.FileUtils;

public class AbstractApproveTestResultCommand extends AbstractHandler {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            return null;
        }

        ISelection selection = HandlerUtil.getCurrentSelection(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            Object selectedElement = structuredSelection.getFirstElement();

            handleSelection(selectedElement);
        }
        return null;
    }

    protected boolean handleSelection(Object selectedElement) {
        throw new UnsupportedOperationException("NOT_IMPLEMENTED");
    }

    protected ComparisonFailureDto getComparisonFailureDetails(Object element) {
        ComparisonFailureDto ret = null;
        try {
            if (element instanceof ITestElement) {
                ret = new ComparisonFailureSelection((ITestElement) element).getFailureInfo();
            }
        } catch (Exception e) {
            logger.warning("Failed to get approval test details from test element: " + e.getMessage());
        }
        return ret;
    }

    protected boolean overwriteFileWithActualResult(ComparisonFailureDto failureInfo) {
        boolean ret = true;
        logger.info("Overwriting approval test file with current output: " + failureInfo.getFilePath());
        try {
            FileUtils.writeFile(failureInfo.getFilePath(), failureInfo.getActual());
        } catch (Throwable e) {
            ret = false;
            showError(failureInfo, "Failed to overwrite test resource [" + failureInfo.getFilePath() + "]", e);
        }
        return ret;
    }

    protected void showError(ComparisonFailureDto failureInfo, String message, Throwable exc) {
        String excDetails = exc == null ? "" : "\n" + exc.getMessage();
        logger.severe(message + excDetails);
        MessageDialog.openError(null, "Approval failure",
                message + excDetails);
        OpenTestResourceCommand.openEditor(failureInfo.getFilePath());
    }

}
