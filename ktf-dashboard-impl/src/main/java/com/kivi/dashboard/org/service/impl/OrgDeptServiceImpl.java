package com.kivi.dashboard.org.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.org.dto.OrgDeptDTO;
import com.kivi.dashboard.org.entity.OrgDept;
import com.kivi.dashboard.org.mapper.OrgDeptExMapper;
import com.kivi.dashboard.org.mapper.OrgDeptMapper;
import com.kivi.dashboard.org.service.OrgDeptService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业部门 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgDeptServiceImpl extends ServiceImpl<OrgDeptMapper, OrgDept> implements OrgDeptService {

	@Autowired
	private OrgDeptExMapper orgDeptExMapper;

	/**
	 * 根据ID查询企业部门
	 */
	@KtfTrace("根据ID查询企业部门")
	@Override
	public OrgDeptDTO getDto(Long id) {
		OrgDept entity = super.getById(id);
		if (entity == null)
			return null;
		OrgDeptDTO dto = BeanConverter.convert(OrgDeptDTO.class, entity);
		return dto;
	}

	/**
	 * 新增企业部门
	 */
	@KtfTrace("新增企业部门")
	@Override
	public Boolean save(OrgDeptDTO dto) {
		OrgDept entity = BeanConverter.convert(OrgDept.class, dto);
		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业部门")
	@Override
	public Boolean updateById(OrgDeptDTO dto) {
		OrgDept entity = BeanConverter.convert(OrgDept.class, dto);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业部门")
	@Override
	public List<OrgDeptDTO> getChildren(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(OrgDept.ID, id);
		params.put(OrgDept.STATUS, 0);
		params.put("hasSelf", false);
		List<OrgDeptDTO> list = orgDeptExMapper.getChildren(params);

		return list;
	}

	/**
	 * 分页查询
	 */
	@KtfTrace("分页查询企业部门")
	public PageInfoVO<OrgDeptDTO> page(Map<String, Object> params) {
		PageParams<OrgDeptDTO>	pageParams	= new PageParams<>(params);
		Page<OrgDeptDTO>		page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
		IPage<OrgDeptDTO>		iPage		= orgDeptExMapper.selectDTO(page, params);

		// 获取没有没有被查询到的父节点
		List<OrgDeptDTO>		deptList	= iPage.getRecords();
		List<Long>				ids			= deptList.stream().map(OrgDeptDTO::getId).collect(Collectors.toList());
		List<Long>				pids		= deptList.stream().map(OrgDeptDTO::getParentId)
				.collect(Collectors.toList());

		List<Long>				missPids	= pids.stream().filter(pid -> !ids.contains(pid))
				.collect(Collectors.toList());

		Map<String, Object>		map			= new HashMap<>();
		map.put("hasSelf", true);
		missPids.stream().forEach(id -> {
			if (!ids.contains(id)) {
				map.put(OrgDept.ID, id);
				List<OrgDeptDTO>	parents		= orgDeptExMapper.getParents(map);
				List<OrgDeptDTO>	missParents	= parents.stream().filter(parent -> ids.contains(parent.getId()))
						.collect(Collectors.toList());
				if (!missParents.isEmpty()) {
					deptList.addAll(missParents);
					List<Long> newIds = missParents.stream().map(OrgDeptDTO::getId).collect(Collectors.toList());
					ids.addAll(newIds);
				}
			}
		});

		PageInfoVO<OrgDeptDTO> pageVo = new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(deptList);
		pageVo.compute();

		return pageVo;

	}

	@Override
	public List<OrgDeptDTO> listByCorp(Long corpId) {
		LambdaQueryWrapper<OrgDept> query = Wrappers.<OrgDept>lambdaQuery().select(OrgDept::getId, OrgDept::getParentId,
				OrgDept::getName, OrgDept::getStatus, OrgDept::getAbbr);
		query.eq(OrgDept::getCorpId, corpId);
		List<OrgDept> list = super.list(query);

		return BeanConverter.convert(OrgDeptDTO.class, list);
	}

	@Override
	public Boolean removeRecursion(Long id) {
		try {
			orgDeptExMapper.deleteWithChildren(id);
		} catch (Exception e) {
			throw new KtfException(KtfError.E_SERVICE_UNAVAILABLE, "数据库错误", e);
		}

		return true;
	}

	@Override
	public Boolean removeRecursion(Long[] ids) {
		LambdaQueryWrapper<OrgDept> query = Wrappers.<OrgDept>lambdaQuery();
		query.in(OrgDept::getId, Arrays.asList(ids)).or().in(OrgDept::getParentId, Arrays.asList(ids));
		try {
			if (super.remove(query)) {
				orgDeptExMapper.deleteOrphan();
			}
		} catch (Exception e) {
			throw new KtfException(KtfError.E_SERVICE_UNAVAILABLE, "数据库错误", e);
		}

		return true;
	}

}
