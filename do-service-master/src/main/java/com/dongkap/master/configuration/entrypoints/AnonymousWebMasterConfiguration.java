package com.dongkap.master.configuration.entrypoints;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.dongkap.common.utils.ResourceCode;

@Configuration
public class AnonymousWebMasterConfiguration extends WebSecurityConfigurerAdapter {

	private static final String OPENAPI_PATH_MASTER_VIEW = "/oa/"+ResourceCode.MASTER.getResourceId()+"/vw/**";
	
	@Override
    public void configure(WebSecurity webSecurity) throws Exception {
       webSecurity.ignoring().antMatchers(OPENAPI_PATH_MASTER_VIEW);
    }

}
