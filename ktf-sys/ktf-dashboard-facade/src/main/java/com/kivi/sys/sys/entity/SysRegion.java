package com.kivi.sys.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 地区信息
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_region")
public class SysRegion extends Model<SysRegion> {

	private static final long	serialVersionUID	= 1L;

	/**
	 * 主键ID
	 */
	@TableField("id")
	private Long				id;
	/**
	 * 上级ID
	 */
	@TableField("pid")
	private Long				pid;
	/**
	 * 代码
	 */
	@TableField("code")
	private String				code;
	/**
	 * 上级代码
	 */
	@TableField("pcode")
	private String				pcode;
	/**
	 * 名称
	 */
	@TableField("name")
	private String				name;
	/**
	 * 类型：0：国家，1：区域，2：20省份（21直辖市22自治区），3：城市，4：区县，5：村镇
	 */
	@TableField("type")
	private String				type;
	/**
	 * 状态
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
	public static final String	DB_TYPE				= "type";
	public static final String	TYPE				= "type";
	public static final String	DB_STATUS			= "status";
	public static final String	STATUS				= "status";

	@Override
	public Serializable pkVal() {
		return id;
	}

}
