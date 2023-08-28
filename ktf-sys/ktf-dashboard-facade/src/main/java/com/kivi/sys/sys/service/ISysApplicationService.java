package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysApplicationDTO;
import com.kivi.sys.sys.entity.SysApplication;

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
	 * 根据业务应用代码获取或创建应用ID
	 * 
	 * @param code
	 * @return
	 */
	Long getOrCreate(String code);

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
