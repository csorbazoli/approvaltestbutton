package testutils;

import java.io.File;

import org.approvaltests.core.ApprovalFailureReporter;
import org.junit.Assert;

import com.spun.util.io.FileUtils;

public enum CustomJunit4Reporter implements ApprovalFailureReporter {
	INSTANCE;

	@Override
	public boolean report(String received, String approved) {
		String actual = FileUtils.readFile(received);
		File approvedFile = new File(approved);
		String expected = approvedFile.canRead() ? FileUtils.readFile(approved) : "";
		Assert.assertEquals("Actual result does not match content of " + approvedFile.getAbsolutePath(), expected,
				actual);
		return true;
	}

}
