package com.kivi.framework.web.constant.tips;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回给前台的提示（最终转化为json形式）
 *
 */
@Setter
@Getter
public abstract class Tip {

    protected String code;
    protected int	 httpStatus ;
    protected String message;

    
}
