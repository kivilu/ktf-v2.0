package com.kivi.dashboard.sys.service.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.kivi.cif.entity.CifCustomer;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.dashboard.enums.KmsUserType;
import com.kivi.dashboard.sys.dto.SysResourceDTO;
import com.kivi.dashboard.sys.dto.SysUserDTO;
import com.kivi.dashboard.sys.entity.SysUser;
import com.kivi.dashboard.sys.mapper.SysUserExMapper;
import com.kivi.dashboard.sys.mapper.SysUserMapper;
import com.kivi.dashboard.sys.service.ISysDicService;
import com.kivi.dashboard.sys.service.ISysResourceService;
import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.dashboard.sys.service.ISysUserRoleService;
import com.kivi.dashboard.sys.service.ISysUserService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.component.KtfKit;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.KtfGender;
import com.kivi.framework.constant.enums.KtfIdentifyType;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.UserType;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	// 控制线程数，最优选择是处理器线程数*3
	private final static int			THREAD_COUNT	= Runtime.getRuntime().availableProcessors() * 3;

	@Autowired
	SysUserExMapper						sysUserExMapper;

	@Autowired
	private ISysResourceService			sysResourceService;

	@Autowired
	private ISysUserRoleService			sysUserRoleService;

	@Autowired
	private ISysUserEnterpriseService	sysUserEnterpriseService;

	@Autowired
	private CifCustomerAuthsService		customerAuthsService;

	@Autowired
	private CifCustomerService			customerService;

	@Autowired
	private ISysDicService				sysDicService;

	/**
	 * 根据ID查询用户
	 */
	@KtfTrace("根据ID查询用户")
	@Override
	public SysUserDTO getDTOById(Long id) {
		SysUser		entity	= super.getById(id);
		SysUserDTO	dto		= BeanConverter.convert(SysUserDTO.class, entity, BeanConverter.long2String,
				BeanConverter.integer2String);
		return dto;
	}

	/**
	 * 新增用户
	 */
	@KtfTrace("新增用户")
	@Override
	public Boolean save(SysUserDTO sysUserDTO) {
		SysUser entity = BeanConverter.convert(SysUser.class, sysUserDTO);

		return super.save(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改用户")
	@Override
	public Boolean updateById(SysUserDTO sysUserDTO) {
		SysUser entity = BeanConverter.convert(SysUser.class, sysUserDTO);
		return super.updateById(entity);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表用户")
	@Override
	public List<SysUserDTO> list(SysUserDTO sysUserDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(sysUserDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表用户")
	@Override
	public List<SysUserDTO> list(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUser>	query	= Wrappers.<SysUser>query().select(columns).allEq(true, params, false);
		List<SysUser>			list	= super.list(query);
		return BeanConverter.convert(SysUserDTO.class, list);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询用户")
	@Override
	public List<SysUserDTO> listLike(SysUserDTO applicationDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
		return listLike(params, new String[0]);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserDTO> listLike(Map<String, Object> params, String... columns) {
		if (params != null)
			params.remove(KtfConstant.URL_TIMESTAMP);
		QueryWrapper<SysUser> query = Wrappers.<SysUser>query().select(columns);
		if (MapUtil.isNotEmpty(params)) {
			params.entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		List<SysUser> list = super.list(query);
		return BeanConverter.convert(SysUserDTO.class, list);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询用户")
	public PageInfoVO<SysUserDTO> page(Map<String, Object> params) {
		PageParams<SysUserDTO>	pageParams	= new PageParams<>(params);
		Page<SysUser>			page		= new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

		QueryWrapper<SysUser>	query		= Wrappers.<SysUser>query();
		if (MapUtil.isNotEmpty(pageParams.getRequestMap())) {
			pageParams.getRequestMap().entrySet().stream().forEach(entry -> {
				if (ObjectKit.isNotEmpty(entry.getValue())) {
					if (NumberKit.isNumberic(entry.getValue()))
						query.eq(entry.getKey(), entry.getValue());
					else
						query.like(entry.getKey(), entry.getValue());
				}
			});
		}

		IPage<SysUser>			iPage	= super.page(page, query);

		PageInfoVO<SysUserDTO>	pageVo	= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(BeanConverter.convert(SysUserDTO.class, iPage.getRecords()));
		pageVo.compute();

		return pageVo;

	}

	@Override
	public UserVo selectByLoginName(String loginName) {
		UserVo userVo = this.sysUserExMapper.selectByLoginName(loginName);

		return userVo;
	}

	@Override
	public UserVo selectByUserId(Long userId) {
		return this.sysUserExMapper.selectByUserId(userId);
	}

	@Override
	public List<Long> selectResourceIdListByUserId(Long userId) {
		return sysUserExMapper.selectResourceIdListByUserId(userId);
	}

	@Override
	public Set<String> selectUserPermissions(long userId) {
		List<String> permsList;
		// 系统管理员，拥有最高权限
		if (userId == KtfConstant.SUPER_ADMIN) {
			List<SysResourceDTO> menuList = sysResourceService.list(Maps.newHashMap(), SysResourceDTO.URL);
			permsList = menuList.stream().map(SysResourceDTO::getUrl).collect(Collectors.toList());
		} else {
			permsList = this.sysUserExMapper.selectPerms(userId);
		}
		// 用户权限列表
		Set<String> permsSet = permsList.stream().map(perm -> StringUtils.split(perm, ","))
				.flatMap(perm -> Arrays.stream(perm)).collect(Collectors.toSet());
		/*
		 * Set<String> permsSet = new HashSet<>(); for (String perms : permsList) { if
		 * (StringUtils.isBlank(perms)) { continue; }
		 * permsSet.addAll(Arrays.asList(perms.trim().split(","))); }
		 */
		return permsSet;
	}

	@Override
	public PageInfoVO<Map<String, Object>> selectDataGrid(Map<String, Object> params) {
		PageParams<Map<String, Object>>	pageParams	= new PageParams<>(params);
		IPage<Map<String, Object>>		iPage		= this.sysUserExMapper.selectSysUserPage(pageParams,
				pageParams.getRequestMap());

		PageInfoVO<Map<String, Object>>	pageVo		= new PageInfoVO<>();
		pageVo.setCurPage(iPage.getCurrent());
		pageVo.setTotal(iPage.getTotal());
		pageVo.setPageSize(iPage.getSize());
		pageVo.setPages(iPage.getPages());
		pageVo.setRequestMap(params);
		pageVo.setList(iPage.getRecords());
		pageVo.compute();

		return pageVo;
	}

	@Override
	public boolean saveByVo(UserVo userVo) {
		ExecutorService	pool		= Executors.newFixedThreadPool(THREAD_COUNT);

		CifCustomer		customer	= customerService.getByPhoneNumber(userVo.getPhone());
		if (customer == null) {
			customer = new CifCustomer();
			customer.setCustomerId("kms" + KtfKit.me().nextId());
			customer.setName(userVo.getName());
			customer.setRegPhoneNumber(userVo.getPhone());
			customer.setEmail(userVo.getEmail());
			customer.setGender(KtfGender.valueOf(userVo.getSex()).text);
			customer.setStatus(KtfStatus.ENABLED.text);
			customerService.save(customer);
		}

		SysUser user = BeanConverter.convert(SysUser.class, userVo);
		user.setCifId(customer.getId());

		user.setApplicationId(KtfConstant.BZ_APPLICATION_ID);
		user.setLoginMode(KtfIdentifyType.USERNAME.text);
		user.setEnterpriseId(1L);
		user.setDepartmentId(1L);
		user.setJobId(1L);
		user.setStatus(KtfStatus.INIT.code);
		this.save(user);

		// 检查角色是否越权
		checkRole(userVo);

		pool.submit(new Runnable() {
			@Override
			public void run() {
				// 保存用户鉴权信息
				CifCustomerAuths cifAuth = BeanConverter.convert(CifCustomerAuths.class, userVo);
				cifAuth.setIdentityType(KtfIdentifyType.USERNAME.text);
				cifAuth.setIdentifier(userVo.getLoginName());
				cifAuth.setCredential(userVo.getPassword());
				cifAuth.setCredentialSalt(userVo.getSalt());
				cifAuth.setUserType(UserType.USER.code);
				cifAuth.setStatus(KtfStatus.ENABLED.text);
				customerAuthsService.save(cifAuth);
			}
		});

		pool.submit(new Runnable() {
			@Override
			public void run() {
				// 根据用户类型查询对应角色
				Long roleId = getRoleId(user.getUserType());

				// 保存用户与角色关系
				sysUserRoleService.saveOrUpdateUserRole(user.getId(), CollectionKit.newArrayList(roleId));
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				// 保存用户与企业关系关系
				sysUserEnterpriseService.saveOrUpdateUserEnterprise(user.getId(), userVo.getEnterpriseIdList());
			}
		});
		pool.shutdown();

		return true;
	}

	@Override
	public boolean updateByVo(UserVo userVo) {
		ExecutorService	pool	= Executors.newFixedThreadPool(THREAD_COUNT);
		SysUser			user	= BeanConverter.convert(SysUser.class, userVo);
		this.updateById(user);

		// 检查角色是否越权
		checkRole(userVo);

		pool.submit(new Runnable() {
			@Override
			public void run() {

				CifCustomer customer = new CifCustomer();
				customer.setId(userVo.getCifId());
				customer.setName(userVo.getName());
				customer.setRegPhoneNumber(userVo.getPhone());
				customer.setEmail(userVo.getEmail());
				customer.setGender(KtfGender.valueOf(userVo.getSex()).text);
				customerService.saveOrUpdate(customer);

				// 保存用户鉴权信息
				CifCustomerAuths cifAuth = BeanConverter.convert(CifCustomerAuths.class, userVo);
				cifAuth.setIdentifier(userVo.getLoginName());
				cifAuth.setCredential(userVo.getPassword());
				cifAuth.setCredentialSalt(userVo.getSalt());
				customerAuthsService.updateById(cifAuth);
			}
		});

		pool.submit(new Runnable() {
			@Override
			public void run() {
				// 根据用户类型查询对应角色
				Long roleId = getRoleId(user.getUserType());
				// 保存用户与角色关系
				sysUserRoleService.saveOrUpdateUserRole(user.getId(), CollectionKit.newArrayList(roleId));
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				// 保存用户与企业关系关系
				sysUserEnterpriseService.saveOrUpdateUserEnterprise(user.getId(), userVo.getEnterpriseIdList());
			}
		});
		pool.shutdown();

		return true;
	}

	private Long getRoleId(Integer userType) {
		KmsUserType	type	= KmsUserType.valueOf(userType);
		String		role	= sysDicService.getByVarName(type.name(), "用户类型角色映射").getVarCode();
		return Long.parseLong(role);
	}

	/**
	 * 检查角色是否越权
	 */
	private void checkRole(UserVo userVo) {
		if (userVo.getRoleIdList() == null || userVo.getRoleIdList().size() == 0) {
			return;
		}
		// 如果不是超级管理员，则需要判断用户的角色是否自己创建
		if (userVo.getCreateUserId() == KtfConstant.SUPER_ADMIN) {
			return;
		}
		// 查询用户创建的角色列表
		List<Long> roleIdList = sysUserRoleService.selectRoleIdListByUserId(userVo.getCreateUserId());

		// 判断是否越权
		if (!roleIdList.containsAll(userVo.getRoleIdList())) {
			throw new KtfException("新增用户所选角色，不是本人创建");
		}
	}

	@Override
	public Boolean deleteBatch(Long[] userIds) {
		List<Long> ids = Arrays.asList(userIds);

		this.removeByIds(ids);
		// 删除用户认证记录
		customerAuthsService.removeByIds(ids);
		// 删除用户与角色关系
		sysUserRoleService.deleteBatchByUserIds(userIds);
		// 删除监管用户与企业关系
		sysUserEnterpriseService.deleteBatchByUserIds(userIds);
		return true;
	}

	@Override
	public List<Map<String, Object>> selectUserTree() {
		return sysUserExMapper.selectUserTree();
	}

}