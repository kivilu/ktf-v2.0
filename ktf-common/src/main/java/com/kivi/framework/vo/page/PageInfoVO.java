package com.kivi.framework.vo.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kivi.framework.util.kit.CollectionKit;
import com.vip.vjtools.vjkit.collection.ListUtil;

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
	@ApiModelProperty(value = "当前页", required = false, dataType = "long", notes = "当前页")
	protected long				curPage;

	// 每页的数量
	@ApiModelProperty(value = "每页的数量", required = false, dataType = "long", notes = "每页的数量")
	protected long				pageSize;
	// 当前页的数量
	@ApiModelProperty(value = "当前页的数量", required = false, dataType = "long", notes = "当前页的数量")
	protected long				size;

	// 总页数
	@ApiModelProperty(value = "总页数", required = false, dataType = "long")
	protected long				pages;

	/**
	 * 是否存在上一页
	 */
	protected boolean			hasPrevious;

	/**
	 * 是否存在下一页
	 */
	protected boolean			hasNext;

	// 总数
	@ApiModelProperty(value = "结果总数", required = false, dataType = "List", notes = "查询结果总数")
	protected long				total;
	// 结果集
	@ApiModelProperty(value = "结果集", required = false, dataType = "List", notes = "查询结果集")
	protected List<T>			list;

	// 参数
	private Map<String, Object>	requestMap			= CollectionKit.newHashMap();

	public void compute() {
		this.hasPrevious	= this.curPage > 1;
		this.hasNext		= this.curPage < this.getPages();
		this.size			= ListUtil.isNotEmpty(list) ? this.list.size() : 0;
	}

}
