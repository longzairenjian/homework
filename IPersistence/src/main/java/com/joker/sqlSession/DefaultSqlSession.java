package com.joker.sqlSession;


import com.joker.pojo.Configuration;
import com.joker.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;
/**
 * @author: wumj
 **/
public class DefaultSqlSession implements SqlSession {

    private static final String VOID = "void";

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {

        //将要去完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }


    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return  simpleExecutor.insert(configuration, mappedStatement, params);
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return  simpleExecutor.update(configuration, mappedStatement, params);
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return  simpleExecutor.delete(configuration, mappedStatement, params);
    }


    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理模式为DAO接口生成对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId  = className + "." + methodName;
                Type genericReturnType = method.getGenericReturnType();
                //是否为参数化类型
                if(genericReturnType instanceof ParameterizedType){
                    List<Object> objects = selectList(statementId, args);

                    return objects;
                }
                //是否为基础类型，且为void
                if(genericReturnType instanceof Class && VOID.equals(genericReturnType.getTypeName())){
                    update(statementId, args);
                    return null;
                }
                return selectOne(statementId,args);
            }
        });
        return (T) proxyInstance;
    }


}
