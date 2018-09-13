package com.yzd.web.api.common.exceptionExt;

/***
 * 用于数据有效性验证异常
 *
 * @author yzd
 * @date 2018/9/13 11:14.
 */

public class DataValidException extends RuntimeException {
    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     */
    public DataValidException(String message) {
        super(message);
    }
}
