package com.yzd.session.session;

/***
 *
 *
 * @author yzd
 * @date 2018/9/12 17:34.
 */

public class CurrentUser {

    public CurrentUser(long id, String name, Integer userTypeId) {
        this.Id = id;
        this.Name = name;
        this.UserTypeId = userTypeId;
    }

    private long Id;
    private String Name;
    private Integer UserTypeId;

    public long getId() {
        return Id;
    }

    public CurrentUser setId(long id) {
        Id = id;
        return this;
    }

    public String getName() {
        return Name;
    }

    public CurrentUser setName(String name) {
        Name = name;
        return this;
    }

    public Integer getUserTypeId() {
        return UserTypeId;
    }

    public CurrentUser setUserTypeId(Integer userTypeId) {
        UserTypeId = userTypeId;
        return this;
    }
}