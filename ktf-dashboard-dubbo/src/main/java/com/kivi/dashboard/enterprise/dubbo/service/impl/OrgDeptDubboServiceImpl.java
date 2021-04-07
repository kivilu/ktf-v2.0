package com.kivi.dashboard.enterprise.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.org.dto.OrgDeptDTO;
import com.kivi.dashboard.org.entity.OrgDept;
import com.kivi.dashboard.org.mapper.OrgDeptMapper;
import com.kivi.dashboard.org.service.OrgDeptService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业部门 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class OrgDeptDubboServiceImpl extends ServiceImpl<OrgDeptMapper, OrgDept> implements OrgDeptService {

	@Autowired
	private OrgDeptService orgDeptService;

	/**
	 * 根据ID查询企业部门
	 */
	@KtfTrace("根据ID查询企业部门")
	@Override
	public OrgDeptDTO getDto(Long id) {
		return orgDeptService.getDto(id);
	}

	/**
	 * 新增企业部门
	 */
	@KtfTrace("新增企业部门")
	@Override
	public Boolean save(OrgDeptDTO dto) {

		return orgDeptService.save(dto);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业部门")
	@Override
	public Boolean updateById(OrgDeptDTO dto) {
		return orgDeptService.updateById(dto);
	}

	@Override
	public List<OrgDeptDTO> getChildren(Long pid) {
		return orgDeptService.getChildren(pid);
	}

	@Override
	public List<OrgDeptDTO> listByCorp(Long corpId) {
		return orgDeptService.listByCorp(corpId);
	}

	@Override
	public PageInfoVO<OrgDeptDTO> page(Map<String, Object> params) {
		return orgDeptService.page(params);
	}

	@Override
	public Boolean removeRecursion(Long id) {
		return orgDeptService.removeRecursion(id);
	}

	@Override
	public Boolean removeRecursion(Long[] ids) {
		return orgDeptService.removeRecursion(ids);
	}

}
