package com.dongkap.feign.client.master;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dongkap.common.utils.RibbonContext;
import com.dongkap.feign.dto.master.ParameterI18nDto;

@FeignClient(value = RibbonContext.MASTER_APP, fallback = ParameterI18nFeignFallback.class)
public interface ParameterI18nFeign {
	
	@RequestMapping(value = RibbonContext.API_MASTER + "/vw/post/parameter-i18n/v.1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ParameterI18nDto getParameter(
			@RequestBody(required = true) Map<String, Object> param,
			@RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, required = false) String locale) throws Exception;
}

@Component
class ParameterI18nFeignFallback implements ParameterI18nFeign {
    
	@Override
    public ParameterI18nDto getParameter(Map<String, Object> param, String locale) {
        return new ParameterI18nDto("", locale);
    }
}