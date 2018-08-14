package com.wsxd.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wsxd.sync.model.power.SysUser;

@Repository
public class SysUserDao extends JdbcDao<SysUser, String> {

	private static final class SysUserMapper implements RowMapper<SysUser> {

		@Override
		public SysUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			SysUser sysUser = new SysUser();
			sysUser.setSysUserId(rs.getString("sysUserId"));
			sysUser.setUsername(rs.getString("username"));
			sysUser.setPassword(rs.getString("password"));
			sysUser.setTrueName(rs.getString("trueName"));
			sysUser.setMobile(rs.getString("mobile"));
			sysUser.setStatus(rs.getString("status"));
			sysUser.setIsShow(rs.getString("isShow"));
			sysUser.setCreateTime(rs.getString("createTime"));
			sysUser.setCreateBy(rs.getString("createBy"));
			return sysUser;
		}
	}

	public SysUser login(String username, String password) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`SYS_USER_ID` `sysUserId`,");
		sb.append(" w1.`USERNAME` `username`,");
		sb.append(" w1.`PASSWORD` `password`,");
		sb.append(" w1.`TRUE_NAME` `trueName`,");
		sb.append(" w1.`MOBILE` `mobile`,");
		sb.append(" w1.`STATUS` `status`,");
		sb.append(" w1.`IS_SHOW` `isShow`,");
		sb.append(" w1.`CREATE_TIME` `createTime`,");
		sb.append(" w1.`CREATE_BY` `createBy`");
		sb.append(" FROM");
		sb.append(" `sys_user` w1");
		sb.append(" WHERE w1.`USERNAME` = ? AND w1.`PASSWORD` = ? AND w1.`STATUS` = 1");

		return (SysUser) queryForObject(sb.toString(), new String[] { username, password }, new SysUserMapper());
	}

	public SysUser selectSysUserById(String userId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`SYS_USER_ID` `sysUserId`,");
		sb.append(" w1.`USERNAME` `username`,");
		sb.append(" w1.`PASSWORD` `password`,");
		sb.append(" w1.`TRUE_NAME` `trueName`,");
		sb.append(" w1.`MOBILE` `mobile`,");
		sb.append(" w1.`STATUS` `status`,");
		sb.append(" w1.`IS_SHOW` `isShow`,");
		sb.append(" w1.`CREATE_TIME` `createTime`,");
		sb.append(" w1.`CREATE_BY` `createBy`");
		sb.append(" FROM");
		sb.append(" `sys_user` w1");
		sb.append(" WHERE w1.`SYS_USER_ID` = ? AND w1.`STATUS` = 1");

		return (SysUser) queryForObject(sb.toString(), new String[] { userId }, new SysUserMapper());
	}
}