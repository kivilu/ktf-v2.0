package com.kivi.mp.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.mp.sys.dto.SysDicDTO;
import com.kivi.mp.sys.entity.SysDic;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysDicService extends IService<SysDic> {

	/**
	 * 根据ID查询DTO
	 */
	SysDicDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysDicDTO sysDicDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysDicDTO sysDicDTO);

	/**
	 * 查询列表
	 */
	List<SysDicDTO> list(SysDicDTO sysDicDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysDicDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysDicDTO> listLike(SysDicDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysDicDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysDicDTO> page(Map<String, Object> params);
}
