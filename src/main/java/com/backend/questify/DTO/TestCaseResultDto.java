package com.backend.questify.DTO;


import com.backend.questify.DTO.testcase.TestCaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResultDto {

    private UUID testCaseResultId;
    private TestCaseDto testCase;
    private String actualOutput;
    private String result;
}