package com.kivi.framework.converter;

import org.junit.Test;

import com.kivi.framework.dto.warapper.WarpReqDTO;
import com.kivi.framework.dto.warapper.WarpRspDTO;

public class BeanConverterTest {

	@Test
	public void testGetModelMapper() {
	}

	@Test
	public void testBeanToMap() {
	}

	@Test
	public void testBeansToMap() {
	}

	@Test
	public void testMapToBeanListOfMapOfStringObjectClassOfT() {
	}

	@Test
	public void testMapToBeanMapOfStringObjectClassOfT() {
	}

	@Test
	public void testConvertClassOfTListOfQ() {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testConvertClassOfTObject() {
		WarpReqDTO<Boolean>	reqDTO		= new WarpReqDTO<>(1L, true);
		WarpReqDTO<Boolean>	copyReqDTO	= BeanConverter.convert(WarpReqDTO.class, reqDTO);
		System.out.println("copyReqDTO:" + copyReqDTO);

		WarpRspDTO<Boolean> copyRspDTO = BeanConverter.convert(WarpRspDTO.class, reqDTO);
		System.out.println("copyRspDTO:" + copyRspDTO);
	}

}
