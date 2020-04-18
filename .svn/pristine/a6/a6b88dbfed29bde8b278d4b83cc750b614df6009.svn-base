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
 * 数据字典
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_dic")
public class SysDic extends Model<SysDic> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 父变量ID
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 变量代码
     */
    @TableField("var_code")
    private String varCode;
    /**
     * 变量名称
     */
    @TableField("var_name")
    private String varName;
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
     * 记录修改时间
     */
    @TableField(value = "gmt_update", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtUpdate;
    /**
     * 记录创建者（用户）
     */
     @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 记录最后修改者（用户）
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;


    public static final String DB_ID = "id";
	public static final String ID = "id";
    public static final String DB_PARENT_ID = "parent_id";
	public static final String PARENT_ID = "parentId";
    public static final String DB_VAR_CODE = "var_code";
	public static final String VAR_CODE = "varCode";
    public static final String DB_VAR_NAME = "var_name";
	public static final String VAR_NAME = "varName";
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
