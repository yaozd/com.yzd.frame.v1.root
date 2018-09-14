package com.yzd.web.util.signExt;

import com.yzd.web.util.encrypt.MD5Util;
import com.yzd.web.util.fastjsonExt.FastJsonUtil;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/***
 *
 *
 * @author yzd
 * @date 2018/9/14 10:46.
 */

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
        System.out.println(objSign);
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
    public static <T> Map<String,Object> beanToMap(T obj){
        String objJson= FastJsonUtil.serialize(obj);
        Map<String,Object> objMap=FastJsonUtil.json2Map(objJson);
        return sortByASCII(objMap);
    }
    /**
     * ASCII 码从小到大排序（字典序）
     * @param paramMap
     * @return
     */
    public static Map<String,Object> sortByASCII(Map<String,Object> paramMap){
        SortedMap<String,Object> sort=new TreeMap<String,Object>(paramMap);
        return sort;
    }
}
