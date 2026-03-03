package org.herba.plugin.junit.approvaltest.commands;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelection;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

import com.spun.util.io.FileUtils;

public class ApproveAllTestResultsCommand extends AbstractHandler {

    private final Logger logger = Logger.getLogger(ApproveAllTestResultsCommand.class.getName());

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
        boolean ret = false;
        ITestElementContainer testContainer = null;
        if (selectedElement instanceof ITestElementContainer) {
            testContainer = (ITestElementContainer) selectedElement;
        } else if (selectedElement instanceof ITestElement) {
            testContainer = ((ITestElement) selectedElement).getParentContainer();
        }
        if (testContainer != null) {
            ret = handleAllComparisonFailuresInTestContainer(testContainer);
        }
        return ret;
    }

    private boolean handleAllComparisonFailuresInTestContainer(ITestElementContainer testContainer) {
        boolean ret = false;
        List<ComparisonFailureDto> comparisonFailures = new LinkedList<ComparisonFailureDto>();
        List<File> fileList = new LinkedList<File>();
        for (ITestElement element : testContainer.getChildren()) {
            ComparisonFailureDto comparisonFailureDetails = getComparisonFailureDetails(element);
            if (comparisonFailureDetails != null && comparisonFailureDetails.getFilePath() != null) {
                fileList.add(comparisonFailureDetails.getFilePath());
                comparisonFailures.add(comparisonFailureDetails);
            }
        }
        if (!fileList.isEmpty() && confirmOverwrite(fileList)) {
            comparisonFailures.forEach(this::handleComparisonFailure);
            ret = true;
        }
        return ret;
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
        logger.info("Overwriting approval test file with current output: " + failureInfo.getFilePath());
        FileUtils.writeFile(failureInfo.getFilePath(), failureInfo.getExpected());
        return true;
    }

    protected boolean confirmOverwrite(List<File> fileList) {
        // TODO add remember choice option
        return MessageDialog.openQuestion(null, "Confirm approval",
                String.format("Approve all test result by overwriting\n"
                        + "%s\n"
                        + "with expected value",
                        fileList.stream()
                        .map(File::getAbsolutePath)
                                .collect(Collectors.joining("\n"))));
    }
}
