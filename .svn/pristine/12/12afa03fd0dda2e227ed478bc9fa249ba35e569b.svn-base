package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysRegionDTO;
import com.kivi.dashboard.sys.entity.SysRegion;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 地区信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysRegionService extends IService<SysRegion> {

	/**
	 * 根据ID查询DTO
	 */
	SysRegionDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysRegionDTO sysRegionDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysRegionDTO sysRegionDTO);

	/**
	 * 查询列表
	 */
	List<SysRegionDTO> list(SysRegionDTO sysRegionDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysRegionDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysRegionDTO> listLike(SysRegionDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysRegionDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 查询省列表
	 * 
	 * @return
	 */
	List<SysRegionDTO> listProvice();

	/**
	 * 分页查询
	 */
	PageInfoVO<SysRegionDTO> page(Map<String, Object> params);

}
