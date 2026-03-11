package org.herba.plugin.junit.approvaltest.models;

import java.io.File;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ComparisonFailureDto extends FailureWithFilePathDto {

    private String actual;
    private String expected;

    public ComparisonFailureDto(String message, String actualContent, String expectedContent, File filePath) {
        super(message, filePath);
        this.actual = actualContent;
        this.expected = expectedContent;
    }
}
