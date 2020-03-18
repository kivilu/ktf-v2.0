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
 * 消息
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_sms")
public class SysSms extends Model<SysSms> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long				id;
	/**
	 * 消息类型ID
	 */
	@TableField("sms_type_id")
	private Long				smsTypeId;
	/**
	 * 消息标题
	 */
	@TableField("title")
	private String				title;
	/**
	 * 消息内容
	 */
	@TableField("content")
	private String				content;
	/**
	 * 推送时间
	 */
	@TableField("sms_time")
	private LocalDateTime		smsTime;
	/**
	 * 推送次数
	 */
	@TableField("sms_count")
	private Integer				smsCount;
	/**
	 * 推送间隔时间（秒）
	 */
	@TableField("interval_time")
	private Integer				intervalTime;
	/**
	 * 状态（0-正常，1-禁止）
	 */
	@TableField("status")
	private Integer				status;
	/**
	 * 每次推送时间
	 */
	@TableField("real_time")
	private LocalDateTime		realTime;
	/**
	 * 推送真实次数
	 */
	@TableField("real_count")
	private Integer				realCount;
	/**
	 * 创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private LocalDateTime		gmtCreate;
	/**
	 * 修改时间
	 */
	@TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime		gmtUpdate;
	/**
	 * 创建者
	 */
	@TableField(value = "create_user", fill = FieldFill.INSERT)
	private Long				createUser;
	/**
	 * 修改者
	 */
	@TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
	private Long				updateUser;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_SMS_TYPE_ID		= "sms_type_id";
	public static final String	SMS_TYPE_ID			= "smsTypeId";
	public static final String	DB_TITLE			= "title";
	public static final String	TITLE				= "title";
	public static final String	DB_CONTENT			= "content";
	public static final String	CONTENT				= "content";
	public static final String	DB_SMS_TIME			= "sms_time";
	public static final String	SMS_TIME			= "smsTime";
	public static final String	DB_SMS_COUNT		= "sms_count";
	public static final String	SMS_COUNT			= "smsCount";
	public static final String	DB_INTERVAL_TIME	= "interval_time";
	public static final String	INTERVAL_TIME		= "intervalTime";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";
	public static final String	DB_REAL_TIME		= "real_time";
	public static final String	REAL_TIME			= "realTime";
	public static final String	DB_REAL_COUNT		= "real_count";
	public static final String	REAL_COUNT			= "realCount";
	public static final String	DB_GMT_CREATE		= "gmt_create";
	public static final String	GMT_CREATE			= "gmtCreate";
	public static final String	DB_GMT_UPDATE		= "gmt_update";
	public static final String	GMT_UPDATE			= "gmtUpdate";
	public static final String	DB_CREATE_USER		= "create_user";
	public static final String	CREATE_USER			= "createUser";
	public static final String	DB_UPDATE_USER		= "update_user";
	public static final String	UPDATE_USER			= "updateUser";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
