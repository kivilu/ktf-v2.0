package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysUserDTO;
import com.kivi.dashboard.sys.entity.SysUser;
import com.kivi.dashboard.sys.mapper.SysUserMapper;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysUserDubboServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	private ISysUserService iSysUserService;

	/**
	 * 根据ID查询用户
	 */
	@KtfTrace("根据ID查询用户")
	@Override
	public SysUserDTO getDTOById(Long id) {
		return iSysUserService.getDTOById(id);
	}

	/**
	 * 新增用户
	 */
	@KtfTrace("新增用户")
	@Override
	public Boolean save(SysUserDTO sysUserDTO) {
		return iSysUserService.save(sysUserDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改用户")
	@Override
	public Boolean updateById(SysUserDTO sysUserDTO) {
		return iSysUserService.updateById(sysUserDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表用户")
	@Override
	public List<SysUserDTO> list(SysUserDTO sysUserDTO) {
		return iSysUserService.list(sysUserDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表用户")
	@Override
	public List<SysUserDTO> list(Map<String, Object> params, String... columns) {
		return iSysUserService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询用户")
	@Override
	public List<SysUserDTO> listLike(SysUserDTO sysUserDTO) {
		return iSysUserService.listLike(sysUserDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserDTO> listLike(Map<String, Object> params, String... columns) {
		return iSysUserService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询用户")
	public PageInfoVO<SysUserDTO> page(Map<String, Object> params) {
		return iSysUserService.page(params);

	}

	@Override
	public UserVo selectByLoginName(String loginName) {
		return this.iSysUserService.selectByLoginName(loginName);
	}

	@Override
	public UserVo selectByUserId(Long userId) {
		return this.iSysUserService.selectByUserId(userId);
	}

	@Override
	public List<Long> selectResourceIdListByUserId(Long userId) {
		return iSysUserService.selectResourceIdListByUserId(userId);
	}

	@Override
	public Set<String> selectUserPermissions(long userId) {

		return iSysUserService.selectUserPermissions(userId);
	}

	@Override
	public PageInfoVO<Map<String, Object>> selectDataGrid(Map<String, Object> params) {
		return iSysUserService.selectDataGrid(params);
	}

	@Override
	public boolean saveByVo(UserVo userVo) {
		return iSysUserService.saveByVo(userVo);
	}

	@Override
	public boolean updateByVo(UserVo userVo) {
		return iSysUserService.updateByVo(userVo);
	}

	@Override
	public Boolean deleteBatch(Long[] userIds) {
		return iSysUserService.deleteBatch(userIds);
	}

	@Override
	public List<Map<String, Object>> selectUserTree() {
		return iSysUserService.selectUserTree();
	}

}
