package com.kivi.framework.util.kit;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * 资源文件相关的操作类
 *
 */
@Slf4j
public class ResKit {

    /**
     * @Description 批量获取ClassPath下的资源文件
     */
    public static Resource[] getClassPathResources(String pattern) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources(pattern);
        } catch (IOException e) {
            log.error("批量获取ClassPath下的资源文件", e);
            return null;
        }
    }

    public static Resource getResource(String location) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResource(location);
    }

    /**
     * @Description 批量获取ClassPath下的资源文件
     */
    public static String getClassPathResFilepath(String file) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(file);
        if (url == null)
            return null;

        return url.getPath();
    }

    public static String getClassPathFilepath(String file) {
        URL url = ResKit.class.getResource(file);
        if (url == null)
            return null;

        return url.getPath();
    }

    public static String getFilepath(String file) {
        String result = null;
        // ClassPathResource res = new ClassPathResource(file);
        try {
            Resource res = getResource(file);
            File f = res.getFile();
            result = f.getAbsolutePath();
        } catch (IOException e) {
            log.error("获取ClassPath下的资源文件", e);
        }

        return result;
    }
}
