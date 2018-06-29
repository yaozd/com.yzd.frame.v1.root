package com.yzd.logging.filter;


import com.alibaba.dubbo.rpc.*;
import com.yzd.logging.consts.ParamEnum;
import com.yzd.logging.util.MDCUtil;
import com.yzd.logging.util.StringUtils;

import java.util.UUID;

public class DubboLogFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (RpcContext.getContext().isConsumerSide()) {
            String traceId = MDCUtil.get(MDCUtil.Type.TRACE_ID);
            if (StringUtils.isBlank(traceId)) {
                traceId = UUID.randomUUID().toString();
            }
            RpcContext.getContext().setAttachment(ParamEnum.TRACE_ID,traceId);
        }
        if (RpcContext.getContext().isProviderSide()) {
            String traceId = RpcContext.getContext().getAttachment(ParamEnum.TRACE_ID);
            if (StringUtils.isBlank(traceId)) {
                traceId = UUID.randomUUID().toString();
            }
            MDCUtil.put(MDCUtil.Type.TRACE_ID,traceId);
        }
        return invoker.invoke(invocation);
    }
}
