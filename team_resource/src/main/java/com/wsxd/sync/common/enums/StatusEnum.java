package com.wsxd.sync.common.enums;

/**
 * 数据表记录的合法性字段
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午1:41:11
 */
public enum StatusEnum {
	/*
	 * 有效
	 */
	VALID("1"),
	/*
	 * 无效
	 */
	INVALID("0");

	private String status;

	public String getStatus() {
		return this.status;
	}

	private StatusEnum(String status) {
		this.status = status;
	}

	public static void main(String[] args) {
		System.out.println(VALID.getStatus());
	}
}