package com.kivi.dashboard.permission.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomer;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.dashboard.enums.KmsUserType;
import com.kivi.dashboard.permission.dto.SysUserDTO;
import com.kivi.dashboard.permission.entity.SysUser;
import com.kivi.dashboard.permission.mapper.SysUserExMapper;
import com.kivi.dashboard.permission.mapper.SysUserMapper;
import com.kivi.dashboard.permission.service.SysUserOrgService;
import com.kivi.dashboard.permission.service.SysUserRoleService;
import com.kivi.dashboard.permission.service.SysUserService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.KtfGender;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.framework.vo.UserVo;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = { Exception.class, SQLException.class })
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	SysUserExMapper					sysUserExMapper;

	@Autowired
	private KtfCommonProperties		ktfCommonProperties;

	@Autowired
	private SysUserRoleService		sysUserRoleService;

	@Autowired
	private SysUserOrgService		sysUserOrgService;

	@Autowired
	private CifCustomerAuthsService	customerAuthsService;

	@Autowired
	private CifCustomerService		customerService;

	/**
	 * 根据ID查询用户
	 */
	@Cacheable(cacheNames = KtfCache.SysUser, key = "caches[0].name+'.dto.'+#id", unless = "#result == null")
	@KtfTrace("根据ID查询用户")
	@Override
	public SysUserDTO getDto(Long id) {
		Map<String, Object> map = MapUtil.newHashMap("userId", id);
		return sysUserExMapper.selectUserDto(map);
	}

	@Cacheable(cacheNames = KtfCache.SysUser, key = "caches[0].name+'.entity.'+#id", unless = "#result == null")
	@KtfTrace("根据ID查询用户")
	@Override
	public SysUser getById(Serializable id) {
		return super.getById(id);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询用户")
	public PageInfoVO<SysUserDTO> page(Map<String, Object> params) {
		PageParams<SysUserDTO>	pageParams	= new PageParams<>(params);
		Page<SysUserDTO>		page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		IPage<SysUserDTO>		iPage		= sysUserExMapper.selectByPage(page, pageParams.getRequestMap());
		PageInfoVO<SysUserDTO>	pageVo		= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(iPage.getRecords());
		pageVo.compute();

		return pageVo;

	}

	@Cacheable(cacheNames = KtfCache.SysUser, key = "caches[0].name+'.vo.'+#loginName", unless = "#result == null")
	@Override
	public UserVo getUserVo(String loginName) {
		Map<String, Object> map = new HashMap<>();
		map.put(SysUserDTO.LOGIN_NAME, loginName);
		map.put("statusList", Arrays.asList(KtfStatus.ENABLED.code, KtfStatus.INIT.code));
		UserVo userVo = this.sysUserExMapper.selectUserVo(map);

		return userVo;
	}

	@Cacheable(cacheNames = KtfCache.SysUser, key = "caches[0].name+'.vo.'+#id", unless = "#result == null")
	@Override
	public UserVo getUserVo(Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put(SysUserDTO.ID, id);
		map.put("statusList", Arrays.asList(KtfStatus.ENABLED.code, KtfStatus.INIT.code));
		UserVo userVo = this.sysUserExMapper.selectUserVo(map);

		return userVo;
	}

	/**
	 * 新增用户
	 */
	@KtfCacheEvict(cacheNames = KtfCache.SysUser)
	@KtfTrace("新增用户")
	@Override
	public Boolean save(SysUserDTO dto) {
		// 检查角色是否越权
		checkRole(dto);

		// 保存客户信息
		CifCustomer customer = new CifCustomer();
		customer.setCustomerId(dto.getLoginName());
		customer.setName(dto.getName());
		customer.setRegPhoneNumber(dto.getPhone());
		customer.setEmail(dto.getEmail());
		customer.setGender(KtfGender.valueOf(dto.getSex()).text);
		CifCustomer entity = customerService.getByCustomerId(dto.getLoginName());
		if (entity == null) {
			customer.setStatus(KtfStatus.ENABLED.text);
		} else {
			customer.setId(entity.getId());
		}
		customerService.saveOrUpdate(customer);

		// 保存用户鉴权信息
		CifCustomerAuthsDTO cifAuth = new CifCustomerAuthsDTO();
		cifAuth.setAppId(ktfCommonProperties.getAppId());
		cifAuth.setCifId(customer.getId());
		cifAuth.setIdentityType(dto.getLoginMode());
		cifAuth.setIdentifier(dto.getLoginName());
		cifAuth.setCredential(dto.getPassword());
		cifAuth.setUserType(UserType.valueOf(dto.getUserType()).code);
		Long userId = customerAuthsService.save(cifAuth);

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdateUserRole(userId, dto.getRoleIds());

		// 保存用户与企业关系关系
		sysUserOrgService.saveOrUpdateUserOrg(userId, dto.getOrgIds());

		// 创建用户
		SysUser user = new SysUser();
		user.setId(userId);
		user.setCifId(cifAuth.getCifId());
		user.setAppId(cifAuth.getAppId());
		user.setLoginMode(dto.getLoginMode());
		user.setOrgId(dto.getOrgId());
		user.setDeptId(dto.getDeptId());
		user.setTitleId(dto.getTitleId());
		// 非API用户，状态默认为初始状态， API用户，状态默认为有效状态
		user.setStatus(
				user.getUserType() == KmsUserType.API_USER.getCode() ? KtfStatus.ENABLED.code : KtfStatus.INIT.code);
		user.setCreateUserId(dto.getCreateUserId());
		this.save(user);

		return true;
	}

	/**
	 * 修改
	 */
	@KtfCacheEvict(cacheNames = { KtfCache.SysUser, KtfCache.SysResource })
	@KtfTrace("修改用户")
	@Override
	public boolean updateById(SysUser entity) {
		return super.updateById(entity);
	}

	@KtfCacheEvict(cacheNames = { KtfCache.SysUser, KtfCache.SysResource })
	@Override
	public boolean update(SysUserDTO dto) {
		// 检查角色是否越权
		checkRole(dto);

		SysUser user = BeanConverter.convert(SysUser.class, dto);
		this.updateById(user);

		CifCustomer customer = new CifCustomer();
		customer.setId(dto.getCifId());
		customer.setName(dto.getName());
		customer.setRegPhoneNumber(dto.getPhone());
		customer.setEmail(dto.getEmail());
		customer.setGender(KtfGender.valueOf(dto.getSex()).text);
		customerService.saveOrUpdate(customer);

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdateUserRole(dto.getId(), dto.getRoleIds());

		// 保存用户与企业关系关系
		sysUserOrgService.saveOrUpdateUserOrg(dto.getId(), dto.getOrgIds());

		return true;
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserDTO dto) {
		if (dto.getRoleIds() == null || dto.getRoleIds().isEmpty()) {
			return;
		}
		// 如果不是超级管理员，则需要判断用户的角色是否自己创建
		if (dto.getCreateUserId() == KtfConstant.SUPER_ADMIN) {
			return;
		}
		// 查询用户创建的角色列表
		List<Long> roleIdList = sysUserRoleService.selectRoleIdListByUserId(dto.getCreateUserId());

		// 判断是否越权
		if (!roleIdList.containsAll(dto.getRoleIds())) {
			throw new KtfException("新增用户所选角色，不是本人创建");
		}
	}

	@KtfCacheEvict(cacheNames = { KtfCache.SysUser, KtfCache.SysResource })
	@Override
	public Boolean deleteBatch(Long[] userIds) {
		List<Long> ids = Arrays.asList(userIds);

		this.removeByIds(ids);
		// 删除用户认证记录
		customerAuthsService.removeByIds(ids);
		// 删除用户与角色关系
		sysUserRoleService.deleteBatchByUserIds(userIds);
		// 删除监管用户与企业关系
		sysUserOrgService.deleteBatchByUserIds(userIds);
		return true;
	}

//	@Cacheable(cacheNames = KtfCache.SysUser, key = "caches[0].name+'.vo.'+#userId", unless = "#result == null")
//	@Override
//	public UserVo selectByUserId(Long userId) {
//
//		return this.sysUserExMapper.selectByUserId(userId);
//	}

	/*
	 * @Cacheable(cacheNames = KtfCache.SysUser, key =
	 * "caches[0].name+'.pm.'+#userId", unless = "#result == null")
	 * 
	 * @Override public Set<String> selectUserPermissions(long userId) {
	 * List<String> permsList; // 系统管理员，拥有最高权限 if (userId ==
	 * KtfConstant.SUPER_ADMIN) { permsList =
	 * sysResourceService.selectUrlsByUserId(userId); } else { permsList =
	 * this.sysUserExMapper.selectPerms(userId); } // 用户权限列表 Set<String> permsSet =
	 * permsList.stream().map(perm -> StringUtils.split(perm, ",")) .flatMap(perm ->
	 * Arrays.stream(perm)).collect(Collectors.toSet());
	 * 
	 * Set<String> permsSet = new HashSet<>(); for (String perms : permsList) { if
	 * (StringUtils.isBlank(perms)) { continue; }
	 * permsSet.addAll(Arrays.asList(perms.trim().split(","))); }
	 * 
	 * return permsSet; }
	 */

	/*
	 * @Override public List<Map<String, Object>> selectUserTree() { return
	 * sysUserExMapper.selectUserTree(); }
	 */

	/*
	 * private Long getRoleId(Integer userType) { return userType.longValue();
	 * 
	 * KmsUserType type = KmsUserType.valueOf(userType); String role =
	 * sysDicService.getByVarName(type.name(), "用户类型角色映射").getVarCode(); return
	 * Long.parseLong(role);
	 * 
	 * }
	 */

}
