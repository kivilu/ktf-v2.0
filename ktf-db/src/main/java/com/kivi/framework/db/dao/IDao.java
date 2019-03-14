package com.kivi.framework.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kivi.framework.vo.PageInfoKtf;
import com.kivi.framework.vo.page.PageReqVO;

/**
 * 通用接口
 */
@Repository
public interface IDao<T> {

    T selectByKey( Object key );

    T save( T entity );

    T saveNotNull( T entity );

    int delete( Object key );

    int deleteByEntity( T entity );

    int deleteByExample( Object example );

    T updateAll( T entity );

    T updateNotNull( T entity );

    int updateNotNull( T condEntity, T updateEntity );

    /**
     * 根据主键字符串进行查询，类中只有存在一个带有@Id注解的字段
     *
     * @param ids
     *            如 "1,2,3,4"
     * @return
     */
    List<T> selectByIds( String ids );

    List<T> selectByIds( List<Object> ids );

    /**
     * 根据条件查询
     * 
     * @param example
     * @return
     */
    List<T> selectByExample( Object example );

    /**
     * 根据条件查询
     * 
     * @param entity
     * @return
     */
    List<T> selectByEntity( T entity );

    /**
     * 查找一个结果
     * 
     * @param entity
     * @return
     */
    T selectOne( T entity );

    /**
     * 查询总数量
     * 
     * @return
     */
    int count( T entity );

    int count();

    int countByExample( Object example );

    /**
     * 分页查询
     * 
     * @param example
     * @param pageReq
     * @return
     */
    PageInfoKtf<T> selectPageByExample( Object example, PageReqVO pageReq );

    /**
     * 分页查询
     * 
     * @return
     */
    PageInfoKtf<T> selectByPage( T entity, PageReqVO pageReq );
}
