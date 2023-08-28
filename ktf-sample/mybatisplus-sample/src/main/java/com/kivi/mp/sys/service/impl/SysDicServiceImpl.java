package com.kivi.mp.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.mp.sys.dto.SysDicDTO;
import com.kivi.mp.sys.entity.SysDic;
import com.kivi.mp.sys.mapper.SysDicMapper;
import com.kivi.mp.sys.service.ISysDicService;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDicServiceImpl extends ServiceImpl<SysDicMapper, SysDic> implements ISysDicService {

	/**
	 * 根据ID查询数据字典
	 */
	@KtfTrace("根据ID查询数据字典")
	@Override
	public SysDicDTO getDTOById(Long id) {
		SysDic		entity	= super.getById(id);
		SysDicDTO	dto		= BeanConverter.convert(SysDicDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增数据字典
	 */
	@KtfTrace("新增数据字典")
	@Override
	public Boolean save(SysDicDTO sysDicDTO) {
		SysDic entity = BeanConverter.convert(SysDic.class, sysDicDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改数据字典")
	@Override
	public Boolean updateById(SysDicDTO sysDicDTO) {
		SysDic entity = BeanConverter.convert(SysDic.class, sysDicDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表数据字典")
	@Override
	public List<SysDicDTO> list(SysDicDTO sysDicDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysDicDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表数据字典")
	@Override
	public List<SysDicDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<SysDic>	query	= Wrappers.<SysDic>query().select(columns).allEq(true, params, false);
		List<SysDic>			list	= super.list(query);
		return BeanConverter.convert(SysDicDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询数据字典")
	@Override
	public List<SysDicDTO> listLike(SysDicDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysDicDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<SysDic> query = Wrappers.<SysDic>query().select(columns);
		if (MapUtil.isNotEmpty(params)) {
			params.entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		List<SysDic> list = super.list(query);
		return BeanConverter.convert(SysDicDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询数据字典")
	public PageInfoVO<SysDicDTO> page(Map<String, Object> params) {
		PageParams<SysDicDTO>	pageParams	= new PageParams<>(params);
		Page<SysDic>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysDic>	query		= Wrappers.<SysDic>query();
		if (MapUtil.isNotEmpty(params)) {
			params.entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		IPage<SysDic>			iPage	= super.page(page, query);

		PageInfoVO<SysDicDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysDicDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
