package com.yzd.web.api.utils.signExt;

import com.yzd.web.api.utils.encrypt.MD5Util;
import com.yzd.web.api.utils.fastjsonExt.FastJsonUtil;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by zd.yao on 2018/9/12.
 */
public class SignUtil {
    private static final String SIGN_KEY="yzd-@#$%&2017";
    public static <T> boolean  check(T obj,String sign){
        Map<String,Object> sortMap=beanToMap(obj);
        StringBuilder sb=new StringBuilder();
        sb.append(SIGN_KEY);
        for (Object item : sortMap.values()) {
            sb.append(item);
        }
        String objSign= MD5Util.encode(sb.toString(), "utf-8");
        return sign.equals(objSign);
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
