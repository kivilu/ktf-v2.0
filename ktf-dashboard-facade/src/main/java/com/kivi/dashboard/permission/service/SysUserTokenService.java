package com.kivi.dashboard.permission.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.permission.dto.SysUserTokenDTO;
import com.kivi.dashboard.permission.entity.SysUserToken;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 系统用户Token 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysUserTokenService extends IService<SysUserToken> {

	/**
	 * 根据ID查询DTO
	 */
	SysUserTokenDTO getDTOById(Long id);

	/**
	 * 根据token查询
	 * 
	 * @param token
	 * @return
	 */
	SysUserToken selectByToken(String token);

	/**
	 * 新增
	 */
	Boolean save(SysUserTokenDTO sysUserTokenDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysUserTokenDTO sysUserTokenDTO);

	/**
	 * 查询列表
	 */
	List<SysUserTokenDTO> list(SysUserTokenDTO sysUserTokenDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysUserTokenDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysUserTokenDTO> listLike(SysUserTokenDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysUserTokenDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysUserTokenDTO> page(Map<String, Object> params);
}
