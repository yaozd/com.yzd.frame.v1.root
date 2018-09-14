package com.yzd.web.api.utils.signExt;

import com.yzd.web.api.utils.encrypt.MD5Util;
import com.yzd.web.api.utils.fastjsonExt.FastJsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by zd.yao on 2018/9/12.
 */
@Slf4j
public class SignUtil {
    private static final String SIGN_KEY="yzd-@#$%&2017";

    /***
     * 验证签名
     * @param obj
     * @param sign
     * @param <T>
     * @return
     */
    public static <T> boolean  check(T obj,String sign){
        String objSign= create(obj);
        log.debug(objSign);
        return sign.equals(objSign);
    }

    /***
     * 创建签名
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String create(T obj){
        Map<String,Object> sortMap=beanToMap(obj);
        StringBuilder sb=new StringBuilder();
        sb.append(SIGN_KEY);
        for (Object item : sortMap.values()) {
            sb.append(item);
        }
        String objSign= MD5Util.encode(sb.toString(), "utf-8");
        return objSign;
    }
    private static <T> Map<String,Object> beanToMap(T obj){
        String objJson= FastJsonUtil.serialize(obj);
        Map<String,Object> objMap=FastJsonUtil.json2Map(objJson);
        return sortByASCII(objMap);
    }
    /**
     * ASCII 码从小到大排序（字典序）
     * @param paramMap
     * @return
     */
    private static Map<String,Object> sortByASCII(Map<String,Object> paramMap){
        SortedMap<String,Object> sort=new TreeMap<String,Object>(paramMap);
        return sort;
    }
}
