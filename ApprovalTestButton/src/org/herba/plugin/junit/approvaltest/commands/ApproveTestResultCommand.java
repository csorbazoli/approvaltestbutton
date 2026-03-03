package org.herba.plugin.junit.approvaltest.commands;

import org.eclipse.jface.dialogs.MessageDialog;
import org.herba.plugin.junit.approvaltest.models.ComparisonFailureDto;

public class ApproveTestResultCommand extends AbstractApproveTestResultCommand {

    // private final Logger logger =
    // Logger.getLogger(ApproveTestResultCommand.class.getName());

    @Override
    protected boolean handleSelection(Object selectedElement) {
        ComparisonFailureDto failureInfo = getComparisonFailureDetails(selectedElement);
        if (failureInfo != null && failureInfo.getFilePath() != null
                && confirmOverwrite(failureInfo)) {
            return handleComparisonFailure(failureInfo);
        }
        return false;
    }

    protected boolean confirmOverwrite(ComparisonFailureDto element) {
        // TODO add remember choice option
        return MessageDialog.openQuestion(null, "Confirm approval",
                String.format("Approve test result by overwriting\n"
                        + "%s\n"
                        + "with expected value",
                        element.getFilePath()));
    }
}
