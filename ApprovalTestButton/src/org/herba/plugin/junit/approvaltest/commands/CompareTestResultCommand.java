package org.herba.plugin.junit.approvaltest.commands;

import java.util.logging.Logger;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.herba.plugin.junit.approvaltest.models.ApprovalTestResultComparisonInput;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

public class CompareTestResultCommand extends AbstractApproveTestResultCommand {

    private final Logger logger = Logger.getLogger(ApproveTestResultCommand.class.getName());

    @Override
    protected boolean handleSelection(Object selectedElement) {
        ComparisonFailureDto failureInfo = getComparisonFailureDetails(selectedElement);
        if (failureInfo != null && failureInfo.getFilePath() != null) {
            return openComparisonView(failureInfo);
        }
        return false;
    }

    protected boolean openComparisonView(ComparisonFailureDto failureInfo) {
        try {
            CompareEditorInput input = new ApprovalTestResultComparisonInput(failureInfo.getActual(),
                    failureInfo.getFilePath());
            CompareUI.openCompareEditor(input, true);
            return true;
        } catch (Exception e) {
            logger.severe("Failed to open compare view: " + e.getMessage());
            return false;
        }
    }
}
