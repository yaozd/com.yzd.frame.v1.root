package com.yzd.dubbo.service.inf.test;

import com.yzd.db.temp.entity.TblTestData;

/**
 * Created by Administrator on 2017/2/28.
 */
public interface ITestDataServiceInf {
    int insert(TblTestData record);
    String getValue();
}
