package com.wsxd.sync.model.power;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {

	private String sysUserId;

	// 用户名
	private String username;

	// 密码
	private String password;

	// 真实名称
	private String trueName;

	// 手机号码
	private String mobile;

	// 是否可用（1可用，0禁用）
	private String status;

	// 1=启用，0=停用
	private String isShow;

	// 创建人
	private String createTime;

	private String createBy;

	public SysUser(String sysUserId, String password, String trueName, String mobile, String isShow) {
		this.sysUserId = sysUserId;
		this.password = password;
		this.trueName = trueName;
		this.mobile = mobile;
		this.isShow = isShow;
	}
}