package com.yzd.web.util.feignExt.client;

import com.yzd.web.util.feignExt.request.DoLoginFeginForm;
import com.yzd.web.util.feignExt.request.GetBaseTokenFeginForm;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

/***
 *
 *
 * @author yzd
 * @date 2018/9/14 9:25.
 */

public interface ITokenClientInf {

    //前提：独产使用feign的情况
    //问题：java.lang.IllegalStateException: Method has too many Body parameters
    //@RequestParam("sign")调整为 @Param("sign")String sign
    //String getBaseToken(GetBaseTokenForm form, @RequestParam("sign")String sign);
    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/token/getBaseToken?sign={sign}")
    String getBaseToken(GetBaseTokenFeginForm form, @Param("sign")String sign);

    @Headers({"Content-Type: application/json"})
    @RequestLine("POST /api/account/doLogin")
    String doLogin(DoLoginFeginForm form, @HeaderMap Map<String, Object> headerMap);
}
