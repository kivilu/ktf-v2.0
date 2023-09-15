package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysLogDTO;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysLogService {

    /**
     * 根据ID查询DTO
     */
    SysLogDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(SysLogDTO sysLogDTO);

    /**
     * 修改
     */
    Boolean updateById(SysLogDTO sysLogDTO);

    /**
     * 分页查询
     */
    PageInfoVO<SysLogDTO> page(Map<String, Object> params);

    List<SysLogDTO> listByIds(List<Long> ids);

}
