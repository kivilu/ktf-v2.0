package com.kivi.db.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.kivi.db.xss.SQLFilter;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.converter.BeanConverter;

/**
 * @Description 分页参数
 */
public class PageParams<T> extends Page<T> implements Serializable {
	private static final long	serialVersionUID	= 1L;

	// 当前页数
	private long				currPage;
	// 每页记录数
	private long				pageSize;
	// 排序字段
	private String				sort;
	// 排序方向
	private String				order;
	// 参数
	private Map<String, Object>	requestMap			= Maps.newHashMap();

	// 总记录数
	private long				totalCount;
	// 列表数据
	private List<T>				list;

	public PageParams() {
		requestMap = Maps.newHashMap();
	}

	/**
	 * 分页
	 */
	public PageParams(IPage<T> page) {
		this.list		= page.getRecords();
		this.totalCount	= page.getTotal();
	}

	public PageParams(Map<String, Object> params) {
		if (params == null) {
			params = Maps.newHashMap();
		}
		this.currPage	= Long.valueOf(params.getOrDefault(KtfConstant.PAGE_KEY, KtfConstant.DEFAULT_PAGE).toString());
		this.pageSize	= Long
				.valueOf(params.getOrDefault(KtfConstant.PAGE_LIMIT_KEY, KtfConstant.DEFAULT_LIMIT).toString());
		// 防止SQL注入
		// 排序方向（asc,desc）
		if (params.containsKey("order")) {
			this.order = SQLFilter.sqlInject((String) params.get("order"));
		}
		// 排序字段
		if (params.containsKey("sort")) {
			this.sort = SQLFilter.sqlInject((String) params.get("sort"));
		}
		super.setCurrent(currPage);
		super.setSize(pageSize);

		params.remove(KtfConstant.PAGE_KEY);
		params.remove(KtfConstant.PAGE_LIMIT_KEY);
		params.remove(KtfConstant.PAGE_ORDER_KEY);
		params.remove(KtfConstant.PAGE_SORT_KEY);
		params.remove(KtfConstant.URL_TIMESTAMP);
		requestMap.putAll(params);

	}

	public long getCurrPage() {
		if (currPage <= KtfConstant.MIN_PAGE) {
			currPage = 1;
		}
		return currPage;
	}

	public void setCurrPage(long currPage) {
		this.currPage = currPage;
	}

	public long getPageSize() {
		if (pageSize >= KtfConstant.MAX_LIMIT) {
			pageSize = KtfConstant.MAX_LIMIT;
		}
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public T mapToObject(Class<T> clazz) {
		return BeanConverter.mapToBean(this.getRequestMap(), clazz);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
