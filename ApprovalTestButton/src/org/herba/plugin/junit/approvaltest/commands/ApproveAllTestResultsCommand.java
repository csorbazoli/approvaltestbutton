package org.herba.plugin.junit.approvaltest.commands;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElementContainer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

public class ApproveAllTestResultsCommand extends AbstractApproveTestResultCommand {

    // private final Logger logger =
    // Logger.getLogger(ApproveAllTestResultsCommand.class.getName());

    @Override
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
            ret = true;
            for (ComparisonFailureDto failureInfo : comparisonFailures) {
                if (!overwriteFileWithActualResult(failureInfo)) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
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
