package com.kivi.dashboard.sys.service;

import com.kivi.dashboard.sys.entity.SysIndustry;
import com.kivi.dashboard.sys.dto.SysIndustryDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 行业代码 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysIndustryService extends IService<SysIndustry> {
 
    /**
     * 根据ID查询DTO
     */
 	SysIndustryDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(SysIndustryDTO sysIndustryDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(SysIndustryDTO sysIndustryDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<SysIndustryDTO> list(SysIndustryDTO sysIndustryDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<SysIndustryDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysIndustryDTO> listLike(SysIndustryDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysIndustryDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<SysIndustryDTO> page(Map<String, Object> params);
}
