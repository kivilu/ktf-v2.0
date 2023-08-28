package com.kivi.sys.org.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgTitleDTO;
import com.kivi.sys.org.entity.OrgDept;
import com.kivi.sys.org.entity.OrgTitle;
import com.kivi.sys.org.mapper.OrgTitleExMapper;
import com.kivi.sys.org.mapper.OrgTitleMapper;
import com.kivi.sys.org.service.OrgTitleService;

/**
 * <p>
 * 企业职务配置 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgTitleServiceImpl extends ServiceImpl<OrgTitleMapper, OrgTitle> implements OrgTitleService {

	@Autowired
	private OrgTitleExMapper orgTitleExMapper;

	/**
	 * 根据ID查询企业职务配置
	 */
	@KtfTrace("根据ID查询企业职务配置")
	@Override
	public OrgTitleDTO getDto(Long id) {
		OrgTitle	entity	= super.getById(id);
		OrgTitleDTO	dto		= BeanConverter.convert(OrgTitleDTO.class, entity);
		return dto;
	}

	/**
	 * 新增企业职务配置
	 */
	@KtfTrace("新增企业职务配置")
	@Override
	public Boolean save(OrgTitleDTO dto) {
		OrgTitle entity = BeanConverter.convert(OrgTitle.class, dto);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业职务配置")
	@Override
	public Boolean updateById(OrgTitleDTO dto) {
		OrgTitle entity = BeanConverter.convert(OrgTitle.class, dto);
		return super.updateById(entity);
	}

	@Override
	public PageInfoVO<OrgTitleDTO> page(Map<String, Object> params) {
		if (params == null)
			params = new HashMap<>();
		if (!params.containsKey(OrgTitleDTO.STATUS))
			params.put(OrgTitleDTO.STATUS, 0);
		PageParams<OrgTitleDTO>	pageParams	= new PageParams<>(params);
		Page<OrgTitleDTO>		page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
		IPage<OrgTitleDTO>		iPage		= orgTitleExMapper.selectDTO(page, params);
		PageInfoVO<OrgTitleDTO>	pageVo		= new PageInfoVO<>();
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
	public List<OrgTitleDTO> list(Map<String, Object> params) {
		if (params == null)
			params = new HashMap<>();
		params.put(OrgDept.STATUS, 0);
		return orgTitleExMapper.selectDTO(params);
	}

}
