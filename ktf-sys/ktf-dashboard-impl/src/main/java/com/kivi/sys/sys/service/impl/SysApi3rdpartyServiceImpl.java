package com.kivi.sys.sys.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysApi3rdpartyDTO;
import com.kivi.sys.sys.entity.SysApi3rdparty;
import com.kivi.sys.sys.mapper.SysApi3rdpartyExMapper;
import com.kivi.sys.sys.mapper.SysApi3rdpartyMapper;
import com.kivi.sys.sys.service.SysApi3rdpartyService;

/**
 * <p>
 * API接口账号信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2020-02-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysApi3rdpartyServiceImpl extends ServiceImpl<SysApi3rdpartyMapper, SysApi3rdparty>
		implements SysApi3rdpartyService {

	@Autowired
	private SysApi3rdpartyExMapper sysMpExMapper;

	/**
	 * 根据ID查询API接口账号信息
	 */
	@KtfTrace("根据ID查询API接口账号信息")
	@Override
	public SysApi3rdpartyDTO getDTOById(Long id) {
		SysApi3rdparty entity = super.getById(id);
		if (entity == null)
			return null;
		SysApi3rdpartyDTO dto = BeanConverter.convert(SysApi3rdpartyDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增API接口账号信息
	 */
	@KtfTrace("新增API接口账号信息")
	@Override
	public Boolean save(SysApi3rdpartyDTO dto) {
		SysApi3rdparty entity = BeanConverter.convert(SysApi3rdparty.class, dto);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改API接口账号信息")
	@Override
	public Boolean updateById(SysApi3rdpartyDTO dto) {
		SysApi3rdparty entity = BeanConverter.convert(SysApi3rdparty.class, dto);
		return super.updateById(entity);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询API接口账号信息")
	public PageInfoVO<SysApi3rdpartyDTO> page(Map<String, Object> params) {
		PageParams<SysApi3rdpartyDTO>	pageParams	= new PageParams<>(params);

		IPage<SysApi3rdpartyDTO>		iPage		= sysMpExMapper.selectByPage(pageParams, params);

		PageInfoVO<SysApi3rdpartyDTO>	pageVo		= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(iPage.getRecords());
		pageVo.compute();

		return pageVo;

	}

	@Override
	public SysApi3rdparty getByWxAppId(String appid) {
		LambdaQueryWrapper<SysApi3rdparty> query = Wrappers.<SysApi3rdparty>lambdaQuery();
		query.eq(SysApi3rdparty::getApiAppid, appid);
		return super.getOne(query);
	}

}
