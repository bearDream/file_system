package com.beardream.file_system.dao;

import com.beardream.file_system.model.Files;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesDao {
    int deleteByPrimaryKey(Integer fileId);

    int insert(Files record);

    int insertSelective(Files record);

    Files selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);
}