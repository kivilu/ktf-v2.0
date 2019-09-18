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
 * 附件
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ktf_sys_file")
public class SysFile extends Model<SysFile> {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;
    /**
     * 附件类型(哪个表的附件)
     */
    @TableField("table_id")
    private Long tableId;
    /**
     * 附件ID(哪个表的记录Id)
     */
    @TableField("record_id")
    private Long recordId;
    /**
     * 表的记录Id下的附件分组的组名
     */
    @TableField("attachment_group")
    private String attachmentGroup;
    /**
     * 附件名称
     */
    @TableField("attachment_name")
    private String attachmentName;
    /**
     * 附件路径
     */
    @TableField("attachment_path")
    private String attachmentPath;
    /**
     * 附件类型(0-word,1-excel,2-pdf,3-jpg,png,4-其他)
     */
    @TableField("attachment_type")
    private Integer attachmentType;
    /**
     * 存储类型（0：本地存储，1:fastdfs）
     */
    @TableField("save_type")
    private Integer saveType;
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
    public static final String DB_TABLE_ID = "table_id";
	public static final String TABLE_ID = "tableId";
    public static final String DB_RECORD_ID = "record_id";
	public static final String RECORD_ID = "recordId";
    public static final String DB_ATTACHMENT_GROUP = "attachment_group";
	public static final String ATTACHMENT_GROUP = "attachmentGroup";
    public static final String DB_ATTACHMENT_NAME = "attachment_name";
	public static final String ATTACHMENT_NAME = "attachmentName";
    public static final String DB_ATTACHMENT_PATH = "attachment_path";
	public static final String ATTACHMENT_PATH = "attachmentPath";
    public static final String DB_ATTACHMENT_TYPE = "attachment_type";
	public static final String ATTACHMENT_TYPE = "attachmentType";
    public static final String DB_SAVE_TYPE = "save_type";
	public static final String SAVE_TYPE = "saveType";
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
