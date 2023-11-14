package com.jackpot.domain;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminVO {

	private Long adminId;
	
	@NotBlank(message = "관리자 id는 필수 항목입니다.")
	private String adminLoginId;
	
	@NotBlank(message = "관리자 pwd는 필수 항목입니다.")
	private String adminLoginPwd;
}