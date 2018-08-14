package com.wsxd.sync.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wsxd.sync.common.Constants;
import com.wsxd.sync.common.StatusCode;
import com.wsxd.sync.model.TokenModel;
import com.wsxd.sync.model.power.SysUser;
import com.wsxd.sync.service.RedisTokenService;
import com.wsxd.sync.service.SysUserService;
import com.wsxd.sync.util.DateUtils;
import com.wsxd.sync.util.Pager;
import com.wsxd.sync.util.ResultUtil;
import com.wsxd.sync.util.StringUtil;

/**
 * 系统管理员controller
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午1:32:39
 */
@Scope("prototype")
@Controller
@RequestMapping({ "/sysUser" })
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisTokenService redisTokenService;

	private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:36:18
	 */
	@RequestMapping({ "/login" })
	@ResponseBody
	public ResultUtil login(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT,DELETE");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		SysUser sysUser = null;
		try {
			sysUser = sysUserService.login(username, password);
		} catch (Exception e) {
			logger.error("登录异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		if (sysUser == null) {
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_412).set("msg", "用户名或密码错误");
		}

		TokenModel model = redisTokenService.createToken(sysUser.getSysUserId());
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("token", model);
	}

	/**
	 * 系统管理员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:36:26
	 */
	@RequestMapping({ "/list" })
	@ResponseBody
	public ResultUtil list(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String mobile = request.getParameter("mobile");
		String trueName = request.getParameter("trueName");
		String isShow = request.getParameter("isShow");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");

		if (StringUtil.isEmpty(username)) {
			username = null;
		}

		if (StringUtil.isEmpty(mobile)) {
			mobile = null;
		}

		if (StringUtil.isEmpty(trueName)) {
			trueName = null;
		}

		if (StringUtil.isEmpty(isShow)) {
			isShow = null;
		}

		if (!StringUtil.isNumber(page)) {
			page = Constants.PAGE;
		}

		if (!StringUtil.isNumber(pageSize)) {
			pageSize = Constants.PAGE_SIZE;
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("username", username);
		param.put("mobile", mobile);
		param.put("trueName", trueName);
		param.put("isShow", isShow);
		param.put("page", page);
		param.put("pageSize", pageSize);
		param.put("currentUsername", currentUser(request).getUsername());
		Pager pager = null;
		List<Map<String, Object>> list = null;
		try {
			list = sysUserService.loadRolesByUsername(currentUser(request).getUsername());
			pager = sysUserService.querySysUsers(param);
		} catch (Exception e) {
			logger.error("查询管理员列表异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("sysUserList", pager.getList()).set("roleList", list).set("totalSize", Integer.valueOf(pager.getItemsTotal()));
	}

	/**
	 * 编辑管理员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:36:36
	 */
	@RequestMapping({ "/editUser" })
	@ResponseBody
	public ResultUtil editUser(HttpServletRequest request, HttpServletResponse response) {
		String sysUserId = request.getParameter("sysUserId");
		String mobile = request.getParameter("mobile");
		String trueName = request.getParameter("trueName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String isShow = request.getParameter("isShow");
		String roleId = request.getParameter("roleId");
		if (StringUtil.isEmpty(sysUserId)) {
			sysUserId = null;
		}

		if ((sysUserId != null) && (!Pattern.matches("[a-zA-Z0-9]{3,8}", username))) {
			logger.error("管理员名不合法,username:" + username);
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_417).set("msg", "管理员名不合法");
		}

		if (!Pattern.matches(".{0,32}", password)) {
			logger.error("密码不合法,password:" + password);
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_422).set("msg", "密码不合法");
		}

		if (!Pattern.matches(".{0,32}", trueName)) {
			logger.error("真实名称不合法,trueName:" + trueName);
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_418).set("msg", "真实名称不合法");
		}

		if (!Pattern.matches("[0-9]{0,32}", mobile)) {
			logger.error("手机号码不合法,mobile:" + mobile);
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_419).set("msg", "手机号码不合法");
		}

		if (!Pattern.matches("[0,1]", isShow)) {
			logger.error("isShow不合法,isShow:" + isShow);
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_421).set("msg", "isShow不合法");
		}

		if (StringUtil.isEmpty(sysUserId)) {
			sysUserId = null;
		}

		SysUser sysUser = new SysUser(sysUserId, password, trueName, mobile, isShow);
		if (sysUserId == null) {
			sysUser.setUsername(username);
			sysUser.setCreateTime(DateUtils.toStr());
			sysUser.setCreateBy(currentUser(request).getUsername());
		}
		try {
			sysUserService.editSysUser(sysUser, roleId);
		} catch (Exception e) {
			logger.error("管理员编辑保存出现异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}

		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS);
	}

	/**
	 * 删除管理员
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:36:44
	 */
	@RequestMapping({ "/deleteUser" })
	@ResponseBody
	public ResultUtil deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String sysUserId = request.getParameter("sysUserIds");

		if (!Pattern.matches("[a-zA-Z0-9,]{1,}", sysUserId)) {
			logger.error("管理员ID不合法,roleIds:" + sysUserId);
			return ResultUtil.fail().set("code", StatusCode.SYS_USER_416).set("msg", "管理员ID不合法");
		}
		List<String> list = Arrays.asList(sysUserId.split(","));

		int row = 0;
		try {
			row = sysUserService.deleteUser(list);
		} catch (Exception e) {
			logger.error("删除角色出现异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("row", Integer.valueOf(row));
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:36:53
	 */
	@RequestMapping({ "/logout" })
	public ResultUtil logout(HttpServletRequest request) {
		redisTokenService.deleteToken(currentUser(request).getSysUserId());
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS);
	}
}