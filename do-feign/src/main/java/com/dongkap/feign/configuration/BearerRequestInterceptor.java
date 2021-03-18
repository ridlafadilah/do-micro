package com.dongkap.feign.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class BearerRequestInterceptor implements RequestInterceptor {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	private FeignSignatureInterceptor feignSignatureInterceptor;

	@Value("${do.signature.param-key}")
	private String paramKey;

	@Value("${do.signature.param-timestamp}")
	private String paramTimestamp;

	@Value("${do.signature.param-signature}")
	private String paramSignature;
	
	public BearerRequestInterceptor() {}
	public BearerRequestInterceptor(FeignSignatureInterceptor feignSignatureInterceptor) {
		this.feignSignatureInterceptor = feignSignatureInterceptor;
	}

	@Override
    public void apply(RequestTemplate requestTemplate) {
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
		String tokenType = details.getTokenType();
		String tokenValue = details.getTokenValue();
		if (!tokenType.isEmpty() && !tokenValue.isEmpty()) {
			Map<String, Collection<String>> headers = new HashMap<String, Collection<String>>();
			headers.put("Authorization", Arrays.asList(String.format("%s %s",tokenType,tokenValue)));
			headers.put(paramKey, Arrays.asList(this.feignSignatureInterceptor.getPublicKey()));
			headers.put(paramTimestamp, Arrays.asList(this.feignSignatureInterceptor.getTimestamp()));
			headers.put(paramSignature, Arrays.asList(this.feignSignatureInterceptor.getSignature(requestTemplate.path(), tokenValue)));
			requestTemplate.headers(headers);
		}
    }

}