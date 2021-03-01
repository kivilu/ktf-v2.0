package com.kivi.dashboard.sys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipOutputStream;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.shiro.ShiroUser;
// import com.kivi.dashboard.shiro.ShiroKit;
import com.kivi.dashboard.sys.dto.SysApi3rdpartyDTO;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfSwaggerProperties;
import com.kivi.framework.util.kit.ByteStringKit;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.vip.vjtools.vjkit.io.FileUtil;
import com.vip.vjtools.vjkit.number.NumberUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jodd.io.ZipUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 第三方API接口账号信息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2020-02-17
 */
@ConditionalOnProperty(
		prefix = KtfSwaggerProperties.PREFIX,
		value = "enable-sys-api",
		havingValue = "true",
		matchIfMissing = false)
@Api(value = "第三方API接口账号信息管理接口", tags = { "第三方API接口账号信息管理接口" })
@RestController
@RequestMapping("/sys/api3rdparty")
@Slf4j
public class SysApi3rdpartyController extends UpLoadController {

	private Map<String, List<Map<String, String>>> uploadFileUrls = new ConcurrentHashMap<String, List<Map<String, String>>>();

	@ApiOperation(value = "第三方API接口账号信息信息", notes = "第三方API接口账号信息信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys/api3rdparty/info")
	public ResultMap info(@PathVariable("id") String id) {
		SysApi3rdpartyDTO dto = sysApi3rdpartyService().getDTOById(NumberUtil.toLongObject(id, -1L));
		return ResultMap.ok().put("api", dto);
	}

