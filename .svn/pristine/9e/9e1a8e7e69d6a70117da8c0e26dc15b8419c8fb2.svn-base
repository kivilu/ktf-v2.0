package com.kivi.dashboard.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysSmsDTO对象", description = "消息")
public class SysSmsDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键")
	private Long				id;

	@ApiModelProperty(value = "消息类型ID")
	private Long				smsTypeId;

	@ApiModelProperty(value = "消息标题")
	private String				title;

	@ApiModelProperty(value = "消息内容")
	private String				content;

	@ApiModelProperty(value = "推送时间")
	private LocalDateTime		smsTime;

	@ApiModelProperty(value = "推送次数")
	private Integer				smsCount;

	@ApiModelProperty(value = "推送间隔时间（秒）")
	private Integer				intervalTime;

	@ApiModelProperty(value = "状态（0-正常，1-禁止）	")
	private Integer				status;

	@ApiModelProperty(value = "每次推送时间")
	private LocalDateTime		realTime;

	@ApiModelProperty(value = "推送真实次数")
	private Integer				realCount;

	@ApiModelProperty(value = "创建者")
	private Long				createUser;

	@ApiModelProperty(value = "修改者")
	private Long				updateUser;

	public static final String	ID					= "id";
	public static final String	SMS_TYPE_ID			= "smsTypeId";
	public static final String	TITLE				= "title";
	public static final String	CONTENT				= "content";
	public static final String	SMS_TIME			= "smsTime";
	public static final String	SMS_COUNT			= "smsCount";
	public static final String	INTERVAL_TIME		= "intervalTime";
	public static final String	STATUS				= "status";
	public static final String	REAL_TIME			= "realTime";
	public static final String	REAL_COUNT			= "realCount";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
