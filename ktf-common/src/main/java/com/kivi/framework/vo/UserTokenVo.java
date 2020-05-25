package com.kivi.framework.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

@ApiModel(value = "UserTokenVo对象", description = "用户Token")
public class UserTokenVo implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Long				userId;

	@ApiModelProperty(value = "token")
	private String				token;

	@ApiModelProperty(value = "过期时间")
	private LocalDateTime		expireTime;

	public static final String	USER_ID				= "userId";
	public static final String	TOKEN				= "token";
	public static final String	EXPIRE_TIME			= "expireTime";

}
