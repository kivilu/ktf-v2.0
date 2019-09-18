package com.kivi.dashboard.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.kivi.dashboard.sys.entity.SysLog;
import com.kivi.dashboard.sys.dto.SysLogDTO;
import com.kivi.dashboard.sys.mapper.SysLogMapper;
import com.kivi.dashboard.sys.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;


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
 	public SysLogDTO getDTOById(Long id){
 		SysLog entity = super.getById(id);
    	SysLogDTO dto = BeanConverter.convert(SysLogDTO.class, entity, BeanConverter.long2String,BeanConverter.integer2String);
 		return dto;
 	}
 	
 	/**
 	 * 新增系统日志
 	 */
 	@KtfTrace("新增系统日志")
 	@Override
 	public Boolean save(SysLogDTO sysLogDTO){
 		SysLog entity = BeanConverter.convert(SysLog.class,sysLogDTO);
 		
 		return super.save(entity);
 	}
 	
 	/**
 	 * 修改
 	 */
 	@KtfTrace("修改系统日志")
 	@Override
 	public Boolean updateById(SysLogDTO sysLogDTO){
 		SysLog entity = BeanConverter.convert(SysLog.class, sysLogDTO);
        return super.updateById(entity);
 	}
 	
 	/**
 	 * 查询列表
 	 */
 	@KtfTrace("查询列表系统日志")
 	@Override
 	public List<SysLogDTO> list(SysLogDTO sysLogDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(sysLogDTO);
		return this.list(params, new String[0]);
	}
	
	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表系统日志")
	@Override
 	public List<SysLogDTO> list(Map<String, Object> params, String... columns){
 		QueryWrapper<SysLog>	query	= Wrappers.<SysLog>query().select(columns).allEq(true, params, false);
		List<SysLog>			list	= super.list(query);
		return BeanConverter.convert(SysLogDTO.class, list);
 	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询系统日志")
	@Override
 	public List<SysLogDTO> listLike(SysLogDTO applicationDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
 	}

	/**
	 * 指定列模糊查询
	 */
	@Override
 	public List<SysLogDTO> listLike(Map<String, Object> params, String... columns){
 		QueryWrapper<SysLog> query = Wrappers.<SysLog>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysLog> list = super.list(query);
		return BeanConverter.convert(SysLogDTO.class, list);
 	}
 	
 	/**
 	 * 分页查询
 	 */
 	 @KtfTrace("分页查询系统日志")
 	public PageInfoVO<SysLogDTO> page(Map<String, Object> params){
 		PageParams<SysLogDTO> pageParams = new PageParams<>(params);
    	Page<SysLog> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
    	
    	QueryWrapper<SysLog> query = Wrappers.<SysLog>query();
    	params.entrySet().stream().forEach(entry->{
    		if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
    	});
    	
    	IPage<SysLog> iPage = super.page(page, query);
    	
    	PageInfoVO<SysLogDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList( BeanConverter.convert(SysLogDTO.class,iPage.getRecords()));
		pageVo.compute();
    	
    	return pageVo;
 	
 	}

}
