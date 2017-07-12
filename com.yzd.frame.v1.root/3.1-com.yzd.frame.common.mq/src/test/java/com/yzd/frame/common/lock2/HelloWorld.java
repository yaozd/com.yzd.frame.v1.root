package com.yzd.frame.common.lock2;

import java.util.concurrent.TimeUnit;

/**
 * Created by zd.yao on 2017/7/12.
 */
public class HelloWorld implements BusinessExecutor {
    @Override
    public void execute() {
        try {
            System.out.println("task begin");
            TimeUnit.SECONDS.sleep(100);
            System.out.println("task end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
