package com.kivi.dashboard.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 系统应用
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_application")
public class SysApplication extends Model<SysApplication> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;
    /**
     * 应用代码
     */
    @TableField("code")
    private String code;
    /**
     * 应用名称
     */
    @TableField("name")
    private String name;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    @TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtUpdate;
    /**
     * 记录创建者(用户)
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 记录修改者(用户)
     */
    @TableField("update_user")
    private String updateUser;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_CODE = "code";
	public static final String CODE = "code";
    public static final String DB_NAME = "name";
	public static final String NAME = "name";
    public static final String DB_GMT_CREATE = "gmt_create";
	public static final String GMT_CREATE = "gmtCreate";
    public static final String DB_GMT_UPDATE = "gmt_update";
	public static final String GMT_UPDATE = "gmtUpdate";
    public static final String DB_CREATE_USER = "create_user";
	public static final String CREATE_USER = "createUser";
    public static final String DB_UPDATE_USER = "update_user";
	public static final String UPDATE_USER = "updateUser";
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
