package com.kivi.cif.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.entity.CifCerts;
import com.kivi.cif.mapper.CifCertsMapper;
import com.kivi.cif.service.ICifCertsService;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.collection.MapUtil;

/**
 * <p>
 * 客户证书 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class CifCertsServiceImpl extends ServiceImpl<CifCertsMapper, CifCerts> implements ICifCertsService {

    /**
     * 根据ID查询客户证书
     */
    @KtfTrace("根据ID查询客户证书")
    @Override
    public CifCertsDTO getDTOById(Long id) {
        CifCerts entity = super.getById(id);
        if (entity == null)
            return null;
        CifCertsDTO dto = BeanConverter.convert(CifCertsDTO.class, decoderPriKey(entity));
        return dto;
    }

    /**
     * 新增客户证书
     */
    @KtfTrace("新增客户证书")
    @Override
    public Boolean save(CifCertsDTO cifCertsDTO) {
        CifCerts entity = BeanConverter.convert(CifCerts.class, cifCertsDTO);

        return this.save(entity);
    }

    @Override
    public boolean save(CifCerts entity) {
        return super.save(encoderPriKey(entity));
    }

    @Override
    public boolean saveBatch(Collection<CifCerts> entityList) {
        entityList.stream().forEach(entity -> encoderPriKey(entity));
        return super.saveBatch(entityList);
    }

    /**
     * 修改
     */
    @KtfTrace("修改客户证书")
    @Override
    public Boolean updateById(CifCertsDTO cifCertsDTO) {
        CifCerts entity = BeanConverter.convert(CifCerts.class, cifCertsDTO);
        return this.updateById(entity);
    }

    @Override
    public boolean updateById(CifCerts entity) {
        return super.updateById(encoderPriKey(entity));
    }

    /**
     * 查询列表
     */
    @KtfTrace("查询列表客户证书")
    @Override
    public List<CifCertsDTO> list(CifCertsDTO cifCertsDTO) {
        Map<String, Object> params = BeanConverter.beanToMap(cifCertsDTO);
        return this.list(params, new String[0]);
    }

    /**
     * 指定列查询列表
     */
    @KtfTrace("指定列查询列表客户证书")
    @Override
    public List<CifCertsDTO> list(Map<String, Object> params, String... columns) {
        if (params != null)
            params.remove(KtfConstant.URL_TIMESTAMP);
        QueryWrapper<CifCerts> query = Wrappers.<CifCerts>query().select(columns).allEq(true, params, false);
        List<CifCerts> list = super.list(query);
        list.stream().forEach(entity -> this.decoderPriKey(entity));
        return BeanConverter.convert(CifCertsDTO.class, list);
    }

    /**
     * 模糊查询
     */
    @KtfTrace("模糊查询客户证书")
    @Override
    public List<CifCertsDTO> listLike(CifCertsDTO applicationDTO) {
        Map<String, Object> params = BeanConverter.beanToMap(applicationDTO);
        return listLike(params, new String[0]);
    }

    /**
     * 指定列模糊查询
     */
    @Override
    public List<CifCertsDTO> listLike(Map<String, Object> params, String... columns) {
        if (params != null)
            params.remove(KtfConstant.URL_TIMESTAMP);
        QueryWrapper<CifCerts> query = Wrappers.<CifCerts>query().select(columns);
        if (MapUtil.isNotEmpty(params)) {
            params.entrySet().stream().forEach(entry -> {
                if (ObjectKit.isNotEmpty(entry.getValue())) {
                    if (NumberKit.isNumberic(entry.getValue()))
                        query.eq(entry.getKey(), entry.getValue());
                    else
                        query.like(entry.getKey(), entry.getValue());
                }
            });
        }

        List<CifCerts> list = super.list(query);
        list.stream().forEach(entity -> this.decoderPriKey(entity));
        return BeanConverter.convert(CifCertsDTO.class, list);
    }

    /**
     * 分页查询
     */
    @Override
    @KtfTrace("分页查询客户证书")
    public PageInfoVO<CifCertsDTO> page(Map<String, Object> params) {
        PageParams<CifCertsDTO> pageParams = new PageParams<>(params);
        Page<CifCerts> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

        QueryWrapper<CifCerts> query = Wrappers.<CifCerts>query();
        if (MapUtil.isNotEmpty(pageParams.getRequestMap())) {
            pageParams.getRequestMap().entrySet().stream().forEach(entry -> {
                if (ObjectKit.isNotEmpty(entry.getValue())) {
                    if (NumberKit.isNumberic(entry.getValue()))
                        query.eq(entry.getKey(), entry.getValue());
                    else
                        query.like(entry.getKey(), entry.getValue());
                }
            });
        }

        IPage<CifCerts> iPage = super.page(page, query);

        PageInfoVO<CifCertsDTO> pageVo = new PageInfoVO<>();
        pageVo.setCurPage(iPage.getCurrent());
        pageVo.setTotal(iPage.getTotal());
        pageVo.setPageSize(iPage.getSize());
        pageVo.setPages(iPage.getPages());
        pageVo.setRequestMap(params);
        pageVo.setList(BeanConverter.convert(CifCertsDTO.class, iPage.getRecords()));
        pageVo.compute();

        return pageVo;

    }

    @KtfTrace("查询客户证书")
    // @Cacheable(value = InsCache.CifCerts, key =
    // "caches[0].name+'_'+#identifier+#type")
    @Override
    public CifCerts getCifCert(String identifier, String type) {
        LambdaQueryWrapper<CifCerts> queryWrapper = Wrappers.<CifCerts>lambdaQuery();
        queryWrapper.eq(CifCerts::getIdentifier, identifier).eq(CifCerts::getType, type).eq(CifCerts::getState,
            String.valueOf(KtfStatus.ENABLED.code));

        return decoderPriKey(super.getOne(queryWrapper));
    }

    @Override
    public Boolean removeByIdentifier(String identifier) {
        LambdaQueryWrapper<CifCerts> queryWrapper = Wrappers.<CifCerts>lambdaQuery();
        queryWrapper.eq(CifCerts::getIdentifier, identifier);
        return super.remove(queryWrapper);
    }

    /**
     * 加密私钥
     * 
     * @param entity
     * @return
     */
    private CifCerts encoderPriKey(CifCerts entity) {

        return entity;
    }

    /**
     * 加密私钥
     * 
     * @param entity
     * @return
     */
    private CifCerts decoderPriKey(CifCerts entity) {

        return entity;
    }

    @Override
    public boolean saveBatch(List<CifCertsDTO> list) {
        return this.saveBatch(BeanConverter.convert(CifCerts.class, list));
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
