package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysSmsRecordDTO;
import com.kivi.dashboard.sys.entity.SysSmsRecord;
import com.kivi.dashboard.sys.entity.SysSmsRecordEx;
import com.kivi.dashboard.sys.mapper.SysSmsRecordMapper;
import com.kivi.dashboard.sys.service.ISysSmsRecordService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 消息记录 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-24
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysSmsRecordDubboServiceImpl extends ServiceImpl<SysSmsRecordMapper, SysSmsRecord>
		implements ISysSmsRecordService {

	@Autowired
	private ISysSmsRecordService sysSmsRecordService;

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
	public Integer findUnreadMessagesCount(Long userId) {
		return sysSmsRecordService.findUnreadMessagesCount(userId);
	}

	@Override
	public List<SysSmsRecordEx> findRecent5Messages(Long userId) {
		return sysSmsRecordService.findRecent5Messages(userId);
	}

	@Override
	public void updateMessageStatus(Long[] ids) {
		sysSmsRecordService.updateMessageStatus(ids);
	}

}
