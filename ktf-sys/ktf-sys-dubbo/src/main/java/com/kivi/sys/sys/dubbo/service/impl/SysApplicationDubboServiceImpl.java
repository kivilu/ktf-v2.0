package com.kivi.sys.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfCommonProperties;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysApplicationDTO;
import com.kivi.sys.sys.entity.SysApplication;
import com.kivi.sys.sys.mapper.SysApplicationMapper;
import com.kivi.sys.sys.service.SysApplicationService;

/**
 * <p>
 * 系统应用 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysApplicationDubboServiceImpl extends ServiceImpl<SysApplicationMapper, SysApplication>
    implements SysApplicationService {

    @Autowired
    KtfCommonProperties ktfCommonProperties;

    @Autowired
    SysApplicationService sysApplicationService;

    /**
     * 根据ID查询系统应用
     */
    @KtfTrace("根据ID查询系统应用")
    @Override
    public SysApplicationDTO getDTOById(Long id) {
        return sysApplicationService.getDTOById(id);
    }

    /**
     * 新增系统应用
     */
    @KtfTrace("新增系统应用")
    @Override
    public Boolean save(SysApplicationDTO sysApplicationDTO) {
        return sysApplicationService.save(sysApplicationDTO);
    }

    /**
     * 修改
     */
    @KtfTrace("修改系统应用")
    @Override
    public Boolean updateById(SysApplicationDTO sysApplicationDTO) {
        return sysApplicationService.updateById(sysApplicationDTO);
    }

    /**
     * 查询列表
     */
    @KtfTrace("查询列表系统应用")
    @Override
    public List<SysApplicationDTO> list(SysApplicationDTO sysApplicationDTO) {
        return sysApplicationService.list(sysApplicationDTO);
    }

    /**
     * 指定列查询列表
     */
    @KtfTrace("指定列查询列表系统应用")
    @Override
    public List<SysApplicationDTO> list(Map<String, Object> params, String... columns) {
        return sysApplicationService.list(params, columns);
    }

    /**
     * 模糊查询
     */
    @KtfTrace("模糊查询系统应用")
    @Override
    public List<SysApplicationDTO> listLike(SysApplicationDTO applicationDTO) {
        return sysApplicationService.listLike(applicationDTO);
    }

    /**
     * 指定列模糊查询
     */
    @Override
    public List<SysApplicationDTO> listLike(Map<String, Object> params, String... columns) {
        return sysApplicationService.listLike(params, columns);
    }

    /**
     * 分页查询
     */
    @Override
    @KtfTrace("分页查询系统应用")
    public PageInfoVO<SysApplicationDTO> page(Map<String, Object> params) {
        return sysApplicationService.page(params);

    }

    @Override
    public Long getOrCreate(String code) {
        return sysApplicationService.getOrCreate(code);
    }

    @Override
    public Boolean removeById(Long id) {
        return sysApplicationService.removeById(id);
    }

    @Override
    public Boolean removeByIds(List<Long> ids) {
        return sysApplicationService.removeByIds(ids);
    }

}
