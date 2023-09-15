package com.kivi.cif.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.AuthService;
import com.kivi.cif.service.ICifCustomerAuthsService;
import com.kivi.cif.util.CifCredential;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;

import lombok.extern.slf4j.Slf4j;

@DubboService(version = CifProperties.DUBBO_VERSION)
@Slf4j
public class AuthServiceImpl implements AuthService {

	@Autowired
	private ICifCustomerAuthsService cifCustomerAuthsService;

	@KtfTrace("客户认证")
	@Override
	public CifCustomerAuthsDTO auth(WarpReqDTO<CifCustomerAuthsDTO> dto) throws KtfException {
		final Long					hawkSeqId	= dto.getTranUniqueId();
		final CifCustomerAuthsDTO	cifAuthDTO	= dto.getReqObject();
		CifCustomerAuthsDTO			result		= null;
		try {
			CifCustomerAuths entity = cifCustomerAuthsService.getCifCustomerAuths(cifAuthDTO);
			if (entity == null) {
				log.error("【{}】客户不存在，appid:{}，identifierType:{}，identifier:{}，userType：", hawkSeqId,
						cifAuthDTO.getBizCode(), cifAuthDTO.getIdentityType(), cifAuthDTO.getIdentifier(),
						cifAuthDTO.getUserType());
				throw new KtfException(KtfError.E_NOT_REGIST, "客户尚未注册");
			}

			if (StrKit.isNotEmpty(cifAuthDTO.getIdentifier())) {
				// 校验密码
				String calCredential = CifCredential.builder().add(entity.getAppId()).add(cifAuthDTO.getIdentifier())
						.add(cifAuthDTO.getCredential()).add(entity.getCredentialSalt()).build();
				if (!entity.getCredential().equalsIgnoreCase(calCredential)) {
					log.warn("【{}】客户校验失败，appid:{}，identifierType:{}，identifier:{}，userType：", hawkSeqId,
							cifAuthDTO.getBizCode(), cifAuthDTO.getIdentityType(), cifAuthDTO.getIdentifier(),
							cifAuthDTO.getUserType());
					throw new KtfException(KtfError.E_UNAUTHORIZED, "客户校验失败");
				}
			}

			result = BeanConverter.convert(CifCustomerAuthsDTO.class, entity);
			result.setUserType(entity.getUserType());
		} catch (KtfException e) {
			throw e;
		} catch (Exception e) {
			log.error("客户认证异常", e);
			throw new KtfException(KtfError.E_UNDEFINED, e.getMessage());
		}

		return result;
	}

	@Override
	public CifCustomerAuthsDTO resetAuthPass(WarpReqDTO<CifCustomerAuthsDTO> condCifAuthDTO) throws KtfException {
		final Long					hawkSeqId	= condCifAuthDTO.getTranUniqueId();
		final CifCustomerAuthsDTO	cifAuthDTO	= condCifAuthDTO.getReqObject();
		try {

			CifCustomerAuths cifCustomerAuths = cifAuthDTO.getId() != null
					? cifCustomerAuthsService.getById(cifAuthDTO.getId())
					: cifCustomerAuthsService.getCifCustomerAuths(cifAuthDTO);
			if (cifCustomerAuths == null) {
				log.error("【{}】客户不存在，appid:{}，identifierType:{}，identifier:{}，userType：", hawkSeqId,
						cifAuthDTO.getBizCode(), cifAuthDTO.getIdentityType(), cifAuthDTO.getIdentifier(),
						cifAuthDTO.getUserType());
				throw new KtfException(KtfError.E_NOT_FOUND, "客户不存在");
			}

			CifCustomerAuths entity = new CifCustomerAuths();
			entity.setId(cifCustomerAuths.getId());
			String credential = CifCredential.builder().add(cifCustomerAuths.getAppId())
					.add(cifCustomerAuths.getIdentifier()).add(cifAuthDTO.getCredential())
					.add(cifCustomerAuths.getCredentialSalt()).build();
			entity.setCredential(credential);
			entity.setAppId(cifCustomerAuths.getAppId());
			entity.setIdentityType(cifCustomerAuths.getIdentityType());
			entity.setIdentifier(cifCustomerAuths.getIdentifier());
			entity.setUserType(cifCustomerAuths.getUserType());
			cifCustomerAuthsService.updateById(entity);
		} catch (KtfException e) {
			throw e;
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】更新户认证凭据异常", e);
			throw new KtfException(KtfError.E_UNDEFINED, e.getMessage());
		}

		return cifAuthDTO;
	}

