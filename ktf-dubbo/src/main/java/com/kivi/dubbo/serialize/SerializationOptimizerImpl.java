package com.kivi.dubbo.serialize;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import com.kivi.dubbo.properties.KtfDubboProperties;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.dto.warapper.WarpRspDTO;
import com.kivi.framework.dto.warapper.WarpperDTO;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoBT;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.framework.vo.page.PageReqVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializationOptimizerImpl implements SerializationOptimizer {

	@SuppressWarnings("rawtypes")
	@Override
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<>();

		classes.add(WarpperDTO.class);
		classes.add(WarpReqDTO.class);
		classes.add(WarpRspDTO.class);
		classes.add(PageInfoBT.class);
		classes.add(PageInfoVO.class);
		classes.add(PageReqVO.class);
		classes.add(byte.class);
		classes.add(Byte.class);
		classes.add(KtfException.class);

		KtfDubboProperties ktfDubboProperties = SpringContextHolder.getBeanNoAssert(KtfDubboProperties.class);
		if (ktfDubboProperties == null) {
			return classes;
		}

		String[] classNames = StrKit.split(ktfDubboProperties.getSerializeClasses(), ",");
		if (classNames == null)
			return classes;
		for (String clazz : classNames) {
			try {
				classes.add(Class.forName(clazz));
			} catch (ClassNotFoundException e) {
				log.error("类名找不到", e);
			}
		}

		log.trace("序列号注册类：{}", classes);

		return classes;
	}

}
