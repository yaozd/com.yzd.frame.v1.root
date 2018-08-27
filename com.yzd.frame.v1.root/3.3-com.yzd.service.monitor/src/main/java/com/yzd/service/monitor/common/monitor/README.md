
## 基于gauva cache的服务监控收集发送service.monitor
```
1.cache
2.同步阻塞队列-ArrayBlockingQueue
3.任务调度-Scheduled
```
## 程序实现思路
```
1.通过cache来记录数据
2.收集数据
通过删除操作触发cache中的数据移除监听，然后把数据放入到同步阻塞队列之中，通过阻塞队列来进行数据的传递
3.发送数据
通过读取阻塞队列中的数据把数据发送出去。
```