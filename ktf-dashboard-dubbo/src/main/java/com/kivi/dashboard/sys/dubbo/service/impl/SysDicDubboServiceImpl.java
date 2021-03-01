package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysDicDTO;
import com.kivi.dashboard.sys.entity.SysDic;
import com.kivi.dashboard.sys.mapper.SysDicMapper;
import com.kivi.dashboard.sys.service.SysDicService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysDicDubboServiceImpl extends ServiceImpl<SysDicMapper, SysDic> implements SysDicService {

	@Autowired
	private SysDicService sysDicService;

	/**
	 * 新增数据字典
	 */
	@KtfTrace("新增数据字典")
	@Override
	public Boolean save(SysDicDTO sysDicDTO) {
		return sysDicService.save(sysDicDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改数据字典")
	@Override
	public Boolean updateById(SysDicDTO sysDicDTO) {
		return sysDicService.updateById(sysDicDTO);
	}

	@Override
	public SysDicDTO getDto(Long id) {
		return sysDicService.getDto(id);
	}

	@Override
	public SysDicDTO getDto(String varCode, String pVarCode) {
		return sysDicService.getDto(varCode, pVarCode);
	}

	@Override
	public List<Object> getValues(String varCode, String... pVarCodes) {
		return sysDicService.getValues(varCode, pVarCodes);
	}

	@Override
	public Map<String, Object> getValuesMap(String varCode, String... pVarCodes) {
		return sysDicService.getValuesMap(varCode, pVarCodes);
	}

	@Override
	public Map<String, Object> getSettings(String varCode) {
		return sysDicService.getSettings(varCode);
	}

	@Override
	public List<SysDicDTO> getChildren(Long id, Boolean recursion) {
		return sysDicService.getChildren(id, recursion);
	}

	@Override
	public List<SysDicDTO> list(Long pid) {
		return sysDicService.list(pid);
	}

	@Override
	public Boolean removeWithChildren(Long id) {
		return sysDicService.removeWithChildren(id);
	}

	@Override
	public List<SysDicDTO> getChildren(String pVarCode, Boolean recursion) {
		return sysDicService.getChildren(pVarCode, recursion);
	}

	@Override
	public PageInfoVO<SysDicDTO> page(Map<String, Object> params) {
		return sysDicService.page(params);
	}

	@Override
	public List<SysDicDTO> treeQuery(Long pid) {
		return sysDicService.treeQuery(pid);
	}

}
