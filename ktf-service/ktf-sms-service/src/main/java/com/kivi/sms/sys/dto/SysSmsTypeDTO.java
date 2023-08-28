package com.kivi.sms.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息类型与用户关系
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

@ApiModel(value = "SysSmsTypeDTO对象", description = "消息类型与用户关系")
public class SysSmsTypeDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@ApiModelProperty(value = "主键")
	private Long				id;

	@ApiModelProperty(value = "类型名称")
	private String				typeName;

	@ApiModelProperty(value = "账号列表")
	private String				targetList;

	@ApiModelProperty(value = "是否发送短信（0-是，0-否）")
	private Integer				isSendSms;

	@ApiModelProperty(value = "是否发送邮件（0-是，1-否）")
	private Integer				isSendEmail;

	@ApiModelProperty(value = "创建者")
	private Long				createUser;

	@ApiModelProperty(value = "修改者")
	private Long				updateUser;

	public static final String	ID					= "id";
	public static final String	TYPE_NAME			= "typeName";
	public static final String	TARGET_LIST			= "targetList";
	public static final String	IS_SEND_SMS			= "isSendSms";
	public static final String	IS_SEND_EMAIL		= "isSendEmail";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
