package com.kivi.dashboard.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysLogDTO;
import com.kivi.dashboard.sys.entity.SysLog;
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
	 * 分页查询
	 */
	PageInfoVO<SysLogDTO> page(Map<String, Object> params);

}
