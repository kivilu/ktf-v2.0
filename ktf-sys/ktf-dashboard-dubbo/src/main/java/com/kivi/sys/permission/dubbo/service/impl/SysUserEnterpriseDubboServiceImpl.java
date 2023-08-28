package com.kivi.sys.permission.dubbo.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.sys.permission.dto.SysUserOrgDTO;
import com.kivi.sys.permission.entity.SysUserOrg;
import com.kivi.sys.permission.mapper.SysUserOrgMapper;
import com.kivi.sys.permission.service.SysUserOrgService;

/**
 * <p>
 * 监管用户与企业关联 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
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
