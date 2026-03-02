package testutils;

import java.io.File;
import java.nio.file.Path;

import org.junit.ComparisonFailure;

public class TestResourceComparisonFailure extends ComparisonFailure {
	private static final long serialVersionUID = -3357534655935895402L;
	private final Path filePath;

	public TestResourceComparisonFailure(File testResource, String testResourceContent, String actualResult) {
		super("Actual result does not match content of " + testResource.getAbsolutePath(), testResourceContent,
				actualResult);
		filePath = testResource.toPath();
	}

	public Path getFilePath() {
		return filePath;
	}

}
