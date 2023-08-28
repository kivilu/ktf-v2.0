package com.kivi.framework.model;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.kivi.framework.constant.KtfError;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * @Description 操作结果集
 */
public class ResultMap extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResultMap() {
		put("code", KtfError.SUCCESS);
		put("msg", "success");
	}

	public static ResultMap error(int code, String msg) {
		ResultMap r = new ResultMap();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ResultMap error(String msg) {
		return error(KtfError.E_UNDEFINED, msg);
	}

	public static ResultMap error() {
		return error(KtfError.E_UNDEFINED, "未知异常，请联系管理员");
	}

	public static ResultMap ok(String msg) {
		ResultMap r = new ResultMap();
		r.put("msg", msg);
		return r;
	}

	public static ResultMap ok(Map<String, Object> par) {
		ResultMap r = new ResultMap();
		r.putAll(par);
		return r;
	}

	public static ResultMap ok() {
		return new ResultMap();
	}

	public ResultMap data(Object value) {
		return this.put("data", value);
	}

	public <T> ResultMap data(PageInfoVO<T> page) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotal());
		map.put("list", page.getList());
		return this.data(map);
	}

	@Override
	public ResultMap put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public String msg() {
		return (String) this.get("msg");
	}

	public int code() {
		return (Integer) this.get("code");
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
