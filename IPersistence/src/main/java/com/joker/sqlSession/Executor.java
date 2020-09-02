package com.joker.sqlSession;

import com.joker.pojo.Configuration;
import com.joker.pojo.MappedStatement;

import java.util.List;
/**
 * @author: wumj
 **/
public interface Executor {

     <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

     int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

     int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

     int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
