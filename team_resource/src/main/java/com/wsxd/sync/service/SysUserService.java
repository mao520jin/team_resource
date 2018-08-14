package com.wsxd.sync.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsxd.sync.common.enums.StatusEnum;
import com.wsxd.sync.dao.PowerDao;
import com.wsxd.sync.dao.RoleDao;
import com.wsxd.sync.dao.SysUserDao;
import com.wsxd.sync.model.power.Role;
import com.wsxd.sync.model.power.SysUser;
import com.wsxd.sync.model.power.UserRole;
import com.wsxd.sync.util.Pager;
import com.wsxd.sync.util.StringUtil;

/**
 * 系统管理员业务层
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午2:18:47
 */
@Service
public class SysUserService {

	private static Log logger = LogFactory.getLog(SysUserService.class);

	@Autowired
	private PowerDao powerDao;

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private RoleDao roleDao;

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:18:56
	 */
	public SysUser login(String username, String password) throws SQLException {
		return sysUserDao.login(username, password);
	}

	/**
	 * 查询用户对象
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:19:01
	 */
	public SysUser selectSysUserById(String userId) throws SQLException {
		return sysUserDao.selectSysUserById(userId);
	}

	/**
	 * 查询角色对象
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:19:09
	 */
	public Role selectRoleByUserId(String userId) throws SQLException {
		return roleDao.selectRoleByUserId(userId);
	}

	/**
	 * 查询该用户创建的角色
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:19:17
	 */
	public List<Map<String, Object>> loadRolesByUsername(String username) throws SQLException {
		return powerDao.loadRolesByUsername(username);
	}

	/**
	 * 用户列表
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:19:34
	 */
	public Pager querySysUsers(Map<String, String> param) throws SQLException {
		return powerDao.querySysUsers(param);
	}

	/**
	 * 编辑管理员
	 * 
	 * @param sysUser
	 * @param roleId
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:19:45
	 */
	public void editSysUser(SysUser sysUser, String roleId) throws SQLException {
		String userId = null;

		if (sysUser.getSysUserId() == null) {
			userId = StringUtil.getUUID32();
			sysUser.setStatus(StatusEnum.VALID.getStatus());
			sysUser.setSysUserId(userId);
			powerDao.inserSysUser(sysUser);
		} else {
			userId = sysUser.getSysUserId();
			powerDao.updateSysUser(sysUser);
		}

		powerDao.deleteSysUserRole(userId);

		if (!StringUtil.isEmpty(roleId)) {
			UserRole userRole = new UserRole(StringUtil.getUUID32(), userId, roleId);
			powerDao.insertUserRole(userRole);
		}
	}

	/**
	 * 删除管理员
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:20:15
	 */
	public int deleteUser(List<String> list) throws SQLException {
		int row = 0;
		for (String userId : list) {
			row += powerDao.deleteUser(userId);
		}
		return row;
	}

	/**
	 * 查询该用户拥有的所有4级权限菜单url信息
	 * 
	 * @param sysUserId
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:20:26
	 */
	public List<String> selectUrlsByUserId(String sysUserId) throws SQLException {
		return powerDao.selectUrlsByUserId(sysUserId);
	}
}