package com.kivi.dashboard.enterprise.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.enterprise.dto.EnterpriseDTO;
import com.kivi.dashboard.enterprise.entity.Enterprise;
import com.kivi.dashboard.enterprise.mapper.EnterpriseMapper;
import com.kivi.dashboard.enterprise.service.IEnterpriseService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class EnterpriseDubboServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise>
		implements IEnterpriseService {

	@Autowired
	private IEnterpriseService enterpriseService;

	/**
	 * 根据ID查询企业信息
	 */
	@KtfTrace("根据ID查询企业信息")
	@Override
	public EnterpriseDTO getDTOById(Long id) {
		return enterpriseService.getDTOById(id);
	}

	/**
	 * 新增企业信息
	 */
	@KtfTrace("新增企业信息")
	@Override
	public Enterprise save(EnterpriseDTO enterpriseDTO) {
		return enterpriseService.save(enterpriseDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业信息")
	@Override
	public Boolean updateById(EnterpriseDTO enterpriseDTO) {
		return enterpriseService.updateById(enterpriseDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业信息")
	@Override
	public List<EnterpriseDTO> list(EnterpriseDTO enterpriseDTO) {
		Map<String, Object> params = BeanConverter.beanToMap(enterpriseDTO);
		return this.list(params, new String[0]);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表企业信息")
	@Override
	public List<EnterpriseDTO> list(Map<String, Object> params, String... columns) {
		return enterpriseService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询企业信息")
	@Override
	public List<EnterpriseDTO> listLike(EnterpriseDTO applicationDTO) {
		return enterpriseService.listLike(applicationDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<EnterpriseDTO> listLike(Map<String, Object> params, String... columns) {
		return enterpriseService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询企业信息")
	public PageInfoVO<EnterpriseDTO> page(Map<String, Object> params) {
		return enterpriseService.page(params);

	}

	@Override
	public List<Map<String, Object>> selectNames() {
		return enterpriseService.selectNames();
	}

	@Override
	public List<Map<String, Object>> select(Map<String, Object> params) {
		return enterpriseService.select(params);
	}

	@Override
	public PageInfoVO<Map<String, Object>> selectByPage(Map<String, Object> params) {

		return enterpriseService.selectByPage(params);
	}

}
