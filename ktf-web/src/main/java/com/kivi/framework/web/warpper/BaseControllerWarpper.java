package com.kivi.framework.web.warpper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kivi.framework.util.Convert;
import com.kivi.framework.util.kit.BeanKit;

/**
 * 控制器查询结果的包装类基类
 *
 */
public abstract class BaseControllerWarpper<T> {

    public T obj = null;

    public BaseControllerWarpper( T obj ) {
        this.obj = obj;
    }

    @SuppressWarnings( "unchecked" )
    public BaseControllerWarpper( List<?> obj, Class<?> clazz ) {

        List<Map<String, Object>> list = obj.stream()
                .map(flow-> Convert.convertObject(flow, clazz))
                .map(BeanKit::beanToMap)
                .collect(Collectors.toCollection(ArrayList::new));

        this.obj = (T) list;
    }

    @SuppressWarnings( "unchecked" )
    public BaseControllerWarpper( List<?> obj ) {

        List<Map<String, Object>> list = obj.stream()
                .map(BeanKit::beanToMap)
                .collect(Collectors.toCollection(ArrayList::new));

        this.obj = (T) list;
    }

    @SuppressWarnings( "unchecked" )
    public T warp() {
        if (this.obj instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) this.obj;
            for (Map<String, Object> map : list) {
                warpTheMap(map);
            }
            return (T) list;
        }
        else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            warpTheMap(map);
            return (T) map;
        }
        else {
            return this.obj;
        }
    }

    protected abstract void warpTheMap( Map<String, Object> map );
}
