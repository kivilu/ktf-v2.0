package com.kivi.cif.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.auth.CifAuthentication;
import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.cif.enums.CifAuthType;
import com.kivi.cif.mapper.CifCustomerAuthsMapper;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.crypto.enums.AlgDigest;
import com.kivi.framework.crypto.util.DigestUtil;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.form.LoginForm;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.UserVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 客户验证 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Slf4j
@Primary
@CacheConfig(cacheNames = KtfCache.CifCustomerAuths)
@Service
@Transactional(rollbackFor = Exception.class)
public class CifCustomerAuthsServiceImpl extends ServiceImpl<CifCustomerAuthsMapper, CifCustomerAuths>
		implements CifCustomerAuthsService {

	@Autowired
	private CifAuthentication	cifAuthentication;

	@Autowired
	CifProperties				cifProperties;

	@KtfTrace("验证用户凭据")
	@Override
	public Integer auth(LoginForm form, UserVo userVo) {
		CifCustomerAuths entity = super.getById(userVo.getId());
		if (entity == null) {
			log.error("用户{}验证凭据不存在", userVo.getId());
			throw new KtfException(KtfError.E_UNAUTHORIZED, "用户身份校验未通过");
		}

		userVo.setPassword(entity.getCredential());
		userVo.setSalt(entity.getCredentialSalt());
		return cifAuthentication.auth(form, userVo);
	}

	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@KtfTrace("修改用户认证凭据")
	@Override
	public Boolean updateCredential(UserVo userVo, String newPassword) throws KtfException {
		log.info("修改用户{}的认证凭据", userVo.getId());

		LoginForm form = new LoginForm();
		form.setUserName(userVo.getLoginName());
		form.setPassword(userVo.getPassword());
		form.setType(CifAuthType.PASS.code);
		this.auth(form, userVo);

		if (StrKit.isBlank(newPassword)) {
			newPassword = DigestUtil.hashBase64(cifProperties.getAlgDigest(), cifProperties.getDefaultPassword());
		}

		String				salt	= StrKit.random(16);
		CifCustomerAuths	cifAuth	= new CifCustomerAuths();
		cifAuth.setId(userVo.getId());
		cifAuth.setCredential(cifAuthentication.credential(newPassword, salt));
		cifAuth.setCredentialSalt(salt);

		return super.updateById(cifAuth);
	}

	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@KtfTrace("重置用户认证凭据")
	@Override
	public Boolean resetCredential(UserVo userVo) throws KtfException {
		log.info("重置用户{}的认证凭据", userVo.getId());

		String				newPassword	= DigestUtil.hashBase64(AlgDigest.SM3, cifProperties.getDefaultPassword());
		String				salt		= StrKit.random(16);
		CifCustomerAuths	cifAuth		= new CifCustomerAuths();
		cifAuth.setId(userVo.getId());
		cifAuth.setCredential(cifAuthentication.credential(newPassword, salt));
		cifAuth.setCredentialSalt(salt);

		return super.updateById(cifAuth);
	}

	@Cacheable(key = "caches[0].name+'.'+#appid+#identityType+#identifier+#userType", unless = "#result == null")
	@Override
	public CifCustomerAuths getCifCustomerAuths(Long appid, String identityType, String identifier, String userType) {

		LambdaQueryWrapper<CifCustomerAuths> queryWrapper = Wrappers.<CifCustomerAuths>lambdaQuery();
		queryWrapper.eq(CifCustomerAuths::getIdentifier, identifier).eq(CifCustomerAuths::getIdentityType, identityType)
				.eq(CifCustomerAuths::getUserType, userType);

		return super.getOne(queryWrapper, false);
	}

	@Override
	@Cacheable(
			key = "caches[0].name+'.'+#cifAuthDTO.applicationId+#cifAuthDTO.identityType+#cifAuthDTO.identifier+#cifAuthDTO.userType",
			unless = "#result == null")
	public CifCustomerAuths getCifCustomerAuths(final CifCustomerAuthsDTO cifAuthDTO) {
		CifCustomerAuths entity = getCifCustomerAuths(cifAuthDTO.getAppId(), cifAuthDTO.getIdentityType(),
				cifAuthDTO.getIdentifier(), cifAuthDTO.getUserType());

		return entity;
	}

	/**
	 * 根据ID查询客户验证
	 */
	@Cacheable(key = "caches[0].name+'.DTO.'+#id", unless = "#result == null")
	@KtfTrace("根据ID查询客户验证")
	@Override
	public CifCustomerAuthsDTO getDto(Long id) {
		CifCustomerAuths entity = super.getById(id);
		if (entity == null)
			return null;
		CifCustomerAuthsDTO dto = BeanConverter.convert(CifCustomerAuthsDTO.class, entity);
		return dto;
	}

	@Cacheable(key = "caches[0].name+'.'+#id", unless = "#result == null")
	@Override
	public CifCustomerAuths getById(Serializable id) {
		return super.getById(id);
	}

	/**
	 * 新增客户验证
	 */
	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@KtfTrace("新增客户验证")
	@Override
	public Long save(CifCustomerAuthsDTO dto) {
		String				password	= StrKit.isBlank(dto.getCredential())
				? DigestUtil.hashBase64(AlgDigest.SM3, cifProperties.getDefaultPassword())
				: dto.getCredential();

		String				salt		= StrKit.isBlank(dto.getCredentialSalt()) ? StrKit.random(16)
				: dto.getCredentialSalt();

		CifCustomerAuths	cifAuth		= new CifCustomerAuths();
		cifAuth.setAppId(dto.getAppId());
		cifAuth.setCifId(dto.getCifId());
		cifAuth.setIdentityType(dto.getIdentityType());
		cifAuth.setIdentifier(dto.getIdentifier());
		cifAuth.setCredential(cifAuthentication.credential(password, salt));
		cifAuth.setCredentialSalt(salt);
		cifAuth.setUserType(dto.getUserType());
		cifAuth.setStatus(KtfStatus.ENABLED.text);

		boolean b = super.saveOrUpdate(cifAuth);
		if (!b) {
			throw new KtfException(KtfError.E_DB_ERROR, "保存客户验证凭据失败");
		}

		return cifAuth.getId();
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改客户验证")
	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@Override
	public boolean updateById(CifCustomerAuths entity) {
		// 确保认证凭据和sal不被修改
		entity.setCredential(null);
		entity.setCredentialSalt(null);
		return super.updateById(entity);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改客户验证")
	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@Override
	public Boolean updateById(CifCustomerAuthsDTO cifCustomerAuthsDTO) {
		CifCustomerAuths entity = BeanConverter.convert(CifCustomerAuths.class, cifCustomerAuthsDTO);
		// 确保认证凭据和sal不被修改
		entity.setCredential(null);
		entity.setCredentialSalt(null);
		return super.updateById(entity);
	}

	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@Override
	public Boolean updateByEntity(CifCustomerAuths condEntity, CifCustomerAuths updaeEntity) {
		QueryWrapper<CifCustomerAuths> query = Wrappers.<CifCustomerAuths>query(condEntity);

		// 确保认证凭据和sal不被修改
		updaeEntity.setCredential(null);
		updaeEntity.setCredentialSalt(null);
		return super.update(updaeEntity, query);
	}

	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@Override
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}

	@KtfCacheEvict(cacheNames = KtfCache.CifCustomerAuths)
	@Override
	public boolean removeByIds(Collection<?> idList) {
		return super.removeByIds(idList);
	}

}
