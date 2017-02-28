package com.yzd.dubbo.service.consumer.test;

import com.yzd.dubbo.service.consumer.base._BaseUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/2/28.
 */
public class TestDataService_UnitTest extends _BaseUnitTest {
    @Autowired
    private TestDataService testDataService;
    @Test
    public void insert(){
        testDataService.insert();
    }
}
