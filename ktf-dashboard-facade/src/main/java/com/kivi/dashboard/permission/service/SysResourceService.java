package com.kivi.dashboard.permission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.permission.dto.SysResourceDTO;
import com.kivi.dashboard.permission.entity.SysResource;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 资源 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysResourceService extends IService<SysResource> {

	/**
	 * 根据ID查询DTO
	 */
	SysResourceDTO getDto(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysResourceDTO dto);

	/**
	 * 修改
	 */
	Boolean updateById(SysResourceDTO dto);

	/**
	 * 
	 * @param params
	 * @return
	 */
	List<SysResourceDTO> selectMenutList(Map<String, Object> params);

	/**
	 * 根据用户ID和资源类型查询
	 * 
	 * @param userId 用户ID
	 * @param types  资源类型
	 * @return
	 */
	List<SysResourceDTO> selectResources(Long userId, MenuType... types);

	/**
	 * 根据用户ID查询URL
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> getPermissions(List<Long> roleIds);

	/**
	 * 查询全部下菜单
	 * 
	 * @param id
	 * @return
	 */
	List<SysResourceDTO> getChildren(Long id, Boolean recursion);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysResourceDTO> tops(Map<String, Object> params);

}
