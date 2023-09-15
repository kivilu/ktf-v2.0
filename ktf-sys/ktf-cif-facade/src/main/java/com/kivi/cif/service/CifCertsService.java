package com.kivi.cif.service;

import java.util.List;
import java.util.Map;

import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.framework.vo.page.PageInfoVO;

public interface CifCertsService {
    CifCertsDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(CifCertsDTO cifCertsDTO);

    boolean saveBatch(List<CifCertsDTO> list);

    /**
     * 修改
     */
    Boolean updateById(CifCertsDTO cifCertsDTO);

    Boolean removeById(Long id);

    Boolean removeByIds(List<Long> ids);

    /**
     * 查询列表
     */
    List<CifCertsDTO> list(CifCertsDTO cifCertsDTO);

    /**
     * 指定列查询列表
     */
    List<CifCertsDTO> list(Map<String, Object> params, String... columns);

    /**
     * 模糊查询
     */
    List<CifCertsDTO> listLike(CifCertsDTO cifCertsDTO);

    /**
     * 指定列模糊查询
     */
    List<CifCertsDTO> listLike(Map<String, Object> params, String... columns);

    /**
     * 分页查询
     */
    PageInfoVO<CifCertsDTO> page(Map<String, Object> params);

    Boolean removeByIdentifier(String identifier);
}
