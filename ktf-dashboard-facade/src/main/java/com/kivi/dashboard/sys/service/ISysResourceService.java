package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.kivi.dashboard.sys.entity.SysResource;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.vo.ResourceVo;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 资源 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysResourceService extends IService<SysResource> {

	/**
	 * 根据ID查询DTO
	 */
	SysResourceDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysResourceDTO sysResourceDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysResourceDTO sysResourceDTO);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysResourceDTO> page(Map<String, Object> params);

	/**
	 * 指定列查询列表
	 */
	List<SysResourceDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 根据用户ID查询对应的资源列表
	 */
	List<ResourceVo> selectUserResourceListByUserId(Long userId, MenuType... types);

	/**
	 * 查询用户的所有菜单ID
	 *
	 * @param userId
	 * @return
	 */
	List<Long> selectResourceIdListByUserId(Long userId);

	/**
	 * 根据条件查询菜单列表
	 * 
	 * @param params
	 * @return
	 */
	List<ResourceVo> selectResourceList(Map<String, Object> params);

	/**
	 * 查询菜单树数据
	 * 
	 * @param params
	 * @return
	 */
	List<ResourceVo> selectMenuTreeList(Map<String, Object> params);

	/**
	 * 查询不是按钮类型的菜单
	 * 
	 * @return
	 */
	List<ResourceVo> selectNotButtonList();

	/**
	 * 查询指定ParentId的菜单列表
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	List<SysResource> selectListByParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据ParentId查询菜单
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysResource> selectListByParentId(Long parentId);

}
