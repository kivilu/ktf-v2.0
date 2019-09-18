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
 * 企业职务配置
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_enterprise_job")
public class EnterpriseJob extends Model<EnterpriseJob> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;
    /**
     * 企业部门表ID
     */
    @TableField("department_id")
    private String departmentId;
    /**
     * 职务代码
     */
    @TableField("job_code")
    private String jobCode;
    /**
     * 职务名称
     */
    @TableField("job_name")
    private String jobName;
    /**
     * 记录创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    /**
     * 记录最后更新时间
     */
    @TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtUpdate;
    /**
     * 记录创建用户
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 记录最后更新用户
     */
    @TableField("update_user")
    private String updateUser;
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
     * 是否同步（0：是，1：否）
     */
    @TableField("is_sync")
    private Integer isSync;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_DEPARTMENT_ID = "department_id";
	public static final String DEPARTMENT_ID = "departmentId";
    public static final String DB_JOB_CODE = "job_code";
	public static final String JOB_CODE = "jobCode";
    public static final String DB_JOB_NAME = "job_name";
	public static final String JOB_NAME = "jobName";
    public static final String DB_GMT_CREATE = "gmt_create";
	public static final String GMT_CREATE = "gmtCreate";
    public static final String DB_GMT_UPDATE = "gmt_update";
	public static final String GMT_UPDATE = "gmtUpdate";
    public static final String DB_CREATE_USER = "create_user";
	public static final String CREATE_USER = "createUser";
    public static final String DB_UPDATE_USER = "update_user";
	public static final String UPDATE_USER = "updateUser";
    public static final String DB_PARAMETER1 = "parameter1";
	public static final String PARAMETER1 = "parameter1";
    public static final String DB_PARAMETER2 = "parameter2";
	public static final String PARAMETER2 = "parameter2";
    public static final String DB_IS_SYNC = "is_sync";
	public static final String IS_SYNC = "isSync";
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
