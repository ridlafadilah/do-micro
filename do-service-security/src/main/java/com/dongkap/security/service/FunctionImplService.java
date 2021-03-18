package com.dongkap.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.common.entity.UserPrincipal;
import com.dongkap.common.exceptions.SystemErrorException;
import com.dongkap.common.http.ApiBaseResponse;
import com.dongkap.common.utils.ErrorCode;
import com.dongkap.feign.dto.security.FunctionRequestDto;
import com.dongkap.security.dao.FunctionRepo;
import com.dongkap.security.dao.MenuRepo;
import com.dongkap.security.dao.RoleRepo;
import com.dongkap.security.entity.FunctionEntity;
import com.dongkap.security.entity.MenuEntity;
import com.dongkap.security.entity.RoleEntity;

@Service("functionService")
public class FunctionImplService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("functionRepo")
	private FunctionRepo functionRepo;
	
	@Autowired
	@Qualifier("roleRepo")
	private RoleRepo roleRepo;
	
	@Autowired
	@Qualifier("menuRepo")
	private MenuRepo menuRepo;
	
	@Value("${do.locale}")
	private String locale;

	@Transactional
	public ApiBaseResponse doPostFunction(FunctionRequestDto p_dto, UserPrincipal userPrincipal, String p_locale) throws Exception {
		if (p_dto != null) {
			if(p_dto.getMenus() == null)
				throw new SystemErrorException(ErrorCode.ERR_SYS0404);
			if(p_dto.getMenus().isEmpty() && p_dto.getAuthority().isEmpty())
				throw new SystemErrorException(ErrorCode.ERR_SYS0404);
			try {
				List<FunctionEntity> functions = new ArrayList<FunctionEntity>();
				List<MenuEntity> menus = this.menuRepo.loadAllMenuInId(p_dto.getMenus());
				RoleEntity role = this.roleRepo.findByAuthority(p_dto.getAuthority());
				for(MenuEntity menu: menus) {
					FunctionEntity function = new FunctionEntity();
					function.setAccess("read,write,trust");
					function.setMenuId(menu.getId());
					function.setRoleId(role.getId());
					// function.setMenu(menu);
					// function.setRole(role);
					function.setCreatedBy(userPrincipal.getUsername());
					function.setCreatedDate(new Date());
					functions.add(function);
				}
				this.functionRepo.deleteFunctionRoleByType(role.getId(), p_dto.getType());
				this.functionRepo.saveAll(functions);
			} catch (Exception e) {
				throw new SystemErrorException(ErrorCode.ERR_SYS0500);
			} 
			return null;
		} else
			throw new SystemErrorException(ErrorCode.ERR_SYS0404);
	}
	
}