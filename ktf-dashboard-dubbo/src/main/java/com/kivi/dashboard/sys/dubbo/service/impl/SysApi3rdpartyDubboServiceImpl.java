package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysApi3rdpartyDTO;
import com.kivi.dashboard.sys.entity.SysApi3rdparty;
import com.kivi.dashboard.sys.mapper.SysApi3rdpartyMapper;
import com.kivi.dashboard.sys.service.SysApi3rdpartyService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 小程序账号信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2020-02-17
 */
@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysApi3rdpartyDubboServiceImpl extends ServiceImpl<SysApi3rdpartyMapper, SysApi3rdparty>
		implements SysApi3rdpartyService {

	@Autowired
	private SysApi3rdpartyService sysMpService;

	/**
	 * 根据ID查询小程序账号信息
	 */
	@KtfTrace("根据ID查询小程序账号信息")
	@Override
	public SysApi3rdpartyDTO getDTOById(Long id) {
		return sysMpService.getDTOById(id);
	}

	/**
	 * 新增小程序账号信息
	 */
	@KtfTrace("新增小程序账号信息")
	@Override
	public Boolean save(SysApi3rdpartyDTO sysMpDTO) {
		return sysMpService.save(sysMpDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改小程序账号信息")
	@Override
	public Boolean updateById(SysApi3rdpartyDTO sysMpDTO) {
		return sysMpService.updateById(sysMpDTO);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询小程序账号信息")
	public PageInfoVO<SysApi3rdpartyDTO> page(Map<String, Object> params) {
		return sysMpService.page(params);

	}

	@Override
	public SysApi3rdparty getByWxAppId(String appid) {
		return sysMpService.getByWxAppId(appid);
	}

}
