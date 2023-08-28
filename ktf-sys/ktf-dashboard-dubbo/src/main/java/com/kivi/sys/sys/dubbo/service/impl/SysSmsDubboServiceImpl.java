package com.kivi.sys.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsDTO;
import com.kivi.sys.sys.entity.SysSms;
import com.kivi.sys.sys.mapper.SysSmsMapper;
import com.kivi.sys.sys.service.ISysSmsService;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysSmsDubboServiceImpl extends ServiceImpl<SysSmsMapper, SysSms> implements ISysSmsService {

	@Autowired
	private ISysSmsService sysSmsService;

	/**
	 * 根据ID查询消息
	 */
	@KtfTrace("根据ID查询消息")
	@Override
	public SysSmsDTO getDTOById(Long id) {
		return sysSmsService.getDTOById(id);
	}

	/**
	 * 新增消息
	 */
	@KtfTrace("新增消息")
	@Override
	public Boolean save(SysSmsDTO sysSmsDTO) {
		return sysSmsService.save(sysSmsDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改消息")
	@Override
	public Boolean updateById(SysSmsDTO sysSmsDTO) {
		return sysSmsService.updateById(sysSmsDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表消息")
	@Override
	public List<SysSmsDTO> list(SysSmsDTO sysSmsDTO) {
		return sysSmsService.list(sysSmsDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表消息")
	@Override
	public List<SysSmsDTO> list(Map<String, Object> params, String... columns) {
		return sysSmsService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询消息")
	@Override
	public List<SysSmsDTO> listLike(SysSmsDTO sysSmsDTO) {
		return sysSmsService.listLike(sysSmsDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysSmsDTO> listLike(Map<String, Object> params, String... columns) {
		return sysSmsService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询消息")
	public PageInfoVO<SysSmsDTO> page(Map<String, Object> params) {

		return sysSmsService.page(params);

	}

}
