package com.tdkj.model;

import java.util.List;

/**
 * @ClassName: SysUser
 * @Description TODO
 * @Author and
 * @Date 2019/5/821:11
 * @Version 1.0
 */
public class SysUser {
    private Integer id;
    private String username;
    private String password;

    private List<SysRole> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

}
