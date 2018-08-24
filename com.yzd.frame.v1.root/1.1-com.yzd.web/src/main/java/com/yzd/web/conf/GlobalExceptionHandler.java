package com.yzd.web.conf;

import cn.hutool.crypto.SecureUtil;
import com.yzd.web.ApplicationWeb;
import com.yzd.web.aspectExt.ParamValidException;
import com.yzd.web.util.exceptionExt.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/***
 *
 *
 * Created by yzd on 2018/8/23 15:27.
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        //未知异常信息
        //对于未知异常信息，可以把异常堆栈信息MD5，
        //然后通过钉钉发送MD5值预警或把MD5值发送到后台，让开发人员及时处理。
        //通过MD5值来追踪信息
        String errorMessage = getErrorMessageForInner(ex);
        String md5Val= SecureUtil.md5(errorMessage);
        logger.error(String.format("handleControllerException-[%s]-异常详细信息:",md5Val),ex);
        return ResponseEntity.status(status).body(errorMessage);
    }

    /***
     * 运行时异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerExceptionForRuntime(HttpServletRequest request, Exception ex) {
        HttpStatus status = getStatus(request);
        //未知异常信息
        //对于未知异常信息，可以把异常堆栈信息MD5，
        //然后通过钉钉发送MD5值预警或把MD5值发送到后台，让开发人员及时处理。
        //通过MD5值来追踪信息
        String errorMessage = getErrorMessageForInner(ex);
        String md5Val= SecureUtil.md5(errorMessage);
        logger.error(String.format("handleControllerExceptionForRuntime-[%s]-异常详细信息:",md5Val),ex);
        return ResponseEntity.status(status).body(errorMessage);
    }

    /***
     * 请求参数格式不正确
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ParamValidException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerExceptionForParamValid(HttpServletRequest request, Exception ex) {
        //生产上会用统一的的输出格式- new JsonResultList
        //当前属于api请求参数的格式验证异常-所以状态是200
        return ResponseEntity.status(200).body(ex.getMessage());
    }
    /***
     * 针对应用程序内部的异常-打印堆栈信息
     * @param ex
     * @return
     */
    private String getErrorMessageForInner(Exception ex) {
        return ExceptionUtil.exceptionToString(ex);
    }
    /***
     * 针对feign远程调用与hystrix的异常
     */
    private String getErrorMessageForHystrix(Throwable ex) {
        String cause="";
        if(ex.getCause()!=null){
            cause=ex.getCause().getMessage();
        }
        StringBuilder sb=new StringBuilder();
        sb.append("Cause:").append(cause)
                .append(System.lineSeparator())
                .append("Exception:").append(ex.toString())
                .append(System.lineSeparator());
        return sb.toString();
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

