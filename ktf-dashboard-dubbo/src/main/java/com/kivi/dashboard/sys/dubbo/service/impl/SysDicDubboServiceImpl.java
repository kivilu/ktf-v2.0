package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysDicDTO;
import com.kivi.dashboard.sys.entity.SysDic;
import com.kivi.dashboard.sys.mapper.SysDicMapper;
import com.kivi.dashboard.sys.service.ISysDicService;
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

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysDicDubboServiceImpl extends ServiceImpl<SysDicMapper, SysDic> implements ISysDicService {
	// private ConcurrentHashMap<String, String> opDic =
	// MapUtil.newConcurrentHashMap();

	@Autowired
	private ISysDicService sysDicService;

	@Override
	public SysDic getByVarCode(String varCode, String pVarCode) {
		return sysDicService.getByVarCode(varCode, pVarCode);
	}

	@Override
	public SysDic getByVarName(String varName, String parentName) {
		return sysDicService.getByVarName(varName, parentName);
	}

	@Override
	public List<SysDicDTO> listByVarCode(String pVarCode) {
		return sysDicService.listByVarCode(pVarCode);
	}

	/**
	 * 根据ID查询数据字典
	 */
	@KtfTrace("根据ID查询数据字典")
	@Override
	public SysDicDTO getDTOById(Long id) {
		return sysDicService.getDTOById(id);
	}

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

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表数据字典")
	@Override
	public List<SysDicDTO> list(SysDicDTO sysDicDTO) {
		return sysDicService.list(sysDicDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表数据字典")
	@Override
	public List<SysDicDTO> list(Map<String, Object> params, String... columns) {
		return sysDicService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询数据字典")
	@Override
	public List<SysDicDTO> listLike(SysDicDTO sysDicDTO) {
		return sysDicService.list(sysDicDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysDicDTO> listLike(Map<String, Object> params, String... columns) {
		return sysDicService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询数据字典")
	public PageInfoVO<SysDicDTO> page(Map<String, Object> params) {
		return sysDicService.page(params);

	}

	@Override
	public Boolean deleteWithChild(Long id) {
		return sysDicService.deleteWithChild(id);
	}

	@Override
	public List<SysDicDTO> getChildren(Map<String, Object> params) {
		return sysDicService.getChildren(params);
	}

	@Override
	public Map<String, Object> getSettings(String varCode) {
		return sysDicService.getSettings(varCode);
	}

}
