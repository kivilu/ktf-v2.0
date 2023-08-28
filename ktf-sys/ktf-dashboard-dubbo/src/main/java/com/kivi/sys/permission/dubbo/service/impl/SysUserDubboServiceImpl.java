package com.kivi.sys.permission.dubbo.service.impl;

import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.permission.dto.SysUserDTO;
import com.kivi.sys.permission.entity.SysUser;
import com.kivi.sys.permission.mapper.SysUserMapper;
import com.kivi.sys.permission.service.SysUserService;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysUserDubboServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 根据ID查询用户
	 */
	@KtfTrace("根据ID查询用户")
	@Override
	public SysUserDTO getDto(Long id) {
		return sysUserService.getDto(id);
	}

	@Override
	public UserVo getUserVo(String loginName) {
		return sysUserService.getUserVo(loginName);
	}

	@Override
	public UserVo getUserVo(Long id) {
		return sysUserService.getUserVo(id);
	}

	@Override
	public PageInfoVO<SysUserDTO> page(Map<String, Object> params) {
		return sysUserService.page(params);
	}

	@Override
	public boolean update(SysUserDTO dto) {
		return sysUserService.update(dto);
	}

	@Override
	public Boolean deleteBatch(Long[] userIds) {
		return sysUserService.deleteBatch(userIds);
	}

	@Override
	public Long save(SysUserDTO dto) {
		return sysUserService.save(dto);
	}

	@Override
	public PageInfoVO<SysUserDTO> pageSimple(Map<String, Object> params) {
		return sysUserService.pageSimple(params);
	}

	@Override
	public Boolean isUserExist(String userName) {
		return sysUserService.isUserExist(userName);
	}

}
