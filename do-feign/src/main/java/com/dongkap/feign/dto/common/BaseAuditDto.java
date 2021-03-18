package com.dongkap.feign.dto.common;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BaseAuditDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1183080114772374130L;
	protected int version;
	protected String active;
	@JsonFormat(pattern="dd/MM/yyyy")
	protected Date createdDate;
	protected String createdBy;
	@JsonFormat(pattern="dd/MM/yyyy")
	protected Date modifiedDate;
	protected String modifiedBy;

	public void setIsActive(boolean active) {
		if(active)
			this.active = "Active";
		else
			this.active = "Deactivated";
	}

}
