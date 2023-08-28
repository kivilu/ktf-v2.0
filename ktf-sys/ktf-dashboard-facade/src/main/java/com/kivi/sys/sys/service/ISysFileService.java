package com.kivi.sys.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysFileDTO;
import com.kivi.sys.sys.entity.SysFile;

/**
 * <p>
 * 附件 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysFileService extends IService<SysFile> {
 
    /**
     * 根据ID查询DTO
     */
 	SysFileDTO getDTOById(Long id);   
 	
 	/**
 	 * 新增
 	 */
 	Boolean save(SysFileDTO sysFileDTO);
 	
 	/**
 	 * 修改
 	 */
 	Boolean updateById(SysFileDTO sysFileDTO);
 	
 	/**
 	 * 查询列表
 	 */
 	List<SysFileDTO> list(SysFileDTO sysFileDTO);
 	
 	/**
	 * 指定列查询列表
	 */
	List<SysFileDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysFileDTO> listLike(SysFileDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysFileDTO> listLike(Map<String, Object> params, String... columns);
 	
 	/**
 	 * 分页查询
 	 */
 	PageInfoVO<SysFileDTO> page(Map<String, Object> params);
}
