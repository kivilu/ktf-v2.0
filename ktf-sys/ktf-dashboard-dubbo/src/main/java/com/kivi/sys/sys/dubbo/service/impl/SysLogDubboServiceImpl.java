package com.kivi.sys.sys.dubbo.service.impl;

import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysLogDTO;
import com.kivi.sys.sys.entity.SysLog;
import com.kivi.sys.sys.mapper.SysLogMapper;
import com.kivi.sys.sys.service.ISysLogService;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysLogDubboServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

	@Autowired
	private ISysLogService sysLogService;

	/**
	 * 根据ID查询系统日志
	 */
	@KtfTrace("根据ID查询系统日志")
	@Override
	public SysLogDTO getDTOById(Long id) {
		return sysLogService.getDTOById(id);
	}

	/**
	 * 新增系统日志
	 */
	@KtfTrace("新增系统日志")
	@Override
	public Boolean save(SysLogDTO sysLogDTO) {
		return sysLogService.save(sysLogDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改系统日志")
	@Override
	public Boolean updateById(SysLogDTO sysLogDTO) {
		return sysLogService.updateById(sysLogDTO);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询系统日志")
	public PageInfoVO<SysLogDTO> page(Map<String, Object> params) {
		return sysLogService.page(params);

	}

}
