package com.kivi.dashboard.sys.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.kivi.dashboard.base.DashboardController;
import com.kivi.framework.exception.KtfException;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.properties.KtfDashboardProperties;
import com.kivi.framework.properties.KtfDashboardProperties.Upload;
import com.kivi.framework.util.QRCodeUtils;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.web.util.kit.HttpKit;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.exception.FdfsUnsupportStorePathException;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.vip.vjtools.vjkit.io.FileUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Descriptin 文件上传下载
 */
@Slf4j
public abstract class UpLoadController extends DashboardController {

	@Autowired
	private KtfDashboardProperties	ktfDashboardProperties;

	@Autowired
	private FastFileStorageClient	fastFileStorageClient;

	/**
	 * 下载文件
	 *
	 * @param file 文件
	 */
	protected ResponseEntity<Resource> download(File file) {
		String fileName = file.getName();
		return download(file, fileName);
	}

	/**
	 * 下载
	 *
	 * @param file     文件
	 * @param fileName 生成的文件名
	 * @return {ResponseEntity}
	 */
	protected ResponseEntity<Resource> download(File file, String fileName) {
		Resource			resource	= new FileSystemResource(file);

		HttpServletRequest	request		= HttpKit.getRequest();
		String				header		= request.getHeader("SysUser-Agent");
		// 避免空指针
		header = header == null ? "" : header.toUpperCase();
		HttpStatus status;
		if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
			fileName	= HttpKit.encodeURL(fileName, "UTF-8");
			status		= HttpStatus.OK;
		} else {
			fileName	= new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
			status		= HttpStatus.CREATED;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<Resource>(resource, headers, status);
	}

