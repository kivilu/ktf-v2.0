package com.kivi.dashboard.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "SysFileDTO对象", description = "附件")
public class SysFileDTO implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Long				id;

	@ApiModelProperty(value = "附件类型(哪个表的附件)")
	private Long				tableId;

	@ApiModelProperty(value = "附件ID(哪个表的记录Id)")
	private Long				recordId;

	@ApiModelProperty(value = "表的记录Id下的附件分组的组名")
	private String				attachmentGroup;

	@ApiModelProperty(value = "附件名称")
	private String				attachmentName;

	@ApiModelProperty(value = "附件路径")
	private String				attachmentPath;

	@ApiModelProperty(value = "附件类型(0-word,1-excel,2-pdf,3-jpg,png,4-其他)")
	private Integer				attachmentType;

	@ApiModelProperty(value = "存储类型（0：本地存储，1:fastdfs）")
	private Integer				saveType;

	@ApiModelProperty(value = "数据是否同步(0:是,1:否)")
	private Integer				isSync;

	@ApiModelProperty(value = "记录创建者(用户)")
	private String				createUser;

	@ApiModelProperty(value = "记录最后修改者(用户)")
	private String				updateUser;

	public static final String	ID					= "id";
	public static final String	TABLE_ID			= "tableId";
	public static final String	RECORD_ID			= "recordId";
	public static final String	ATTACHMENT_GROUP	= "attachmentGroup";
	public static final String	ATTACHMENT_NAME		= "attachmentName";
	public static final String	ATTACHMENT_PATH		= "attachmentPath";
	public static final String	ATTACHMENT_TYPE		= "attachmentType";
	public static final String	SAVE_TYPE			= "saveType";
	public static final String	IS_SYNC				= "isSync";
	public static final String	CREATE_USER			= "createUser";
	public static final String	UPDATE_USER			= "updateUser";

}
