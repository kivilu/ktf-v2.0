package com.kivi.sys.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.collect.Maps;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.TestApplication;
import com.kivi.sys.sys.dto.SysApplicationDTO;
import com.kivi.sys.sys.service.SysApplicationService;

// @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class ApplicationServiceImplTest {
    @Autowired
    private SysApplicationService applicationService;

    @Test
    public void testGetDTOById() {
        Assertions.fail("Not yet implemented");
    }

    @Test
    public void testSaveApplicationDTO() {
        SysApplicationDTO applicationDTO = new SysApplicationDTO();

        int no = 1;
        String today = DateTimeKit.today();

        applicationDTO.setCode(StringUtils.joinWith("-", "APP", today, no++));
        applicationDTO.setName(StringUtils.joinWith("-", "测试应用", today, no++));
        Boolean ret = applicationService.save(applicationDTO);
        Assertions.assertEquals(true, ret);
        System.out.println(StrKit.toJson(applicationDTO));

        SysApplicationDTO entity = new SysApplicationDTO();
        entity.setCode(StringUtils.joinWith("-", "APP", today, no++));
        entity.setName(StringUtils.joinWith("-", "测试应用", today, no++));
        ret = applicationService.save(entity);
        Assertions.assertEquals(true, ret);
        System.out.println(StrKit.toJson(entity));

    }

    @Test
    public void testUpdateByIdApplicationDTO() {
        SysApplicationDTO applicationDTO = new SysApplicationDTO();
        applicationDTO.setId(1174228977317236737L);
        applicationDTO.setName("测试DTO更新");
        applicationDTO.setUpdateUser("junit更新");
        Boolean ret = applicationService.updateById(applicationDTO);
        Assertions.assertEquals(true, ret);

        SysApplicationDTO application = new SysApplicationDTO();
        application.setId(1174228209126289409L);
        application.setName("测试Entity更新");
        ret = applicationService.updateById(application);
        Assertions.assertEquals(true, ret);
    }

    @Test
    public void testListApplicationDTO() {
        SysApplicationDTO applicationDTO = new SysApplicationDTO();
        applicationDTO.setId(1174228612140146690L);
        applicationDTO.setName("测试应用");
        List<SysApplicationDTO> list = applicationService.list(applicationDTO);
        System.out.println("精确查询结果：" + StrKit.toJson(list));

        Map<String, Object> params = Maps.newHashMap();
        params.put(SysApplicationDTO.NAME, "测试应用");
        params.put(SysApplicationDTO.ID, 1174228612140146690L);
        list = applicationService.list(params, SysApplicationDTO.ID, SysApplicationDTO.CODE, SysApplicationDTO.NAME);
        System.out.println("精确查询结果：" + StrKit.toJson(list));

        list = applicationService.listLike(applicationDTO);
        System.out.println("模糊查询结果：" + StrKit.toJson(list));

        list =
            applicationService.listLike(params, SysApplicationDTO.ID, SysApplicationDTO.CODE, SysApplicationDTO.NAME);
        System.out.println("模糊查询结果：" + StrKit.toJson(list));
    }

    @Test
    public void testPageMapOfStringObject() {
        Map<String, Object> params = Maps.newHashMap();
        params.put(KtfConstant.PAGE_KEY, 1);
        params.put(KtfConstant.PAGE_LIMIT_KEY, 3);
        PageInfoVO<SysApplicationDTO> page = applicationService.page(params);
        System.out.println(StrKit.toJson(page));

        params.put(KtfConstant.PAGE_KEY, 2);
        params.put(KtfConstant.PAGE_LIMIT_KEY, 3);
        page = applicationService.page(params);
        System.out.println(StrKit.toJson(page));
    }

    @Test
    public void testRemoveAll() {
        // applicationService.remove(Wrappers.<SysApplication>emptyWrapper());
    }

}