	/**
	 * 单个文件上传
	 *
	 * @param file 前台传过来文件路径
	 * @param dir  保存文件的相对路径,相对路径必须upload开头，例如upload/test
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> upload(MultipartFile file, String dir) {
		Map<String, String>	params		= Maps.newHashMap();
		String				resultPath	= "";
		try {
			Upload	uploacConfig	= this.uploandConfig();
			String	savePath		= "";
			if (StringUtils.isBlank(dir)) {
				dir = "default";
			}
			savePath = StringUtils.joinWith(File.separator, uploacConfig.getFilePrefix(), "upload", dir,
					DateTimeKit.today(), "");

			// 保存文件
			String	realFileName	= file.getOriginalFilename();
			Long	fileName		= System.currentTimeMillis();
			String	fileExtName		= "." + FileUtil.getFileExtension(realFileName);
			String	targetFilePath	= StringUtils.join(new File(savePath).getAbsolutePath(), File.separator, fileName,
					fileExtName);

			File	targetFile		= new File(targetFilePath);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}

			FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);// 复制临时文件到指定目录下
			if (StringUtils.isNotBlank(uploacConfig.getFileServer())) {
				resultPath = StringUtils.joinWith("/", uploacConfig.getFileServer(), "static/upload", dir,
						DateTimeKit.today(), fileName) + fileExtName;
			} else {
				resultPath = StringUtils.joinWith("/", uploacConfig.getFileServer(), "static/upload", dir,
						DateTimeKit.today(), fileName) + fileExtName;
			}

			params.put("fileName", realFileName);
			params.put("filePath", resultPath);

		} catch (Exception e) {
			log.error("单个文件上传异常", e);
		}
		return params;
	}

	/**
	 * 多文件上传
	 *
	 * @param multipartFiles 文件
	 * @param dir            保存文件的文件夹名称,,相对路径必须upload开头，例如upload/test
	 * @return
	 */
	public List<Map<String, String>> uploads(MultipartFile[] multipartFiles, String dir) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			Upload uploacConfig = this.uploandConfig();
			// 判断file数组不能为空并且长度大于0
			if (multipartFiles != null && multipartFiles.length > 0) {
				// 循环获取file数组中得文件
				for (MultipartFile file : multipartFiles) {
					String savePath = "";
					if (StringUtils.isBlank(dir)) {
						dir = "default";
					}
					savePath = StringUtils.joinWith(File.separator, uploacConfig.getFilePrefix(), "upload", dir,
							DateTimeKit.today(), "");

					// 保存文件
					String	realFileName	= file.getOriginalFilename();
					String	fileExtName		= "." + FileUtil.getFileExtension(realFileName);
					Long	fileName		= System.currentTimeMillis();
					String	fullPath		= StringUtils.join(new File(savePath).getAbsolutePath(), File.separator,
							fileName, fileExtName);
					File	targetFile		= new File(fullPath);
					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();
					}

					FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);// 复制临时文件到指定目录下
					if (StringUtils.isNotBlank(uploacConfig.getFileServer())) {
						String				resultPath	= StringUtils.joinWith("/", uploacConfig.getFileServer(),
								"static/upload", dir, DateTimeKit.today(), fileName) + fileExtName;
						Map<String, String>	params		= Maps.newHashMap();
						params.put("fileName", realFileName);
						params.put("filePath", resultPath);
						list.add(params);
					} else {
						String				resultPath	= StringUtils.joinWith("/", uploacConfig.getFileServer(),
								"static/upload", dir, DateTimeKit.today(), fileName) + fileExtName;
						Map<String, String>	params		= Maps.newHashMap();
						params.put("fileName", realFileName);
						params.put("filePath", resultPath);
						list.add(params);
					}
				}
			}
		} catch (Exception e) {
			log.error("多文件上传异常", e);
		}
		return list;
	}

	protected String realFilePath(String fileUrl) {
		if (StringUtils.isBlank(fileUrl)) {
			return null;
		}
		Upload	uploacConfig	= this.uploandConfig();
		String	temp			= fileUrl.substring(fileUrl.indexOf("/static") + 7);

		return StringUtils.joinWith(File.separator, uploacConfig.getFilePrefix(), temp);
	}

	/**
	 * 从本地删除文件
	 *
	 * @param fileUrl http路径
	 * @return
	 */
	public ResultMap deleteFileFromLocal(String fileUrl) {
		if (StringUtils.isBlank(fileUrl)) {
			return ResultMap.error(1, "文件删除失败");
		}
		Upload	uploacConfig	= this.uploandConfig();
		String	temp			= fileUrl.substring(fileUrl.indexOf("/static") + 7);

		String	tempFilePath	= StringUtils.joinWith(File.separator, uploacConfig.getFilePrefix(), temp);
		log.debug(tempFilePath);

		File file = new File(tempFilePath);
		if (file.exists() && file.isFile()) {
			file.delete();
			return ResultMap.ok("文件删除成功");
		} else {
			return ResultMap.error("文件删除失败");
		}
	}

	/**
	 * 上传文件到FastDFS
	 *
	 * @param localFilePath
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, String> uploadToFastDFS(String localFilePath) {
		Map<String, String>	params	= Maps.newHashMap();
		String				path	= "";
		try {
			Upload	uploacConfig	= this.uploandConfig();

			byte[]	bytes			= FileUtil.toByteArray(new File(localFilePath));
			String	fileName		= "";
			if ((localFilePath != null) && (localFilePath.length() > 0)) {
				int dot = localFilePath.lastIndexOf(File.separator);
				if ((dot > -1) && (dot < (localFilePath.length() - 1))) {
					fileName = localFilePath.substring(dot + 1, localFilePath.length());
				}
			}
			StorePath storePath = fastFileStorageClient.uploadFile(bytes, FilenameUtils.getExtension(fileName));
			log.info("上传文件路径：{}\r\n文件分组：{}\r\n上传文件路径：{}", storePath.getFullPath(), storePath.getGroup(),
					storePath.getFullPath());
			path = uploacConfig.getFdfsFileServer() + "/" + storePath.getFullPath();
			params.put("fileName", fileName);
			params.put("filePath", path);
		} catch (Exception e) {
			log.error("上传文件到FastDFS异常", e);
		}
		return params;
	}

	/**
	 * 上传文件到FastDFS
	 *
	 * @param file
	 * @return
	 */
	public Map<String, String> uploadToFastDFS(File file) {
		Map<String, String>	params	= Maps.newHashMap();
		String				path	= "";
		try {
			Upload		uploacConfig	= this.uploandConfig();
			String		fileName		= file.getName();
			StorePath	storePath		= fastFileStorageClient.uploadFile(
					IOUtils.toByteArray(new FileInputStream(file)), FilenameUtils.getExtension(file.getName()));

			log.info("上传文件路径：{}\r\n文件分组：{}\r\n上传文件路径：{}", storePath.getFullPath(), storePath.getGroup(),
					storePath.getFullPath());
			path = uploacConfig.getFdfsFileServer() + "/" + storePath.getFullPath();
			params.put("fileName", fileName);
			params.put("filePath", path);
		} catch (Exception e) {
			log.error("上传文件到FastDFS异常", e);
		}
		return params;
	}

	/**
	 * 上传文件到FastDFS
	 *
	 * @param file
	 * @return
	 */
	public Map<String, String> uploadToFastDFS(MultipartFile file) {
		Map<String, String>	params	= Maps.newHashMap();
		String				path	= "";
		try {
			Upload		uploacConfig	= this.uploandConfig();

			String		fileName		= file.getOriginalFilename();
			StorePath	storePath		= fastFileStorageClient.uploadFile(IOUtils.toByteArray(file.getInputStream()),
					FilenameUtils.getExtension(file.getOriginalFilename()));

			log.info("上传文件路径：{}\r\n文件分组：{}\r\n上传文件路径：{}", storePath.getFullPath(), storePath.getGroup(),
					storePath.getFullPath());
			path = uploacConfig.getFdfsFileServer() + "/" + storePath.getFullPath();

			params.put("fileName", fileName);
			params.put("filePath", path);
		} catch (Exception e) {
			log.error("上传文件到FastDFS异常", e);
		}
		return params;
	}

	/**
	 * 多文件上传到FastDFS
	 *
	 * @param fileList
	 * @return
	 */
	public List<Map<String, String>> uploadsToFastDFS(List<File> fileList) {
		Upload						uploacConfig	= this.uploandConfig();

		List<Map<String, String>>	list			= new ArrayList<>();
		if (null != fileList && !fileList.isEmpty()) {
			for (File file : fileList) {
				try {
					String		fileName	= file.getName();
					StorePath	storePath	= fastFileStorageClient.uploadFile(
							IOUtils.toByteArray(new FileInputStream(file)), FilenameUtils.getExtension(file.getName()));

					log.info("上传文件路径：{}\r\n文件分组：{}\r\n上传文件路径：{}", storePath.getFullPath(), storePath.getGroup(),
							storePath.getFullPath());
					String				path	= uploacConfig.getFdfsFileServer() + "/" + storePath.getFullPath();
					Map<String, String>	params	= Maps.newHashMap();
					params.put("fileName", fileName);
					params.put("filePath", path);
					list.add(params);
				} catch (IOException e) {
					log.error("上传文件到FastDFS异常", e);
				}
			}
		}
		return list;
	}

	/**
	 * 多文件上传到FastDFS
	 *
	 * @param multipartFiles
	 * @return
	 */
	public List<Map<String, String>> uploadsToFastDFS(MultipartFile[] multipartFiles) {
		Upload						uploacConfig	= this.uploandConfig();
		List<Map<String, String>>	list			= new ArrayList<>();
		if (multipartFiles != null && multipartFiles.length > 0) {
			for (MultipartFile file : multipartFiles) {
				try {
					String		fileName	= file.getOriginalFilename();
					StorePath	storePath	= fastFileStorageClient.uploadFile(
							IOUtils.toByteArray(file.getInputStream()),
							FilenameUtils.getExtension(file.getOriginalFilename()));

					log.info("上传文件路径：{}\r\n文件分组：{}\r\n上传文件路径：{}", storePath.getFullPath(), storePath.getGroup(),
							storePath.getFullPath());
					String				path	= uploacConfig.getFdfsFileServer() + "/" + storePath.getFullPath();
					Map<String, String>	params	= Maps.newHashMap();
					params.put("fileName", fileName);
					params.put("filePath", path);
					list.add(params);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * 从FastDFS上下载文件
	 *
	 * @param fileUrl  源文件路径
	 * @param filePath 保存路径（不需要带文件名）
	 * @return
	 */
	public Object downloadFileFromFastDFS(String fileUrl, String filePath) {
		try {
			String temp = "";
			if (fileUrl.indexOf("?") > -1) {
				temp = fileUrl.substring(fileUrl.indexOf("group"), fileUrl.indexOf("?"));
			} else {
				temp = fileUrl.substring(fileUrl.indexOf("group"));
			}
			String	group		= temp.substring(0, temp.indexOf("/"));
			String	path		= temp.substring(temp.indexOf("/") + 1);
			String	fileName	= fileUrl.substring(fileUrl.indexOf("=") + 1);
			byte[]	bfile		= fastFileStorageClient.downloadFile(group, path);
			getFile(bfile, filePath, fileName);
			return ResultMap.ok("文件下载成功");
		} catch (Exception e) {
			log.error("从FastDFS上下载文件异常", e);
			return ResultMap.error(e.getMessage());
		}
	}

	/**
	 * 从FastDFS上下载文件
	 *
	 * @param fileUrl 源文件路径
	 * @return
	 */
	public byte[] downloadFileFromFastDFS(String fileUrl) {
		byte[] bfile = null;
		try {
			String temp = "";
			if (fileUrl.indexOf("?") > -1) {
				temp = fileUrl.substring(fileUrl.indexOf("group"), fileUrl.indexOf("?"));
			} else {
				temp = fileUrl.substring(fileUrl.indexOf("group"));
			}

			temp = StringUtils.substringBetween(fileUrl, "group", "?");

			String	group	= temp.substring(0, temp.indexOf("/"));
			String	path	= temp.substring(temp.indexOf("/") + 1);
			bfile = fastFileStorageClient.downloadFile(group, path);
		} catch (Exception e) {
			log.error("从FastDFS上下载文件异常", e);
		}
		return bfile;
	}

	/**
	 * 从FastDFS上删除文件
	 *
	 * @param fileUrl 源文件路径
	 */
	public Object deleteFileFromFastDFS(String fileUrl) {
		if (StringUtils.isEmpty(fileUrl)) {
			return ResultMap.error(1, "文件删除失败");
		}
		try {
			String temp = "";
			if (fileUrl.indexOf("?") > -1) {
				temp = fileUrl.substring(fileUrl.indexOf("group"), fileUrl.indexOf("?"));
			} else {
				temp = fileUrl.substring(fileUrl.indexOf("group"));
			}
			String	group	= temp.substring(0, temp.indexOf("/"));
			String	path	= temp.substring(temp.indexOf("/") + 1);
			fastFileStorageClient.deleteFile(group, path);
			return ResultMap.ok("文件删除成功");
		} catch (FdfsUnsupportStorePathException e) {
			log.error("从FastDFS上下载文件异常", e);
			return ResultMap.error(e.getMessage());
		}
	}

	/**
	 * 根据byte数组，生成文件
	 *
	 * @param bfile
	 * @param filePath
	 * @param fileName
	 */
	protected File getFile(byte[] bfile, String filePath, String fileName) {
		if (StringUtils.isAnyEmpty(filePath, fileName)) {
			throw new KtfException("文件路径或文件名为null");
		}

		if (bfile == null || bfile.length == 0)
			return null;

		String	outFilePath	= StringUtils.joinWith(File.separator, filePath, fileName);
		File	dir			= new File(filePath);
		if (!dir.exists()) {// 判断文件目录是否存在
			dir.mkdirs();
		}

		File file = new File(outFilePath);
		if (file.exists())
			file.delete();
		try (FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {
			bos.write(bfile);
		} catch (Exception e) {
			log.error("根据byte数组，生成文件", e);
		}

		return file;
	}

	/**
	 * 创建二维码
	 *
	 * @param qrResource 内容
	 * @return
	 */
	public String createQrcode(String qrResource) {
		Upload				uploacConfig	= this.uploandConfig();
		String				quCodeFile		= StringUtils.joinWith(File.separator, uploacConfig.getQrCodeDir(),
				"upload", "qr", "");
		String				pngDir			= QRCodeUtils.createQRCode(quCodeFile, qrResource);
		String				qrDir			= "";
		Map<String, String>	params			= uploadToFastDFS(pngDir);
		if (null != params) {
			qrDir = params.get("filePath");
		}
		return qrDir;
	}

	private Upload uploandConfig() {
		return this.ktfDashboardProperties.getUpload();
	}

}
