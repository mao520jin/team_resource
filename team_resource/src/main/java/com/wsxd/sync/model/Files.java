package com.wsxd.sync.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * files 对应实体类
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018-01-15 15:35:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Files {

	// 主键ID
	@Id
	private String filesId;

	// md5值
	private String md;

	// 文件路径
	private String path;

	public Files(String filesId, String md) {
		this.filesId = filesId;
		this.md = md;
	}

}
