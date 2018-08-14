package com.wsxd.sync.model.power;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

	private String resourceId;

	// 父级资源ID
	private String parentId;

	// 菜单级别（1,2,3,4级）
	private String resType;

	// 同级别菜单排序
	private String sort;

	// 资源标识
	private String resKey;

	// 资源名称
	private String resName;

	// 小图标
	private String icon;

	// 菜单url
	private String url;

	// 是否可用（1可用，0禁用）
	private String status;

	// 创建时间
	private String createTime;

	// 创建人
	private String createBy;

	// 更新时间
	private String updateTime;

	// 更新人
	private String updateBy;

	public Resource(String resourceId, String parentId, String resType, String sort, String resKey, String resName, String icon, String url, String status) {
		this.resourceId = resourceId;
		this.parentId = parentId;
		this.resType = resType;
		this.sort = sort;
		this.resKey = resKey;
		this.resName = resName;
		this.icon = icon;
		this.url = url;
		this.status = status;
	}

}