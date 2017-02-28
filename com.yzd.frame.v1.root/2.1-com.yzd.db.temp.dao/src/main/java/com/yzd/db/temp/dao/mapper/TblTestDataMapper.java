package com.yzd.db.temp.dao.mapper;

import com.yzd.db.temp.entity.TblTestData;

public interface TblTestDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TblTestData record);

    int insertSelective(TblTestData record);

    TblTestData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TblTestData record);

    int updateByPrimaryKey(TblTestData record);
}