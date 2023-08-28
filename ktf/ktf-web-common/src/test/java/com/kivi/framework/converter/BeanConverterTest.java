package com.kivi.framework.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.kivi.framework.dto.KtfBaseReq;

public class BeanConverterTest {

	@Test
	public void testCopyProperties() {
		KtfBaseReq<Integer>	srcDto	= new KtfBaseReq<>();
		KtfBaseReq<Integer>	dstDto	= new KtfBaseReq<>();
		srcDto.setData(1);
		dstDto.setData(2);

		BeanConverter.copyProperties(srcDto, dstDto);
		System.out.println("srcDto:" + srcDto);
		System.out.println("dstDto:" + dstDto);

		assertEquals(1, dstDto.getData());
	}

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

	@Test
	public void testConvertClassOfTObject() {

	}

}
