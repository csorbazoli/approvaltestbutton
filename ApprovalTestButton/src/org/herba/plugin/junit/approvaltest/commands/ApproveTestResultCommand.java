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

public class ApproveTestResultCommand extends AbstractHandler {

    private final Logger logger = Logger.getLogger(ApproveTestResultCommand.class.getName());

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
        ComparisonFailureDto comparisonFailureDetails = getComparisonFailureDetails(selectedElement);
        if (comparisonFailureDetails != null) {
            return handleComparisonFailure(comparisonFailureDetails);
        }
        return false;
    }

    private ComparisonFailureDto getComparisonFailureDetails(Object element) {
        ComparisonFailureDto ret = null;
        try {
            if (element instanceof ITestElement) {
                ret = new ComparisonFailureSelection((ITestElement) element).getComparisonFailure();
            }
        } catch (Exception e) {
            logger.warning("Failed to get approval test details from test element: "+e.getMessage());
        }
        return ret;
    }

    protected boolean handleComparisonFailure(ComparisonFailureDto failureInfo) {
        boolean ret = (failureInfo.getFilePath() != null) && confirmOverwrite(failureInfo);
        if (ret) {
            logger.info("Overwriting approval test file with current output: " + failureInfo.getFilePath());
            FileUtils.writeFile(failureInfo.getFilePath(), failureInfo.getExpected());
        }
        return ret;
    }

    protected boolean confirmOverwrite(ComparisonFailureDto element) {
        return MessageDialog.openQuestion(null, "Comparison Failure",
                String.format("Approve test result by overwriting: %s\n"
                        + "With expected value: %s",
                        element.getFilePath(),
                        element.getActual()));
    }
}
