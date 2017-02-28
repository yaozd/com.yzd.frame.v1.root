package com.yzd.dubbo.service.consumer.test;

import com.yzd.db.temp.entity.TblTestData;
import com.yzd.dubbo.service.inf.test.ITestDataServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/28.
 */
@Service
public class TestDataService {
    @Autowired
    private ITestDataServiceInf testDataServiceInf;

    //region 实现初始化之后进行的操作
/*    @PostConstruct
    public void init() {
        TblTestData record=new TblTestData();
        record.setName("name");
        record.setPassword("password");
        record.setGmtCreate(new Date());
        record.setGmtModified(new Date());
        record.setGmtIsCachedUpdated(0);
        record.setGmtIsDeleted(0);
        int id = testDataServiceInf.insert(record);
        System.out.println(id);
    }*/
    //endregion

    public void insert(){
        TblTestData record=new TblTestData();
        record.setName("name");
        record.setPassword("password");
        record.setGmtCreate(new Date());
        record.setGmtModified(new Date());
        record.setGmtIsCachedUpdated(0);
        record.setGmtIsDeleted(0);
        int id = testDataServiceInf.insert(record);
        System.out.println(id);
    }
}
