package com.yzd.db.temp.dao.dao;

import com.yzd.db.temp.dao.mapper.TblTestDataMapper;
import com.yzd.db.temp.entity.TblTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/2/27.
 */
@Repository
public class TblTestDataDao {
    @Autowired
    private TblTestDataMapper tbTempTestMapper;

    public int insert(TblTestData record) {
        int id= tbTempTestMapper.insert(record);
        System.out.println(id);
        System.out.println(record.getId());
        //return id;
        return  record.getId().intValue();
    }
}
