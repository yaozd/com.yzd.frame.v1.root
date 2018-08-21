package com.yzd.logging.filter;

import com.alibaba.dubbo.rpc.*;
import com.yzd.logging.util.FastJsonLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 此过滤为可选方案-有请求可以加上
 * 只适用于dubbo的生产者的请求信息收集
 * 用于收集Dubbo RPC 请求入口信息包括（方法与请求参数）
 *
 */
public class DubboRpcRequestLogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DubboRpcRequestLogFilter.class);
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String remoteIp = RpcContext.getContext().getRemoteHost();
        String hostName = RpcContext.getContext().getRemoteHostName();
        Class<?> clazz =  invocation.getInvoker().getInterface();
        String method = RpcContext.getContext().getMethodName();
        //建议请求的参数进行json序列化操作，放便以后出问题时请求数据的回放
        Object[] args = RpcContext.getContext().getArguments();
        //invoker.invoke(invocation)前记录dubbo的RPC请求信息
        //目前暂定不记录清求时间花费的时间
        logger.info("DubboRpcRequestInfo:remote ip:{},hostname:{} ,class:{}, invoke method:{}, args:{}",remoteIp,hostName,clazz,method,FastJsonLogUtil.toJsonString(args));
        return invoker.invoke(invocation);

    }
}
