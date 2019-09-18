package com.kivi.dashboard.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysUserDTO;
import com.kivi.dashboard.sys.entity.SysUser;
import com.kivi.dashboard.sys.entity.vo.UserVo;
import com.kivi.dashboard.sys.mapper.SysUserExMapper;
import com.kivi.dashboard.sys.mapper.SysUserMapper;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	SysUserExMapper sysUserExMapper;

	/**
	 * 根据ID查询用户
	 */
	@KtfTrace("根据ID查询用户")
	@Override
	public SysUserDTO getDTOById(Long id) {
		SysUser		entity	= super.getById(id);
		SysUserDTO	dto		= BeanConverter.convert(SysUserDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增用户
	 */
	@KtfTrace("新增用户")
	@Override
	public Boolean save(SysUserDTO sysUserDTO) {
		SysUser entity = BeanConverter.convert(SysUser.class, sysUserDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改用户")
	@Override
	public Boolean updateById(SysUserDTO sysUserDTO) {
		SysUser entity = BeanConverter.convert(SysUser.class, sysUserDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表用户")
	@Override
	public List<SysUserDTO> list(SysUserDTO sysUserDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysUserDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表用户")
	@Override
	public List<SysUserDTO> list(Map<String, Object> params, String... columns) {
		QueryWrapper<SysUser>	query	= Wrappers.<SysUser>query().select(columns).allEq(true, params, false);
		List<SysUser>			list	= super.list(query);
		return BeanConverter.convert(SysUserDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询用户")
	@Override
	public List<SysUserDTO> listLike(SysUserDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserDTO> listLike(Map<String, Object> params, String... columns) {
		QueryWrapper<SysUser> query = Wrappers.<SysUser>query().select(columns);
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		List<SysUser> list = super.list(query);
		return BeanConverter.convert(SysUserDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询用户")
	public PageInfoVO<SysUserDTO> page(Map<String, Object> params) {
		PageParams<SysUserDTO>	pageParams	= new PageParams<>(params);
		Page<SysUser>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysUser>	query		= Wrappers.<SysUser>query();
		params.entrySet().stream().forEach(entry -> {
			if (ObjectKit.isNotEmpty(entry.getValue())) {
				if (NumberKit.isNumberic(entry.getValue()))
					query.eq(entry.getKey(), entry.getValue());
				else
					query.like(entry.getKey(), entry.getValue());
			}
		});

		IPage<SysUser>			iPage	= super.page(page, query);

		PageInfoVO<SysUserDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysUserDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public UserVo selectByLoginName(String loginName) {
		return this.sysUserExMapper.selectByLoginName(loginName);
	}

	@Override
	public UserVo selectByUserId(Long userId) {
		return this.sysUserExMapper.selectByUserId(userId);
	}

}
