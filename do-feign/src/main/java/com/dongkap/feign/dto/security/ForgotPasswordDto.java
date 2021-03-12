package com.dongkap.feign.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class ForgotPasswordDto extends RequestForgotPasswordDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8133839452021875038L;
	private String verificationId;
	private String verificationCode;
	private String newPassword;
	private String confirmPassword;

}