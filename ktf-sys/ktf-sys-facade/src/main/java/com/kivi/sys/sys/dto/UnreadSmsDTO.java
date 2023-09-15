package com.kivi.sys.sys.dto;

import java.util.List;

import lombok.Data;

@Data
public class UnreadSmsDTO {
    /**
     * 未读消息数量
     */
    private Long count;

    private List<Sms> list;
}
