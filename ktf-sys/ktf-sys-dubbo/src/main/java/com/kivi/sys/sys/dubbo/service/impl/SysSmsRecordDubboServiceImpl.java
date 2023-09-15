package com.kivi.sys.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfSysProperties;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.sys.dto.SysSmsRecordDTO;
import com.kivi.sys.sys.dto.SysSmsRecordExDTO;
import com.kivi.sys.sys.service.SysSmsRecordService;

/**
 * <p>
 * 消息记录 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */

@DubboService(version = KtfSysProperties.DUBBO_VERSION)
public class SysSmsRecordDubboServiceImpl implements SysSmsRecordService {

    @Autowired
    private SysSmsRecordService sysSmsRecordService;

    /**
     * 根据ID查询消息记录
     */
    @KtfTrace("根据ID查询消息记录")
    @Override
    public SysSmsRecordDTO getDTOById(Long id) {
        return sysSmsRecordService.getDTOById(id);
    }

    /**
     * 新增消息记录
     */
    @KtfTrace("新增消息记录")
    @Override
    public Boolean save(SysSmsRecordDTO sysSmsRecordDTO) {
        return sysSmsRecordService.save(sysSmsRecordDTO);
    }

    /**
     * 修改
     */
    @KtfTrace("修改消息记录")
    @Override
    public Boolean updateById(SysSmsRecordDTO sysSmsRecordDTO) {
        return sysSmsRecordService.updateById(sysSmsRecordDTO);
    }

    /**
     * 查询列表
     */
    @KtfTrace("查询列表消息记录")
    @Override
    public List<SysSmsRecordDTO> list(SysSmsRecordDTO sysSmsRecordDTO) {
        return sysSmsRecordService.list(sysSmsRecordDTO);
    }

    /**
     * 指定列查询列表
     */
    @KtfTrace("指定列查询列表消息记录")
    @Override
    public List<SysSmsRecordDTO> list(Map<String, Object> params, String... columns) {
        return sysSmsRecordService.list(params, columns);
    }

    /**
     * 模糊查询
     */
    @KtfTrace("模糊查询消息记录")
    @Override
    public List<SysSmsRecordDTO> listLike(SysSmsRecordDTO sysSmsRecordDTO) {
        return sysSmsRecordService.listLike(sysSmsRecordDTO);
    }

    /**
     * 指定列模糊查询
     */
    @Override
    public List<SysSmsRecordDTO> listLike(Map<String, Object> params, String... columns) {
        return sysSmsRecordService.listLike(params, columns);
    }

    /**
     * 分页查询
     */
    @Override
    @KtfTrace("分页查询消息记录")
    public PageInfoVO<SysSmsRecordDTO> page(Map<String, Object> params) {
        return sysSmsRecordService.page(params);
    }

    @Override
    public Long findUnreadMessagesCount(Long userId) {
        return sysSmsRecordService.findUnreadMessagesCount(userId);
    }

    @Override
    public List<SysSmsRecordExDTO> findRecent5Messages(Long userId) {
        return sysSmsRecordService.findRecent5Messages(userId);
    }

    @Override
    public void updateMessageStatus(Long[] ids) {
        sysSmsRecordService.updateMessageStatus(ids);
    }

}
