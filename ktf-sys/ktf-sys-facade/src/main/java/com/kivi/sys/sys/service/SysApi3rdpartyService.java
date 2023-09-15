package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysApi3rdpartyDTO;

/**
 * <p>
 * 小程序账号信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2020-02-17
 */
public interface SysApi3rdpartyService {

    /**
     * 根据appid查询
     * 
     * @param appid
     * @return
     */
    SysApi3rdpartyDTO getByWxAppId(String appid);

    /**
     * 根据ID查询DTO
     */
    SysApi3rdpartyDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(SysApi3rdpartyDTO dto);

    /**
     * 修改
     */
    Boolean updateById(SysApi3rdpartyDTO dto);

    /**
     * 分页查询
     */
    PageInfoVO<SysApi3rdpartyDTO> page(Map<String, Object> params);

    Boolean removeById(Long id);

    Boolean removeByIds(List<Long> ids);
}
