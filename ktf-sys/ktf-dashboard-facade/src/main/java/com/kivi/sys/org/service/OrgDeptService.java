package com.kivi.sys.org.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgDeptDTO;
import com.kivi.sys.org.entity.OrgDept;

/**
 * <p>
 * 企业部门 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface OrgDeptService extends IService<OrgDept> {

	/**
	 * 根据ID查询DTO
	 */
	OrgDeptDTO getDto(Long id);

	/**
	 * 新增
	 */
	Boolean save(OrgDeptDTO dto);

	/**
	 * 修改
	 */
	Boolean updateById(OrgDeptDTO dto);

	/**
	 * 查询下级部门
	 * 
	 * @param id 当前节点ID
	 * @return
	 */
	List<OrgDeptDTO> getChildren(Long id);

	/**
	 * 模糊查询
	 */
	List<OrgDeptDTO> listByCorp(Long corpId);

	/**
	 * 分页查询
	 */
	PageInfoVO<OrgDeptDTO> page(Map<String, Object> params);

	/**
	 * 删除指定ID及下级
	 * 
	 * @return
	 */
	Boolean removeRecursion(Long id);

	/**
	 * 批量删除指定ID及下级
	 * 
	 * @return
	 */
	Boolean removeRecursion(Long[] ids);
}
