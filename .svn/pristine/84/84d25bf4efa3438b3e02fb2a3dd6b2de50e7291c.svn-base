package com.kivi.dashboard.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.kivi.dashboard.sys.entity.SysRegion;
import com.kivi.dashboard.sys.dto.SysRegionDTO;
import com.kivi.dashboard.sys.mapper.SysRegionMapper;
import com.kivi.dashboard.sys.service.ISysRegionService;
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
 * 地区信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements ISysRegionService {

	/**
     * 根据ID查询地区信息
     */
    @KtfTrace("根据ID查询地区信息")
    @Override
 	public SysRegionDTO getDTOById(Long id){
 		SysRegion entity = super.getById(id);
    	SysRegionDTO dto = BeanConverter.convert(SysRegionDTO.class, entity, BeanConverter.long2String,BeanConverter.integer2String);
 		return dto;
 	}
 	
 	/**
 	 * 新增地区信息
 	 */
 	@KtfTrace("新增地区信息")
 	@Override
 	public Boolean save(SysRegionDTO sysRegionDTO){
 		SysRegion entity = BeanConverter.convert(SysRegion.class,sysRegionDTO);
 		
 		return super.save(entity);
 	}
 	
 	/**
 	 * 修改
 	 */
 	@KtfTrace("修改地区信息")
 	@Override
 	public Boolean updateById(SysRegionDTO sysRegionDTO){
 		SysRegion entity = BeanConverter.convert(SysRegion.class, sysRegionDTO);
        return super.updateById(entity);
 	}
 	
 	/**
 	 * 查询列表
 	 */
 	@KtfTrace("查询列表地区信息")
 	@Override
 	public List<SysRegionDTO> list(SysRegionDTO sysRegionDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(sysRegionDTO);
		return this.list(params, new String[0]);
	}
	
	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表地区信息")
	@Override
 	public List<SysRegionDTO> list(Map<String, Object> params, String... columns){
 		QueryWrapper<SysRegion>	query	= Wrappers.<SysRegion>query().select(columns).allEq(true, params, false);
		List<SysRegion>			list	= super.list(query);
		return BeanConverter.convert(SysRegionDTO.class, list);
 	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询地区信息")
	@Override
 	public List<SysRegionDTO> listLike(SysRegionDTO applicationDTO){
 		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
 	}

	/**
	 * 指定列模糊查询
	 */
	@Override
 	public List<SysRegionDTO> listLike(Map<String, Object> params, String... columns){
 		QueryWrapper<SysRegion> query = Wrappers.<SysRegion>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysRegion> list = super.list(query);
		return BeanConverter.convert(SysRegionDTO.class, list);
 	}
 	
 	/**
 	 * 分页查询
 	 */
 	 @KtfTrace("分页查询地区信息")
 	public PageInfoVO<SysRegionDTO> page(Map<String, Object> params){
 		PageParams<SysRegionDTO> pageParams = new PageParams<>(params);
    	Page<SysRegion> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
    	
    	QueryWrapper<SysRegion> query = Wrappers.<SysRegion>query();
    	params.entrySet().stream().forEach(entry->{
    		if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
    	});
    	
    	IPage<SysRegion> iPage = super.page(page, query);
    	
    	PageInfoVO<SysRegionDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList( BeanConverter.convert(SysRegionDTO.class,iPage.getRecords()));
		pageVo.compute();
    	
    	return pageVo;
 	
 	}

}
