package com.yzd.web.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.yzd.logging.util.SensitiveLogInfo;
import com.yzd.logging.util.SensitiveLogType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = 8655851615465363473L;

    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    //推荐使用注解的方式对日志字段进行脱敏，不推荐使用@JSONField(serialize=false)或transient
    @SensitiveLogInfo(type = SensitiveLogType.PASSWORD)
    private String password;
    //@JSONField(serialize=false)
    //private  String password;
    //private transient String password;

    // TODO  省略get set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
