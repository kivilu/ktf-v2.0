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
public interface SysDicService extends IService<SysDic> {

	/**
	 * 根据ID查询DTO
	 */
	SysDicDTO getDto(Long id);

	/**
	 * 根据varCode和上级varCode查询
	 * 
	 * @param varCode
	 * @param pVarCode
	 * @return
	 */
	SysDicDTO getDto(String varCode, String pVarCode);

	/**
	 * 根据变量代码和上级代码获取列表
	 * 
	 * @param varCode  变量代码
	 * @param pVarCode 上级变量代码
	 * @return
	 */
	List<Object> getValues(String varCode, String... pVarCodes);

	/**
	 * 根据变量代码和上级代码获取列表
	 * 
	 * @param varCode  变量代码
	 * @param pVarCode 上级变量代码
	 * @return
	 */
	Map<String, Object> getValuesMap(String varCode, String... pVarCodes);

	/**
	 * 查询系统运行配置
	 * 
	 * @param varCode
	 * @return
	 */
	Map<String, Object> getSettings(String varCode);

	/**
	 * 查找子数据
	 * 
	 * @param pVarCode
	 * @param recursion
	 * @return
	 */
	List<SysDicDTO> getChildren(String pVarCode, Boolean recursion);

	/**
	 * 分页查询
	 */
	PageInfoVO<SysDicDTO> page(Map<String, Object> params);

	/**
	 * 递归查询下属字段数据
	 * 
	 * @param id
	 * @return
	 */
	List<SysDicDTO> getChildren(Long id, Boolean recursion);

	/**
	 * 根据Pid查询
	 */
	List<SysDicDTO> list(Long pid);

	/**
	 * 根据Pid字典树一个级别的数据
	 */
	List<SysDicDTO> treeQuery(Long pid);

	/**
	 * 查询父级
	 * 
	 * @param id
	 * @return
	 */
	List<SysDicDTO> getParents(Long id);

	/**
	 * 新增
	 */
	Boolean save(SysDicDTO dto);

	/**
	 * 修改
	 */
	Boolean updateById(SysDicDTO dto);

	/**
	 * 删除自身以及下属
	 * 
	 * @param pid
	 * @return
	 */
	Boolean removeWithChildren(Long id);

}
