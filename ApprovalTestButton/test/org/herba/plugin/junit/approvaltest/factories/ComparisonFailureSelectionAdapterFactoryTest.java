package org.herba.plugin.junit.approvaltest.factories;

import static org.assertj.core.api.Assertions.assertThat;

import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelection;
import org.herba.plugin.junit.approvaltest.handlers.ComparisonFailureSelectionAny;
import org.herba.plugin.junit.approvaltest.handlers.FailureWithFilePathSelection;
import org.junit.Test;

import com.herba.plugin.junit.approvaltest.TestHelper;

public class ComparisonFailureSelectionAdapterFactoryTest {

    private ComparisonFailureSelectionAdapterFactory underTest = new ComparisonFailureSelectionAdapterFactory();

    @Test
    public void testGetAdapter_testCaseWithFileComparisonFailure() throws Exception {
        // given
        Object adaptable = TestHelper.createTestElementWithPathInMessage();
        Class<ComparisonFailureSelection> adapterType = ComparisonFailureSelection.class;
        // when
        ComparisonFailureSelection actual = underTest.getAdapter(adaptable, adapterType);
        // then
        assertThat(actual).isNotNull();
        assertThat(underTest.getAdapterList()).contains(adapterType);
    }

    @Test
    public void testGetAdapter_testCaseWithSimpleComparisonFailure() throws Exception {
        // given
        Object adaptable = TestHelper.createTestElementWithoutPath();
        Class<ComparisonFailureSelection> adapterType = ComparisonFailureSelection.class;
        // when
        ComparisonFailureSelection actual = underTest.getAdapter(adaptable, adapterType);
        // then
        assertThat(actual).isNull();
    }

    @Test
    public void testGetAdapter_any_testCaseWithFileComparisonFailure() throws Exception {
        // given
        Object adaptable = TestHelper.createTestElementWithPathInMessage();
        Class<ComparisonFailureSelectionAny> adapterType = ComparisonFailureSelectionAny.class;
        // when
        ComparisonFailureSelection actual = underTest.getAdapter(adaptable, adapterType);
        // then
        assertThat(actual).isNotNull();
        assertThat(underTest.getAdapterList()).contains(adapterType);
    }

    @Test
    public void testGetAdapter_any_testCaseWithSimpleComparisonFailure() throws Exception {
        // given
        Object adaptable = TestHelper.createTestElementWithoutPath();
        Class<ComparisonFailureSelectionAny> adapterType = ComparisonFailureSelectionAny.class;
        // when
        ComparisonFailureSelection actual = underTest.getAdapter(adaptable, adapterType);
        // then
        assertThat(actual).isNull();
    }

    @Test
    public void testGetAdapter_testCaseWithFileReferenceFailure() throws Exception {
        // given
        Object adaptable = TestHelper.createTestElementWithPathInMessage();
        Class<FailureWithFilePathSelection> adapterType = FailureWithFilePathSelection.class;
        // when
        FailureWithFilePathSelection actual = underTest.getAdapter(adaptable, adapterType);
        // then
        assertThat(actual).isNotNull();
        assertThat(underTest.getAdapterList()).contains(adapterType);
    }

    @Test
    public void testGetAdapter_testCaseWithoutFileReferenceInFailure() throws Exception {
        // given
        Object adaptable = TestHelper.createTestElementWithoutPath();
        Class<FailureWithFilePathSelection> adapterType = FailureWithFilePathSelection.class;
        // when
        FailureWithFilePathSelection actual = underTest.getAdapter(adaptable, adapterType);
        // then
        assertThat(actual).isNull();
    }

}