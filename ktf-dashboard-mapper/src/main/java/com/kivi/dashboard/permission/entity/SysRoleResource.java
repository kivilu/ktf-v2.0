package com.kivi.dashboard.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色资源
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_role_resource")
public class SysRoleResource extends Model<SysRoleResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * 资源id
     */
    @TableField("resource_id")
    private Long resourceId;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_ROLE_ID = "role_id";
	public static final String ROLE_ID = "roleId";
    public static final String DB_RESOURCE_ID = "resource_id";
	public static final String RESOURCE_ID = "resourceId";
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
