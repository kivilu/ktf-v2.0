package com.kivi.sys.permission.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.db.page.PageParams;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.cache.annotation.KtfCacheEvict;
import com.kivi.framework.constant.KtfCache;
import com.kivi.framework.constant.KtfConstant;
import com.kivi.framework.constant.enums.CommonEnum.MenuType;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.constant.enums.KtfYesNo;
import com.kivi.framework.converter.BeanConverter;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.NumberKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.permission.dto.SysResourceDTO;
import com.kivi.sys.permission.entity.SysResource;
import com.kivi.sys.permission.mapper.SysResourceExMapper;
import com.kivi.sys.permission.mapper.SysResourceMapper;
import com.kivi.sys.permission.service.SysResourceService;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @since 2019-09-18
 */

@Primary
@Service
@Transactional(rollbackFor = Exception.class)
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    @Autowired
    private SysResourceExMapper sysResourceExMapper;

    /**
     * 根据ID查询资源
     */
    @Cacheable(value = KtfCache.SysResource, key = "caches[0].name+'.dto.'+#id", unless = "#result == null")
    @KtfTrace("根据ID查询资源")
    @Override
    public SysResourceDTO getDto(Long id) {
        SysResource entity = super.getById(id);
        SysResourceDTO dto = BeanConverter.convert(SysResourceDTO.class, entity);
        return dto;
    }

    /**
     * 新增资源
     */
    @KtfCacheEvict(cacheNames = {KtfCache.SysResource, KtfCache.SysUser})
    @KtfTrace("新增资源")
    @Override
    public Boolean save(SysResourceDTO dto) {
        SysResource entity = BeanConverter.convert(SysResource.class, dto);

        if (entity.getResourceType() < MenuType.BUTTON.getValue()) {
            Long id = sysResourceExMapper
                .getMaxId(CollectionKit.newHashMap(SysResource.RESOURCE_TYPE, entity.getResourceType())) + 1;
            if (entity.getResourceType() == MenuType.CATALOG.getValue() && id < 21) {
                entity.setId(id);
            } else {
                if (id < 100)
                    entity.setId(id);
            }
        }

        return super.save(entity);
    }

    /**
     * 修改
     */
    @KtfCacheEvict(cacheNames = {KtfCache.SysResource, KtfCache.SysUser})
    @KtfTrace("修改资源")
    @Override
    public Boolean updateById(SysResourceDTO dto) {
        SysResource entity = BeanConverter.convert(SysResource.class, dto);

        if ((entity.getResourceType() != null && entity.getResourceType() != MenuType.BUTTON.getValue())
            && (entity.getStatus() != null || entity.getHidden() != null)) {
            SysResource childEntity = new SysResource();
            childEntity.setStatus(entity.getStatus());
            childEntity.setHidden(entity.getHidden());

            Map<String, Object> params = new HashMap<>();
            params.put(SysResourceDTO.ID, entity.getId());
            params.put("hasSelf", true);
            params.put("recursion", true);
            List<SysResourceDTO> children = sysResourceExMapper.getChildren(params);
            List<Long> ids = children.stream().map(SysResourceDTO::getId).collect(Collectors.toList());
            if (!ids.isEmpty()) {
                LambdaUpdateWrapper<SysResource> updateWrapper = Wrappers.<SysResource>lambdaUpdate();
                updateWrapper.in(SysResource::getId, ids);
                super.update(childEntity, updateWrapper);
            }

        }

        return super.updateById(entity);
    }

    /**
     * 根据ID删除资源
     */
    @KtfCacheEvict(cacheNames = {KtfCache.SysResource, KtfCache.SysUser})
    @KtfTrace("删除资源")
    @Override
    public boolean removeById(Serializable id) {
        sysResourceExMapper.deleteWithChildren(NumberKit.toLong(id));
        return true;
    }

    /**
     * 根据IDs删除资源
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @KtfCacheEvict(cacheNames = {KtfCache.SysResource, KtfCache.SysUser})
    @KtfTrace("删除资源")
    @Override
    public boolean removeByIds(Collection<?> idList) {
        super.removeByIds(idList);
        sysResourceExMapper.deleteOrphan();

        return true;
    }

    @Cacheable(value = KtfCache.SysResource,
        key = "caches[0].name+'.list.'+#userId+T(org.apache.commons.lang3.StringUtils).join(#types)",
        condition = "#types!=null", unless = "#result == null")
    @KtfTrace("根据用户ID和资源类型查询")
    @Override
    public List<SysResourceDTO> selectResources(Long userId, MenuType... types) {

        // 系统管理员，拥有最高权限
        Map<String, Object> params = new HashMap<>();

        if (userId != null && KtfConstant.SUPER_ADMIN != userId.longValue())
            params.put("userId", userId);

        if (types != null && types.length > 0)
            params.put("resourceTypes",
                Arrays.asList(types).stream().map(MenuType::getValue).collect(Collectors.toList()));

        params.put(SysResourceDTO.STATUS, KtfStatus.ENABLED.code);
        params.put(SysResourceDTO.HIDDEN, KtfYesNo.NO.bool);

        List<SysResourceDTO> list = sysResourceExMapper.selectByUserId(params);

        return list;
    }

    @Override
    public List<SysResourceDTO> getChildren(Long id, Boolean recursion) {
        Map<String, Object> params = new HashMap<>();
        params.put(SysResourceDTO.ID, id);
        // params.put(SysResourceDTO.STATUS, KtfStatus.ENABLED.code);
        params.put("hasSelf", false);
        params.put("recursion", recursion);
        return sysResourceExMapper.getChildren(params);
    }

    @Override
    @KtfTrace("查询顶级菜单")
    public PageInfoVO<SysResourceDTO> tops(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        if (!params.containsKey(SysResourceDTO.RESOURCE_TYPE)) {
            params.put("resourceTypes", ArrayUtils.toArray(MenuType.CATALOG.getValue()));
        } else {
            params.put("resourceTypes", ArrayUtils.toArray(params.get(SysResourceDTO.RESOURCE_TYPE)));
        }

        // if (!params.containsKey(SysResourceDTO.STATUS))
        // params.put(SysResourceDTO.STATUS, KtfStatus.ENABLED.code);

        // if (!params.containsKey(SysResourceDTO.HIDDEN))
        // params.put(SysResourceDTO.HIDDEN, KtfYesNo.NO.code);

        PageParams<SysResourceDTO> pageParams = new PageParams<>(params);
        Page<SysResourceDTO> page = new Page<>(pageParams.getCurrPage(), pageParams.getPageSize());

        IPage<SysResourceDTO> iPage = sysResourceExMapper.selectResources(page, pageParams.getRequestMap());

        PageInfoVO<SysResourceDTO> pageVo = new PageInfoVO<>();
        pageVo.setCurPage(iPage.getCurrent());
        pageVo.setTotal(iPage.getTotal());
        pageVo.setPageSize(iPage.getSize());
        pageVo.setPages(iPage.getPages());
        pageVo.setRequestMap(params);

        List<SysResourceDTO> menus = iPage.getRecords();
        if (params.containsKey(SysResourceDTO.NAME)) {
            List<SysResourceDTO> matches = menus.stream()
                .filter(dto -> dto.getResourceType() == MenuType.CATALOG.getValue()).collect(Collectors.toList());
            Set<Long> ids = matches.stream().map(SysResourceDTO::getId).collect(Collectors.toSet());

            List<Long> nomatches = menus.stream().filter(dic -> dic.getResourceType() != MenuType.CATALOG.getValue())
                .map(SysResourceDTO::getParentId).collect(Collectors.toList());

            Map<String, Object> map = new HashMap<>();
            nomatches.stream().forEach(id -> {
                map.put(SysResourceDTO.ID, id);
                map.put(SysResourceDTO.RESOURCE_TYPE, MenuType.CATALOG.getValue());
                List<SysResourceDTO> parents = sysResourceExMapper.getParents(map);
                matches.addAll(parents.stream().filter(dto -> !ids.contains(dto.getId())).collect(Collectors.toList()));
                ids.addAll(parents.stream().map(SysResourceDTO::getId).collect(Collectors.toSet()));
            });
            pageVo.setList(matches);

        } else {
            pageVo.setList(menus);
        }

        pageVo.compute();

        return pageVo;

    }

    @Cacheable(value = KtfCache.SysResource,
        key = "caches[0].name+'.urls.'+T(org.apache.commons.lang3.StringUtils).join(#roleIds)",
        condition = "#roleIds!=null", unless = "#result == null")
    @Override
    public Set<String> getPermissions(List<Long> roleIds) {
        Map<String, Object> params = new HashMap<>();

        params.put(SysResourceDTO.RESOURCE_TYPE, MenuType.BUTTON.getValue());
        params.put(SysResourceDTO.STATUS, KtfStatus.ENABLED.code);
        params.put(SysResourceDTO.HIDDEN, KtfYesNo.NO.code);
        if (roleIds != null)
            params.put("roleIds", roleIds);

        List<String> urls = sysResourceExMapper.selectUrls(params);

        return urls.stream().filter(url -> StrKit.isNotBlank(url)).collect(Collectors.toSet());
    }

    @Override
    public List<SysResourceDTO> selectMenutList(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        return sysResourceExMapper.selectResourceList(params);
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
