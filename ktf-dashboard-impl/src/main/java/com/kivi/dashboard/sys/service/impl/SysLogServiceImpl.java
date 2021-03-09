package com.kivi.dashboard.sys.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysLogDTO;
import com.kivi.dashboard.sys.entity.SysLog;
import com.kivi.dashboard.sys.mapper.SysLogMapper;
import com.kivi.dashboard.sys.service.ISysLogService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

	/**
	 * 根据ID查询系统日志
	 */
	@KtfTrace("根据ID查询系统日志")
	@Override
	public SysLogDTO getDTOById(Long id) {
		SysLog		entity	= super.getById(id);
		SysLogDTO	dto		= BeanConverter.convert(SysLogDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增系统日志
	 */
	@KtfTrace("新增系统日志")
	@Override
	public Boolean save(SysLogDTO sysLogDTO) {
		SysLog entity = BeanConverter.convert(SysLog.class, sysLogDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改系统日志")
	@Override
	public Boolean updateById(SysLogDTO sysLogDTO) {
		SysLog entity = BeanConverter.convert(SysLog.class, sysLogDTO);
		return super.updateById(entity);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询系统日志")
	public PageInfoVO<SysLogDTO> page(Map<String, Object> params) {
		PageParams<SysLogDTO>	pageParams	= new PageParams<>(params);
		Page<SysLog>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysLog>	query		= Wrappers.<SysLog>query()
				.select(SysLog.DB_LOGIN_NAME, SysLog.DB_USER_NAME, SysLog.DB_USER_TYPE, SysLog.DB_TYPE,
						SysLog.DB_OPERATION, SysLog.DB_RESULT, SysLog.DB_GMT_CREATE, SysLog.DB_CLIENT_IP)
				.orderByDesc(SysLog.DB_GMT_CREATE);
		if (MapUtil.isNotEmpty(pageParams.getRequestMap())) {
			String	types			= (String) pageParams.getRequestMap().get(SysLog.TYPE);
			String	key				= (String) pageParams.getRequestMap().get("keyword");
			Integer	userType		= NumberKit.toInt(pageParams.getRequestMap().get(SysLog.USER_TYPE));
			String	operation		= (String) pageParams.getRequestMap().get(SysLog.OPERATION);
			Date	startTime		= DateTimeKit.parse((String) params.get("startTime"));
			Date	endTime			= DateTimeKit.parse((String) params.get("endTime"));
			Long	enterpriseId	= (Long) pageParams.getRequestMap().get(SysLog.ENTERPRISE_ID);
			if (types != null)
				query.in(SysLog.DB_TYPE, StrKit.split(types, ','));

			if (StringUtils.isNotBlank(key))
				query.like(SysLog.DB_LOGIN_NAME, key).or().like(SysLog.DB_USER_NAME, key);

			if (userType != null)
				query.eq(SysLog.DB_USER_TYPE, userType);

			if (StringUtils.isNotBlank(operation))
				query.like(SysLog.DB_OPERATION, operation);

			if (startTime != null)
				query.ge(SysLog.DB_GMT_CREATE, startTime);

			if (endTime != null)
				query.le(SysLog.DB_GMT_CREATE, endTime);

			if (enterpriseId != null)
				query.eq(SysLog.DB_ENTERPRISE_ID, enterpriseId);

		}

		IPage<SysLog>			iPage	= super.page(page, query);

		PageInfoVO<SysLogDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysLogDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
