package com.kivi.dashboard.enterprise.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.enterprise.dto.EnterpriseDepartmentDTO;
import com.kivi.dashboard.enterprise.entity.EnterpriseDepartment;
import com.kivi.dashboard.enterprise.mapper.EnterpriseDepartmentMapper;
import com.kivi.dashboard.enterprise.service.IEnterpriseDepartmentService;
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

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class EnterpriseDepartmentDubboServiceImpl extends ServiceImpl<EnterpriseDepartmentMapper, EnterpriseDepartment>
		implements IEnterpriseDepartmentService {

	@Autowired
	private IEnterpriseDepartmentService enterpriseDepartmentService;

	/**
	 * 根据ID查询企业部门
	 */
	@KtfTrace("根据ID查询企业部门")
	@Override
	public EnterpriseDepartmentDTO getDTOById(Long id) {
		return enterpriseDepartmentService.getDTOById(id);
	}

	/**
	 * 新增企业部门
	 */
	@KtfTrace("新增企业部门")
	@Override
	public Boolean save(EnterpriseDepartmentDTO enterpriseDepartmentDTO) {

		return enterpriseDepartmentService.save(enterpriseDepartmentDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业部门")
	@Override
	public Boolean updateById(EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		return enterpriseDepartmentService.updateById(enterpriseDepartmentDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业部门")
	@Override
	public List<EnterpriseDepartmentDTO> list(EnterpriseDepartmentDTO enterpriseDepartmentDTO) {
		return enterpriseDepartmentService.list(enterpriseDepartmentDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表企业部门")
	@Override
	public List<EnterpriseDepartmentDTO> list(Map<String, Object> params, String... columns) {
		return enterpriseDepartmentService.listLike(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询企业部门")
	@Override
	public List<EnterpriseDepartmentDTO> listLike(EnterpriseDepartmentDTO applicationDTO) {
		return enterpriseDepartmentService.listLike(applicationDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<EnterpriseDepartmentDTO> listLike(Map<String, Object> params, String... columns) {

		return enterpriseDepartmentService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询企业部门")
	public PageInfoVO<EnterpriseDepartmentDTO> page(Map<String, Object> params) {

		return enterpriseDepartmentService.page(params);

	}

	@Override
	public List<Map<String, Object>> selectTreeGrid(Map<String, Object> params) {
		return enterpriseDepartmentService.selectTreeGrid(params);
	}

	@Override
	public List<EnterpriseDepartmentDTO> selectEnterpriseDepartmentList(Map<String, Object> params) {
		return enterpriseDepartmentService.selectEnterpriseDepartmentList(params);
	}

	@Override
	public Boolean removeByParentId(Long parentId) {
		return enterpriseDepartmentService.removeByParentId(parentId);
	}

	@Override
	public Boolean removeByParentIds(Long[] parentIds) {
		return enterpriseDepartmentService.removeByParentIds(parentIds);
	}

}
