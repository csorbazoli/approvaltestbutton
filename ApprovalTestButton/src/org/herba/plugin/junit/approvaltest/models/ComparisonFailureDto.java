package org.herba.plugin.junit.approvaltest.models;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonFailureDto {

    private String message;
    private String actual;
    private String expected;
    @JsonIgnore
    private File filePath;

}
