package com.kivi.framework.vo.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kivi.framework.util.kit.CollectionKit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 由于PageHelper插件中的PageInfo的对象属性为乱码，所以重新定义
 *
 */
@ApiModel(value = "PageInfoVO", description = "分页查询结果")
@Data
public class PageInfoVO<T> implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// 当前页
	@ApiModelProperty(value = "当前页", required = false, dataType = "int", notes = "当前页")
	protected int				curPage;

	// 每页的数量

	@ApiModelProperty(value = "每页的数量", required = false, dataType = "int", notes = "每页的数量")
	protected int				pageSize;
	// 当前页的数量
	@ApiModelProperty(value = "当前页的数量", required = false, dataType = "int", notes = "当前页的数量")
	protected int				size;
	// 排序
	@ApiModelProperty(value = "排序", required = false, dataType = "string", notes = "排序")
	protected String			orderBy;

	// 总页数
	@ApiModelProperty(value = "总页数", required = false, dataType = "int")
	protected int				pages;

	// 总数
	@ApiModelProperty(value = "结果总数", required = false, dataType = "List", notes = "查询结果总数")
	protected long				total;
	// 结果集
	@ApiModelProperty(value = "结果集", required = false, dataType = "List", notes = "查询结果集")
	protected List<T>			list;

	// 排序字段
	private String				sort;
	// 排序方向
	private String				order;
	// 参数
	private Map<String, Object>	requestMap			= CollectionKit.newHashMap();

}