	// @POST
	// @Path( "updateAuth" )
	// @ApiOperation( value = "更新客户认证凭据", notes = "更新客户认证凭据" )
	@KtfTrace("更新户认证凭据")
	@Override
	public CifCustomerAuthsDTO updateAuthPass(WarpReqDTO<CifCustomerAuthsDTO> condCifAuthDTO, String newPassword)
			throws KtfException {
		final Long					hawkSeqId	= condCifAuthDTO.getTranUniqueId();
		final CifCustomerAuthsDTO	cifAuthDTO	= condCifAuthDTO.getReqObject();
		try {

			CifCustomerAuths cifCustomerAuths = cifAuthDTO.getId() != null
					? cifCustomerAuthsService.getById(cifAuthDTO.getId())
					: cifCustomerAuthsService.getCifCustomerAuths(cifAuthDTO);
			if (cifCustomerAuths == null) {
				log.error("【{}】客户不存在，appid:{}，identifierType:{}，identifier:{}，userType：", hawkSeqId,
						cifAuthDTO.getBizCode(), cifAuthDTO.getIdentityType(), cifAuthDTO.getIdentifier(),
						cifAuthDTO.getUserType());
				throw new KtfException(KtfError.E_NOT_FOUND, "客户不存在");
			}

			CifCustomerAuths condEntity = new CifCustomerAuths();
			condEntity.setId(cifCustomerAuths.getId());
			String oriCredential = CifCredential.builder().add(cifCustomerAuths.getAppId())
					.add(cifCustomerAuths.getIdentifier()).add(cifAuthDTO.getCredential())
					.add(cifCustomerAuths.getCredentialSalt()).build();
			condEntity.setCredential(oriCredential);
			condEntity.setAppId(cifCustomerAuths.getAppId());
			condEntity.setIdentityType(cifCustomerAuths.getIdentityType());
			condEntity.setIdentifier(cifCustomerAuths.getIdentifier());
			condEntity.setUserType(cifCustomerAuths.getUserType());

			CifCustomerAuths	entity		= new CifCustomerAuths();
			String				credential	= CifCredential.builder().add(cifCustomerAuths.getAppId())
					.add(cifCustomerAuths.getIdentifier()).add(newPassword).add(cifCustomerAuths.getCredentialSalt())
					.build();
			entity.setCredential(credential);
			Boolean ret = cifCustomerAuthsService.updateByEntity(condEntity, entity);
			if (!ret) {
				log.error("【{}】更新用户认证凭据记录影响行数为0，更新是失败");
				throw new KtfException(KtfError.E_NOT_FOUND, "更新用户密码失败");
			}
		} catch (KtfException e) {
			throw e;
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】更新户认证凭据异常", e);
			throw new KtfException(KtfError.E_UNDEFINED, e.getMessage());
		}

		return cifAuthDTO;
	}

	// @GET
	// @Path( "getAuth/{bizCode: \\w+}/{type: \\w+}/{identifier:
	// \\w+}/{credential: \\w+}" )
	// @ApiOperation( value = "查询客户认证记录", notes = "查询客户认证记录" )
	@KtfTrace("查询客户认证记录")
	@Override
	public CifCustomerAuthsDTO getAuth(WarpReqDTO<CifCustomerAuthsDTO> dto) throws KtfException {
		final Long					hawkSeqId	= dto.getTranUniqueId();
		final CifCustomerAuthsDTO	cifAuthDTO	= dto.getReqObject();

		CifCustomerAuthsDTO			result		= null;

		try {
			CifCustomerAuths entity = cifCustomerAuthsService.getCifCustomerAuths(cifAuthDTO);
			result = BeanConverter.convert(CifCustomerAuthsDTO.class, entity);
			result.setUserType(entity.getUserType());
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】查询客户认证记录异常", e);
			throw new KtfException(KtfError.E_UNDEFINED, e.getMessage());
		}

		return result;
	}

	@KtfTrace("客户认证记录回滚")
	@Override
	public void rollbackAuth(WarpReqDTO<CifCustomerAuthsDTO> dto) {
		final Long					hawkSeqId	= dto.getTranUniqueId();
		final CifCustomerAuthsDTO	cifAuthDTO	= dto.getReqObject();

		try {
			log.info("【{}】客户认证记录回滚，记录ID:{}", hawkSeqId, cifAuthDTO.getId());

			if (cifAuthDTO.getId() != null)
				cifCustomerAuthsService.removeById(cifAuthDTO.getId());
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】客户认证记录回滚异常", e);
			throw new KtfException(KtfError.E_UNDEFINED, e.getMessage());
		}

	}

}
