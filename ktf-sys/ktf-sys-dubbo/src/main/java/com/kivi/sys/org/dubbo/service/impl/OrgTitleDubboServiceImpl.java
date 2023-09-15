package com.kivi.sys.org.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgTitleDTO;
import com.kivi.sys.org.entity.OrgTitle;
import com.kivi.sys.org.mapper.OrgTitleMapper;
import com.kivi.sys.org.service.OrgTitleService;

/**
 * <p>
 * 企业职务配置 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class OrgTitleDubboServiceImpl extends ServiceImpl<OrgTitleMapper, OrgTitle> implements OrgTitleService {

    @Autowired
    private OrgTitleService orgTitleService;

    /**
     * 根据ID查询企业职务配置
     */
    @KtfTrace("根据ID查询企业职务配置")
    @Override
    public OrgTitleDTO getDto(Long id) {
        return orgTitleService.getDto(id);
    }

    /**
     * 新增企业职务配置
     */
    @KtfTrace("新增企业职务配置")
    @Override
    public Boolean save(OrgTitleDTO dto) {

        return orgTitleService.save(dto);
    }

    /**
     * 修改
     */
    @KtfTrace("修改企业职务配置")
    @Override
    public Boolean updateById(OrgTitleDTO dto) {
        return orgTitleService.updateById(dto);
    }

    /**
     * 分页查询
     */
    @Override
    @KtfTrace("分页查询企业职务配置")
    public PageInfoVO<OrgTitleDTO> page(Map<String, Object> params) {
        return orgTitleService.page(params);

    }

    @Override
    public List<OrgTitleDTO> list(Map<String, Object> params) {
        return orgTitleService.list(params);
    }

    @Override
    public Boolean removeById(Long id) {
        return orgTitleService.removeById(id);
    }

    @Override
    public Boolean removeByIds(List<Long> ids) {
        return orgTitleService.removeByIds(ids);
    }

}
