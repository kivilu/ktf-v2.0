package com.kivi.dashboard.sys.service;

import com.kivi.dashboard.sys.entity.SysApplication;
import com.kivi.dashboard.sys.dto.SysApplicationDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 系统应用 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysApplicationService extends IService<SysApplication> {
 
    /**
     * 根据ID查询DTO
     */
 	SysApplicationDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(SysApplicationDTO sysApplicationDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(SysApplicationDTO sysApplicationDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<SysApplicationDTO> list(SysApplicationDTO sysApplicationDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<SysApplicationDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysApplicationDTO> listLike(SysApplicationDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysApplicationDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<SysApplicationDTO> page(Map<String, Object> params);
}
