package com.kivi.dashboard.enterprise.service;

import com.kivi.dashboard.enterprise.entity.EnterpriseDepartment;
import com.kivi.dashboard.enterprise.dto.EnterpriseDepartmentDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业部门 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface IEnterpriseDepartmentService extends IService<EnterpriseDepartment> {
 
    /**
     * 根据ID查询DTO
     */
 	EnterpriseDepartmentDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(EnterpriseDepartmentDTO enterpriseDepartmentDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(EnterpriseDepartmentDTO enterpriseDepartmentDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<EnterpriseDepartmentDTO> list(EnterpriseDepartmentDTO enterpriseDepartmentDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<EnterpriseDepartmentDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<EnterpriseDepartmentDTO> listLike(EnterpriseDepartmentDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<EnterpriseDepartmentDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<EnterpriseDepartmentDTO> page(Map<String, Object> params);
}
