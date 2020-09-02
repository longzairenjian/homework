package com.joker.dao;

import com.joker.pojo.User;

import java.util.List;

/**
 * @author wumj
 */
public interface IUserDao {

    //查询所有用户
     List<User> findAll() throws Exception;
    //根据条件进行用户查询
     User findByCondition(User user) throws Exception;
    //添加一个用户
     void saveUser(User user)throws Exception;
    //修改一个用户
     void updateUser(User user)throws Exception;
    //删除一个用户
     void deleteUser(User user)throws Exception;
}
