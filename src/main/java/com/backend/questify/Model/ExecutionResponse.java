package com.backend.questify.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResponse {
	private String StdErr;
	private String StdOut;
	private String Output;
	private Integer Code;
	private String Signal;
	private String Language;
	private String Version;
}
