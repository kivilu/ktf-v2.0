package com.kivi.dashboard.org.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.org.dto.OrgTitleDTO;
import com.kivi.dashboard.org.entity.OrgTitle;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 企业职务配置 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface OrgTitleService extends IService<OrgTitle> {

	/**
	 * 根据ID查询DTO
	 */
	OrgTitleDTO getDto(Long id);

	/**
	 * 新增
	 */
	Boolean save(OrgTitleDTO dto);

	/**
	 * 修改
	 */
	Boolean updateById(OrgTitleDTO dto);

	/**
	 * 指定列查询列表
	 */
	List<OrgTitleDTO> list(Map<String, Object> params);

	/**
	 * 分页查询
	 */
	PageInfoVO<OrgTitleDTO> page(Map<String, Object> params);

}
