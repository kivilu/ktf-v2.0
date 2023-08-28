package com.kivi.sys.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysFileDTO;
import com.kivi.sys.sys.entity.SysFile;
import com.kivi.sys.sys.mapper.SysFileMapper;
import com.kivi.sys.sys.service.ISysFileService;

/**
 * <p>
 * 附件 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysFileDubboServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

	@Autowired
	ISysFileService sysFileService;

	/**
	 * 根据ID查询附件
	 */
	@KtfTrace("根据ID查询附件")
	@Override
	public SysFileDTO getDTOById(Long id) {
		return sysFileService.getDTOById(id);
	}

	/**
	 * 新增附件
	 */
	@KtfTrace("新增附件")
	@Override
	public Boolean save(SysFileDTO sysFileDTO) {
		return sysFileService.save(sysFileDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改附件")
	@Override
	public Boolean updateById(SysFileDTO sysFileDTO) {
		return sysFileService.updateById(sysFileDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表附件")
	@Override
	public List<SysFileDTO> list(SysFileDTO sysFileDTO) {
		return sysFileService.list(sysFileDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表附件")
	@Override
	public List<SysFileDTO> list(Map<String, Object> params, String... columns) {
		return sysFileService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询附件")
	@Override
	public List<SysFileDTO> listLike(SysFileDTO sysFileDTO) {
		return sysFileService.listLike(sysFileDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysFileDTO> listLike(Map<String, Object> params, String... columns) {
		return sysFileService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询附件")
	public PageInfoVO<SysFileDTO> page(Map<String, Object> params) {
		return sysFileService.page(params);

	}

}
