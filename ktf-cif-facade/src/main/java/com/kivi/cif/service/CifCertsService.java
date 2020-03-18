package com.kivi.cif.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.entity.CifCerts;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 客户证书 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
public interface CifCertsService extends IService<CifCerts> {

	/**
	 * 根据标识和类型获取证书
	 * 
	 * @param identifier
	 * @param type
	 * @return
	 */
	CifCerts getCifCert(String identifier, String type);

	/**
	 * 根据ID查询DTO
	 */
	CifCertsDTO getDTOById(Long id);

	/**
	 * 删除
	 * 
	 * @param identifier
	 * @return
	 */
	Boolean removeByIdentifier(String identifier);

	/**
	 * 新增
	 */
	Boolean save(CifCertsDTO cifCertsDTO);

	/**
	 * 修改
	 */
	Boolean updateById(CifCertsDTO cifCertsDTO);

	/**
	 * 查询列表
	 */
	List<CifCertsDTO> list(CifCertsDTO cifCertsDTO);

	/**
	 * 指定列查询列表
	 */
	List<CifCertsDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<CifCertsDTO> listLike(CifCertsDTO cifCertsDTO);

	/**
	 * 指定列模糊查询
	 */
	List<CifCertsDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<CifCertsDTO> page(Map<String, Object> params);
}
