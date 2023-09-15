package com.kivi.sys.org.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgTitleDTO;

/**
 * <p>
 * 企业职务配置 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface OrgTitleService {

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

    Boolean removeById(Long id);

    Boolean removeByIds(List<Long> ids);

}
