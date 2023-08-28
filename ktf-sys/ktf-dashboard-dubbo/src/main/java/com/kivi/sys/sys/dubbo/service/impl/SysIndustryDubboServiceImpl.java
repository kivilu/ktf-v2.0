package com.kivi.sys.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysIndustryDTO;
import com.kivi.sys.sys.entity.SysIndustry;
import com.kivi.sys.sys.mapper.SysIndustryMapper;
import com.kivi.sys.sys.service.ISysIndustryService;

/**
 * <p>
 * 行业代码 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysIndustryDubboServiceImpl extends ServiceImpl<SysIndustryMapper, SysIndustry>
		implements ISysIndustryService {

	@Autowired
	ISysIndustryService sysIndustryService;

	/**
	 * 根据ID查询行业代码
	 */
	@KtfTrace("根据ID查询行业代码")
	@Override
	public SysIndustryDTO getDTOById(Long id) {
		return sysIndustryService.getDTOById(id);
	}

	/**
	 * 新增行业代码
	 */
	@KtfTrace("新增行业代码")
	@Override
	public Boolean save(SysIndustryDTO sysIndustryDTO) {
		return sysIndustryService.save(sysIndustryDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改行业代码")
	@Override
	public Boolean updateById(SysIndustryDTO sysIndustryDTO) {
		return sysIndustryService.updateById(sysIndustryDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表行业代码")
	@Override
	public List<SysIndustryDTO> list(SysIndustryDTO sysIndustryDTO) {
		return sysIndustryService.list(sysIndustryDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表行业代码")
	@Override
	public List<SysIndustryDTO> list(Map<String, Object> params, String... columns) {
		return sysIndustryService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询行业代码")
	@Override
	public List<SysIndustryDTO> listLike(SysIndustryDTO sysIndustryDTO) {
		return sysIndustryService.listLike(sysIndustryDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysIndustryDTO> listLike(Map<String, Object> params, String... columns) {
		return sysIndustryService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询行业代码")
	public PageInfoVO<SysIndustryDTO> page(Map<String, Object> params) {
		return sysIndustryService.page(params);

	}

	@Override
	public List<SysIndustryDTO> getChildren(Long pid, Boolean recursion) {
		return sysIndustryService.getChildren(pid, recursion);
	}

}
