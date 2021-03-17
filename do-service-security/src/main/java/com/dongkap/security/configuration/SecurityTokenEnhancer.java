package com.dongkap.security.configuration;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.dongkap.common.entity.UserPrincipal;
import com.dongkap.common.utils.DateUtil;
import com.dongkap.feign.dto.security.MenuDto;
import com.dongkap.security.service.MenuImplService;

public class SecurityTokenEnhancer implements TokenEnhancer {

    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Value("${do.client-id.web}")
	private String clientIdWeb;
	
	@Autowired
	@Qualifier("menuService")
	private MenuImplService menuService;
	
	@Value("${do.signature.public-key}")
	private String publicKey;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if (authentication.getPrincipal() instanceof UserPrincipal) {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
	        Map<String, Object> additionalInfo = new TreeMap<String, Object>();	
			if(authentication.getOAuth2Request().getClientId().equals(clientIdWeb) && user.getRaw() == null) {
				try {
					Map<String, List<MenuDto>> allMenus = menuService.loadAllMenuByRole(user.getAuthorityDefault(), (user.getAttributes().get("locale") == null)? "en-US" : user.getAttributes().get("locale").toString());
					additionalInfo.put("menus", allMenus.get("main"));
					additionalInfo.put("extras", allMenus.get("extra"));
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
	        additionalInfo.put("username", user.getUsername());
	        additionalInfo.put("authority", user.getAuthorityDefault());
	        additionalInfo.put("provider", user.getProvider());
	        additionalInfo.put("email", user.getEmail());
	        additionalInfo.put("name", user.getName());
	        additionalInfo.put("image", (user.getAttributes().get("image") == null)? null : user.getAttributes().get("image"));
	        additionalInfo.put("locale", (user.getAttributes().get("locale") == null)? "en-US" : user.getAttributes().get("locale"));
	        additionalInfo.put("theme", (user.getAttributes().get("theme") == null)? "default" : user.getAttributes().get("theme"));
	        additionalInfo.put("server_date", DateUtil.DATE.format(new Date()));
	        additionalInfo.put("xrkey", publicKey);
	        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		}
        return accessToken;
	}

}
