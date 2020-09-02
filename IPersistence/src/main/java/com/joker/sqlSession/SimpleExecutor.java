package com.joker.sqlSession;

import com.joker.pojo.Configuration;
import com.joker.pojo.MappedStatement;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
/**
 * @author: wumj
 **/
public class SimpleExecutor implements Executor {


    @Override                                                                                //user
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        //参数处理转换
        PreparedStatementHandler preparedStatementHandler = new PreparedStatementHandler(configuration, mappedStatement, params);
        PreparedStatement preparedStatement = preparedStatementHandler.getPreparedStatement();

        //  执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();

        // 封装返回结果集
        while (resultSet.next()){
            Object o =resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段的值
                Object value = resultSet.getObject(columnName);

                //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);


            }
            objects.add(o);

        }
            return (List<E>) objects;

    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        PreparedStatementHandler preparedStatementHandler = new PreparedStatementHandler(configuration, mappedStatement, params);
        PreparedStatement preparedStatement = preparedStatementHandler.getPreparedStatement();
        return  preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        return this.update(configuration,mappedStatement,params);
    }

    @Override
    public int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        return this.update(configuration,mappedStatement,params);
    }


    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if(paramterType!=null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
         return null;

    }



}
