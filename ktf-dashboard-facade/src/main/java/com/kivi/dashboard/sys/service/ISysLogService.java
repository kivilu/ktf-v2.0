package com.kivi.dashboard.sys.service;

import com.kivi.dashboard.sys.entity.SysLog;
import com.kivi.dashboard.sys.dto.SysLogDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysLogService extends IService<SysLog> {
 
    /**
     * 根据ID查询DTO
     */
 	SysLogDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(SysLogDTO sysLogDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(SysLogDTO sysLogDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<SysLogDTO> list(SysLogDTO sysLogDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<SysLogDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysLogDTO> listLike(SysLogDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysLogDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<SysLogDTO> page(Map<String, Object> params);
}
