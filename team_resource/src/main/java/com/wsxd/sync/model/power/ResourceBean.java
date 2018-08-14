package com.wsxd.sync.model.power;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceBean {

	private String label;

	private String key;

	private String sort;

	private String url;

	private String icon;

	private List<ResourceBean> children;

	private Object[] values;

	public ResourceBean(String label, String key) {
		this.label = label;
		this.key = key;
	}

	public ResourceBean(String label, String key, String sort, List<ResourceBean> children) {
		this.label = label;
		this.key = key;
		this.sort = sort;
		this.children = children;
	}

	public ResourceBean(String label, String key, String sort, Object[] values) {
		this.label = label;
		this.key = key;
		this.sort = sort;
		this.values = values;
	}

	public ResourceBean(String label, String key, String sort, String url, String icon, List<ResourceBean> children) {
		this.label = label;
		this.key = key;
		this.sort = sort;
		this.url = url;
		this.icon = icon;
		this.children = children;
	}

	public ResourceBean(String label, String key, String sort, String url, String icon, Object[] values) {
		this.label = label;
		this.key = key;
		this.sort = sort;
		this.url = url;
		this.icon = icon;
		this.values = values;
	}
}