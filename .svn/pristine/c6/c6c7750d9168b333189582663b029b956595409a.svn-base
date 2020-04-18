package com.kivi.framework.web.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadVO {
    public static String PRE_PERCENT     = "percent_";
    public static String PRE_LAST_UPTIME = "lastUploadTime_";
    public static String PRE_NAME        = "fileName_";

    private String       id;
    private String       name;
    private String       type;
    private Date         lastModifiedDate;
    private int          chunkSize;                          // 分片大小
    private int          chunks;                             // 分卷总数
    private String       chunk;                              // 分卷 0开始
    private int          percent;                            // 完成百分比
    private String       md5;

    public FileUploadVO( String name, String md5 ) {
        this(name, null, null, null, md5);
    }

    public FileUploadVO( String name, String chunkSize, String chunk, String percent, String md5 ) {
        this.name = name;
        this.chunk = chunk;
        this.chunkSize = chunkSize == null ? 0 : Integer.parseInt(chunkSize);
        this.percent = percent == null ? 0 : Integer.parseInt(percent);
        this.md5 = md5;
    }
}
