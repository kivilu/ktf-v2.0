package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysUserDTO;
import com.kivi.dashboard.sys.entity.SysUser;
import com.kivi.dashboard.sys.entity.vo.UserVo;
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
}
