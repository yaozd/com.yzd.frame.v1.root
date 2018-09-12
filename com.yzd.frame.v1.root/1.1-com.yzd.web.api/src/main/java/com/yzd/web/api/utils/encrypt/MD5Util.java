package com.yzd.web.api.utils.encrypt;

import java.security.MessageDigest;

/**
 * Created by zd.yao on 2018/9/12.
 */
public class MD5Util {
    public final static String encode(String s, String charset) {
        try {
            byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
    public final static String encode(String s){
        return encode(s,"utf-8");
    }
}
