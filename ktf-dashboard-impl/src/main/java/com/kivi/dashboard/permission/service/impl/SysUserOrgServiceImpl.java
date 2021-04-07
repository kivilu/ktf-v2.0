package com.kivi.dashboard.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.permission.dto.SysUserOrgDTO;
import com.kivi.dashboard.permission.entity.SysUserOrg;
import com.kivi.dashboard.permission.mapper.SysUserOrgExMapper;
import com.kivi.dashboard.permission.mapper.SysUserOrgMapper;
import com.kivi.dashboard.permission.service.SysUserOrgService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.vip.vjtools.vjkit.collection.ListUtil;

/**
 * <p>
 * 监管用户与企业关联 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserOrgServiceImpl extends ServiceImpl<SysUserOrgMapper, SysUserOrg> implements SysUserOrgService {

	@Autowired
	SysUserOrgExMapper sysUserOrgExMapper;

	/**
	 * 根据ID查询监管用户与企业关联
	 */
	@KtfTrace("根据ID查询监管用户与企业关联")
	@Override
	public SysUserOrgDTO getDto(Long id) {
		SysUserOrg		entity	= super.getById(id);
		SysUserOrgDTO	dto		= BeanConverter.convert(SysUserOrgDTO.class, entity);
		return dto;
	}

	@KtfTrace("保存或修改用户所监管的企业关系")
	@Override
	public void saveOrUpdateUserOrg(Long userId, List<Long> orgIds) {
		// 先删除企业与用户关系
		Map<String, Object> params = new HashMap<>();
		params.put(SysUserOrg.DB_USER_ID, userId);
		this.removeByMap(params);
		if (orgIds == null || orgIds.size() == 0) {
			return;
		}

		// 保存用户与企业关系
		List<SysUserOrg> list = orgIds.stream().map(orgId -> {
			SysUserOrg userOrg = new SysUserOrg();
			userOrg.setUserId(userId);
			userOrg.setOrgId(orgId);
			return userOrg;
		}).collect(Collectors.toList());

		this.saveBatch(list);

	}

	@KtfTrace("根据用户批量删除")
	@Override
	public Boolean deleteBatchByUserIds(Long[] userIds) {
		if (userIds == null)
			return false;
		QueryWrapper<SysUserOrg> query = Wrappers.<SysUserOrg>query();
		query.in(SysUserOrg.DB_USER_ID, ListUtil.newArrayList(userIds));

		return super.remove(query);
	}

	@KtfTrace("根据企业批量删除")
	@Override
	public Boolean deleteBatchByOrgIds(Long[] orgIds) {
		if (orgIds == null)
			return false;
		QueryWrapper<SysUserOrg> query = Wrappers.<SysUserOrg>query();
		query.in(SysUserOrg.DB_ORG_ID, ListUtil.newArrayList(orgIds));

		return super.remove(query);
	}

	@Override
	public List<Long> selectOrgIdByUserId(Long userId) {
		return sysUserOrgExMapper.selectOrgIdByUserId(userId);
	}

	/**
	 * 新增监管用户与企业关联
	 */
//	@KtfTrace("新增监管用户与企业关联")
//	@Override
//	public Boolean save(SysUserOrgDTO sysUserEnterpriseDTO) {
//		SysUserOrg entity = BeanConverter.convert(SysUserOrg.class, sysUserEnterpriseDTO);
//
//		return super.save(entity);
//	}

	/**
	 * 修改
	 */
//	@KtfTrace("修改监管用户与企业关联")
//	@Override
//	public Boolean updateById(SysUserOrgDTO sysUserEnterpriseDTO) {
//		SysUserOrg entity = BeanConverter.convert(SysUserOrg.class, sysUserEnterpriseDTO);
//		return super.updateById(entity);
//	}

	/**
	 * 查询列表
	 */
//	@KtfTrace("查询列表监管用户与企业关联")
//	@Override
//	public List<SysUserOrgDTO> list(SysUserOrgDTO sysUserEnterpriseDTO) {
//		Map<String, Object> params = BeanConverter.beanToMap(sysUserEnterpriseDTO);
//		return this.list(params, new String[0]);
//	}

	/**
	 * 指定列查询列表
	 */
//	@KtfTrace("指定列查询列表监管用户与企业关联")
//	@Override
//	public List<SysUserOrgDTO> list(Map<String, Object> params, String... columns) {
//		if (params != null)
//			params.remove(KtfConstant.URL_TIMESTAMP);
//		QueryWrapper<SysUserOrg>	query	= Wrappers.<SysUserOrg>query().select(columns).allEq(true, params, false);
//		List<SysUserOrg>			list	= super.list(query);
//		return BeanConverter.convert(SysUserOrgDTO.class, list);
//	}

	/**
	 * 模糊查询
	 */
//	@KtfTrace("模糊查询监管用户与企业关联")
//	@Override
//	public List<SysUserOrgDTO> listLike(SysUserOrgDTO applicationDTO) {
//		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
//		return listLike(params, new String[0]);
//	}

	/**
	 * 指定列模糊查询
	 */
//	@Override
//	public List<SysUserOrgDTO> listLike(Map<String, Object> params, String... columns) {
//		if (params != null)
//			params.remove(KtfConstant.URL_TIMESTAMP);
//		QueryWrapper<SysUserOrg> query = Wrappers.<SysUserOrg>query().select(columns);
//		if (MapUtil.isNotEmpty(params)) {
//			params.entrySet().stream().forEach(entry -> {
//				if (ObjectKit.isNotEmpty(entry.getValue())) {
//					if (NumberKit.isNumberic(entry.getValue()))
//						query.eq(entry.getKey(), entry.getValue());
//					else
//						query.like(entry.getKey(), entry.getValue());
//				}
//			});
//		}
//
//		List<SysUserOrg> list = super.list(query);
//		return BeanConverter.convert(SysUserOrgDTO.class, list);
//	}

	/**
	 * 分页查询
	 */
//	@Override
//	@KtfTrace("分页查询用户与企业关联")
//	public PageInfoVO<SysUserOrgDTO> page(Map<String, Object> params) {
//		PageParams<SysUserOrgDTO>	pageParams	= new PageParams<>(params);
//		Page<SysUserOrg>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
//
//		QueryWrapper<SysUserOrg>	query		= Wrappers.<SysUserOrg>query();
//		if (MapUtil.isNotEmpty(params)) {
//			params.entrySet().stream().forEach(entry -> {
//				if (ObjectKit.isNotEmpty(entry.getValue())) {
//					if (NumberKit.isNumberic(entry.getValue()))
//						query.eq(entry.getKey(), entry.getValue());
//					else
//						query.like(entry.getKey(), entry.getValue());
//				}
//			});
//		}
//
//		IPage<SysUserOrg>			iPage	= super.page(page, query);
//
//		PageInfoVO<SysUserOrgDTO>	pageVo	= new PageInfoVO<>();
//		pageVo.setCurPage(iPage.getCurrent());
//		pageVo.setTotal(iPage.getTotal());
//		pageVo.setPageSize(iPage.getSize());
//		pageVo.setPages(iPage.getPages());
//		pageVo.setRequestMap(params);
//		pageVo.setList(BeanConverter.convert(SysUserOrgDTO.class, iPage.getRecords()));
//		pageVo.compute();
//
//		return pageVo;
//
//	}
//
//	@KtfTrace("根据ID查找所属企业ID")
//	@Override
//	public List<Long> selectEnterpriseIdByUserId(Long userId) {
//		return sysUserEnterpriseExMapper.selectEnterpriseIdByUserId(userId);
//	}

}
