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
import com.wsxd.sync.model.power.Resource;
import com.wsxd.sync.model.power.ResourceBean;
import com.wsxd.sync.model.power.Role;
import com.wsxd.sync.model.power.RoleRs;
import com.wsxd.sync.model.power.SysUser;
import com.wsxd.sync.service.PowerService;
import com.wsxd.sync.service.SysUserService;
import com.wsxd.sync.util.DateUtils;
import com.wsxd.sync.util.Pager;
import com.wsxd.sync.util.ResultUtil;
import com.wsxd.sync.util.StringUtil;

/**
 * 权限资源controller
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午1:32:50
 */
@Scope("prototype")
@Controller
@RequestMapping("/power")
public class PowerController extends BaseController {

	@Autowired
	private PowerService powerService;

	@Autowired
	private SysUserService sysUserService;
	private static final Logger logger = LoggerFactory.getLogger(PowerController.class);

	/**
	 * 资源列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:40:10
	 */
	@RequestMapping("/resourcesList")
	@ResponseBody
	public ResultUtil resourcesList(HttpServletRequest request, HttpServletResponse response) {
		String resName = request.getParameter("resName");
		String resKey = request.getParameter("resKey");
		String resType = request.getParameter("resourceType");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");

		if (!Pattern.matches(".{1,32}", resName)) {
			resName = null;
		}

		if (!Pattern.matches(".{1,32}", resKey)) {
			resKey = null;
		}

		if (!Pattern.matches(".{1,32}", resType)) {
			resType = null;
		}

		if (!StringUtil.isNumber(page)) {
			page = Constants.PAGE;
		}

		if (!StringUtil.isNumber(pageSize)) {
			pageSize = Constants.PAGE_SIZE;
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("resName", resName);
		param.put("resKey", resKey);
		param.put("resType", resType);
		param.put("page", page);
		param.put("pageSize", pageSize);
		Pager pager = null;
		try {
			pager = powerService.queryResources(param);
		} catch (Exception e) {
			logger.error("查询资源列表异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("resourceList", pager.getList()).set("totalSize", Integer.valueOf(pager.getItemsTotal()));
	}

	/**
	 * 当前用户的所有资源
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月17日 上午10:12:51
	 */
	@RequestMapping("/currentResource")
	@ResponseBody
	public ResultUtil currentResource(HttpServletRequest request, HttpServletResponse response) {
		SysUser sysUser = currentUser(request);

		RoleRs rs = null;
		try {
			rs = powerService.selectByUsername(sysUser.getUsername());
		} catch (Exception e) {
			logger.error("通过管理员名获取权限资源异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		if (rs == null) {
			logger.error("通过管理员名获取权限资源为空");
			return ResultUtil.ok().set("code", StatusCode.SYS_USER_401).set("msg", "通过管理员名获取权限资源为空");
		}

		rs.setUsername(sysUser.getUsername());
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("resourcesList", rs);

	}

	/**
	 * 编辑资源
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:40:22
	 */
	@RequestMapping("/resourcesEdit")
	@ResponseBody
	public ResultUtil resourcesEdit(HttpServletRequest request, HttpServletResponse response) {
		String resourceId = request.getParameter("resourceId");
		String parentId = request.getParameter("parentId");
		String resType = request.getParameter("resType");
		String sort = request.getParameter("sort");
		String icon = request.getParameter("icon");
		String status = request.getParameter("status");
		String resKey = request.getParameter("resKey");
		String resName = request.getParameter("resName");
		String url = request.getParameter("url");

		if (!Pattern.matches(".{1,32}", resName)) {
			logger.error("资源名称长度不合法,resName:" + resName);
			return ResultUtil.fail().set("code", StatusCode.POWER_404).set("msg", "资源名称长度不合法");
		}

		if (!Pattern.matches(".{1,128}", url)) {
			logger.error("资源路径长度不合法,url:" + url);
			return ResultUtil.fail().set("code", StatusCode.POWER_405).set("msg", "资源路径长度不合法");
		}

		if (!Pattern.matches(".{0,128}", icon)) {
			logger.error("菜单图标不合法,icon:" + icon);
			return ResultUtil.fail().set("code", StatusCode.POWER_424).set("msg", "菜单图标不合法");
		}

		if (StringUtil.isEmpty(resourceId)) {
			if (!Pattern.matches("[1-4]", resType)) {
				logger.error("资源类型不合法,resType:" + resType);
				return ResultUtil.fail().set("code", StatusCode.POWER_407).set("msg", "资源类型不合法");
			}

			if (!"1".equals(resType)) {
				if (!Pattern.matches(".{1,32}", parentId)) {
					logger.error("父级ID不合法,parentId:" + parentId);
					return ResultUtil.fail().set("code", StatusCode.POWER_406).set("msg", "父级ID不合法");
				}
			}

			if (!Pattern.matches("[a-zA-Z_]{1,128}", resKey)) {
				logger.error("key不合法,resKey:" + resKey);
				return ResultUtil.fail().set("code", StatusCode.POWER_408).set("msg", "key不合法,支持数字字母下划线");
			}

			resourceId = null;
			boolean r = false;
			try {
				r = powerService.exists(resKey);
			} catch (Exception e) {
				logger.error("查询资源key是否存在异常", e);
				return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
			}
			if (r) {
				return ResultUtil.fail().set("code", StatusCode.POWER_402).set("msg", "新增资源key已经存在");
			}
		}

		SysUser sysUser = currentUser(request);

		Resource resource = new Resource(resourceId, parentId, resType, sort, resKey, resName, icon, url, status);
		resource.setUpdateBy(sysUser.getUsername());
		resource.setUpdateTime(DateUtils.toStr());
		if (resourceId == null) {
			resource.setCreateBy(sysUser.getUsername());
			resource.setCreateTime(DateUtils.toStr());
			resource.setUpdateBy(sysUser.getUsername());
			resource.setUpdateTime(DateUtils.toStr());
		}
		try {
			powerService.saveResource(resource);
		} catch (Exception e) {
			logger.error("资源编辑保存存在异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}

		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS);
	}

	/**
	 * 删除资源
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:40:34
	 */
	@RequestMapping("/resourcesRemove")
	@ResponseBody
	public ResultUtil resourcesRemove(HttpServletRequest request, HttpServletResponse response) {
		String resKeys = request.getParameter("resKeys");

		if (!Pattern.matches("[a-zA-Z,_]{1,}", resKeys)) {
			logger.error("删除资源key不合法,resKeys:" + resKeys);
			return ResultUtil.fail().set("code", StatusCode.POWER_415).set("msg", "删除资源key不合法");
		}

		List<String> list = Arrays.asList(resKeys.split(","));
		int row = 0;
		try {
			row = powerService.deleteResources(list);
		} catch (Exception e) {
			logger.error("删除资源存在异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}

		if (row == 0) {
			logger.error("删除资源失败");
			return ResultUtil.fail().set("code", StatusCode.POWER_423).set("msg", "删除资源失败");
		}
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("row", Integer.valueOf(row));
	}

	/**
	 * 角色列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:40:42
	 */
	@RequestMapping("/rolesList")
	@ResponseBody
	public ResultUtil rolesList(HttpServletRequest request, HttpServletResponse response) {
		String roleName = request.getParameter("roleName");
		String score = request.getParameter("score");
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");

		if (StringUtil.isEmpty(score)) {
			score = null;
		}

		if (StringUtil.isEmpty(roleName)) {
			roleName = null;
		}

		if (!StringUtil.isNumber(page)) {
			page = Constants.PAGE;
		}

		if (!StringUtil.isNumber(pageSize)) {
			pageSize = Constants.PAGE_SIZE;
		}

		Role role = null;
		try {
			role = sysUserService.selectRoleByUserId(currentUser(request).getSysUserId());
		} catch (Exception e) {
			logger.error("加载角色信息异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}

		if (role == null) {
			return ResultUtil.fail().set("code", StatusCode.POWER_413);
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("score", score);
		param.put("roleName", roleName);
		param.put("page", page);
		param.put("pageSize", pageSize);
		param.put("currentScore", role.getScore());
		param.put("currentUsername", currentUser(request).getUsername());

		Pager pager = null;
		List<ResourceBean> list2 = null;
		try {
			pager = powerService.selectRoles(param);
			list2 = powerService.loadCurrentResource(role.getRoleId());
		} catch (Exception e) {
			logger.error("查询角色列表存在异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("roleResourcesList", pager.getList()).set("totalSize", Integer.valueOf(pager.getItemsTotal())).set("resourcesList", list2)
				.set("score", role.getScore());
	}

	/**
	 * 编辑角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:40:49
	 */
	@RequestMapping("/rolesEdit")
	@ResponseBody
	public ResultUtil rolesEdit(HttpServletRequest request, HttpServletResponse response) {
		String roleId = request.getParameter("roleId");

		String score = request.getParameter("score");

		String roleName = request.getParameter("roleName");

		String resKeys = request.getParameter("resKeys");

		if (!Pattern.matches("[1-9]|10", score)) {
			logger.error("编辑角色score不合法,score:" + score);
			return ResultUtil.fail().set("code", StatusCode.POWER_409).set("msg", "编辑角色score不合法");
		}

		if (!Pattern.matches(".{1,32}", roleName)) {
			logger.error("角色名称长度不合法,roleName:" + roleName);
			return ResultUtil.fail().set("code", StatusCode.POWER_410).set("msg", "角色名称长度不合法");
		}

		if (!Pattern.matches("[a-zA-Z0-9_,]{1,}", resKeys)) {
			logger.error("资源key不合法,resKeys:" + resKeys);
			return ResultUtil.fail().set("code", StatusCode.POWER_411).set("msg", "资源key不合法");
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("roleId", roleId);
		param.put("score", score);
		param.put("roleName", roleName);
		param.put("resKeys", resKeys);
		param.put("createBy", currentUser(request).getUsername());
		try {
			powerService.saveRole(param);
		} catch (Exception e) {
			logger.error("编辑角色存在异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}

		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS);
	}

	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangyi
	 * @time 2018年1月8日 下午1:40:57
	 */
	@RequestMapping("/rolesRemove")
	@ResponseBody
	public ResultUtil rolesRemove(HttpServletRequest request, HttpServletResponse response) {
		String roleIds = request.getParameter("roleIds");

		if (!Pattern.matches("[a-zA-Z0-9,]{1,}", roleIds)) {
			logger.error("角色ID不合法,roleIds:" + roleIds);
			return ResultUtil.fail().set("code", StatusCode.POWER_414);
		}

		List<String> list = Arrays.asList(roleIds.split(","));
		int row = 0;
		try {
			row = powerService.deleteRoles(list);
		} catch (Exception e) {
			logger.error("删除角色出现异常", e);
			return ResultUtil.fail().set("code", StatusCode.REQUEST_SYSTEM_ERROR).set("msg", Constants.SYS_ERROR_MSG);
		}
		return ResultUtil.ok().set("code", StatusCode.REQUEST_SUCCESS).set("row", Integer.valueOf(row));
	}
}