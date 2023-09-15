package com.kivi.sys.org.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgCorpDTO;
import com.kivi.sys.org.entity.OrgCorp;
import com.kivi.sys.org.mapper.OrgCorpExMapper;
import com.kivi.sys.org.mapper.OrgCorpMapper;
import com.kivi.sys.org.service.IOrgCorpService;

/**
 * <p>
 * 企业信息 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgCorpServiceImpl extends ServiceImpl<OrgCorpMapper, OrgCorp> implements IOrgCorpService {

    @Autowired
    private OrgCorpExMapper orgCorpExMapper;

    /**
     * 根据ID查询企业信息
     */
    @KtfTrace("根据ID查询企业信息")
    @Override
    public OrgCorpDTO getDto(Long id) {
        OrgCorp entity = super.getById(id);
        OrgCorpDTO dto = BeanConverter.convert(OrgCorpDTO.class, entity);
        return dto;
    }

    /**
     * 新增企业信息
     */
    @KtfTrace("新增企业信息")
    @KtfCacheEvict(cacheNames = "kikv.org.list")
    @Override
    public OrgCorpDTO save(OrgCorpDTO dto) {
        OrgCorp entity = BeanConverter.convert(OrgCorp.class, dto);
        super.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    /**
     * 修改
     */
    @KtfTrace("修改企业信息")
    @KtfCacheEvict(cacheNames = "kikv.org.list")
    @Override
    public Boolean updateById(OrgCorpDTO dto) {
        OrgCorp entity = BeanConverter.convert(OrgCorp.class, dto);
        return super.updateById(entity);
    }

    @Override
    public PageInfoVO<OrgCorpDTO> page(Map<String, Object> params) {
        PageParams<OrgCorpDTO> pageParams = new PageParams<>(params);
        Page<OrgCorpDTO> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
        IPage<OrgCorpDTO> iPage = orgCorpExMapper.selectDTO(page, params);

        PageInfoVO<OrgCorpDTO> pageVo = new PageInfoVO<>();
        pageVo.setCurPage(iPage.getCurrent());
        pageVo.setTotal(iPage.getTotal());
        pageVo.setPageSize(iPage.getSize());
        pageVo.setPages(iPage.getPages());
        pageVo.setRequestMap(params);
        pageVo.setList(iPage.getRecords());
        pageVo.compute();

        return pageVo;
    }

    @Override
    public List<OrgCorpDTO> list(Map<String, Object> params) {
        return orgCorpExMapper.selectDTO(params);
    }

    @Override
    @Cacheable(value = "kikv.org.list", key = "caches[0].name+'.dto.'", unless = "#result == null")
    public List<OrgCorpDTO> allList() {
        QueryWrapper<OrgCorp> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(OrgCorp.ID);
        List<OrgCorpDTO> list = BeanConverter.convert(OrgCorpDTO.class, super.list(queryWrapper));
        return list;
    }

    @Override
    public Boolean removeById(Long id) {
        return this.removeById(id);
    }

    @Override
    public Boolean removeByIds(List<Long> ids) {
        return this.removeByIds(ids);
    }

}
