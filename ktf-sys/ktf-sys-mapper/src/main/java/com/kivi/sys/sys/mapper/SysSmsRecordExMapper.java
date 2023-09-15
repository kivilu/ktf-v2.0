package com.kivi.sys.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kivi.sys.sys.dto.SysSmsRecordExDTO;

/**
 * <p>
 * 消息记录 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
public interface SysSmsRecordExMapper {

    /**
     * 最近5条未读消息
     *
     * @param userId
     * @return
     */
    List<SysSmsRecordExDTO> findRecent5Messages(@Param("userId") Long userId);

}
