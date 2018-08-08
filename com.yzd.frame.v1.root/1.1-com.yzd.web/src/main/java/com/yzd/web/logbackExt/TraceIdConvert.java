package com.yzd.web.logbackExt;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.yzd.logging.util.MDCUtil;
import com.yzd.logging.util.StringUtils;
import com.yzd.logging.util.UUIDUtil;

import java.util.UUID;


/**
 * Created by zd.yao on 2017/5/24.
 */
public class TraceIdConvert extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String traceId = null;
        traceId=MDCUtil.get(MDCUtil.Type.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUIDUtil.getUUID();
            MDCUtil.put(MDCUtil.Type.TRACE_ID, traceId);;
        }
        return traceId;
    }
}
