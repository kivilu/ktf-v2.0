package com.kivi.dashboard.enterprise.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.enterprise.dto.EnterpriseJobDTO;
import com.kivi.dashboard.enterprise.entity.EnterpriseJob;
import com.kivi.dashboard.enterprise.mapper.EnterpriseJobMapper;
import com.kivi.dashboard.enterprise.service.IEnterpriseJobService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业职务配置 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class EnterpriseJobDubboServiceImpl extends ServiceImpl<EnterpriseJobMapper, EnterpriseJob>
		implements IEnterpriseJobService {

	@Autowired
	private IEnterpriseJobService enterpriseJobService;

	/**
	 * 根据ID查询企业职务配置
	 */
	@KtfTrace("根据ID查询企业职务配置")
	@Override
	public EnterpriseJobDTO getDTOById(Long id) {
		return enterpriseJobService.getDTOById(id);
	}

	/**
	 * 新增企业职务配置
	 */
	@KtfTrace("新增企业职务配置")
	@Override
	public Boolean save(EnterpriseJobDTO enterpriseJobDTO) {

		return enterpriseJobService.save(enterpriseJobDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业职务配置")
	@Override
	public Boolean updateById(EnterpriseJobDTO enterpriseJobDTO) {
		return enterpriseJobService.updateById(enterpriseJobDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表企业职务配置")
	@Override
	public List<EnterpriseJobDTO> list(EnterpriseJobDTO enterpriseJobDTO) {
		return enterpriseJobService.list(enterpriseJobDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表企业职务配置")
	@Override
	public List<EnterpriseJobDTO> list(Map<String, Object> params, String... columns) {
		return enterpriseJobService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询企业职务配置")
	@Override
	public List<EnterpriseJobDTO> listLike(EnterpriseJobDTO applicationDTO) {
		return enterpriseJobService.listLike(applicationDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<EnterpriseJobDTO> listLike(Map<String, Object> params, String... columns) {
		return enterpriseJobService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询企业职务配置")
	public PageInfoVO<EnterpriseJobDTO> page(Map<String, Object> params) {
		return enterpriseJobService.page(params);

	}

	@Override
	public List<EnterpriseJobDTO> select(Map<String, Object> params) {
		return enterpriseJobService.select(params);
	}

	@Override
	public PageInfoVO<Map<String, Object>> selectByPage(Map<String, Object> params) {
		return enterpriseJobService.selectByPage(params);
	}

}
