package com.kivi.dashboard.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kivi.dashboard.sys.dto.SysDicDTO;
import com.kivi.dashboard.sys.entity.SysDic;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface ISysDicService extends IService<SysDic> {

	/**
	 * 根据Code获取变量
	 * 
	 * @param code
	 * @return
	 */
	SysDic getByVarCode(String varCode, String pVarCode);

	/**
	 * 根据名称和上级名称获取code
	 * 
	 * @param varName
	 * @param pVarName
	 * @return
	 */
	SysDic getByVarName(String varName, String pVarName);
	
	/**
	 * 根据父级查询孩子数据
	 * @return
	 */
	List<SysDicDTO> getChildren(Map<String, Object> params);

	/**
	 * 根据代码查询子变量
	 * 
	 * @param pCode 上级变量代码
	 * @return
	 */
	List<SysDicDTO> listByVarCode(String pCode);

	/**
	 * 根据ID查询DTO
	 */
	SysDicDTO getDTOById(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysDicDTO sysDicDTO);

	/**
	 * 修改
	 */
	Boolean updateById(SysDicDTO sysDicDTO);

	/**
	 * 查询列表
	 */
	List<SysDicDTO> list(SysDicDTO sysDicDTO);

	/**
	 * 指定列查询列表
	 */
	List<SysDicDTO> list(Map<String, Object> params, String... columns);

	/**
	 * 模糊查询
	 */
	List<SysDicDTO> listLike(SysDicDTO applicationDTO);

	/**
	 * 指定列模糊查询
	 */
	List<SysDicDTO> listLike(Map<String, Object> params, String... columns);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysDicDTO> page(Map<String, Object> params);

	/**
	 * 删除自身以及下属
	 * 
	 * @param pid
	 * @return
	 */
	Boolean deleteWithChild(Long id);
}
