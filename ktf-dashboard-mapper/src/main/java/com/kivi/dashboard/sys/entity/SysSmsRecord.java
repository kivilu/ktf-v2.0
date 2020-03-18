package com.kivi.dashboard.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
@TableName("ktf_sys_sms_record")
public class SysSmsRecord extends Model<SysSmsRecord> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long				id;
	/**
	 * 账号
	 */
	@TableField("user_id")
	private Long				userId;
	/**
	 * 消息ID
	 */
	@TableField("sms_id")
	private Long				smsId;
	/**
	 * 要求推送时间
	 */
	@TableField("sms_time")
	private LocalDateTime		smsTime;
	/**
	 * 实际推送时间
	 */
	@TableField("push_time")
	private LocalDateTime		pushTime;
	/**
	 * 状态（0-待推送，1-推送成功，2-推送失败，3-已读）
	 */
	@TableField("status")
	private Integer				status;
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_USER_ID			= "user_id";
	public static final String	USER_ID				= "userId";
	public static final String	DB_SMS_ID			= "sms_id";
	public static final String	SMS_ID				= "smsId";
	public static final String	DB_SMS_TIME			= "sms_time";
	public static final String	SMS_TIME			= "smsTime";
	public static final String	DB_PUSH_TIME		= "push_time";
	public static final String	PUSH_TIME			= "pushTime";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
