package com.wsxd.sync.common;

/**
 * 请求响应码，根据controller前缀命名
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午1:22:03
 */
public class StatusCode {

	// 请求成功
	public static final String REQUEST_SUCCESS = "200";

	// 系统异常编码
	public static final String LOGIN_FAIL = "400";

	// 无菜单权限
	public static final String NO_AUTHORITY = "420";

	// 系统异常编码
	public static final String REQUEST_SYSTEM_ERROR = "501";

	// 新增资源key已经存在
	public static final String POWER_402 = "402";

	// 获取资源用户名不合法
	public static final String POWER_403 = "403";

	// 资源名称长度不合法
	public static final String POWER_404 = "404";

	// 资源路径长度不合法
	public static final String POWER_405 = "405";

	// 父级ID不合法
	public static final String POWER_406 = "406";

	// 资源类型不合法
	public static final String POWER_407 = "407";

	// key不合法
	public static final String POWER_408 = "408";

	// 编辑角色score不合法
	public static final String POWER_409 = "409";

	// 角色名称长度不合法
	public static final String POWER_410 = "410";

	// 资源key不合法
	public static final String POWER_411 = "411";

	// 用户无角色信息
	public static final String POWER_413 = "413";

	// 删除角色ID不合法
	public static final String POWER_414 = "414";

	// 删除资源KEY不合法
	public static final String POWER_415 = "415";

	// 删除资源失败
	public static final String POWER_423 = "423";

	// 菜单图标不合法
	public static final String POWER_424 = "424";

	// 通过用户名获取权限资源为空
	public static final String SYS_USER_401 = "401";

	// 用户名或密码错误
	public static final String SYS_USER_412 = "412";

	// 删除用户ID不合法
	public static final String SYS_USER_416 = "416";

	// 管理员名不合法
	public static final String SYS_USER_417 = "417";

	// 真实名称不合法
	public static final String SYS_USER_418 = "418";

	// 手机号码不合法
	public static final String SYS_USER_419 = "419";

	// isShow不合法
	public static final String SYS_USER_421 = "421";

	// 密码不合法
	public static final String SYS_USER_422 = "422";
}
