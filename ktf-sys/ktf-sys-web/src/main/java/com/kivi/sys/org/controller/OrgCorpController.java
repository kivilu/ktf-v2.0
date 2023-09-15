package com.kivi.sys.org.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.kivi.framework.model.ResultMap;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.page.PageInfoVO;
import com.kivi.sys.org.dto.OrgCorpDTO;
import com.kivi.sys.shiro.ShiroKit;
import com.kivi.sys.shiro.ShiroUser;
import com.kivi.sys.sys.controller.UpLoadController;
import com.kivi.sys.sys.dto.SysFileDTO;
import com.vip.vjtools.vjkit.collection.ListUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 企业信息 前端控制器
 * </p>
 *
 * @author Auto-generator
 * @since 2019-09-18
 */
@Api(tags = "组织架构—企业管理")
@ApiSupport(order = 20)
@RestController
@RequestMapping("/org/corp")
@Slf4j
public class OrgCorpController extends UpLoadController {

    private Map<String, List<Map<String, String>>> uploadFileUrls =
        new ConcurrentHashMap<String, List<Map<String, String>>>();

    @ApiOperation(value = "企业信息信息", notes = "企业信息信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("org/corp/info")
    public ResultMap info(@PathVariable("id") Long id) {
        OrgCorpDTO dto = orgCorpService().getDto(id);
        return ResultMap.ok().data(dto);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增企业信息", notes = "新增企业信息")
    @RequiresPermissions("org/corp/save")
    @PostMapping("/save")
    public ResultMap save(@Valid @RequestBody OrgCorpDTO dto) {
        try {
            OrgCorpDTO enterprise = orgCorpService().save(dto);
            saveFile(enterprise.getId());
            return ResultMap.ok("新增成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultMap.error("添加失败，请联系管理员");
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改企业信息", notes = "修改企业信息")
    @RequiresPermissions("org/corp/update")
    @PostMapping("/update")
    public ResultMap updateById(@RequestBody OrgCorpDTO dto) {
        try {
            Boolean b = orgCorpService().updateById(dto);
            saveFile(dto.getId());
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

    /**
     * 删除
     */
    @ApiOperation(value = "批量删除企业信息", notes = "删除企业信息")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("org/corp/delete")
    public ResultMap delete(@PathVariable("id") Long id) {
        try {
            Boolean b = orgCorpService().removeById(id);
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
     * 批量删除
     */
    @ApiOperation(value = "批量删除企业信息", notes = "删除企业信息")
    @PostMapping("/delete")
    @RequiresPermissions("org/corp/delete")
    public ResultMap deleteBatchIds(@RequestBody Long[] ids) {
        try {
            Boolean b = orgCorpService().removeByIds(Arrays.asList(ids));
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
     * 根据条件查询全部符合条件的企业列表
     */
    @ApiOperation(value = "企业列表", notes = "企业列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "keyword", dataType = "string", value = "名称，可选，模糊匹配",
        paramType = "query", allowEmptyValue = true)})
    @RequiresPermissions("org/corp/list")
    @GetMapping("/list")
    public ResultMap list(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
        Object keyword = params.get("keyword");
        if (keyword != null) {
            params.put(OrgCorpDTO.NAME, keyword);
        }
        List<OrgCorpDTO> list = orgCorpService().list(params);

        return ResultMap.ok().data(list);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @ApiOperation(value = "企业分页查询", notes = "企业分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "keyword", dataType = "string", value = "名称，可选，模糊匹配", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "page", dataType = "integer", value = "当前页，可选，默认值：1", paramType = "query",
            allowEmptyValue = true),
        @ApiImplicitParam(name = "limit", dataType = "integer", value = "每页大小，可选，默认值：10", paramType = "query",
            allowEmptyValue = true)})
    @GetMapping("/page")
    @RequiresPermissions("org/corp/page")
    public ResultMap names(@ApiIgnore @RequestParam(required = false) Map<String, Object> params) {
        Object keyword = params.get("keyword");
        if (keyword != null) {
            params.put(OrgCorpDTO.NAME, keyword);
        }
        PageInfoVO<OrgCorpDTO> page = orgCorpService().page(params);
        return ResultMap.ok().data(page);
    }

    /**
     * 上传附件
     */
    @PostMapping("/uploadFile")
    public Object uploadFile(@RequestParam("file") MultipartFile[] files) {
        try {
            List<Map<String, String>> uploadFileUrl = uploads(files, "enterprise");
            // String fileName = "";
            String filePath = "";
            if (!uploadFileUrl.isEmpty() && uploadFileUrl.size() > 0) {
                for (Map<String, String> map : uploadFileUrl) {
                    // fileName = map.get("fileName");
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

    /**
     * 列示附件
     */
    @GetMapping("/selectFile/{id}")
    public Object listFile(@PathVariable("id") String id) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(SysFileDTO.TABLE_ID, "t_enterprise");
        params.put(SysFileDTO.RECORD_ID, id);
        List<SysFileDTO> fileList = sysFileService().list(params, StrKit.emptyArray());
        return ResultMap.ok().put("fileList", fileList);
    }

    /**
     * 删除附件
     */
    @GetMapping("/deleteFileById/{id}")
    public Object deleteFileById(@PathVariable("id") String id) {
        try {
            SysFileDTO sysFile = sysFileService().getDTOById(Long.parseLong(id));
            if (sysFile != null) {
                sysFileService().removeById(sysFile.getId());
                deleteFileFromLocal(sysFile.getAttachmentPath());
            }
            return ResultMap.ok("删除成功");
        } catch (Exception e) {
            log.error("删除附件异常", e);
            return ResultMap.error("删除失败，请联系管理员");
        }
    }

    /**
     * 删除附件(刚上传到后端的附件)
     */
    @GetMapping("/deleteFileByName")
    public Object deleteFileByName(@RequestParam String fileName) {
        try {
            List<Map<String, String>> list = getUploadFile();
            if (StringUtils.isNotBlank(fileName) && ListUtil.isNotEmpty(list)) {
                for (Map<String, String> uploadFileUrl : list) {
                    boolean canDel = false;
                    if (uploadFileUrl.get("fileName").equalsIgnoreCase(fileName)) {
                        deleteFileFromLocal(uploadFileUrl.get("filePath"));
                        canDel = true;
                        break;
                    }
                    if (canDel) {
                        list.remove(uploadFileUrl);
                        break;
                    }
                }
            }
            return ResultMap.ok("删除成功");
        } catch (Exception e) {
            log.error("删除附件异常", e);
            return ResultMap.error("删除失败,请联系管理员");
        }
    }

    public Object saveFile(Long id) {
        try {
            if (getUploadFile() != null) {
                // 获取企业ID前缀，生成UUID
                for (Map<String, String> uploadFileUrl : getUploadFile()) {
                    String fileName = uploadFileUrl.get("fileName");
                    String filePah = uploadFileUrl.get("filePath");
                    SysFileDTO sysFile = new SysFileDTO();
                    sysFile.setRecordId(id);
                    sysFile.setTableId("ktf_enterprise");
                    sysFile.setAttachmentGroup("企业");
                    sysFile.setAttachmentName(fileName);
                    sysFile.setAttachmentPath(filePah);
                    // 附件类型(0-word,1-excel,2-pdf,3-jpg,png,4-其他等)
                    String fileType = fileName.substring(fileName.indexOf("."));
                    if ("doc".equals(fileType.toLowerCase())) {
                        sysFile.setAttachmentType(0);
                    } else if ("xlsx".equals(fileType.toLowerCase())) {
                        sysFile.setAttachmentType(1);
                    } else if ("pdf".equals(fileType.toLowerCase())) {
                        sysFile.setAttachmentType(2);
                    } else if ("jpg".equals(fileType.toLowerCase()) || "png".equals(fileType.toLowerCase())) {
                        sysFile.setAttachmentType(3);
                    } else {
                        sysFile.setAttachmentType(4);
                    }
                    sysFile.setSaveType(0);
                    sysFile.setIsSync(0);
                    sysFile.setCreateUser(ShiroKit.getUser().getLoginName());
                    sysFileService().save(sysFile);
                }
                resetUploadFile();
            }
            return ResultMap.ok("保存成功");
        } catch (Exception e) {
            log.error("保存失败", e);
            return ResultMap.error("保存失败，请联系管理员");
        }
    }

    private void setUploadFile(List<Map<String, String>> uploadFileUrl) {
        ShiroUser user = ShiroKit.getUser();
        Object o = uploadFileUrls.get(user.getId().toString());
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
        ShiroUser user = ShiroKit.getUser();
        uploadFileUrls.remove(user.getId().toString());
    }

}
