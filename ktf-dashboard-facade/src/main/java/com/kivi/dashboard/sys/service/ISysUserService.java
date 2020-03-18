package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysUserDTO;
import com.kivi.dashboard.sys.entity.SysUser;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysUserService extends IService<SysUser> {

	/**
	 * 根据ID查询DTO
	 */
	SysUserDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysUserDTO sysUserDTO);

	/**
	 * 新增用户
	 * 
	 * @param userVo
	 * @return
	 */
	boolean saveByVo(UserVo userVo);

	/**
	 * 更新用户
	 * 
	 * @param userVo
	 * @return
	 */
	boolean updateByVo(UserVo userVo);

	/**
	 * 修改
	 */
	Boolean updateById(SysUserDTO sysUserDTO);

	/**
	 * 查询列表
	 */
	List<SysUserDTO> list(SysUserDTO sysUserDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysUserDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysUserDTO> listLike(SysUserDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysUserDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysUserDTO> page(Map<String, Object> params);

	/**
	 * 根据用户名查找
	 * 
	 * @param loginName
	 * @return
	 */
	UserVo selectByLoginName(String loginName);

	/**
	 * 根据用户ID查找
	 * 
	 * @param userId
	 * @return
	 */
	UserVo selectByUserId(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 *
	 * @param userId
	 * @return
	 */
	List<Long> selectResourceIdListByUserId(Long userId);

	/**
	 * 获取用户权限列表
	 */
	Set<String> selectUserPermissions(long userId);

	/**
	 * 查找用户选择树
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectUserTree();

	/**
	 * 分页查询
	 *
	 * @param params
	 * @return
	 */
	PageInfoVO<Map<String, Object>> selectDataGrid(Map<String, Object> params);

	/**
	 * 根据用户批量删除
	 *
	 * @param userIds
	 */
	Boolean deleteBatch(Long[] userIds);
}
