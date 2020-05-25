package com.kivi.dashboard.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysUserEnterpriseDTO;
import com.kivi.dashboard.sys.entity.SysUserEnterprise;
import com.kivi.dashboard.sys.mapper.SysUserEnterpriseExMapper;
import com.kivi.dashboard.sys.mapper.SysUserEnterpriseMapper;
import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.collection.MapUtil;

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
public class SysUserEnterpriseServiceImpl extends ServiceImpl<SysUserEnterpriseMapper, SysUserEnterprise>
		implements ISysUserEnterpriseService {

	@Autowired
	SysUserEnterpriseExMapper sysUserEnterpriseExMapper;

	/**
	 * 根据ID查询监管用户与企业关联
	 */
	@KtfTrace("根据ID查询监管用户与企业关联")
	@Override
	public SysUserEnterpriseDTO getDTOById(Long id) {
		SysUserEnterprise		entity	= super.getById(id);
		SysUserEnterpriseDTO	dto		= BeanConverter.convert(SysUserEnterpriseDTO.class, entity,
				BeanConverter.long2String, BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增监管用户与企业关联
	 */
	@KtfTrace("新增监管用户与企业关联")
	@Override
	public Boolean save(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		SysUserEnterprise entity = BeanConverter.convert(SysUserEnterprise.class, sysUserEnterpriseDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改监管用户与企业关联")
	@Override
	public Boolean updateById(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		SysUserEnterprise entity = BeanConverter.convert(SysUserEnterprise.class, sysUserEnterpriseDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表监管用户与企业关联")
	@Override
	public List<SysUserEnterpriseDTO> list(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysUserEnterpriseDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表监管用户与企业关联")
	@Override
	public List<SysUserEnterpriseDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUserEnterprise>	query	= Wrappers.<SysUserEnterprise>query().select(columns).allEq(true,
				params, false);
		List<SysUserEnterprise>			list	= super.list(query);
		return BeanConverter.convert(SysUserEnterpriseDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询监管用户与企业关联")
	@Override
	public List<SysUserEnterpriseDTO> listLike(SysUserEnterpriseDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserEnterpriseDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUserEnterprise> query = Wrappers.<SysUserEnterprise>query().select(columns);
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

		List<SysUserEnterprise> list = super.list(query);
		return BeanConverter.convert(SysUserEnterpriseDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询用户与企业关联")
	public PageInfoVO<SysUserEnterpriseDTO> page(Map<String, Object> params) {
		PageParams<SysUserEnterpriseDTO>	pageParams	= new PageParams<>(params);
		Page<SysUserEnterprise>				page		= new Page<>(pageParams.getCurrPage(),
				pageParams.getPageSize());

		QueryWrapper<SysUserEnterprise>		query		= Wrappers.<SysUserEnterprise>query();
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

		IPage<SysUserEnterprise>			iPage	= super.page(page, query);

		PageInfoVO<SysUserEnterpriseDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysUserEnterpriseDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@KtfTrace("根据ID查找所属企业ID")
	@Override
	public List<Long> selectEnterpriseIdByUserId(Long userId) {
		return sysUserEnterpriseExMapper.selectEnterpriseIdByUserId(userId);
	}

	@KtfTrace("保存或修改用户所监管的企业关系")
	@Override
	public void saveOrUpdateUserEnterprise(Long userId, List<Long> enterpriseIdList) {
		// 先删除企业与用户关系
		Map<String, Object> params = new HashMap<>();
		params.put(SysUserEnterprise.DB_USER_ID, userId);
		this.removeByMap(params);
		if (enterpriseIdList == null || enterpriseIdList.size() == 0) {
			return;
		}

		// 保存用户与企业关系
		List<SysUserEnterprise> list = enterpriseIdList.stream().map(enterpriseId -> {
			SysUserEnterprise sysUserEnterprise = new SysUserEnterprise();
			sysUserEnterprise.setUserId(userId);
			sysUserEnterprise.setEnterpriseId(enterpriseId);
			return sysUserEnterprise;
		}).collect(Collectors.toList());

		this.saveBatch(list);

	}

	@KtfTrace("根据用户批量删除")
	@Override
	public Boolean deleteBatchByUserIds(Long[] userIds) {
		if (userIds == null)
			return false;
		QueryWrapper<SysUserEnterprise> query = Wrappers.<SysUserEnterprise>query();
		query.in(SysUserEnterprise.DB_USER_ID, ListUtil.newArrayList(userIds));

		return super.remove(query);
	}

	@KtfTrace("根据企业批量删除")
	@Override
	public Boolean deleteBatchByEnterpriseIds(Long[] enterpriseIds) {
		if (enterpriseIds == null)
			return false;
		QueryWrapper<SysUserEnterprise> query = Wrappers.<SysUserEnterprise>query();
		query.in(SysUserEnterprise.DB_ENTERPRISE_ID, ListUtil.newArrayList(enterpriseIds));

		return super.remove(query);
	}

}
