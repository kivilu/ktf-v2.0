package com.kivi.dashboard.sys.dubbo.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kivi.dashboard.sys.dto.SysUserEnterpriseDTO;
import com.kivi.dashboard.sys.entity.SysUserEnterprise;
import com.kivi.dashboard.sys.mapper.SysUserEnterpriseMapper;
import com.kivi.dashboard.sys.service.ISysUserEnterpriseService;
import com.kivi.framework.annotation.KtfTrace;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.vo.page.PageInfoVO;

/**
 * <p>
 * 监管用户与企业关联 服务实现类
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */

@Service(version = KtfDashboardProperties.DUBBO_VERSION)
public class SysUserEnterpriseDubboServiceImpl extends ServiceImpl<SysUserEnterpriseMapper, SysUserEnterprise>
		implements ISysUserEnterpriseService {

	@Autowired
	private ISysUserEnterpriseService iSysUserEnterpriseService;

	/**
	 * 根据ID查询监管用户与企业关联
	 */
	@KtfTrace("根据ID查询监管用户与企业关联")
	@Override
	public SysUserEnterpriseDTO getDTOById(Long id) {
		return iSysUserEnterpriseService.getDTOById(id);
	}

	/**
	 * 新增监管用户与企业关联
	 */
	@KtfTrace("新增监管用户与企业关联")
	@Override
	public Boolean save(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		return iSysUserEnterpriseService.save(sysUserEnterpriseDTO);
	}

	/**
	 * 修改
	 */
	@KtfTrace("修改监管用户与企业关联")
	@Override
	public Boolean updateById(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		return iSysUserEnterpriseService.updateById(sysUserEnterpriseDTO);
	}

	/**
	 * 查询列表
	 */
	@KtfTrace("查询列表监管用户与企业关联")
	@Override
	public List<SysUserEnterpriseDTO> list(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		return iSysUserEnterpriseService.list(sysUserEnterpriseDTO);
	}

	/**
	 * 指定列查询列表
	 */
	@KtfTrace("指定列查询列表监管用户与企业关联")
	@Override
	public List<SysUserEnterpriseDTO> list(Map<String, Object> params, String... columns) {
		return iSysUserEnterpriseService.list(params, columns);
	}

	/**
	 * 模糊查询
	 */
	@KtfTrace("模糊查询监管用户与企业关联")
	@Override
	public List<SysUserEnterpriseDTO> listLike(SysUserEnterpriseDTO sysUserEnterpriseDTO) {
		return iSysUserEnterpriseService.listLike(sysUserEnterpriseDTO);
	}

	/**
	 * 指定列模糊查询
	 */
	@Override
	public List<SysUserEnterpriseDTO> listLike(Map<String, Object> params, String... columns) {
		return iSysUserEnterpriseService.listLike(params, columns);
	}

	/**
	 * 分页查询
	 */
	@Override
	@KtfTrace("分页查询监管用户与企业关联")
	public PageInfoVO<SysUserEnterpriseDTO> page(Map<String, Object> params) {
		return iSysUserEnterpriseService.page(params);

	}

	@KtfTrace("根据ID查找所属企业ID")
	@Override
	public List<Long> selectEnterpriseIdByUserId(Long userId) {
		return iSysUserEnterpriseService.selectEnterpriseIdByUserId(userId);
	}

	@KtfTrace("保存或修改用户所监管的企业关系")
	@Override
	public void saveOrUpdateUserEnterprise(Long userId, List<Long> enterpriseIdList) {
		iSysUserEnterpriseService.saveOrUpdateUserEnterprise(userId, enterpriseIdList);

	}

	@KtfTrace("根据用户批量删除")
	@Override
	public Boolean deleteBatchByUserIds(Long[] userIds) {

		return iSysUserEnterpriseService.deleteBatchByUserIds(userIds);
	}

	@KtfTrace("根据企业批量删除")
	@Override
	public Boolean deleteBatchByEnterpriseIds(Long[] enterpriseIds) {

		return iSysUserEnterpriseService.deleteBatchByEnterpriseIds(enterpriseIds);
	}

}