	/**
	 * 新增
	 */
	@ApiOperation(value = "新增第三方API接口账号信息", notes = "新增第三方API接口账号信息")
	@RequiresPermissions("sys/api3rdparty/save")
	@PostMapping("/save")
	public ResultMap save(@Valid @RequestBody SysApi3rdpartyDTO dto) {
		try {
			List<Map<String, String>> uploadFileUrl = getUploadFile();
			if (uploadFileUrl != null) {
				Optional<Map<String, String>> selfOp = uploadFileUrl.stream()
						.filter(map -> map.containsKey(dto.getSelfCertName())).findAny();
				if (selfOp.isPresent()) {
					String	filePath	= realFilePath(selfOp.get().get("filePath"));
					byte[]	data		= FileUtil.toByteArray(new File(filePath));
					dto.setSelfCertData(ByteStringKit.toBase64(data));
				}

				Optional<Map<String, String>> peerOp = uploadFileUrl.stream()
						.filter(map -> map.containsKey(dto.getPeerCertName())).findAny();
				if (peerOp.isPresent()) {
					String	filePath	= realFilePath(peerOp.get().get("filePath"));
					byte[]	data		= FileUtil.toByteArray(new File(filePath));
					dto.setPeerCertData(ByteStringKit.toBase64(data));
				}
			}

			Boolean b = sysApi3rdpartyService().save(dto);
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
	@ApiOperation(value = "修改第三方API接口账号信息", notes = "修改第三方API接口账号信息")
	@RequiresPermissions("sys/api3rdparty/update")
	@PostMapping("/update")
	public ResultMap updateById(@RequestBody SysApi3rdpartyDTO dto) {
		try {
			List<Map<String, String>>		uploadFileUrl	= getUploadFile();
			Optional<Map<String, String>>	selfOp			= uploadFileUrl.stream()
					.filter(map -> map.containsKey(dto.getSelfCertName())).findAny();
			if (selfOp.isPresent()) {
				String	filePath	= realFilePath(selfOp.get().get("filePath"));
				byte[]	data		= FileUtil.toByteArray(new File(filePath));
				dto.setSelfCertData(ByteStringKit.toBase64(data));
			}

			Optional<Map<String, String>> peerOp = uploadFileUrl.stream()
					.filter(map -> map.containsKey(dto.getPeerCertName())).findAny();
			if (peerOp.isPresent()) {
				String	filePath	= realFilePath(peerOp.get().get("filePath"));
				byte[]	data		= FileUtil.toByteArray(new File(filePath));
				dto.setPeerCertData(ByteStringKit.toBase64(data));
			}

			Boolean b = sysApi3rdpartyService().updateById(dto);
			if (b) {
				resetUploadFile();
				return ResultMap.ok("编辑成功！");
			} else {
				return ResultMap.ok("编辑失败！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultMap.error("编辑失败，请联系管理员");
		}
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除第三方API接口账号信息", notes = "删除第三方API接口账号信息")
	@GetMapping("/delete/{id}")
	@RequiresPermissions("sys/api3rdparty/delete")
	public ResultMap delete(@PathVariable("id") Long id) {
		try {
			Boolean b = sysApi3rdpartyService().removeById(id);
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
	 * 批量删除
	 */
	@ApiOperation(value = "批量删除第三方API接口账号信息", notes = "批量删除第三方API接口账号信息")
	@PostMapping("/delete")
	@RequiresPermissions("sys/api3rdparty/delete")
	public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
		try {
			Boolean b = sysApi3rdpartyService().removeByIds(Arrays.asList(ids));
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
	@ApiOperation(value = "分页查询第三方API接口账号信息", notes = "分页查询第三方API接口账号信息")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "name",
					dataType = "string",
					value = "名称，可选，模糊匹配",
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
	@RequiresPermissions("sys/api3rdparty/page")
	@GetMapping("/page")
	public ResultMap page(@RequestParam(required = false) Map<String, Object> params) {
		PageInfoVO<SysApi3rdpartyDTO> page = sysApi3rdpartyService().page(params);

		/*
		 * page.getList().stream().forEach(dto -> {
		 * dto.setApiSecret(StrKit.mask(dto.getApiSecret())); });
		 */

		return ResultMap.ok().put("page", page);
	}

	/**
	 * 删除接口证书
	 */
	@ApiOperation(value = "删除第三方API接口证书", notes = "删除第三方API接口证书")
	@PostMapping("/deleteFileByName")
	@RequiresPermissions("sys/api3rdparty/deleteFileByName")
	public ResultMap delete(@RequestParam("name") String name) {
		try {
			List<Map<String, String>> uploadFileUrl = getUploadFile();
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
	 * 上传附件
	 */
	@PostMapping("/uploadFile")
	@RequiresPermissions("sys/api3rdparty/uploadFile")
	public Object uploadFile(@RequestParam("file") MultipartFile[] files) {
		try {
			List<Map<String, String>>	uploadFileUrl	= uploads(files, "api3rdparty");
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
	@RequiresPermissions("sys/api3rdparty/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {
		SysApi3rdpartyDTO	dto					= sysApi3rdpartyService().getDTOById(id);

		String				tempPath			= StringUtils.join("/tmp/download/api3rdparty/", DateTimeKit.today());
		String				tempSelfFileName	= StringUtils.join(id, dto.getSelfCertName());
		String				tempPeerFileName	= StringUtils.join(id, dto.getPeerCertName());
		String				zipFileName			= StringUtils.join(dto.getApiName(), "-", id, ".zip");
		String				zipFilePath			= StringUtils.join(tempPath, zipFileName);

		File				selfFile			= super.getFile(ByteStringKit.fromBase64(dto.getSelfCertData()),
				tempPath, tempSelfFileName);
		File				peerFile			= super.getFile(ByteStringKit.fromBase64(dto.getPeerCertData()),
				tempPath, tempPeerFileName);
		File				zipFile				= new File(zipFilePath);

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
			if (selfFile != null) {
				ZipUtil.addToZip(zos, selfFile, null, dto.getSelfCertName(), false);
				selfFile.delete();
			}
			if (peerFile != null) {
				ZipUtil.addToZip(zos, peerFile, null, dto.getPeerCertName(), false);
				peerFile.delete();
			}
		} catch (IOException e) {
			log.error("压缩文件异常", e);
			return null;
		}

		return super.download(zipFile, zipFileName);
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
