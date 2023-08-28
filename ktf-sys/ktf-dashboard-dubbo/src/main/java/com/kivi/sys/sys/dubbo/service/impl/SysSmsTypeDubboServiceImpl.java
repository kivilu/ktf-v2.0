package com.kivi.sys.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsTypeDTO;
import com.kivi.sys.sys.entity.SysSmsType;
import com.kivi.sys.sys.mapper.SysSmsTypeMapper;
import com.kivi.sys.sys.service.ISysSmsTypeService;

/**
 * <p>
 * 消息类型与用户关系 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */

@DubboService(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysSmsTypeDubboServiceImpl extends ServiceImpl<SysSmsTypeMapper, SysSmsType>
		implements ISysSmsTypeService {

	@Autowired
	private ISysSmsTypeService iSysSmsTypeService;

	/**
	 * 根据ID查询消息类型与用户关系
	 */
	@KtfTrace("根据ID查询消息类型与用户关系")
	@Override
	public SysSmsTypeDTO getDTOById(Long id) {
		return iSysSmsTypeService.getDTOById(id);
	}

	/**
	 * 新增消息类型与用户关系
	 */
	@KtfTrace("新增消息类型与用户关系")
	@Override
	public Boolean save(SysSmsTypeDTO sysSmsTypeDTO) {
		return iSysSmsTypeService.save(sysSmsTypeDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改消息类型与用户关系")
	@Override
	public Boolean updateById(SysSmsTypeDTO sysSmsTypeDTO) {
		return iSysSmsTypeService.updateById(sysSmsTypeDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表消息类型与用户关系")
	@Override
	public List<SysSmsTypeDTO> list(SysSmsTypeDTO sysSmsTypeDTO) {
		return iSysSmsTypeService.list(sysSmsTypeDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表消息类型与用户关系")
	@Override
	public List<SysSmsTypeDTO> list(Map<String, Object> params, String... columns) {
		return iSysSmsTypeService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询消息类型与用户关系")
	@Override
	public List<SysSmsTypeDTO> listLike(SysSmsTypeDTO sysSmsTypeDTO) {
		return iSysSmsTypeService.listLike(sysSmsTypeDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysSmsTypeDTO> listLike(Map<String, Object> params, String... columns) {
		return iSysSmsTypeService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询消息类型与用户关系")
	public PageInfoVO<SysSmsTypeDTO> page(Map<String, Object> params) {
		return iSysSmsTypeService.page(params);

	}

}
