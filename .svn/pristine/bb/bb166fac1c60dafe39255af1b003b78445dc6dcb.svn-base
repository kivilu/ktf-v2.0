package com.kivi.dashboard.enterprise.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.enterprise.dto.EnterpriseJobDTO;
import com.kivi.dashboard.enterprise.entity.EnterpriseJob;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业职务配置 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface IEnterpriseJobService extends IService<EnterpriseJob> {

	/**
	 * 根据ID查询DTO
	 */
	EnterpriseJobDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(EnterpriseJobDTO enterpriseJobDTO);

	/**
	 * 修改
	 */
	Boolean updateById(EnterpriseJobDTO enterpriseJobDTO);

	/**
	 * 查询列表
	 */
	List<EnterpriseJobDTO> list(EnterpriseJobDTO enterpriseJobDTO);

	/**
	 * 指定列查询列表
	 */
	List<EnterpriseJobDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<EnterpriseJobDTO> listLike(EnterpriseJobDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<EnterpriseJobDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<EnterpriseJobDTO> page(Map<String, Object> params);

	/**
	 * 多表企业查询
	 *
	 * @param params
	 * @return
	 */
	List<EnterpriseJobDTO> select(Map<String, Object> params);

	/**
	 * 多表分页查询
	 * 
	 * @param params
	 * @return
	 */
	PageInfoVO<Map<String, Object>> selectByPage(Map<String, Object> params);
}
