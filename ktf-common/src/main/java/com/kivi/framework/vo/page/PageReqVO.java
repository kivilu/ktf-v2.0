package com.kivi.framework.vo.page;

import java.io.Serializable;

import com.kivi.framework.constant.enums.Order;
import com.kivi.framework.util.kit.StrKit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 */
@Setter
@Getter
@ApiModel( value = "PageReq", description = "分页请求" )
public class PageReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty( value = "分页大小", required = false, dataType = "integer", notes = "每页照片数量", example = "10" )
    private Integer           limit;

    @ApiModelProperty( value = "记录位置", required = false, dataType = "integer", notes = "记录位置", example = "0" )
    private Integer           offset;

    @ApiModelProperty( value = "排序字段", required = false, dataType = "String", notes = "记录位置", example = "" )
    private String            sort;

    @ApiModelProperty( value = "排序方式", required = false, dataType = "String", notes = "排序方式", example = "asc" )
    private String            order;

    @ApiModelProperty( value = "是否排序", required = false, dataType = "boolean", notes = "是否排序", hidden = true )
    private boolean           openSort;

    @ApiModelProperty( value = "是否升序", required = false, dataType = "boolean", notes = "是否排序", hidden = true )
    private boolean           asc;

    public PageReqVO() {
        this.limit = 10;
        this.offset = 0;
        this.order = Order.ASC.getCode();

        initValue();
    }

    public PageReqVO( Integer limit, Integer offset, String sort, String order ) {
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
        this.order = order;
        initValue();
    }

    private void initValue() {
        if (StrKit.isEmpty(sort)) {
            setOpenSort(false);
            this.order = null;
        }
        else {
            if (Order.ASC.getCode().equals(order)) {
                setAsc(true);
            }
            else {
                setAsc(false);
            }
        }
    }

    public String getOrderBy() {
        if (!openSort)
            return null;

        return StrKit.builder(sort, " ", order).toString();
    }

}
