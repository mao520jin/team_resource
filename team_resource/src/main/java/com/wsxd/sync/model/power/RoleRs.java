package com.wsxd.sync.model.power;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRs {

	private String roleName;

	private String roleId;

	private String score;
	
	private String username;

	private List<ResourceBean> resourceBeanlist;

	public RoleRs(String roleName, String score, List<ResourceBean> resourceBeanlist) {
		this.roleName = roleName;
		this.score = score;
		this.resourceBeanlist = resourceBeanlist;
	}
}