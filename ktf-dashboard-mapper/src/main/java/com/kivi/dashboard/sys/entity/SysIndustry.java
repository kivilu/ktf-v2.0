package com.kivi.dashboard.sys.entity;

import java.io.Serializable;

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
 * 行业代码
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_industry")
public class SysIndustry extends Model<SysIndustry> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long				id;
	/**
	 * 上级ID
	 */
	@TableField("pid")
	private Long				pid;
	/**
	 * 行业代码
	 */
	@TableField("code")
	private String				code;
	/**
	 * 上级行业代码
	 */
	@TableField("pcode")
	private String				pcode;
	/**
	 * 行业名称
	 */
	@TableField("name")
	private String				name;
	/**
	 * 状态，0无效，1有效
	 */
	@TableField("status")
	private Boolean				status;

	public static final String	DB_ID				= "id";
	public static final String	ID					= "id";
	public static final String	DB_PID				= "pid";
	public static final String	PID					= "pid";
	public static final String	DB_CODE				= "code";
	public static final String	CODE				= "code";
	public static final String	DB_PCODE			= "pcode";
	public static final String	PCODE				= "pcode";
	public static final String	DB_NAME				= "name";
	public static final String	NAME				= "name";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
