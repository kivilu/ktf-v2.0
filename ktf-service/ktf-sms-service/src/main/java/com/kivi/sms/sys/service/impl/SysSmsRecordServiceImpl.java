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
import com.kivi.sms.sys.dto.SysSmsRecordDTO;
import com.kivi.sms.sys.entity.SysSmsRecord;
import com.kivi.sms.sys.mapper.SysSmsRecordMapper;
import com.kivi.sms.sys.service.ISysSmsRecordService;

/**
 * <p>
 * 消息记录 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysSmsRecordServiceImpl extends ServiceImpl<SysSmsRecordMapper, SysSmsRecord>
		implements ISysSmsRecordService {

	/**
	 * 根据ID查询消息记录
	 */
	@KtfTrace("根据ID查询消息记录")
	@Override
	public SysSmsRecordDTO getDTOById(Long id) {
		SysSmsRecord	entity	= super.getById(id);
		SysSmsRecordDTO	dto		= BeanConverter.convert(SysSmsRecordDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增消息记录
	 */
	@KtfTrace("新增消息记录")
	@Override
	public Boolean save(SysSmsRecordDTO sysSmsRecordDTO) {
		SysSmsRecord entity = BeanConverter.convert(SysSmsRecord.class, sysSmsRecordDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改消息记录")
	@Override
	public Boolean updateById(SysSmsRecordDTO sysSmsRecordDTO) {
		SysSmsRecord entity = BeanConverter.convert(SysSmsRecord.class, sysSmsRecordDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表消息记录")
	@Override
	public List<SysSmsRecordDTO> list(SysSmsRecordDTO sysSmsRecordDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysSmsRecordDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表消息记录")
	@Override
	public List<SysSmsRecordDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<SysSmsRecord>	query	= Wrappers.<SysSmsRecord>query().select(columns).allEq(true, params, false);
		List<SysSmsRecord>			list	= super.list(query);
		return BeanConverter.convert(SysSmsRecordDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询消息记录")
	@Override
	public List<SysSmsRecordDTO> listLike(SysSmsRecordDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysSmsRecordDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<SysSmsRecord> query = Wrappers.<SysSmsRecord>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysSmsRecord> list = super.list(query);
		return BeanConverter.convert(SysSmsRecordDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询消息记录")
	public PageInfoVO<SysSmsRecordDTO> page(Map<String, Object> params) {
		PageParams<SysSmsRecordDTO>	pageParams	= new PageParams<>(params);
		Page<SysSmsRecord>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysSmsRecord>	query		= Wrappers.<SysSmsRecord>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<SysSmsRecord>			iPage	= super.page(page, query);

		PageInfoVO<SysSmsRecordDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysSmsRecordDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
