package com.yzd.web.util.cookieExt;

/***
 *
 *
 * @author yzd
 * @date 2018/9/14 10:20.
 */

public enum  CookieName {
    //25天=25*24*60*60;cookie的有效时间小于TOKEN的有效时间
    TOKEN("_01TN",25*24*60*60),
    //TOKEN的创建时间
    TOKEN_TIME("_01TT",25*24*60*60);
    private CookieName(String key,int expiry){
        this.key=key;
        this.expiry=expiry;
    }

    public String getKey() {
        return key;
    }

    /***
     * 名称
     */
    private String key;

    public int getExpiry() {
        return expiry;
    }

    /***
     * 过期时间
     */
    private int expiry;

}
