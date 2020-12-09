package com.kivi.dashboard.permission.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.permission.dto.SysUserDTO;
import com.kivi.dashboard.permission.entity.SysUser;
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
public interface SysUserService extends IService<SysUser> {

	/**
	 * 根据ID查询DTO
	 */
	SysUserDTO getDto(Long id);

	/**
	 * 根据用户名查找
	 * 
	 * @param loginName
	 * @return
	 */
	UserVo getUserVo(String loginName);

	/**
	 * 根据ID查找
	 * 
	 * @param id
	 * @return
	 */
	UserVo getUserVo(Long id);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysUserDTO> page(Map<String, Object> params);

	/**
	 * 新增
	 */
	Boolean save(SysUserDTO dto);

	/**
	 * 更新用户
	 * 
	 * @param userVo
	 * @return
	 */
	boolean update(SysUserDTO dto);

	/**
	 * 根据用户批量删除
	 *
	 * @param userIds
	 */
	Boolean deleteBatch(Long[] userIds);

	/**
	 * 获取用户权限列表
	 */
	// Set<String> selectUserPermissions(long userId);

	/**
	 * 查找用户选择树
	 * 
	 * @return
	 */
	// List<Map<String, Object>> selectUserTree();

}
