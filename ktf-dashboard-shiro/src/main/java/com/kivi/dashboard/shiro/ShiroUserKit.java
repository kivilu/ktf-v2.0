package com.kivi.dashboard.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kivi.dashboard.shiro.service.ShiroUserService;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.vo.ResourceVo;
import com.kivi.framework.vo.RoleVo;
import com.kivi.framework.vo.UserVo;
import com.vip.vjtools.vjkit.collection.ListUtil;

@Component
public class ShiroUserKit {

	@Autowired
	private ShiroUserService shiroUserService;

	/**
	 * 将UserVo赋值给shiroUser
	 *
	 * @param userVo
	 * @return
	 */
	public ShiroUser userVoToShiroUser(UserVo userVo) {
		if (userVo == null) {
			return null;
		} else {
			ShiroUser su = new ShiroUser();
			su.setId(userVo.getId());
			su.setCifId(userVo.getCifId());
			su.setName(userVo.getName());
			su.setLoginName(userVo.getLoginName());
			su.setUserType(userVo.getUserType());
			su.setStatus(userVo.getStatus());
			su.setIsLeader(userVo.getIsLeader());
			su.setLastIp(userVo.getLastIp());
			su.setLastTime(DateTimeKit.toDate(userVo.getLastTime()));
			List<RoleVo>	rvList	= userVo.getRoles();
			List<String>	urlSet	= new ArrayList<>();
			List<String>	roles	= new ArrayList<>();
			if (rvList != null && !rvList.isEmpty()) {
				for (RoleVo rv : rvList) {
					roles.add(rv.getName());
					List<ResourceVo> rList = shiroUserService.getRoleById(rv.getId()).getPermissions();
					if (rList != null && !rList.isEmpty()) {
						for (ResourceVo r : rList) {
							if (StringUtils.isNotBlank(r.getUrl())) {
								urlSet.add(r.getUrl());
							}
						}
					}
				}
			}
			su.setRoles(roles);
			su.setUrlSet(urlSet);
			List<Long>	enterpriseIdList	= new ArrayList<>();
			List<Long>	enterpriseIds		= shiroUserService.getEnterpriseIdByUserId(userVo.getId());
			if (enterpriseIds != null && enterpriseIds.size() > 0) {
				enterpriseIdList.addAll(enterpriseIds);
			}
			if (userVo.getEnterpriseId() != null) {
				enterpriseIdList.add(userVo.getEnterpriseId());
			}
			su.setEnterpriseIdList(removeDuplicate(enterpriseIdList));
			su.setEnterpriseId(userVo.getEnterpriseId());
			su.setDepartmentId(userVo.getDepartmentId());
			su.setJobId(userVo.getJobId());
			return su;
		}
	}

	/**
	 * list去重复
	 *
	 * @param list
	 * @return
	 */
	public static List<Long> removeDuplicate(List<Long> list) {
		ListUtil.uniqueNotNullList(list);
		return list;
	}
}
