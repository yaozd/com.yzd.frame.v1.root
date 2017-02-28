package com.yzd.dubbo.service.provider.test;

import com.yzd.db.temp.dao.dao.TblTestDataDao;
import com.yzd.db.temp.entity.TblTestData;
import com.yzd.dubbo.service.inf.test.ITestDataServiceInf;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/2/28.
 */
//错误提醒--provider提供者是不需要增加注解，提供者是通过XML文件进行注入实例的
public class TestDataServiceImpl implements ITestDataServiceInf {
    @Autowired
    private TblTestDataDao tblTestDataDao;
    @Override
    public int insert(TblTestData record) {
        int id= tblTestDataDao.insert(record);
        return id;
    }
}
