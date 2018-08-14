package com.wsxd.sync.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wsxd.sync.common.Constants;
import com.wsxd.sync.model.power.Resource;
import com.wsxd.sync.model.power.Role;
import com.wsxd.sync.model.power.RoleResource;
import com.wsxd.sync.model.power.SysUser;
import com.wsxd.sync.model.power.UserRole;
import com.wsxd.sync.util.Pager;

@Repository
public class PowerDao extends JdbcDao<Resource, String> {
	public List<Map<String, Object>> selectByUsername(String username) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`RES_NAME` `resName1`,");
		sb.append(" w1.`RES_KEY` `resKey1`,");
		sb.append(" w1.`SORT` `sort1`,");
		sb.append(" w1.`URL` `url1`,");
		sb.append(" w1.`ICON` `icon1`,");
		sb.append(" w2.`RES_NAME` `resName2`,");
		sb.append(" w2.`RES_KEY` `resKey2`,");
		sb.append(" w2.`SORT` `sort2`,");
		sb.append(" w2.`URL` `url2`,");
		sb.append(" w2.`ICON` `icon2`,");
		sb.append(" w3.`RES_NAME` `resName3`,");
		sb.append(" w3.`RES_KEY` `resKey3`,");
		sb.append(" w3.`SORT` `sort3`,");
		sb.append(" w3.`URL` `url3`,");
		sb.append(" w3.`ICON` `icon3`,");
		sb.append(" w4.`RES_KEY` `resKey4`,");
		sb.append(" w7.`ROLE_NAME` `roleName`,");
		sb.append(" w7.`SCORE`");
		sb.append(" FROM");
		sb.append(" `resource` w1,");
		sb.append(" `resource` w2,");
		sb.append(" `resource` w3,");
		sb.append(" `resource` w4,");
		sb.append(" `sys_user` w5,");
		sb.append(" `user_role` w6,");
		sb.append(" `role` w7,");
		sb.append(" `role_resource` w8");
		sb.append(" WHERE w1.`RESOURCE_ID` = w2.`PARENT_ID` AND w1.`STATUS` = 1 AND w2.`STATUS` = 1 AND w2.`RESOURCE_ID` = w3.`PARENT_ID` AND w3.`STATUS` = 1 AND w3.`RESOURCE_ID` = w4.`PARENT_ID` ");
		sb.append(
				" AND w4.`STATUS` = 1 AND w5.`SYS_USER_ID` = w6.`SYS_USER_ID` AND w6.`ROLE_ID` = w7.`ROLE_ID` AND w7.`ROLE_ID` = w8.`ROLE_ID` AND w8.`RESOURCE_ID` = w4.`RESOURCE_ID` AND w5.`STATUS` = 1");
		sb.append(" AND w5.`USERNAME` = ? ORDER BY w1.`CREATE_TIME` ASC, w2.`CREATE_TIME` ASC, w3.`CREATE_TIME` ASC, w4.`CREATE_TIME` ASC");

