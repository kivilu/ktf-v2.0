package com.kivi.sms.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sms.sys.dto.SysSmsRecordDTO;
import com.kivi.sms.sys.entity.SysSmsRecord;

/**
 * <p>
 * 消息记录 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysSmsRecordService extends IService<SysSmsRecord> {

	/**
	 * 根据ID查询DTO
	 */
	SysSmsRecordDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysSmsRecordDTO sysSmsRecordDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysSmsRecordDTO sysSmsRecordDTO);

	/**
	 * 查询列表
	 */
	List<SysSmsRecordDTO> list(SysSmsRecordDTO sysSmsRecordDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysSmsRecordDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysSmsRecordDTO> listLike(SysSmsRecordDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysSmsRecordDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysSmsRecordDTO> page(Map<String, Object> params);
}
