package com.kivi.sys.enterprise.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgCorpDTO;
import com.kivi.sys.org.entity.OrgCorp;
import com.kivi.sys.org.mapper.OrgCorpMapper;
import com.kivi.sys.org.service.OrgCorpService;

/**
 * <p>
 * 企业信息 服务实现类
 * </p>
 *
 * @author Auto-generator s
 */
@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class OrgCorpDubboServiceImpl extends ServiceImpl<OrgCorpMapper, OrgCorp> implements OrgCorpService {

	@Autowired
	private OrgCorpService orgCorpService;

	/**
	 * 根据ID查询企业信息
	 */
	@KtfTrace("根据ID查询企业信息")
	@Override
	public OrgCorpDTO getDto(Long id) {
		return orgCorpService.getDto(id);
	}

	/**
	 * 新增企业信息
	 */
	@KtfTrace("新增企业信息")
	@Override
	public OrgCorp save(OrgCorpDTO dto) {
		return orgCorpService.save(dto);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改企业信息")
	@Override
	public Boolean updateById(OrgCorpDTO dto) {
		return orgCorpService.updateById(dto);
	}

	@Override
	public List<OrgCorpDTO> list(Map<String, Object> params) {
		return orgCorpService.list(params);
	}

	@Override
	public List<OrgCorpDTO> allList() {
		return orgCorpService.allList();
	}

	@Override
	public PageInfoVO<OrgCorpDTO> page(Map<String, Object> params) {
		return orgCorpService.page(params);
	}

}
