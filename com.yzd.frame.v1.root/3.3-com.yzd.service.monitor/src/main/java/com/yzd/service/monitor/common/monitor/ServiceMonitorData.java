package com.yzd.service.monitor.common.monitor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/***
 *
 *
 * Created by yzd on 2018/8/27 14:36.
 */
@Slf4j
public class ServiceMonitorData {
    private static ServiceMonitorData ourInstance = new ServiceMonitorData();

    public static ServiceMonitorData getInstance() {
        return ourInstance;
    }

    private ServiceMonitorData() {
    }

    final int maxSize = 10;
    //参考：BlockingQueue的使用
    //https://www.cnblogs.com/liuling/p/2013-8-20-01.html
    private BlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<AtomicLong>(maxSize);

    public void add(AtomicLong val) {
        //offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.
       boolean isOk= arrayBlockingQueue.offer(val);
        if(BooleanUtils.isFalse(isOk)){
            log.error("ServiceMonitorData.arrayBlockingQueue已经满了无法加入新的数据，可以是队列容量太小或是发送程序出现问题");
        }
    }

    /***
     * 逐条发发送使用take
     * @return
     */
    public AtomicLong take(){
        try {
            //取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到Blocking有新的对象被加入为止
            return (AtomicLong)arrayBlockingQueue.take();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    /***
     * 批量发送使用poll(time,unit)
     */
    public AtomicLong poll(long timeout, TimeUnit unit){
        try {
            //poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null
           return (AtomicLong)arrayBlockingQueue.poll(timeout,unit);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
    private int getSize() {
        return arrayBlockingQueue.size();
    }

}
