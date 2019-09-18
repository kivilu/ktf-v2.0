package com.kivi.dashboard.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.kivi.dashboard.sys.mapper.SysResourceMapper;
import com.kivi.dashboard.sys.service.ISysResourceService;
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
 * 资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

	/**
     * 根据ID查询资源
     */
    @KtfTrace("根据ID查询资源")
    @Override
 	public SysResourceDTO getDTOById(Long id){
 		SysResource entity = super.getById(id);
    	SysResourceDTO dto = BeanConverter.convert(SysResourceDTO.class, entity, BeanConverter.long2String,BeanConverter.integer2String);
 		return dto;
 	}
 	
 	/**
 	 * 新增资源
 	 */
 	@KtfTrace("新增资源")
 	@Override
 	public Boolean save(SysResourceDTO sysResourceDTO){
 		SysResource entity = BeanConverter.convert(SysResource.class,sysResourceDTO);
 		
 		return super.save(entity);
 	}
 	
 	/**
 	 * 修改
 	 */
 	@KtfTrace("修改资源")
 	@Override
 	public Boolean updateById(SysResourceDTO sysResourceDTO){
 		SysResource entity = BeanConverter.convert(SysResource.class, sysResourceDTO);
        return super.updateById(entity);
 	}
 	
 	/**
 	 * 查询列表
 	 */
 	@KtfTrace("查询列表资源")
 	@Override
 	public List<SysResourceDTO> list(SysResourceDTO sysResourceDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(sysResourceDTO);
		return this.list(params, new String[0]);
	}
	
	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表资源")
	@Override
 	public List<SysResourceDTO> list(Map<String, Object> params, String... columns){
 		QueryWrapper<SysResource>	query	= Wrappers.<SysResource>query().select(columns).allEq(true, params, false);
		List<SysResource>			list	= super.list(query);
		return BeanConverter.convert(SysResourceDTO.class, list);
 	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询资源")
	@Override
 	public List<SysResourceDTO> listLike(SysResourceDTO applicationDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
 	}

	/**
	 * 指定列模糊查询
	 */
	@Override
 	public List<SysResourceDTO> listLike(Map<String, Object> params, String... columns){
 		QueryWrapper<SysResource> query = Wrappers.<SysResource>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysResource> list = super.list(query);
		return BeanConverter.convert(SysResourceDTO.class, list);
 	}
 	
 	/**
 	 * 分页查询
 	 */
 	 @KtfTrace("分页查询资源")
 	public PageInfoVO<SysResourceDTO> page(Map<String, Object> params){
 		PageParams<SysResourceDTO> pageParams = new PageParams<>(params);
    	Page<SysResource> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
    	
    	QueryWrapper<SysResource> query = Wrappers.<SysResource>query();
    	params.entrySet().stream().forEach(entry->{
    		if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
    	});
    	
    	IPage<SysResource> iPage = super.page(page, query);
    	
    	PageInfoVO<SysResourceDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList( BeanConverter.convert(SysResourceDTO.class,iPage.getRecords()));
		pageVo.compute();
    	
    	return pageVo;
 	
 	}

}
