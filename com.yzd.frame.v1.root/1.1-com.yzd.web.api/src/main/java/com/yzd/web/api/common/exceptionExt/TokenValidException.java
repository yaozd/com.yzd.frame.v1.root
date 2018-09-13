package com.yzd.web.api.common.exceptionExt;

/***
 * 用于Token有效性验证异常
 *
 * @author yzd
 * @date 2018/9/13 11:20.
 */

public class TokenValidException extends RuntimeException {
    /**
     * 构造一个基本异常.
     *
     * @param message 信息描述
     */
    public TokenValidException(String message) {
        super(message);
    }
}