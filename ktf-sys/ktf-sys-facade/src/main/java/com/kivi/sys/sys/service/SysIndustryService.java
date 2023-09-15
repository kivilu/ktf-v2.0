package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysIndustryDTO;

/**
 * <p>
 * 行业代码 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysIndustryService {

    /**
     * 根据ID查询DTO
     */
    SysIndustryDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(SysIndustryDTO sysIndustryDTO);

    /**
     * 修改
     */
    Boolean updateById(SysIndustryDTO sysIndustryDTO);

    /**
     * 分页查询
     */
    PageInfoVO<SysIndustryDTO> page(Map<String, Object> params);

    /**
     * 查找子数据
     * 
     * @param pVarCode
     * @param recursion
     * @return
     */
    List<SysIndustryDTO> getChildren(Long pid, Boolean recursion);

    /**
     * 查询列表
     */
    List<SysIndustryDTO> list(SysIndustryDTO sysIndustryDTO);

    /**
     * 指定列查询列表
     */
    List<SysIndustryDTO> list(Map<String, Object> params, String... columns);

    /**
     * 模糊查询
     */
    List<SysIndustryDTO> listLike(SysIndustryDTO applicationDTO);

    /**
     * 指定列模糊查询
     */
    List<SysIndustryDTO> listLike(Map<String, Object> params, String... columns);

    Boolean removeById(Long id);

    Boolean removeByIds(List<Long> ids);

    Boolean removeWithChildren(Long id);

    Boolean removeWithChildren(List<Long> ids);
}
