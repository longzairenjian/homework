package com.joker.sqlSession;

import java.util.List;
/**
 * @author: wumj
 **/
public interface SqlSession {

    //查询所有
    <E> List<E> selectList(String statementid,Object... params) throws Exception;

    //根据条件查询单个
    <T> T selectOne(String statementid,Object... params) throws Exception;

    int insert(String statementId, Object... params) throws Exception;

    int update(String statementId, Object... params) throws Exception;

    int delete(String statementId, Object... params) throws Exception;


    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);


}
