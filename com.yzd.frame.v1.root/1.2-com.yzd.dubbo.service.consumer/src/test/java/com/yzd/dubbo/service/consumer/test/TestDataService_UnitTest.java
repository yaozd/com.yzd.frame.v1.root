package com.yzd.dubbo.service.consumer.test;

import com.yzd.dubbo.service.consumer.base._BaseUnitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/2/28.
 */
public class TestDataService_UnitTest extends _BaseUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(TestDataService_UnitTest.class);
    @Autowired
    private TestDataService testDataService;
    @Test
    public void insert(){
        logger.info("insert begin");
        testDataService.insert();
        logger.info("insert end");
    }
}
