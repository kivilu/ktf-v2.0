package com.kivi.cif.dubbo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.kivi.cif.dto.CifCustomerAuthsDTO;
import com.kivi.cif.dto.CifCustomerDTO;
import com.kivi.cif.dubbo.service.CustomerService;
import com.kivi.cif.entity.CifCustomer;
import com.kivi.cif.entity.CifCustomerAuths;
import com.kivi.cif.properties.CifProperties;
import com.kivi.cif.service.CifCustomerAuthsService;
import com.kivi.cif.service.CifCustomerService;
import com.kivi.cif.util.CifCredential;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.KtfYesNo;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;
import com.vip.vjtools.vjkit.collection.ListUtil;

import lombok.extern.slf4j.Slf4j;

@DubboService(version = CifProperties.DUBBO_VERSION)
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CifCustomerService		cifCustomerService;

	@Autowired
	private CifCustomerAuthsService	cifCustomerAuthsService;

	@KtfTrace("获取客户信息")
	@Override
	public CifCustomerDTO getCustomer(Long cifId) {
		return cifCustomerService.getDTOById(cifId);
	}

	@Transactional
	@KtfTrace("注册客户信息")
	@Override
	public CifCustomerAuthsDTO registCustomer(WarpReqDTO<CifCustomerDTO> customerDTO, CifCustomerAuthsDTO cifAuthDTO)
			throws KtfException {
		final Long				hawkSeqId	= customerDTO.getTranUniqueId();
		final CifCustomerDTO	dto			= customerDTO.getReqObject();

		CifCustomer				customer	= null;
		try {
			// 根据注册手机号查询客户信息
			customer = cifCustomerService.getByPhoneNumber(cifAuthDTO.getIdentifier());
			if (customer == null) {
				log.info("【{}】客户手机号{}首次使用平台，创建客户信息。", hawkSeqId, cifAuthDTO.getIdentifier());
				// 创建客户信息
				customer = BeanConverter.convert(CifCustomer.class, dto);
				customer.setId(cifAuthDTO.getId());
				customer.setCustomerId(StrKit.join("SL", cifAuthDTO.getId()));
				customer.setRegPhoneNumber(cifAuthDTO.getIdentifier());// 注册手机号
				customer.setCertNo(null);
				customer.setCertType(null);
				customer.setStatus(KtfStatus.ENABLED.text);
				cifCustomerService.save(customer);
			}

			// 保存客户认证信息
			cifAuthDTO = createAuth(hawkSeqId, customer.getId(), cifAuthDTO, customerDTO.getClientIp());
		} catch (KtfException e) {
			log.error("【" + hawkSeqId + "】客户注册异常", e);
			throw e;
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】客户注册异常", e);
			throw new KtfException(KtfError.E_REGIST_FAILURE, "客户注册失败", e);
		}

		return cifAuthDTO;
	}

	@KtfTrace("查询客户信息集合")
	@Override
	public List<CifCustomerDTO> getCustomer(List<Long> cifIds) throws KtfException {

		List<CifCustomer> list = ListUtil.newArrayList(cifCustomerService.listByIds(cifIds));

		return BeanConverter.convert(CifCustomerDTO.class, list);
	}

	private CifCustomerAuthsDTO createAuth(
			final Long hawkSeqId,
			final Long cifId,
			final CifCustomerAuthsDTO cifAuthDTO,
			final String clientIp) throws KtfException {
		try {
			if (cifCustomerAuthsService.getCifCustomerAuths(cifAuthDTO) != null) {
				log.error("【{}】客户已经存在，appid:{}，identifierType:{}，identifier:{}，userType：", hawkSeqId,
						cifAuthDTO.getBizCode(), cifAuthDTO.getIdentityType(), cifAuthDTO.getIdentifier(),
						cifAuthDTO.getUserType());
				throw new KtfException(KtfError.E_REGISTED, "客户已经注册");
			}

			CifCustomerAuths entity = BeanConverter.convert(CifCustomerAuths.class, cifAuthDTO);
			entity.setCifId(cifId);
			entity.setUserType(cifAuthDTO.getUserType());
			entity.setVerified(KtfYesNo.NO.text);
			entity.setRegIp(clientIp);
			entity.setRegTime(LocalDateTime.now());
			entity.setLastIp(entity.getRegIp());
			entity.setLastTime(entity.getRegTime());
			entity.setStatus(KtfStatus.ENABLED.text);

			String credential = CifCredential.builder().add(entity.getAppId()).add(entity.getIdentifier())
					.add(entity.getCredential()).add(entity.getCredentialSalt()).build();
			entity.setCredential(credential);
			cifCustomerAuthsService.save(entity);

			cifAuthDTO.setId(entity.getId());
			cifAuthDTO.setCifId(cifId);
		} catch (Exception e) {
			log.error("【" + hawkSeqId + "】保存客户认证凭据异常", e);
			throw new KtfException(KtfError.E_REGIST_FAILURE, "客户注册失败", e);
		}

		return cifAuthDTO;
	}

}
