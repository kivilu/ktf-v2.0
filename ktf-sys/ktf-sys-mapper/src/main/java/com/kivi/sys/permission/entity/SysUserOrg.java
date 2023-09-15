package com.kivi.sys.permission.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kivi.framework.util.kit.StrKit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 监管用户与企业关联
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_user_org")
public class SysUserOrg extends Model<SysUserOrg> {

    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;
    /**
     * 企业id列表(;分割)
     */
    @TableField(value = "org_id", insertStrategy = FieldStrategy.ALWAYS)
    private Long orgId;

    public static final String DB_USER_ID = "user_id";
    public static final String USER_ID = "userId";
    public static final String DB_ORG_ID = "org_id";
    public static final String ORG_ID = "orgId";

    @Override
    public Serializable pkVal() {
        return StrKit.join(this.userId, this.orgId);
    }

}
