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
 * 消息类型与用户关系
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_sms_type")
public class SysSmsType extends Model<SysSmsType> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;
    /**
     * 账号列表
     */
    @TableField("target_list")
    private String targetList;
    /**
     * 是否发送短信（0-是，0-否）
     */
    @TableField("is_send_sms")
    private Integer isSendSms;
    /**
     * 是否发送邮件（0-是，1-否）
     */
    @TableField("is_send_email")
    private Integer isSendEmail;
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
     * 创建者
     */
    @TableField("create_user")
    private Long createUser;
    /**
     * 修改者
     */
    @TableField("update_user")
    private Long updateUser;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_TYPE_NAME = "type_name";
	public static final String TYPE_NAME = "typeName";
    public static final String DB_TARGET_LIST = "target_list";
	public static final String TARGET_LIST = "targetList";
    public static final String DB_IS_SEND_SMS = "is_send_sms";
	public static final String IS_SEND_SMS = "isSendSms";
    public static final String DB_IS_SEND_EMAIL = "is_send_email";
	public static final String IS_SEND_EMAIL = "isSendEmail";
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
