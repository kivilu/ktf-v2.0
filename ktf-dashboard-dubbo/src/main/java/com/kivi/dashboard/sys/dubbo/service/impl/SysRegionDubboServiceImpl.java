package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysRegionDTO;
import com.kivi.dashboard.sys.entity.SysRegion;
import com.kivi.dashboard.sys.mapper.SysRegionMapper;
import com.kivi.dashboard.sys.service.ISysRegionService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 地区信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysRegionDubboServiceImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements ISysRegionService {

	@Autowired
	private ISysRegionService sysRegionService;

	/**
	 * 根据ID查询地区信息
	 */
	@KtfTrace("根据ID查询地区信息")
	@Override
	public SysRegionDTO getDTOById(Long id) {
		return sysRegionService.getDTOById(id);
	}

	/**
	 * 新增地区信息
	 */
	@KtfTrace("新增地区信息")
	@Override
	public Boolean save(SysRegionDTO sysRegionDTO) {
		return sysRegionService.save(sysRegionDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改地区信息")
	@Override
	public Boolean updateById(SysRegionDTO sysRegionDTO) {
		return sysRegionService.updateById(sysRegionDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表地区信息")
	@Override
	public List<SysRegionDTO> list(SysRegionDTO sysRegionDTO) {
		return sysRegionService.list(sysRegionDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表地区信息")
	@Override
	public List<SysRegionDTO> list(Map<String, Object> params, String... columns) {
		return sysRegionService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询地区信息")
	@Override
	public List<SysRegionDTO> listLike(SysRegionDTO sysRegionDTO) {
		return sysRegionService.listLike(sysRegionDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysRegionDTO> listLike(Map<String, Object> params, String... columns) {
		return sysRegionService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询地区信息")
	public PageInfoVO<SysRegionDTO> page(Map<String, Object> params) {
		return sysRegionService.page(params);

	}

	@Override
	public List<SysRegionDTO> listProvice() {
		return sysRegionService.listProvice();
	}

}
