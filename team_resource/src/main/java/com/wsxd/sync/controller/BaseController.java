package com.wsxd.sync.controller;

import javax.servlet.http.HttpServletRequest;

import com.wsxd.sync.model.power.SysUser;

/**
 * 基类controller
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午2:07:03
 */
public class BaseController {

	/**
	 * 获取当前用户
	 * 
	 * @param request
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:07:26
	 */
	public SysUser currentUser(HttpServletRequest request) {
		return (SysUser) request.getSession().getAttribute("sysUser");
	}

}