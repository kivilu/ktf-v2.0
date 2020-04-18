package com.kivi.dashboard.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kivi.dashboard.sys.entity.SysDic;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
public interface SysDicExMapper {

	/**
	 * 根据变量和上级变量代码获取变量名称
	 * 
	 * @param varCode
	 * @param pVarCode
	 * @return
	 */
	SysDic getByVarCode(@Param("varCode") String varCode, @Param("pVarCode") String pVarCode);

	/**
	 * 根据变量和上级变量名称获取变量代码
	 * 
	 * @param varName
	 * @param parentName
	 * @return
	 */
	SysDic getByVarName(@Param("varName") String varName, @Param("pVarName") String pVarName);

	/**
	 * 根据上级变量代码查询变量代码列表
	 * 
	 * @param pVarCode 上级代码
	 * @param ppId     上上级变量的ID
	 * @return
	 */
	List<String> listVarCode(@Param("pVarCode") String pVarCode, @Param("ppId") Long ppId);
}
