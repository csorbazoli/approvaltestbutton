package testutils;

import java.io.File;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.io.FileUtils;

public class CustomJunit4Reporter implements ApprovalFailureReporter {

	@Override
	public boolean report(String received, String approved) {
		String actual = FileUtils.readFile(received);
		File approvedFile = new File(approved);
		String expected = approvedFile.canRead() ? FileUtils.readFile(approved) : "";
		if (!actual.equals(expected)) {
			throw new TestResourceComparisonFailure(approvedFile, expected, actual);
		}
		return true;
	}

}
