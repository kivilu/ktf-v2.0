package com.kivi.dashboard.sys.service;

import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 资源 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysResourceService extends IService<SysResource> {
 
    /**
     * 根据ID查询DTO
     */
 	SysResourceDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(SysResourceDTO sysResourceDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(SysResourceDTO sysResourceDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<SysResourceDTO> list(SysResourceDTO sysResourceDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<SysResourceDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysResourceDTO> listLike(SysResourceDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysResourceDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<SysResourceDTO> page(Map<String, Object> params);
}
