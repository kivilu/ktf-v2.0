package com.kivi.sys.sys.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息记录
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysSmsRecordExDTO extends SysSmsRecordDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 消息距当前时间
     */
    private String timeStr;

    private String userName;

    private String title;

    private String content;

    private String startTime;

    private String endTime;

}
