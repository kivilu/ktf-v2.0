package com.kivi.sys.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.constant.KtfCache;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysDicDTO;
import com.kivi.sys.sys.entity.SysDic;
import com.kivi.sys.sys.mapper.SysDicExMapper;
import com.kivi.sys.sys.mapper.SysDicMapper;
import com.kivi.sys.sys.service.ISysDicService;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDicServiceImpl extends ServiceImpl<SysDicMapper, SysDic> implements ISysDicService {

    private static final Long ROOT_ID = 0L;

    @Autowired
    private SysDicExMapper sysDicExMapper;

    /**
     * 根据ID查询数据字典
     */
    @Cacheable(value = KtfCache.SysDic, key = "caches[0].name+'.dto.'+#id", unless = "#result == null")
    @KtfTrace("根据ID查询数据字典")
    @Override
    public SysDicDTO getDto(Long id) {
        SysDic entity = super.getById(id);
        SysDicDTO dto = BeanConverter.convert(SysDicDTO.class, entity);
        return dto;
    }

    @Cacheable(value = KtfCache.SysDic, key = "caches[0].name+'.'+#varCode+#pVarCode", unless = "#result == null")
    @Override
    public SysDicDTO getDto(String varCode, String pVarCode) {
        SysDicDTO dto = sysDicExMapper.getDto(varCode, pVarCode);
        return dto;
    }

    /**
     * 新增数据字典
     */

    @KtfTrace("新增数据字典")
    @Override
    public Boolean save(SysDicDTO sysDicDTO) {
        SysDic entity = BeanConverter.convert(SysDic.class, sysDicDTO);

        Long id = sysDicExMapper.getMaxId(CollectionKit.newHashMap(SysDic.PARENT_ID, entity.getParentId())) + 1;
        if (entity.getParentId() == 0 && id < 10) {
            entity.setId(id);
        } else if (entity.getParentId() > 0 && id < 10 * (entity.getParentId() + 1)) {
            entity.setId(id);
        }

        return super.save(entity);
    }

    /**
     * 修改
     */
    @KtfCacheEvict(cacheNames = KtfCache.SysDic)
    @KtfTrace("修改数据字典")
    @Override
    public Boolean updateById(SysDicDTO sysDicDTO) {
        SysDic entity = BeanConverter.convert(SysDic.class, sysDicDTO);
        return super.updateById(entity);
    }

    @KtfCacheEvict(cacheNames = KtfCache.SysDic)
    @Override
    public Boolean removeWithChildren(Long id) {
        Boolean result = super.removeById(id);

        LambdaQueryWrapper<SysDic> wrapper = Wrappers.<SysDic>lambdaQuery();
        wrapper.eq(SysDic::getParentId, id);
        result = super.remove(wrapper);

        return result;
    }

    @KtfTrace("分页查询数据字典")
    @Override
    public PageInfoVO<SysDicDTO> page(Map<String, Object> params) {
        if (params == null)
            params = new HashMap<>();

        if (!params.containsKey(SysDicDTO.STATUS))
            params.put(SysDicDTO.STATUS, KtfStatus.ENABLED.code);

        if (!ObjectKit.isNotEmpty(params.get("keyword")))
            params.put(SysDicDTO.PARENT_ID, ROOT_ID);

        PageParams<SysDicDTO> pageParams = new PageParams<>(params);
        Page<SysDicDTO> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());
        IPage<SysDicDTO> pages = sysDicExMapper.selectDTO(page, params);

        PageInfoVO<SysDicDTO> pageVo = new PageInfoVO<>();
        pageVo.setCurPage(pages.getCurrent());
        pageVo.setTotal(pages.getTotal());
        pageVo.setPageSize(pages.getSize());
        pageVo.setPages(pages.getPages());
        pageVo.setRequestMap(params);

        List<SysDicDTO> dicList = pages.getRecords();
        if (!params.containsKey("keyword")) {
            List<SysDicDTO> matches = dicList.stream()
                .filter(dic -> dic.getParentId().longValue() == ROOT_ID.longValue()).collect(Collectors.toList());
            Set<Long> ids = matches.stream().map(SysDicDTO::getId).collect(Collectors.toSet());

            List<Long> nomatches = dicList.stream().filter(dic -> dic.getParentId().longValue() != ROOT_ID.longValue())
                .map(SysDicDTO::getParentId).collect(Collectors.toList());

            Map<String, Object> map = new HashMap<>();
            nomatches.stream().forEach(id -> {
                map.put(SysDicDTO.ID, id);
                map.put(SysDicDTO.PARENT_ID, ROOT_ID);
                List<SysDicDTO> parents = sysDicExMapper.getParents(map);
                matches.addAll(parents.stream().filter(dto -> !ids.contains(dto.getId())).collect(Collectors.toList()));
                ids.addAll(parents.stream().map(SysDicDTO::getId).collect(Collectors.toSet()));
            });
            pageVo.setList(matches);
        } else {
            pageVo.setList(dicList);
        }

        pageVo.compute();

        return pageVo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysDicDTO> list(Long pid) {
        LambdaQueryWrapper<SysDic> query = new LambdaQueryWrapper<SysDic>();

        query.select(SysDic::getId, SysDic::getParentId, SysDic::getVarCode, SysDic::getVarName, SysDic::getVarValue,
            SysDic::getType, SysDic::getStatus);

        query.eq(SysDic::getParentId, pid).eq(SysDic::getStatus, KtfStatus.ENABLED.code);

        List<SysDic> list = super.list(query);
        return BeanConverter.convert(SysDicDTO.class, list);
    }

    // @Cacheable(value = KtfCache.SysDic, key =
    // "caches[0].name+'.Settings.'+#varCode", unless = "#result == null")
    @Override
    public Map<String, Object> getSettings(String varCode) {
        Map<String, Object> params = new HashMap<>();
        params.put(SysDicDTO.VAR_CODE, varCode);
        params.put(SysDicDTO.STATUS, KtfStatus.ENABLED.code);
        params.put("hasSelf", true);
        List<SysDicDTO> list = sysDicExMapper.getChildren(params);
        Map<String, Object> result = new HashMap<>();
        list.stream().forEach(dic -> {
            if ("json".equals(dic.getType())) {
                if (dic.getIsLeaf() && list.size() > 1)
                    result.put(StrKit.underlineToCamel(dic.getVarCode()), JSON.parse(dic.getVarValue()));
                else {
                    result.putAll(JSON.parseObject(dic.getVarValue()));
                }
            } else {
                result.put(StrKit.underlineToCamel(dic.getVarCode()), dic.getVarValue());
            }

        });

        return result;
    }

    // @Cacheable(
    // value = KtfCache.SysDic,
    // key = "caches[0].name+'.'+#id+'.'+#recursion",
    // unless = "#result == null || #result.isEmpty()")
    @Override
    public List<SysDicDTO> getChildren(Long id, Boolean recursion) {
        Map<String, Object> params = new HashMap<>();
        params.put(SysDicDTO.ID, id);
        params.put(SysDicDTO.STATUS, KtfStatus.ENABLED.code);
        params.put("hasSelf", false);
        params.put("recursion", recursion);
        List<SysDicDTO> list = sysDicExMapper.getChildren(params);

        return list;
    }

    // @Cacheable(
    // value = KtfCache.SysDic,
    // key = "caches[0].name+'.'+#pVarCode+'.'+#recursion",
    // unless = "#result == null || #result.isEmpty()")
    @Override
    public List<SysDicDTO> getChildren(String pVarCode, Boolean recursion) {
        Map<String, Object> params = new HashMap<>();
        params.put(SysDicDTO.VAR_CODE, pVarCode);
        params.put(SysDicDTO.STATUS, KtfStatus.ENABLED.code);
        params.put("hasSelf", false);
        params.put("recursion", recursion);
        List<SysDicDTO> list = sysDicExMapper.getChildren(params);

        return list;
    }

    // @Cacheable(value = KtfCache.SysDic, key =
    // "caches[0].name+'.'+#varCode+#pVarCode", unless = "#result == null")
    @Override
    public Map<String, Object> getValuesMap(String varCode, String... pVarCodes) {
        List<Map<String, Object>> list = sysDicExMapper.getKvMap(varCode, pVarCodes);

        return list.stream()
            .collect(Collectors.toMap(map -> (String)map.get("key"), map -> map.get("value"), (key1, key2) -> key2));
    }

    @Override
    public List<Object> getValues(String varCode, String... pVarCodes) {
        List<Map<String, Object>> list = sysDicExMapper.getKvMap(varCode, pVarCodes);
        return list.stream().map(map -> map.get("value")).collect(Collectors.toList());
    }

    @Override
    public List<SysDicDTO> treeQuery(Long pid) {
        if (pid == null)
            pid = ROOT_ID;

        Map<String, Object> params = new HashMap<>();
        params.put(SysDicDTO.STATUS, KtfStatus.ENABLED.code);
        params.put(SysDicDTO.PARENT_ID, pid);

        return sysDicExMapper.treeQuery(params);
    }

    @Override
    public List<SysDicDTO> getParents(Long id) {

        Map<String, Object> params = new HashMap<>();
        params.put(SysDicDTO.STATUS, KtfStatus.ENABLED.code);
        params.put(SysDicDTO.ID, id);

        return sysDicExMapper.getParents(params);
    }

    @Override
    public Boolean removeById(Long id) {
        return this.removeById(id);
    }

    @Override
    public Boolean removeByParentId(Long pid) {
        QueryWrapper<SysDic> wrapper = new QueryWrapper<>();
        wrapper.eq(SysDic.DB_PARENT_ID, pid);
        return this.remove(wrapper);
    }

    @Override
    public Boolean removeByIds(List<Long> ids) {
        return this.removeByIds(ids);
    }
}
