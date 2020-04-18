package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysApplicationDTO;
import com.kivi.dashboard.sys.entity.SysApplication;
import com.kivi.dashboard.sys.mapper.SysApplicationMapper;
import com.kivi.dashboard.sys.service.ISysApplicationService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 系统应用 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysApplicationDubboServiceImpl extends ServiceImpl<SysApplicationMapper, SysApplication>
		implements ISysApplicationService {

	@Autowired
	KtfCommonProperties		ktfCommonProperties;

	@Autowired
	ISysApplicationService	sysApplicationService;

	/**
	 * 根据ID查询系统应用
	 */
	@KtfTrace("根据ID查询系统应用")
	@Override
	public SysApplicationDTO getDTOById(Long id) {
		return sysApplicationService.getDTOById(id);
	}

	/**
	 * 新增系统应用
	 */
	@KtfTrace("新增系统应用")
	@Override
	public Boolean save(SysApplicationDTO sysApplicationDTO) {
		return sysApplicationService.save(sysApplicationDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改系统应用")
	@Override
	public Boolean updateById(SysApplicationDTO sysApplicationDTO) {
		return sysApplicationService.updateById(sysApplicationDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表系统应用")
	@Override
	public List<SysApplicationDTO> list(SysApplicationDTO sysApplicationDTO) {
		return sysApplicationService.list(sysApplicationDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表系统应用")
	@Override
	public List<SysApplicationDTO> list(Map<String, Object> params, String... columns) {
		return sysApplicationService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询系统应用")
	@Override
	public List<SysApplicationDTO> listLike(SysApplicationDTO applicationDTO) {
		return sysApplicationService.listLike(applicationDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysApplicationDTO> listLike(Map<String, Object> params, String... columns) {
		return sysApplicationService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询系统应用")
	public PageInfoVO<SysApplicationDTO> page(Map<String, Object> params) {
		return sysApplicationService.page(params);

	}

	@Override
	public Long getOrCreate(String code) {
		return sysApplicationService.getOrCreate(code);
	}

}
