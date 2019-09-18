package com.kivi.dashboard.enterprise.entity;

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
 * 企业部门
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_enterprise_department")
public class EnterpriseDepartment extends Model<EnterpriseDepartment> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;
    /**
     * 企业部门父ID
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 企业ID(对应企业主表ID)
     */
    @TableField("enterprise_id")
    private Long enterpriseId;
    /**
     * 部门代码(可添加多个部门ID，用逗号隔开，表示该部门可以管理多个部门)
     */
    @TableField("department_code")
    private String departmentCode;
    /**
     * 部门名称
     */
    @TableField("department_name")
    private String departmentName;
    /**
     * 预留1
     */
    @TableField("parameter1")
    private String parameter1;
    /**
     * 预留2
     */
    @TableField("parameter2")
    private String parameter2;
    /**
     * 数据是否同步(0:是,1:否)
     */
    @TableField("is_sync")
    private Integer isSync;
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
     * 记录创建者(用户)
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 记录最后修改者(用户)
     */
    @TableField("update_user")
    private String updateUser;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_PARENT_ID = "parent_id";
	public static final String PARENT_ID = "parentId";
    public static final String DB_ENTERPRISE_ID = "enterprise_id";
	public static final String ENTERPRISE_ID = "enterpriseId";
    public static final String DB_DEPARTMENT_CODE = "department_code";
	public static final String DEPARTMENT_CODE = "departmentCode";
    public static final String DB_DEPARTMENT_NAME = "department_name";
	public static final String DEPARTMENT_NAME = "departmentName";
    public static final String DB_PARAMETER1 = "parameter1";
	public static final String PARAMETER1 = "parameter1";
    public static final String DB_PARAMETER2 = "parameter2";
	public static final String PARAMETER2 = "parameter2";
    public static final String DB_IS_SYNC = "is_sync";
	public static final String IS_SYNC = "isSync";
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
