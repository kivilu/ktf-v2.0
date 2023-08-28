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
import com.kivi.sys.sys.dto.SysFileDTO;
import com.kivi.sys.sys.entity.SysFile;
import com.kivi.sys.sys.mapper.SysFileMapper;
import com.kivi.sys.sys.service.ISysFileService;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 附件 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

	/**
	 * 根据ID查询附件
	 */
	@KtfTrace("根据ID查询附件")
	@Override
	public SysFileDTO getDTOById(Long id) {
		SysFile		entity	= super.getById(id);
		SysFileDTO	dto		= BeanConverter.convert(SysFileDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增附件
	 */
	@KtfTrace("新增附件")
	@Override
	public Boolean save(SysFileDTO sysFileDTO) {
		SysFile entity = BeanConverter.convert(SysFile.class, sysFileDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改附件")
	@Override
	public Boolean updateById(SysFileDTO sysFileDTO) {
		SysFile entity = BeanConverter.convert(SysFile.class, sysFileDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表附件")
	@Override
	public List<SysFileDTO> list(SysFileDTO sysFileDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysFileDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表附件")
	@Override
	public List<SysFileDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysFile>	query	= Wrappers.<SysFile>query().select(columns).allEq(true, params, false);
		List<SysFile>			list	= super.list(query);
		return BeanConverter.convert(SysFileDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询附件")
	@Override
	public List<SysFileDTO> listLike(SysFileDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysFileDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysFile> query = Wrappers.<SysFile>query().select(columns);
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

		List<SysFile> list = super.list(query);
		return BeanConverter.convert(SysFileDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询附件")
	public PageInfoVO<SysFileDTO> page(Map<String, Object> params) {
		PageParams<SysFileDTO>	pageParams	= new PageParams<>(params);
		Page<SysFile>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysFile>	query		= Wrappers.<SysFile>query();
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

		IPage<SysFile>			iPage	= super.page(page, query);

		PageInfoVO<SysFileDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysFileDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

}
