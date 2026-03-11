package org.herba.plugin.junit.approvaltest.utils;

import org.herba.plugin.junit.approvaltest.mocks.MockWorkspaceRoot;

public class MockFileUtils {

    public static void initMock() {
        ApprovalTestFileUtils.setRoot(new MockWorkspaceRoot());
    }
}