		return queryForList(sb.toString(), new String[] { username });
	}

	public List<Map<String, Object>> loadCurrentResource(String roleId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`RES_NAME` `resName1`,");
		sb.append(" w1.`RES_KEY` `resKey1`,");
		sb.append(" w1.`SORT` `sort1`,");
		sb.append(" w1.`URL` `url1`,");
		sb.append(" w2.`RES_NAME` `resName2`,");
		sb.append(" w2.`RES_KEY` `resKey2`,");
		sb.append(" w2.`SORT` `sort2`,");
		sb.append(" w2.`URL` `url2`,");
		sb.append(" w3.`RES_NAME` `resName3`,");
		sb.append(" w3.`RES_KEY` `resKey3`,");
		sb.append(" w3.`SORT` `sort3`,");
		sb.append(" w3.`URL` `url3`,");
		sb.append(" w4.`RES_NAME` `resName4`,");
		sb.append(" w4.`RES_KEY` `resKey4`");
		sb.append(" FROM");
		sb.append(" `resource` w1,");
		sb.append(" `resource` w2,");
		sb.append(" `resource` w3,");
		sb.append(" `resource` w4,");
		sb.append(" `role_resource` w5");
		sb.append(
				" WHERE w1.`RESOURCE_ID` = w2.`PARENT_ID` AND w1.`STATUS` = 1 AND w2.`STATUS` = 1 AND w2.`RESOURCE_ID` = w3.`PARENT_ID` AND w3.`STATUS` = 1 AND w3.`RESOURCE_ID` = w4.`PARENT_ID` AND w4.`STATUS` = 1 ");
		sb.append(" AND w4.`RESOURCE_ID` = w5.`RESOURCE_ID` AND w5.`ROLE_ID` = ?");

		return queryForList(sb.toString(), new String[] { roleId });
	}

	public int insertResource(Resource resource) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO `resource` (");
		sb.append(" ").append("`RESOURCE_ID`");
		sb.append(",").append("`PARENT_ID`");
		sb.append(",").append("`RES_TYPE`");
		sb.append(",").append("`SORT`");
		sb.append(",").append("`RES_KEY`");
		sb.append(",").append("`RES_NAME`");
		sb.append(",").append("`ICON`");
		sb.append(",").append("`URL`");
		sb.append(",").append("`STATUS`");
		sb.append(",").append("`CREATE_TIME`");
		sb.append(",").append("`CREATE_BY`");
		sb.append(",").append("`UPDATE_TIME`");
		sb.append(",").append("`UPDATE_BY`");
		sb.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		List<String> params = new ArrayList<String>();
		params.add(resource.getResourceId());
		params.add(resource.getParentId());
		params.add(resource.getResType());
		params.add(resource.getSort());
		params.add(resource.getResKey());
		params.add(resource.getResName());
		params.add(resource.getIcon());
		params.add(resource.getUrl());
		params.add(resource.getStatus());
		params.add(resource.getCreateTime());
		params.add(resource.getCreateBy());
		params.add(resource.getUpdateTime());
		params.add(resource.getUpdateBy());

		return update(sb.toString(), params.toArray());
	}

	public int updateResource(Resource resource) throws SQLException {
		StringBuffer sb = new StringBuffer();
		List<String> params = new ArrayList<String>();

		sb.append("UPDATE `resource` SET `RESOURCE_ID` = ?");
		params.add(resource.getResourceId());

		if (resource.getSort() != null) {
			sb.append(", `SORT` = ?");
			params.add(resource.getSort());
		}

		if (resource.getResType() != null) {
			sb.append(", `RES_TYPE` = ?");
			params.add(resource.getResType());
		}

		if (resource.getUrl() != null) {
			sb.append(", `URL` = ?");
			params.add(resource.getUrl());
		}

		if (resource.getIcon() != null) {
			sb.append(", `ICON` = ?");
			params.add(resource.getIcon());
		}

		if (resource.getResName() != null) {
			sb.append(", `RES_NAME` = ?");
			params.add(resource.getResName());
		}

		if (resource.getStatus() != null) {
			sb.append(", `STATUS` = ?");
			params.add(resource.getStatus());
		}

		if (resource.getUpdateTime() != null) {
			sb.append(", `UPDATE_TIME` = ?");
			params.add(resource.getUpdateTime());
		}

		if (resource.getUpdateBy() != null) {
			sb.append(", `UPDATE_BY` = ?");
			params.add(resource.getUpdateBy());
		}

		sb.append(" WHERE `RESOURCE_ID` = ?");
		params.add(resource.getResourceId());

		return update(sb.toString(), params.toArray());
	}

	public boolean existsResource(String resKey) throws SQLException {
		String sql = "select count(1) from `resource` where `RES_KEY` = ? AND `status` = 1";
		String rs = querySingleObject(sql, String.class, new String[] { resKey });
		return Integer.parseInt(rs) > 0;
	}

	public Pager resourcesList(Map<String, String> param) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" `RESOURCE_ID` `resourceId`,");
		sb.append("`PARENT_ID` `parentId`,");
		sb.append("`RES_TYPE` `resType`,");
		sb.append("`SORT` sort,");
		sb.append("`RES_KEY` `resKey`,");
		sb.append("`RES_NAME` `resName`,");
		sb.append("`URL` url,");
		sb.append("`ICON` icon,");
		sb.append("`STATUS` status,");
		sb.append("`CREATE_TIME` `createTime`,");
		sb.append("`CREATE_BY` `createBy`,");
		sb.append("`UPDATE_TIME` `updateTime`,");
		sb.append("`UPDATE_BY` `updateBy`");
		sb.append(" FROM");
		sb.append(" `resource` w WHERE 1 = 1 AND `status` = 1");

		String resName = (String) param.get("resName");
		String resKey = (String) param.get("resKey");
		String resType = (String) param.get("resType");
		int page = Integer.parseInt((String) param.get("page"));
		int pageSize = Integer.parseInt((String) param.get("pageSize"));

		List<Object> params = new ArrayList<Object>();
		if (resName != null) {
			sb.append(" AND `RES_NAME` LIKE ?");
			params.add("%" + resName + "%");
		}

		if (resKey != null) {
			sb.append(" AND `RES_KEY` LIKE ?");
			params.add("%" + resKey + "%");
		}

		if (resType != null) {
			sb.append(" AND `RES_TYPE` = ?");
			params.add(resType);
		}

		int totalSize = count(sb.toString(), params.toArray());

		Pager pager = new Pager();
		pager.setItemsTotal(totalSize);
		pager.setItemsPerPage(pageSize);
		pager.setCurPage(page);

		sb.append(" ORDER BY `RES_TYPE` ASC");
		sb.append(" Limit ?, ?");

		params.add(Integer.valueOf(pager.getItemsPerPage() * (pager.getCurPage() - 1)));
		params.add(Integer.valueOf(pager.getItemsPerPage()));

		List<Map<String, Object>> list = queryForList(sb.toString(), params.toArray());
		pager.setList(list);
		return pager;
	}

	public int resourcesRemove(String key) throws SQLException {
		String sql = "update `resource` set `STATUS` = 0 where `RES_KEY` = ?";
		return update(sql, new Object[] { key });
	}

	public Pager rolesList(Map<String, String> param) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`ROLE_ID` `roleId`,");
		sb.append(" w1.`ROLE_NAME` `roleName`,");
		sb.append(" w1.`SCORE` `score`,");
		sb.append(" w1.`CREATE_TIME` `createTime`,");
		sb.append(" w1.`CREATE_BY` `createBy`");
		sb.append(" FROM");
		sb.append(" `role` w1");
		sb.append(" WHERE w1.`STATUS` = 1");

		String score = (String) param.get("score");
		String roleName = (String) param.get("roleName");
		String currentScore = (String) param.get("currentScore");
		int page = Integer.parseInt((String) param.get("page"));
		int pageSize = Integer.parseInt((String) param.get("pageSize"));
		String currentUsername = (String) param.get("currentUsername");

		List<Object> params = new ArrayList<Object>();
		if (!Constants.SYS_USERNAME.equalsIgnoreCase(currentUsername)) {
			sb.append(" AND w1.`CREATE_BY` = ? ");
			params.add(currentUsername);
		}

		if (roleName != null) {
			sb.append(" AND w1.`ROLE_NAME` LIKE ?");
			params.add("%" + roleName + "%");
		}

		if (score != null) {
			sb.append(" AND w1.`SCORE` = ?");
			params.add(score);
		} else {
			sb.append(" AND w1.`SCORE` <= ?");
			params.add(currentScore);
		}

		int totalSize = count(sb.toString(), params.toArray());

		Pager pager = new Pager();
		pager.setItemsTotal(totalSize);
		pager.setItemsPerPage(pageSize);
		pager.setCurPage(page);

		sb.append(" Limit ?, ?");
		params.add(Integer.valueOf(pager.getItemsPerPage() * (pager.getCurPage() - 1)));
		params.add(Integer.valueOf(pager.getItemsPerPage()));

		List<Map<String, Object>> list = queryForList(sb.toString(), params.toArray());
		pager.setList(list);
		return pager;
	}

	public List<Map<String, Object>> queryRoleResourcesByRoleId(Object roleId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w3.`RES_KEY` `resKey`");
		sb.append(" FROM");
		sb.append(" `role` w1,");
		sb.append(" `role_resource` w2,");
		sb.append(" `resource` w3");
		sb.append(" WHERE w1.`ROLE_ID` = w2.`ROLE_ID` AND w2.`RESOURCE_ID` = w3.`RESOURCE_ID` AND w1.`STATUS` = 1 AND w3.`STATUS` = 1");
		sb.append(" AND w1.`ROLE_ID` = ?");

		return queryForList(sb.toString(), new Object[] { roleId });
	}

	public int rolesRemove(String roleId) throws SQLException {
		String sql = "update `role` set `STATUS` = 0 where `ROLE_ID` = ?";
		return update(sql, new Object[] { roleId });
	}

	public int clearRole(String roleId) throws SQLException {
		String sql = "delete from `role_resource` where `role_id` = ?";
		return update(sql, new Object[] { roleId });
	}

	public int insertRoleResource(RoleResource roleResource) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO `role_resource` (");
		sb.append(" ").append("`ROLE_RESOURCE_ID`");
		sb.append(",").append("`ROLE_ID`");
		sb.append(",").append("`RESOURCE_ID`");
		sb.append(") VALUES (?, ?, ?)");

		List<Object> params = new ArrayList<Object>();
		params.add(roleResource.getRoleResourceId());
		params.add(roleResource.getRoleId());
		params.add(roleResource.getResourceId());

		return update(sb.toString(), params.toArray());
	}

	public String getResourceId(String key) throws SQLException {
		String sql = "select `RESOURCE_ID` from `resource` where `RES_KEY` = ?";
		return (String) querySingleObject(sql, String.class, new String[] { key });
	}

	public int updateRole(Role role) throws SQLException {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sb.append("UPDATE `role` SET `ROLE_ID` = ?");
		params.add(role.getRoleId());

		if (role.getRoleName() != null) {
			sb.append(",`ROLE_NAME` = ?");
			params.add(role.getRoleName());
		}

		if (role.getScore() != null) {
			sb.append(", `SCORE` = ?");
			params.add(role.getScore());
		}

		sb.append(" WHERE `ROLE_ID` = ?");
		params.add(role.getRoleId());

		return update(sb.toString(), params.toArray());
	}

	public int insertRole(Role role) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO `role` (");
		sb.append(" ").append("`ROLE_ID`");
		sb.append(",").append("`ROLE_NAME`");
		sb.append(",").append("`SCORE`");
		sb.append(",").append("`STATUS`");
		sb.append(",").append("`CREATE_TIME`");
		sb.append(",").append("`CREATE_BY`");
		sb.append(") VALUES (?, ?, ?, ?, ?, ?)");

		List<Object> params = new ArrayList<Object>();
		params.add(role.getRoleId());
		params.add(role.getRoleName());
		params.add(role.getScore());
		params.add(role.getStatus());
		params.add(role.getCreateTime());
		params.add(role.getCreateBy());

		return update(sb.toString(), params.toArray());
	}

	public Pager querySysUsers(Map<String, String> param) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`SYS_USER_ID` `sysUserId`,");
		sb.append(" w1.`USERNAME` `username`,");
		sb.append(" w1.`TRUE_NAME` `trueName`,");
		sb.append(" w1.`MOBILE` `mobile`,");
		sb.append(" w1.`IS_SHOW` `isShow`,");
		sb.append(" w1.`CREATE_BY` `createBy`,");
		sb.append(" w1.`CREATE_TIME` `createTime`,");
		sb.append(" w3.`ROLE_ID` `roleId`,");
		sb.append(" w3.`ROLE_NAME` `roleName`,");
		sb.append(" w3.`SCORE` `score`");
		sb.append(" FROM");
		sb.append(" `sys_user` w1");
		sb.append(" LEFT JOIN `user_role` w2 ON w1.`SYS_USER_ID` = w2.`SYS_USER_ID`");
		sb.append(" LEFT JOIN `role` w3 ON w2.`ROLE_ID` = w3.`ROLE_ID` AND w3.`STATUS` = 1");
		sb.append(" WHERE w1.`STATUS` = 1");

		List<Object> params = new ArrayList<Object>();
		String currentUsername = (String) param.get("currentUsername");
		if (!Constants.SYS_USERNAME.equalsIgnoreCase(currentUsername)) {
			sb.append(" AND  w1.`CREATE_BY` = ?");
			params.add(currentUsername);
		}

		String username = (String) param.get("username");
		String mobile = (String) param.get("mobile");
		String trueName = (String) param.get("trueName");
		String isShow = (String) param.get("isShow");
		int page = Integer.parseInt((String) param.get("page"));
		int pageSize = Integer.parseInt((String) param.get("pageSize"));

		if (username != null) {
			sb.append(" AND w1.`USERNAME` LIKE ?");
			params.add("%" + username + "%");
		}

		if (mobile != null) {
			sb.append(" AND w1.`MOBILE` LIKE ?");
			params.add("%" + mobile + "%");
		}

		if (trueName != null) {
			sb.append(" AND w1.`TRUE_NAME` LIKE ?");
			params.add("%" + trueName + "%");
		}

		if (isShow != null) {
			sb.append(" AND w1.`IS_SHOW` = ?");
			params.add(isShow);
		}

		int totalSize = count(sb.toString(), params.toArray());

		Pager pager = new Pager();
		pager.setItemsTotal(totalSize);
		pager.setItemsPerPage(pageSize);
		pager.setCurPage(page);

		sb.append(" ORDER BY w1.`CREATE_TIME` DESC");
		sb.append(" Limit ?, ?");

		params.add(Integer.valueOf(pager.getItemsPerPage() * (pager.getCurPage() - 1)));
		params.add(Integer.valueOf(pager.getItemsPerPage()));

		List<Map<String, Object>> list = queryForList(sb.toString(), params.toArray());
		pager.setList(list);
		return pager;
	}

	public int inserSysUser(SysUser sysUser) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO `sys_user` (");
		sb.append(" ").append("`SYS_USER_ID`");
		sb.append(",").append("`USERNAME`");
		sb.append(",").append("`PASSWORD`");
		sb.append(",").append("`TRUE_NAME`");
		sb.append(",").append("`MOBILE`");
		sb.append(",").append("`STATUS`");
		sb.append(",").append("`IS_SHOW`");
		sb.append(",").append("`CREATE_TIME`");
		sb.append(",").append("`CREATE_BY`");
		sb.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		List<Object> params = new ArrayList<Object>();
		params.add(sysUser.getSysUserId());
		params.add(sysUser.getUsername());
		params.add(sysUser.getPassword());
		params.add(sysUser.getTrueName());
		params.add(sysUser.getMobile());
		params.add(sysUser.getStatus());
		params.add(sysUser.getIsShow());
		params.add(sysUser.getCreateTime());
		params.add(sysUser.getCreateBy());

		return update(sb.toString(), params.toArray());
	}

	public int updateSysUser(SysUser sysUser) throws SQLException {
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sb.append("UPDATE `sys_user` SET `SYS_USER_ID` = ?");
		params.add(sysUser.getSysUserId());

		if (sysUser.getUsername() != null) {
			sb.append(", `USERNAME` = ?");
			params.add(sysUser.getUsername());
		}

		if (sysUser.getPassword() != null) {
			sb.append(", `PASSWORD` = ?");
			params.add(sysUser.getPassword());
		}

		if (sysUser.getTrueName() != null) {
			sb.append(", `TRUE_NAME` = ?");
			params.add(sysUser.getTrueName());
		}

		if (sysUser.getMobile() != null) {
			sb.append(", `MOBILE` = ?");
			params.add(sysUser.getMobile());
		}

		if (sysUser.getIsShow() != null) {
			sb.append(", `IS_SHOW` = ?");
			params.add(sysUser.getIsShow());
		}

		if (sysUser.getStatus() != null) {
			sb.append(", `STATUS` = ?");
			params.add(sysUser.getStatus());
		}

		sb.append(" WHERE `SYS_USER_ID` = ?");
		params.add(sysUser.getSysUserId());

		return update(sb.toString(), params.toArray());
	}

	public List<Map<String, Object>> loadRolesByUsername(String username) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`ROLE_ID` `roleId`,");
		sb.append(" w1.`ROLE_NAME` `roleName`");
		sb.append(" FROM");
		sb.append(" `role` w1");
		sb.append(" WHERE w1.`CREATE_BY` = ? AND w1.`STATUS` = 1");

		return queryForList(sb.toString(), new Object[] { username });
	}

	public int deleteSysUserRole(String userId) throws SQLException {
		String sql = "delete from `user_role` where `SYS_USER_ID` = ?";
		return update(sql, new Object[] { userId });
	}

	public int insertUserRole(UserRole userRole) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO `user_role` (");
		sb.append(" ").append("`USER_ROLE_ID`");
		sb.append(",").append("`SYS_USER_ID`");
		sb.append(",").append("`ROLE_ID`");
		sb.append(") VALUES (?, ?, ?)");

		List<Object> params = new ArrayList<Object>();
		params.add(userRole.getUserRoleId());
		params.add(userRole.getSysUserId());
		params.add(userRole.getRoleId());

		return update(sb.toString(), params.toArray());
	}

	public int deleteUser(String sysUserId) throws SQLException {
		String sql = "update `sys_user` set `status` = 0 where `SYS_USER_ID` = ?";
		return update(sql, new Object[] { sysUserId });
	}

	public List<String> selectUrlsByUserId(String sysUserId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w4.`URL`");
		sb.append(" FROM");
		sb.append(" `resource` w1,");
		sb.append(" `resource` w2,");
		sb.append(" `resource` w3,");
		sb.append(" `resource` w4,");
		sb.append(" `sys_user` w5,");
		sb.append(" `user_role` w6,");
		sb.append(" `role` w7,");
		sb.append(" `role_resource` w8");
		sb.append(" WHERE w1.`RESOURCE_ID` = w2.`PARENT_ID` AND w1.`STATUS` = 1 AND w2.`STATUS` = 1 AND w2.`RESOURCE_ID` = w3.`PARENT_ID` AND w3.`STATUS` = 1 AND w3.`RESOURCE_ID` = w4.`PARENT_ID` ");
		sb.append(
				" AND w4.`STATUS` = 1 AND w5.`SYS_USER_ID` = w6.`SYS_USER_ID` AND w6.`ROLE_ID` = w7.`ROLE_ID` AND w7.`ROLE_ID` = w8.`ROLE_ID` AND w8.`RESOURCE_ID` = w4.`RESOURCE_ID` AND w5.`STATUS` = 1");
		sb.append(" AND w5.`USERNAME` = ?");

		return queryForList(sb.toString(), String.class, new String[] { sysUserId });
	}

	public boolean existsChildrenResource(String key) throws SQLException {
		String sql = "select count(1) FROM `resource` w1,`resource` w2 WHERE w1.`RESOURCE_ID` = w2.`PARENT_ID` AND w1.`RES_KEY` = ?";
		String rs = querySingleObject(sql, String.class, new String[] { key });
		return Integer.parseInt(rs) > 0;
	}
}