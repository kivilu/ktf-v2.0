package com.kivi.dashboard.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysApi3rdpartyDTO;
import com.kivi.dashboard.sys.entity.SysApi3rdparty;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 小程序账号信息 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2020-02-17
 */
public interface SysApi3rdpartyService extends IService<SysApi3rdparty> {

	/**
	 * 根据appid查询
	 * 
	 * @param appid
	 * @return
	 */
	SysApi3rdparty getByWxAppId(String appid);

	/**
	 * 根据ID查询DTO
	 */
	SysApi3rdpartyDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysApi3rdpartyDTO dto);

	/**
	 * 修改
	 */
	Boolean updateById(SysApi3rdpartyDTO dto);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysApi3rdpartyDTO> page(Map<String, Object> params);
}
