package com.dongkap.security.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dongkap.common.aspect.ResponseSuccess;
import com.dongkap.common.entity.UserPrincipal;
import com.dongkap.common.exceptions.BaseControllerException;
import com.dongkap.common.http.ApiBaseResponse;
import com.dongkap.common.utils.SuccessCode;
import com.dongkap.security.service.DeactivatedAccountImplService;

@RestController
@RequestMapping("/api/security")
public class DeactivatedAccountController extends BaseControllerException {

	@Autowired
	private DeactivatedAccountImplService deactivatedAccountService;
	
	@ResponseSuccess(SuccessCode.OK_SCR003)
	@RequestMapping(value = "/trx/post/deactivated/v.1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiBaseResponse> putSettings(Authentication authentication,
			@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) String locale,
			@RequestBody(required = true) Map<String, String> dto) throws Exception {
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		return new ResponseEntity<ApiBaseResponse>(deactivatedAccountService.doDeactivate(dto, userPrincipal, locale), HttpStatus.OK);
	}
	
}
