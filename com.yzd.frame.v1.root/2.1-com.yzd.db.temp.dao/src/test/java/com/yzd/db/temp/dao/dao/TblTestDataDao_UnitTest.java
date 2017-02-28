package com.yzd.db.temp.dao.dao;

import com.yzd.db.temp.dao.dao.base._BaseUnitTest;
import com.yzd.db.temp.entity.TblTestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TblTestDataDao_UnitTest extends _BaseUnitTest {
    @Autowired
    private TblTestDataDao tblTestDataDao;
    @Test
    public void insert(){
        TblTestData record=new TblTestData();
        record.setName("name");
        record.setPassword("password");
        record.setGmtCreate(new Date());
        record.setGmtModified(new Date());
        record.setGmtIsCachedUpdated(0);
        record.setGmtIsDeleted(0);
        tblTestDataDao.insert(record);
    }
}
