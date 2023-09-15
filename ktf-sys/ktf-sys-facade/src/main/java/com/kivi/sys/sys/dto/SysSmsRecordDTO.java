package com.kivi.sys.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "SysSmsRecordDTO对象", description = "消息记录")
public class SysSmsRecordDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键")
	private Long				id;

	@ApiModelProperty(value = "账号")
	private Long				userId;

	@ApiModelProperty(value = "消息ID")
	private Long				smsId;

	@ApiModelProperty(value = "要求推送时间")
	private LocalDateTime		smsTime;

	@ApiModelProperty(value = "实际推送时间")
	private LocalDateTime		pushTime;

	@ApiModelProperty(value = "状态（0-待推送，1-推送成功，2-推送失败，3-已读）")
	private Integer				status;

	public static final String	ID					= "id";
	public static final String	USER_ID				= "userId";
	public static final String	SMS_ID				= "smsId";
	public static final String	SMS_TIME			= "smsTime";
	public static final String	PUSH_TIME			= "pushTime";
	public static final String	STATUS				= "status";

}
