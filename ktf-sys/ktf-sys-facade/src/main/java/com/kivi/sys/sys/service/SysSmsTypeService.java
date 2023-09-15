package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsTypeDTO;

/**
 * <p>
 * 消息类型与用户关系 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
public interface SysSmsTypeService {

    /**
     * 根据ID查询DTO
     */
    SysSmsTypeDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(SysSmsTypeDTO sysSmsTypeDTO);

    /**
     * 修改
     */
    Boolean updateById(SysSmsTypeDTO sysSmsTypeDTO);

    /**
     * 查询列表
     */
    List<SysSmsTypeDTO> list(SysSmsTypeDTO sysSmsTypeDTO);

    /**
     * 指定列查询列表
     */
    List<SysSmsTypeDTO> list(Map<String, Object> params, String... columns);

    /**
     * 模糊查询
     */
    List<SysSmsTypeDTO> listLike(SysSmsTypeDTO applicationDTO);

    /**
     * 指定列模糊查询
     */
    List<SysSmsTypeDTO> listLike(Map<String, Object> params, String... columns);

    /**
     * 分页查询
     */
    PageInfoVO<SysSmsTypeDTO> page(Map<String, Object> params);
}
