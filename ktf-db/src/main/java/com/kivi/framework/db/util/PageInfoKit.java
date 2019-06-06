package com.kivi.framework.db.util;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.kivi.framework.util.kit.BeanKit;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.vo.page.PageInfoVO;

public class PageInfoKit {

	public static <T> PageInfoVO<T> convert(PageInfo<T> pageInfo) {
		PageInfoVO<T> result = new PageInfoVO<T>();
		result.setList(null);
		BeanKit.copyProperties(pageInfo, result, true);

		List<T> list = CollectionKit.newArrayList();
		list.addAll(pageInfo.getList());
		result.setList(list);
		return result;
	}
}
