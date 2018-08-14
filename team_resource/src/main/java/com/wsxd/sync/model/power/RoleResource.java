package com.wsxd.sync.model.power;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResource {

	private String roleResourceId;

	private String roleId;

	private String resourceId;
}