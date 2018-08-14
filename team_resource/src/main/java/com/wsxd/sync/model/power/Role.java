package com.wsxd.sync.model.power;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	private String roleId;

	private String roleName;

	// 权重（0-10分）
	private String score;

	// 是否可用（1可用，0禁用）
	private String status;

	private String createTime;

	// 创建人
	private String createBy;
}