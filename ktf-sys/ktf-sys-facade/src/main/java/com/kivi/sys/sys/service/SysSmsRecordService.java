package com.kivi.sys.sys.service;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsRecordDTO;
import com.kivi.sys.sys.dto.SysSmsRecordExDTO;

/**
 * <p>
 * 消息记录 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
public interface SysSmsRecordService {

    /**
     * 根据ID查询DTO
     */
    SysSmsRecordDTO getDTOById(Long id);

    /**
     * 新增
     */
    Boolean save(SysSmsRecordDTO sysSmsRecordDTO);

    /**
     * 修改
     */
    Boolean updateById(SysSmsRecordDTO sysSmsRecordDTO);

    /**
     * 查询列表
     */
    List<SysSmsRecordDTO> list(SysSmsRecordDTO sysSmsRecordDTO);

    /**
     * 指定列查询列表
     */
    List<SysSmsRecordDTO> list(Map<String, Object> params, String... columns);

    /**
     * 模糊查询
     */
    List<SysSmsRecordDTO> listLike(SysSmsRecordDTO applicationDTO);

    /**
     * 指定列模糊查询
     */
    List<SysSmsRecordDTO> listLike(Map<String, Object> params, String... columns);

    /**
     * 分页查询
     */
    PageInfoVO<SysSmsRecordDTO> page(Map<String, Object> params);

    /**
     * 未读消息数量
     *
     * @param userId
     * @return
     */
    Long findUnreadMessagesCount(Long userId);

    /**
     * 最近5条未读消息
     *
     * @param userId
     * @return
     */
    List<SysSmsRecordExDTO> findRecent5Messages(Long userId);

    /**
     * 更改状态已读
     *
     * @param smsRecordId
     */
    void updateMessageStatus(Long[] ids);

}
