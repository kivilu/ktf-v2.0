package com.kivi.dashboard.permission.dubbo.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysUserOrgDTO;
import com.kivi.dashboard.permission.entity.SysUserOrg;
import com.kivi.dashboard.permission.mapper.SysUserOrgMapper;
import com.kivi.dashboard.permission.service.SysUserOrgService;
import com.kivi.framework.properties.KtfDashboardProperties;

/**
 * <p>
 * 监管用户与企业关联 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysUserEnterpriseDubboServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrg>
		implements SysUserOrgService {

	@Autowired
	private SysUserOrgService sysUserOrgService;

	@Override
	public List<Long> selectOrgIdByUserId(Long userId) {
		return sysUserOrgService.selectOrgIdByUserId(userId);
	}

	@Override
	public void saveOrUpdateUserOrg(Long userId, List<Long> orgIds) {
		sysUserOrgService.saveOrUpdateUserOrg(userId, orgIds);
	}

	@Override
	public Boolean deleteBatchByUserIds(Long[] userIds) {
		return sysUserOrgService.deleteBatchByUserIds(userIds);
	}

	@Override
	public Boolean deleteBatchByOrgIds(Long[] orgIds) {
		return sysUserOrgService.deleteBatchByOrgIds(orgIds);
	}

	@Override
	public SysUserOrgDTO getDto(Long id) {
		return sysUserOrgService.getDto(id);
	}

}
