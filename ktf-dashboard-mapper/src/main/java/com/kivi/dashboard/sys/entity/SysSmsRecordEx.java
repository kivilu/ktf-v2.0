package com.kivi.dashboard.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

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
public class SysSmsRecordEx extends SysSmsRecord {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 消息距当前时间
	 */
	@TableField(exist = false)
	private String				timeStr;

	@TableField(exist = false)
	private String				userName;

	@TableField(exist = false)
	private String				title;

	@TableField(exist = false)
	private String				content;

	@TableField(exist = false)
	private String				startTime;

	@TableField(exist = false)
	private String				endTime;

}
