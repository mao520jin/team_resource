package com.wsxd.sync.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wsxd.sync.common.Constants;
import com.wsxd.sync.common.enums.StatusEnum;
import com.wsxd.sync.dao.PowerDao;
import com.wsxd.sync.model.power.Resource;
import com.wsxd.sync.model.power.ResourceBean;
import com.wsxd.sync.model.power.Role;
import com.wsxd.sync.model.power.RoleResource;
import com.wsxd.sync.model.power.RoleRs;
import com.wsxd.sync.util.DateUtils;
import com.wsxd.sync.util.Pager;
import com.wsxd.sync.util.StringUtil;

/**
 * 权限业务层
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月8日 下午2:16:45
 */
@Service
public class PowerService {

	private static Log logger = LogFactory.getLog(PowerService.class);

	@Autowired
	private PowerDao powerDao;

	/**
	 * 用户名查询资源
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:16:57
	 */
	public RoleRs selectByUsername(String username) throws SQLException {
		List<Map<String, Object>> list1 = (List<Map<String, Object>>) powerDao.selectByUsername(username);
		if (list1 == null || list1.size() == 0) {
			return null;
		}
		Map<String, List<Map<String, Object>>> map1 = list1.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("resKey1")).trim()));
		List<ResourceBean> list2 = new ArrayList<ResourceBean>();
		for (String k1 : map1.keySet()) {
			List<Map<String, Object>> l1 = map1.get(k1);
			Map<String, List<Map<String, Object>>> map2 = l1.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("resKey2")).trim()));
			List<ResourceBean> list3 = new ArrayList<ResourceBean>();
			for (String k2 : map2.keySet()) {
				List<Map<String, Object>> l2 = map2.get(k2);
				Map<String, List<Map<String, Object>>> map3 = l2.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("resKey3")).trim()));
				List<ResourceBean> list4 = new ArrayList<ResourceBean>();
				for (String k3 : map3.keySet()) {
					List<Map<String, Object>> l3 = map3.get(k3);

					Object[] s = l3.stream().map(m -> String.valueOf(m.get("resKey4"))).collect(Collectors.toList()).toArray();
					ResourceBean rb3 = new ResourceBean(String.valueOf(l3.get(0).get("resName3")), String.valueOf(l3.get(0).get("resKey3")), String.valueOf(l3.get(0).get("sort3")),
							String.valueOf(l3.get(0).get("url3")), String.valueOf(l3.get(0).get("icon3")), s);
					list4.add(rb3);
				}
				ResourceBean rb4 = new ResourceBean(String.valueOf(l2.get(0).get("resName2")), String.valueOf(l2.get(0).get("resKey2")), String.valueOf(l2.get(0).get("sort2")),
						String.valueOf(l2.get(0).get("url2")), String.valueOf(l2.get(0).get("icon2")), list4);
				list3.add(rb4);
			}
			ResourceBean rb5 = new ResourceBean(String.valueOf(l1.get(0).get("resName1")), String.valueOf(l1.get(0).get("resKey1")), String.valueOf(l1.get(0).get("sort1")),
					String.valueOf(l1.get(0).get("url1")), String.valueOf(l1.get(0).get("icon1")), list3);
			list2.add(rb5);
		}
		RoleRs rs = new RoleRs(String.valueOf(list1.get(0).get("roleName")), String.valueOf(list1.get(0).get("score")), list2);
		return rs;
	}

	/**
	 * 查询当前角色资源
	 * 
	 * @param roleId
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:17:14
	 */
	public List<ResourceBean> loadCurrentResource(String roleId) throws SQLException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) powerDao.loadCurrentResource(roleId);
		Map<String, List<Map<String, Object>>> map1 = list.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("resKey1")).trim()));
		List<ResourceBean> list2 = new ArrayList<ResourceBean>();
		for (String k1 : map1.keySet()) {
			List<Map<String, Object>> l1 = map1.get(k1);
			Map<String, List<Map<String, Object>>> map2 = l1.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("resKey2")).trim()));
			List<ResourceBean> list3 = new ArrayList<ResourceBean>();
			for (String k2 : map2.keySet()) {
				List<Map<String, Object>> l2 = map2.get(k2);
				Map<String, List<Map<String, Object>>> map3 = l2.stream().collect(Collectors.groupingBy(m -> String.valueOf(m.get("resKey3")).trim()));
				List<ResourceBean> list4 = new ArrayList<ResourceBean>();
				for (String k3 : map3.keySet()) {
					List<Map<String, Object>> l3 = map3.get(k3);
					List<ResourceBean> l4 = new ArrayList<ResourceBean>();

					for (Map<String, Object> m2 : l3) {
						ResourceBean rb4 = new ResourceBean(String.valueOf(m2.get("resName4")), String.valueOf(m2.get("resKey4")));
						l4.add(rb4);
					}
					ResourceBean rb5 = new ResourceBean(String.valueOf(l3.get(0).get("resName3")), String.valueOf(l3.get(0).get("resKey3")), String.valueOf(l3.get(0).get("sort3")), l4);
					list4.add(rb5);
				}
				ResourceBean rb6 = new ResourceBean(String.valueOf(l2.get(0).get("resName2")), String.valueOf(l2.get(0).get("resKey2")), String.valueOf(l2.get(0).get("sort2")), list4);
				list3.add(rb6);
			}
			ResourceBean rb7 = new ResourceBean(String.valueOf(l1.get(0).get("resName1")), String.valueOf(l1.get(0).get("resKey1")), String.valueOf(l1.get(0).get("sort1")), list3);
			list2.add(rb7);
		}
		return list2;
	}

	/**
	 * 编辑资源
	 * 
	 * @param resource
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:17:29
	 */
	public int saveResource(Resource resource) throws SQLException {
		int row = 0;
		if (resource.getResourceId() == null) {
			resource.setResourceId(StringUtil.getUUID32());
			resource.setStatus(StatusEnum.VALID.getStatus());
			row = powerDao.insertResource(resource);

			// 每次新增一个资源，同步把该资源加给admin角色
			RoleResource roleResource = new RoleResource(StringUtil.getUUID32(), Constants.SYS_USERNAME, resource.getResourceId());
			powerDao.insertRoleResource(roleResource);
		} else {
			row = powerDao.updateResource(resource);
		}
		return row;
	}

	/**
	 * key是否存在
	 * 
	 * @param resKey
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:17:38
	 */
	public boolean exists(String resKey) throws SQLException {
		return powerDao.existsResource(resKey);
	}

	/**
	 * 资源列表
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:17:45
	 */
	public Pager queryResources(Map<String, String> param) throws SQLException {
		return powerDao.resourcesList(param);
	}

	/**
	 * 删除资源
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:17:54
	 */
	public int deleteResources(List<String> list) throws SQLException {
		int row = 0;
		for (String key : list) {
			if (StringUtil.isEmpty(key)) {
				continue;
			}
			boolean r = powerDao.existsChildrenResource(key);
			if (r) {
				return 0;
			}
			row += powerDao.resourcesRemove(key);
		}
		return row;
	}

	/**
	 * 角色列表
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:18:05
	 */
	public Pager selectRoles(Map<String, String> param) throws SQLException {
		Pager pager = powerDao.rolesList(param);
		List<Map<String, Object>> list = (List<Map<String, Object>>) pager.getList();
		if (list == null || list.size() == 0) {
			return pager;
		}
		for (Map<String, Object> map : list) {
			Object roleId = map.get("roleId");
			List<Map<String, Object>> l = (List<Map<String, Object>>) powerDao.queryRoleResourcesByRoleId(roleId);
			List<String> keys = l.stream().map(m -> String.valueOf(m.get("resKey"))).collect(Collectors.toList());
			map.put("resKeys", keys);
		}
		return pager;
	}

	/**
	 * 删除资源
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:18:17
	 */
	public int deleteRoles(List<String> list) throws SQLException {
		int row = 0;
		for (String roleId : list) {
			row += powerDao.rolesRemove(roleId);
		}
		return row;
	}

	/**
	 * 编辑资源
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 * @author zhangyi
	 * @time 2018年1月8日 下午2:18:25
	 */
	public int saveRole(Map<String, String> param) throws SQLException {
		String roleId = param.get("roleId");
		String score = param.get("score");
		String roleName = param.get("roleName");
		String resKeys = param.get("resKeys");
		String createBy = param.get("createBy");
		Role role = new Role(roleId, roleName, score, StatusEnum.VALID.getStatus(), DateUtils.toStr(), createBy);
		String newRoleId = StringUtil.getUUID32();
		if (!StringUtil.isEmpty(role.getRoleId())) {
			powerDao.clearRole(role.getRoleId());
			powerDao.updateRole(role);
		} else {
			role.setRoleId(newRoleId);
			powerDao.insertRole(role);
		}
		List<String> list = Arrays.asList(resKeys.split(","));
		int row = 0;
		for (String key : list) {
			String resourceId = powerDao.getResourceId(key);
			roleId = ((roleId == null) ? newRoleId : roleId);
			RoleResource roleResource = new RoleResource(StringUtil.getUUID32(), roleId, resourceId);
			row += powerDao.insertRoleResource(roleResource);
		}
		return row;
	}
}