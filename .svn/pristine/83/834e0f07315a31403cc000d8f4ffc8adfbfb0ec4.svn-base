package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysLogDTO;
import com.kivi.dashboard.sys.entity.SysLog;
import com.kivi.dashboard.sys.mapper.SysLogMapper;
import com.kivi.dashboard.sys.service.ISysLogService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
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
	 * 查询列表
	 */
	@KtfTrace("查询列表系统日志")
	@Override
	public List<SysLogDTO> list(SysLogDTO sysLogDTO) {
		return sysLogService.list(sysLogDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表系统日志")
	@Override
	public List<SysLogDTO> list(Map<String, Object> params, String... columns) {
		return sysLogService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询系统日志")
	@Override
	public List<SysLogDTO> listLike(SysLogDTO sysLogDTO) {
		return sysLogService.listLike(sysLogDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysLogDTO> listLike(Map<String, Object> params, String... columns) {
		return sysLogService.listLike(params, columns);
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
