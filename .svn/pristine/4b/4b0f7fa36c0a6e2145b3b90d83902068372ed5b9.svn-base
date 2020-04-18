package com.kivi.dashboard.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色名
     */
    @TableField("name")
    private String name;
    /**
     * 排序号
     */
    @TableField("seq")
    private Integer seq;
    /**
     * 简介
     */
    @TableField("description")
    private String description;
    /**
     * 状态(0：开启，1：关闭)
     */
    @TableField("status")
    private Integer status;
    /**
     * 记录创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    /**
     * 记录最后修改时间
     */
    @TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtUpdate;
    /**
     * 记录创建者ID
     */
    @TableField("create_user_id")
    private Long createUserId;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_NAME = "name";
	public static final String NAME = "name";
    public static final String DB_SEQ = "seq";
	public static final String SEQ = "seq";
    public static final String DB_DESCRIPTION = "description";
	public static final String DESCRIPTION = "description";
    public static final String DB_STATUS = "status";
	public static final String STATUS = "status";
    public static final String DB_GMT_CREATE = "gmt_create";
	public static final String GMT_CREATE = "gmtCreate";
    public static final String DB_GMT_UPDATE = "gmt_update";
	public static final String GMT_UPDATE = "gmtUpdate";
    public static final String DB_CREATE_USER_ID = "create_user_id";
	public static final String CREATE_USER_ID = "createUserId";
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
