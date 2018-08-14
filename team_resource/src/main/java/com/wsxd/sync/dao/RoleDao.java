package com.wsxd.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wsxd.sync.model.power.Role;

@Repository
public class RoleDao extends JdbcDao<Role, String> {

	private static final class RoleMapper implements RowMapper<Role> {

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setRoleId(rs.getString("roleId"));
			role.setRoleName(rs.getString("roleName"));
			role.setScore(rs.getString("score"));
			role.setStatus(rs.getString("status"));
			role.setCreateTime(rs.getString("createTime"));
			role.setCreateBy(rs.getString("createBy"));
			return role;
		}
	}

	public Role selectRoleByUserId(String userId) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" w1.`ROLE_ID` `roleId`,");
		sb.append(" w1.`ROLE_NAME` `roleName`,");
		sb.append(" w1.`SCORE` `score`,");
		sb.append(" w1.`STATUS` `status`,");
		sb.append(" w1.`CREATE_TIME` `createTime`,");
		sb.append(" w1.`CREATE_BY` `createBy`");
		sb.append(" FROM");
		sb.append(" `role` w1,");
		sb.append(" `user_role` w2");
		sb.append(" WHERE w1.`ROLE_ID` = w2.`ROLE_ID` AND w1.`STATUS` = 1 AND w2.`SYS_USER_ID` = ?");

		return (Role) queryForObject(sb.toString(), new String[] { userId }, new RoleMapper());
	}
}
