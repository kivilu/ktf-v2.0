package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsDTO;
import com.kivi.sys.sys.entity.SysSms;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
public interface ISysSmsService extends IService<SysSms> {

	/**
	 * 根据ID查询DTO
	 */
	SysSmsDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysSmsDTO sysSmsDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysSmsDTO sysSmsDTO);

	/**
	 * 查询列表
	 */
	List<SysSmsDTO> list(SysSmsDTO sysSmsDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysSmsDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysSmsDTO> listLike(SysSmsDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysSmsDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysSmsDTO> page(Map<String, Object> params);
}
