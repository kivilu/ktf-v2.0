package com.kivi.framework.util.kit;

import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 资源文件相关的操作类
 *
 */
public class ResKit {

    /**
     * @Description 批量获取ClassPath下的资源文件
     */
    public static Resource[] getClassPathResources( String pattern ) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources(pattern);
        }
        catch (IOException e) {
            return null;
        }
    }

    public static Resource getResource( String location ) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResource(location);
    }

    /**
     * @Description 批量获取ClassPath下的资源文件
     */
    public static String getClassPathResFilepath( String file ) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(file);
        if (url == null)
            return null;

        return url.getPath();
    }

    public static String getClassPathFilepath( String file ) {
        URL url = ResKit.class.getResource(file);
        if (url == null)
            return null;

        return url.getPath();
    }
}
