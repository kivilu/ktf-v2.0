package com.kivi.sys.sys.service.impl;

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
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsTypeDTO;
import com.kivi.sys.sys.entity.SysSmsType;
import com.kivi.sys.sys.mapper.SysSmsTypeMapper;
import com.kivi.sys.sys.service.SysSmsTypeService;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 消息类型与用户关系 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysSmsTypeServiceImpl extends ServiceImpl<SysSmsTypeMapper, SysSmsType> implements SysSmsTypeService {

	/**
	 * 根据ID查询消息类型与用户关系
	 */
	@KtfTrace("根据ID查询消息类型与用户关系")
	@Override
	public SysSmsTypeDTO getDTOById(Long id) {
		SysSmsType		entity	= super.getById(id);
		SysSmsTypeDTO	dto		= BeanConverter.convert(SysSmsTypeDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增消息类型与用户关系
	 */
	@KtfTrace("新增消息类型与用户关系")
	@Override
	public Boolean save(SysSmsTypeDTO sysSmsTypeDTO) {
		SysSmsType entity = BeanConverter.convert(SysSmsType.class, sysSmsTypeDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改消息类型与用户关系")
	@Override
	public Boolean updateById(SysSmsTypeDTO sysSmsTypeDTO) {
		SysSmsType entity = BeanConverter.convert(SysSmsType.class, sysSmsTypeDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表消息类型与用户关系")
	@Override
	public List<SysSmsTypeDTO> list(SysSmsTypeDTO sysSmsTypeDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysSmsTypeDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表消息类型与用户关系")
	@Override
	public List<SysSmsTypeDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysSmsType>	query	= Wrappers.<SysSmsType>query().select(columns).allEq(true, params, false);
		List<SysSmsType>			list	= super.list(query);
		return BeanConverter.convert(SysSmsTypeDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询消息类型与用户关系")
	@Override
	public List<SysSmsTypeDTO> listLike(SysSmsTypeDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysSmsTypeDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysSmsType> query = Wrappers.<SysSmsType>query().select(columns);
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

		List<SysSmsType> list = super.list(query);
		return BeanConverter.convert(SysSmsTypeDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询消息类型与用户关系")
	public PageInfoVO<SysSmsTypeDTO> page(Map<String, Object> params) {
		PageParams<SysSmsTypeDTO>	pageParams	= new PageParams<>(params);
		Page<SysSmsType>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysSmsType>	query		= Wrappers.<SysSmsType>query();
		if (MapUtil.isNotEmpty(pageParams.getRequestMap())) {
			pageParams.getRequestMap().entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		IPage<SysSmsType>			iPage	= super.page(page, query);

		PageInfoVO<SysSmsTypeDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysSmsTypeDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
