package com.kivi.sys.org.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgCorpDTO;

/**
 * <p>
 * 企业信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface OrgCorpService {

    /**
     * 根据ID查询DTO
     */
    OrgCorpDTO getDto(Long id);

    /**
     * 新增
     */
    OrgCorpDTO save(OrgCorpDTO dto);

    /**
     * 修改
     */
    Boolean updateById(OrgCorpDTO dto);

    /**
     * 指定列查询列表
     */
    List<OrgCorpDTO> list(Map<String, Object> params);

    /**
     * 指定列查询列表
     */
    List<OrgCorpDTO> allList();

    /**
     * 单表分页查询
     */
    PageInfoVO<OrgCorpDTO> page(Map<String, Object> params);

    Boolean removeById(Long id);

    Boolean removeByIds(List<Long> ids);
}
