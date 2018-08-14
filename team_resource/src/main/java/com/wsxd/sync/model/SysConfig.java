package com.wsxd.sync.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sys_config 对应实体类
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018-01-16 15:17:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysConfig {

	@Id
	private String sysConfigId;

	// 配置键
	private String key;

	// 配置值
	private String value;

	// 备注
	private String remark;

	// 创建时间
	private String createDate;

	// 创建人
	private String createBy;

	// 修改时间
	private String updateDate;

	// 修改人
	private String updateBy;

	public SysConfig(String sysConfigId, String key) {
		this.sysConfigId = sysConfigId;
		this.key = key;
	}

}
