package com.kivi.sms.sys.service.impl;

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
import com.kivi.sms.sys.dto.SysSmsDTO;
import com.kivi.sms.sys.entity.SysSms;
import com.kivi.sms.sys.mapper.SysSmsMapper;
import com.kivi.sms.sys.service.ISysSmsService;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysSmsServiceImpl extends ServiceImpl<SysSmsMapper, SysSms> implements ISysSmsService {

	/**
	 * 根据ID查询消息
	 */
	@KtfTrace("根据ID查询消息")
	@Override
	public SysSmsDTO getDTOById(Long id) {
		SysSms		entity	= super.getById(id);
		SysSmsDTO	dto		= BeanConverter.convert(SysSmsDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增消息
	 */
	@KtfTrace("新增消息")
	@Override
	public Boolean save(SysSmsDTO sysSmsDTO) {
		SysSms entity = BeanConverter.convert(SysSms.class, sysSmsDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改消息")
	@Override
	public Boolean updateById(SysSmsDTO sysSmsDTO) {
		SysSms entity = BeanConverter.convert(SysSms.class, sysSmsDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表消息")
	@Override
	public List<SysSmsDTO> list(SysSmsDTO sysSmsDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysSmsDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表消息")
	@Override
	public List<SysSmsDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<SysSms>	query	= Wrappers.<SysSms>query().select(columns).allEq(true, params, false);
		List<SysSms>			list	= super.list(query);
		return BeanConverter.convert(SysSmsDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询消息")
	@Override
	public List<SysSmsDTO> listLike(SysSmsDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysSmsDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<SysSms> query = Wrappers.<SysSms>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysSms> list = super.list(query);
		return BeanConverter.convert(SysSmsDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询消息")
	public PageInfoVO<SysSmsDTO> page(Map<String, Object> params) {
		PageParams<SysSmsDTO>	pageParams	= new PageParams<>(params);
		Page<SysSms>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysSms>	query		= Wrappers.<SysSms>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<SysSms>			iPage	= super.page(page, query);

		PageInfoVO<SysSmsDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysSmsDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
