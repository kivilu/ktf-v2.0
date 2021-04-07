package com.kivi.dashboard.permission.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysUserTokenDTO;
import com.kivi.dashboard.permission.entity.SysUserToken;
import com.kivi.dashboard.permission.mapper.SysUserTokenMapper;
import com.kivi.dashboard.permission.service.SysUserTokenService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken>
		implements SysUserTokenService {

	/**
	 * 根据ID查询系统用户Token
	 */
	@KtfTrace("根据ID查询系统用户Token")
	@Override
	public SysUserTokenDTO getDTOById(Long id) {
		SysUserToken	entity	= super.getById(id);
		SysUserTokenDTO	dto		= BeanConverter.convert(SysUserTokenDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增系统用户Token
	 */
	@KtfTrace("新增系统用户Token")
	@Override
	public Boolean save(SysUserTokenDTO sysUserTokenDTO) {
		SysUserToken entity = BeanConverter.convert(SysUserToken.class, sysUserTokenDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改系统用户Token")
	@Override
	public Boolean updateById(SysUserTokenDTO sysUserTokenDTO) {
		SysUserToken entity = BeanConverter.convert(SysUserToken.class, sysUserTokenDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表系统用户Token")
	@Override
	public List<SysUserTokenDTO> list(SysUserTokenDTO sysUserTokenDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysUserTokenDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表系统用户Token")
	@Override
	public List<SysUserTokenDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUserToken>	query	= Wrappers.<SysUserToken>query().select(columns).allEq(true, params, false);
		List<SysUserToken>			list	= super.list(query);
		return BeanConverter.convert(SysUserTokenDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询系统用户Token")
	@Override
	public List<SysUserTokenDTO> listLike(SysUserTokenDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserTokenDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUserToken> query = Wrappers.<SysUserToken>query().select(columns);
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

		List<SysUserToken> list = super.list(query);
		return BeanConverter.convert(SysUserTokenDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询系统用户Token")
	public PageInfoVO<SysUserTokenDTO> page(Map<String, Object> params) {
		PageParams<SysUserTokenDTO>	pageParams	= new PageParams<>(params);
		Page<SysUserToken>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysUserToken>	query		= Wrappers.<SysUserToken>query();
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

		IPage<SysUserToken>			iPage	= super.page(page, query);

		PageInfoVO<SysUserTokenDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysUserTokenDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@KtfTrace("根据token的值查找用户Token")
	@Override
	public SysUserToken selectByToken(String token) {
		LambdaQueryWrapper<SysUserToken> query = Wrappers.<SysUserToken>lambdaQuery();
		query.eq(SysUserToken::getToken, token);
		return super.getOne(query);
	}

}
