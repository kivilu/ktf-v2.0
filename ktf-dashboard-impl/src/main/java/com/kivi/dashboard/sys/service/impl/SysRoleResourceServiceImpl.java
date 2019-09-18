package com.kivi.dashboard.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.kivi.dashboard.sys.entity.SysRoleResource;
import com.kivi.dashboard.sys.dto.SysRoleResourceDTO;
import com.kivi.dashboard.sys.mapper.SysRoleResourceMapper;
import com.kivi.dashboard.sys.service.ISysRoleResourceService;
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
 * 角色资源 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements ISysRoleResourceService {

	/**
     * 根据ID查询角色资源
     */
    @KtfTrace("根据ID查询角色资源")
    @Override
 	public SysRoleResourceDTO getDTOById(Long id){
 		SysRoleResource entity = super.getById(id);
    	SysRoleResourceDTO dto = BeanConverter.convert(SysRoleResourceDTO.class, entity, BeanConverter.long2String,BeanConverter.integer2String);
 		return dto;
 	}
 	
 	/**
 	 * 新增角色资源
 	 */
 	@KtfTrace("新增角色资源")
 	@Override
 	public Boolean save(SysRoleResourceDTO sysRoleResourceDTO){
 		SysRoleResource entity = BeanConverter.convert(SysRoleResource.class,sysRoleResourceDTO);
 		
 		return super.save(entity);
 	}
 	
 	/**
 	 * 修改
 	 */
 	@KtfTrace("修改角色资源")
 	@Override
 	public Boolean updateById(SysRoleResourceDTO sysRoleResourceDTO){
 		SysRoleResource entity = BeanConverter.convert(SysRoleResource.class, sysRoleResourceDTO);
        return super.updateById(entity);
 	}
 	
 	/**
 	 * 查询列表
 	 */
 	@KtfTrace("查询列表角色资源")
 	@Override
 	public List<SysRoleResourceDTO> list(SysRoleResourceDTO sysRoleResourceDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(sysRoleResourceDTO);
		return this.list(params, new String[0]);
	}
	
	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表角色资源")
	@Override
 	public List<SysRoleResourceDTO> list(Map<String, Object> params, String... columns){
 		QueryWrapper<SysRoleResource>	query	= Wrappers.<SysRoleResource>query().select(columns).allEq(true, params, false);
		List<SysRoleResource>			list	= super.list(query);
		return BeanConverter.convert(SysRoleResourceDTO.class, list);
 	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询角色资源")
	@Override
 	public List<SysRoleResourceDTO> listLike(SysRoleResourceDTO applicationDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
 	}

	/**
	 * 指定列模糊查询
	 */
	@Override
 	public List<SysRoleResourceDTO> listLike(Map<String, Object> params, String... columns){
 		QueryWrapper<SysRoleResource> query = Wrappers.<SysRoleResource>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysRoleResource> list = super.list(query);
		return BeanConverter.convert(SysRoleResourceDTO.class, list);
 	}
 	
 	/**
 	 * 分页查询
 	 */
 	 @KtfTrace("分页查询角色资源")
 	public PageInfoVO<SysRoleResourceDTO> page(Map<String, Object> params){
 		PageParams<SysRoleResourceDTO> pageParams = new PageParams<>(params);
    	Page<SysRoleResource> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
    	
    	QueryWrapper<SysRoleResource> query = Wrappers.<SysRoleResource>query();
    	params.entrySet().stream().forEach(entry->{
    		if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
    	});
    	
    	IPage<SysRoleResource> iPage = super.page(page, query);
    	
    	PageInfoVO<SysRoleResourceDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList( BeanConverter.convert(SysRoleResourceDTO.class,iPage.getRecords()));
		pageVo.compute();
    	
    	return pageVo;
 	
 	}

}
