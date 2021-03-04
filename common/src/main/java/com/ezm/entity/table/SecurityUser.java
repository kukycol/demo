package com.ezm.entity.table;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * (SecurityUser)实体类
 *
 * @author makejava
 * @since 2021-01-22 22:23:18
 */
@Data
@Builder
@Table(name = "security_user")
public class SecurityUser implements Serializable, UserDetails {
    private static final long serialVersionUID = -65077247227734158L;


    /**
     * 用户编号
     */
    @Id
    @Column(name = "id")
    private Integer id;


    /**
     * 用户名
     */
    @Column(name = "uname")
    private String uname;


    /**
     * 登录名
     */
    @Column(name = "account")
    private String account;


    /**
     * 密码
     */
    @Column(name = "password")
    private String password;


    /**
     * 账号启用状态：1是，0否
     */
    @Column(name = "enabled")
    private boolean enabled;


    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;


    /**
     * 密码过期状态：1否，0是
     */
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;


    /**
     * 账号锁定状态：1否，0是
     */
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;


    /**
     * 账号过期状态：1否，0是
     */
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;


    /**
     * 邮箱地址
     */
    @Column(name = "email")
    private String email;


    /**
     * 手机号码
     */
    @Column(name = "mobile")
    private String mobile;


    /**
     * 修改初始化密码：1是，0否
     */
    @Column(name = "state")
    private String state;


    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;


    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;


    /**
     * 住址
     */
    @Column(name = "addr")
    private String addr;

    //省
    @Column(name = "province")
    private String province;

    //市
    @Column(name = "city")
    private String city;

    //区
    @Column(name = "county")
    private String county;


    //角色列表
    private List<SecurityRole> roleList;


    //赋予角色给security框架
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (SecurityRole role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    //登录账号
    @Override
    public String getUsername() {
        return this.account;
    }

    //账号过期
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    //账号锁定
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    //密码过期
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    //账号启用
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    @Tolerate
    public SecurityUser() {
    }
}