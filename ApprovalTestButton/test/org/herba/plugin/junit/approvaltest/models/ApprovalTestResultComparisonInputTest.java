package org.herba.plugin.junit.approvaltest.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.compare.internal.CompareUIPlugin;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.runtime.IProgressMonitor;
import org.herba.plugin.junit.approvaltest.mocks.MockBundleContext;
import org.herba.plugin.junit.approvaltest.mocks.MockProgressMonitor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.osgi.framework.BundleContext;

public class ApprovalTestResultComparisonInputTest {

    private ApprovalTestResultComparisonInput underTest;

    @Before
    public void setup() throws Exception {
        // init compare UI
        BundleContext context = new MockBundleContext();
        new CompareUIPlugin().start(context);
        initUnderTest();
    }

    @Test
    @Ignore("would require Ecipse runtime")
    public void testPrepareInput() throws Exception {
        // given
        IProgressMonitor monitor = new MockProgressMonitor();
        // when
        Object actual = underTest.prepareInput(monitor);
        // then
        assertThat(actual).isInstanceOf(DiffNode.class);
    }

    private ApprovalTestResultComparisonInput initUnderTest() {
        underTest = new ApprovalTestResultComparisonInput("actual value",
                new File("test-resources/approvaltest/sample.approved.txt"), "expected value");
        return underTest;
    }

}