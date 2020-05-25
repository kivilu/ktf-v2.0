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
 * 系统用户Token
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_user_token")
public class SysUserToken extends Model<SysUserToken> {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Long userId;
    /**
     * token
     */
    @TableField("token")
    private String token;
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;
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


    public static final String DB_USER_ID = "user_id";
	public static final String USER_ID = "userId";
    public static final String DB_TOKEN = "token";
	public static final String TOKEN = "token";
    public static final String DB_EXPIRE_TIME = "expire_time";
	public static final String EXPIRE_TIME = "expireTime";
    public static final String DB_GMT_CREATE = "gmt_create";
	public static final String GMT_CREATE = "gmtCreate";
    public static final String DB_GMT_UPDATE = "gmt_update";
	public static final String GMT_UPDATE = "gmtUpdate";
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
