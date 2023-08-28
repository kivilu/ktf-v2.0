package com.kivi.sys.sys.controller;

import java.io.File;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bouncycastle.cert.CertException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kivi.cif.dto.CifCertsDTO;
import com.kivi.cif.entity.CifCerts;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.crypto.enums.KtfCertUse;
import com.kivi.framework.crypto.util.CertUtil;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.shiro.ShiroKit;
import com.kivi.sys.shiro.ShiroUser;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.io.FileUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 客户证书 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-10-28
 */
@Api(tags = { "系统管理—证书管理" })
@ApiSupport(order = 34)
@RestController
@RequestMapping("/sys/cert")
@Slf4j
public class SysCertsController extends UpLoadController {

	private Map<String, List<Map<String, String>>> uploadFileUrls = new ConcurrentHashMap<String, List<Map<String, String>>>();

	@ApiOperation(value = "系统证书信息", notes = "系统证书信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys/cert/info")
	public ResultMap info(@PathVariable("id") Long id) {
		CifCertsDTO dto = cifCertsService().getDTOById(id);
		return ResultMap.ok().put("cert", dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增系统证书", notes = "新增系统证书")
	@RequiresPermissions("sys/cert/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody CifCertsDTO cifCertsDTO) {
		try {

			LocalDateTime	notBefore	= null, notAfter = null;
			String			dn			= null, algSign = null, serialNumber = null;

			List<CifCerts>	list		= new ArrayList<>();
			for (Map<String, String> uploadFileUrl : getUploadFile()) {
				CifCerts entity = getCertInfo(cifCertsDTO, uploadFileUrl);
				entity.setCertUse(KtfCertUse.SYS.code);
				entity.setState(KtfStatus.ENABLED.text);

				if (entity.getNotBefore() != null) {
					notBefore		= entity.getNotBefore();
					dn				= entity.getDn();
					algSign			= entity.getAlgSign();
					serialNumber	= entity.getSerialNumber();
					notAfter		= entity.getNotAfter();
				}

				list.add(entity);
			}

			for (CifCerts entity : list) {
				entity.setDn(dn);
				entity.setAlgSign(algSign);
				entity.setAlgEncryption(algSign);
				entity.setSerialNumber(serialNumber);
				entity.setNotBefore(notBefore);
				entity.setNotAfter(notAfter);
			}

			Boolean b = cifCertsService().saveBatch(list);
			if (b) {
				resetUploadFile();
				return ResultMap.ok("新增成功！");
			} else {
				return ResultMap.ok("新增失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("添加失败，请联系管理员");
		}
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改系统证书", notes = "修改系统证书")
	@RequiresPermissions("sys/cert/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody CifCertsDTO cifCertsDTO) {
		try {
			List<Map<String, String>>	fileList		= getUploadFile();
			Map<String, String>			uploadFileUrl	= ListUtil.isEmpty(fileList) ? null : fileList.get(0);
			CifCerts					entity			= getCertInfo(cifCertsDTO, uploadFileUrl);
			Boolean						b				= cifCertsService().updateById(entity);
			if (b) {
				return ResultMap.ok("编辑成功！");
			} else {
				return ResultMap.ok("编辑失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("编辑失败，请联系管理员");
		}
	}

	private CifCerts getCertInfo(CifCertsDTO cifCertsDTO, Map<String, String> uploadFileUrl)
			throws IOException, CertException {
		CifCerts entity = new CifCerts();
		entity.setIdentifier(cifCertsDTO.getIdentifier());
		entity.setType(cifCertsDTO.getType());
		entity.setPasswdBase64(cifCertsDTO.getPasswdBase64());

		if (uploadFileUrl == null)
			return entity;

		LocalDateTime	notBefore	= null, notAfter = null;
		String			dn			= null, algSign = null, serialNumber = null;

		String			fileName	= uploadFileUrl.get("fileName");
		String			filePath	= realFilePath(uploadFileUrl.get("filePath"));
		String			fileExt		= FileUtil.getFileExtension(filePath);
		byte[]			data		= FileUtil.toByteArray(new File(filePath));

		entity.setExt(fileExt);
		entity.setFileName(fileName);

		X509Certificate cert = CertUtil.readX509Cert(filePath,
				new String(ByteStringKit.fromBase64(cifCertsDTO.getPasswdBase64())));
		if (cert != null) {
			dn				= cert.getSubjectDN().getName();
			algSign			= cert.getSigAlgName();
			serialNumber	= cert.getSerialNumber().toString();
			notBefore		= DateTimeKit.toLocalDateTime(cert.getNotBefore());
			notAfter		= DateTimeKit.toLocalDateTime(cert.getNotAfter());
		}

		entity.setDn(dn);
		entity.setAlgSign(algSign);
		entity.setAlgEncryption(algSign);
		entity.setSerialNumber(serialNumber);
		entity.setNotBefore(notBefore);
		entity.setNotAfter(notAfter);

		entity.setDataBase64(ByteStringKit.toBase64(data));

		return entity;
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除系统证书", notes = "删除系统证书")
	@GetMapping("/delete/{id}")
	@RequiresPermissions("sys/cert/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = cifCertsService().removeById(id);
			if (b) {
				return ResultMap.ok("删除成功！");
			} else {
				return ResultMap.ok("删除失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("删除失败，请联系管理员");
		}
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除系统证书", notes = "删除系统证书")
	@GetMapping("/deleteByName")
	@RequiresPermissions("sys/cert/deleteByName")
	public ResultMap delete(@RequestParam("name") String name) {
		try {
			ShiroUser					user			= ShiroKit.getUser();
			List<Map<String, String>>	uploadFileUrl	= uploadFileUrls.get(user.getId().toString());
			if (uploadFileUrl != null) {
				int pos = -1;
				for (Map<String, String> map : uploadFileUrl) {
					pos++;
					String fileName = map.get("fileName");
					if (StrKit.equals(fileName, name)) {
						super.deleteFileFromLocal(map.get("filePath"));
						break;
					}
				}
				if (pos > -1) {
					uploadFileUrl.remove(pos);
				}

			}
			return ResultMap.ok("删除成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("删除失败，请联系管理员");
		}
	}

	/**
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除系统证书", notes = "批量删除系统证书")
	@PostMapping("/delete")
	@RequiresPermissions("sys/cert/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = cifCertsService().removeByIds(Arrays.asList(ids));
			if (b) {
				return ResultMap.ok("删除成功！");
			} else {
				return ResultMap.ok("删除失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("批量删除失败，请联系管理员");
		}
	}

	/**
	 * 分页查询
	 */
	@ApiOperation(value = "分页查询系统证书", notes = "分页查询系统证书")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "identifier",
					dataType = "string",
					value = "证书标识，可选，模糊匹配",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "serialNumber",
					dataType = "string",
					value = "证书序列号，可选，模糊匹配",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "page",
					dataType = "integer",
					value = "当前页，可选，默认值：1",
					paramType = "query",
					allowEmptyValue = true),
			@ApiImplicitParam(
					name = "limit",
					dataType = "integer",
					value = "每页大小，可选，默认值：10",
					paramType = "query",
					allowEmptyValue = true) })
	@RequiresPermissions("sys/cert/page")
	@GetMapping("/page")
	public ResultMap page(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
		params.put(CifCerts.DB_CERT_USE, KtfCertUse.SYS.code);
		PageInfoVO<CifCertsDTO> page = cifCertsService().page(params);

		return ResultMap.ok().put("page", page);
	}

	/**
	 * 上传附件
	 */
	@PostMapping("/uploadFile")
	@RequiresPermissions("sys/cert/uploadFile")
	public Object uploadFile(@RequestParam("file") MultipartFile[] files) {
		try {
			List<Map<String, String>>	uploadFileUrl	= uploads(files, "cert");
			String						filePath		= "";
			if (!uploadFileUrl.isEmpty() && uploadFileUrl.size() > 0) {
				for (Map<String, String> map : uploadFileUrl) {
					filePath = map.get("filePath");
				}
				setUploadFile(uploadFileUrl);
				return ResultMap.ok().put("filePath", filePath);
			} else {
				return ResultMap.ok().put("filePath", "");
			}
		} catch (Exception e) {
			log.error("上传失败", e);
			return ResultMap.error("上传失败，请联系管理员");
		}
	}

	@GetMapping("/download/{id}")
	@RequiresPermissions("sys/cert/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {
		CifCertsDTO	dto				= cifCertsService().getDTOById(id);

		String		tempPath		= StringUtils.join("/tmp/download/cert/", DateTimeKit.today());
		String		tempFileName	= StringUtils.join(id, ".tmp");

		File		certFile		= super.getFile(ByteStringKit.fromBase64(dto.getDataBase64()), tempPath,
				tempFileName);

		return super.download(certFile, dto.getFileName());
	}

	private void setUploadFile(List<Map<String, String>> uploadFileUrl) {
		ShiroUser	user	= ShiroKit.getUser();
		Object		o		= uploadFileUrls.get(user.getId().toString());
		if (o == null) {
			uploadFileUrls.put(user.getId().toString(), new ArrayList<>());
		}
		uploadFileUrls.get(user.getId().toString()).addAll(uploadFileUrl);
	}

	private List<Map<String, String>> getUploadFile() {
		ShiroUser user = ShiroKit.getUser();
		return uploadFileUrls.get(user.getId().toString());
	}

	private void resetUploadFile() {
		ShiroUser					user			= ShiroKit.getUser();
		List<Map<String, String>>	uploadFileUrl	= uploadFileUrls.remove(user.getId().toString());
		if (uploadFileUrl != null) {
			uploadFileUrl.stream().forEach(map -> {
				super.deleteFileFromLocal(map.get("filePath"));
			});
		}
	}

}
