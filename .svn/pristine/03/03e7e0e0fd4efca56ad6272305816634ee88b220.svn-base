package com.kivi.dashboard.sys.service.impl;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.kivi.dashboard.TestApplication;
import com.kivi.dashboard.sys.dto.SysDicDTO;
import com.kivi.dashboard.sys.service.ISysDicService;
import com.kivi.framework.util.kit.StrKit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { TestApplication.class })
public class DicServiceImplTest {

	@Autowired
	private ISysDicService dicService;

	@Test
	public void testGetDTOById() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveDicDTO() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByIdDicDTO() {
		fail("Not yet implemented");
	}

	@Test
	public void testListDicDTO() {
		SysDicDTO		dicDTO	= new SysDicDTO();
		// dicDTO.setParentName("NB运营商");
		List<SysDicDTO>	list	= dicService.list(dicDTO);

		System.out.println(StrKit.toJson(list));
	}

	@Test
	public void testSelectTreeGrid() {
		Map<String, Object>	par		= Maps.newHashMap();
		List<SysDicDTO>		result	= dicService.listLike(par, new String[0]);
		System.out.println(StrKit.toJson(result));
	}

	@Test
	public void testPageMapOfStringObject() {
		fail("Not yet implemented");
	}

}
