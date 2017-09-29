package com.beardream.file_system.dao;

import com.beardream.file_system.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User login(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}