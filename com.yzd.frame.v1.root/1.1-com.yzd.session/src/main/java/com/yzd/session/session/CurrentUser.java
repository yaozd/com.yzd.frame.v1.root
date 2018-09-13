package com.yzd.session.session;

import java.util.UUID;

/***
 *
 *
 * @author yzd
 * @date 2018/9/12 17:34.
 */

public class CurrentUser {
    private CurrentUser(){}
    private CurrentUser(long id, String name, Integer userTypeId) {
        this.id = id;
        this.name = name;
        this.userTypeId = userTypeId;
    }
    //登录用户主要用于登录情况下，访问token生成
    public static CurrentUser createLoginUser(long id, String name, Integer userTypeId){
        return new CurrentUser(id,name,userTypeId);
    }
    //空用户主要用于无登录情况下，访问token生成
    public static CurrentUser createEmptyUser(){
        return new CurrentUser();
    }
    private long id;
    private String name;
    private Integer userTypeId;
    /***
     * accessUUID(访问请求UUID)
     * 作用一：实现防止重复请求，重复表单提交；
     * 作用二：实现控制器访问次数限制
     */
    private String accessUUID=UUID.randomUUID().toString().replace("-","").toLowerCase();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getAccessUUID() {
        return accessUUID;
    }
}