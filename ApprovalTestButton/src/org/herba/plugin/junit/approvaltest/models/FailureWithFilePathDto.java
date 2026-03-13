package org.herba.plugin.junit.approvaltest.models;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FailureWithFilePathDto {

    private String message;
    private File filePath;
    /**
     * When exact file path was not found
     */
    private String fileNameOnly;

}
