package com.ezm.entity.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel(description = "搜索条件和分页条件dto")
@Data
@Builder
public class UserPageBean {


    @ApiModelProperty(value = "偏移量",required = true)
    @NotNull
    @Range(max = 10000000,min = 1)
    private Integer page = 1;


    @ApiModelProperty(value = "页条数",required = true)
    @NotNull
    @Range(max = 100,min = 1)
    private Integer limit = 10;


    @ApiModelProperty(value = "用户名")
    private String uname;


    @ApiModelProperty(value = "账号")
    private String account;


    @ApiModelProperty(value = "邮箱地址")
    private String email;


    @ApiModelProperty(value = "手机号码")
    private String mobile;


    @ApiModelProperty(value = "性别")
    private String sex;


    @ApiModelProperty(value = "启用状态")
    private String enabled;


    @ApiModelProperty(value = "密码过期")
    private String credentialsNonExpired;


    @ApiModelProperty(value = "账号锁定")
    private String accountNonLocked;


    @ApiModelProperty(value = "账号过期")
    private String accountNonExpired;


    @ApiModelProperty(value = "初始化密码状态")
    private String state;


    @ApiModelProperty(value = "详细地址")
    private String addr;

    @ApiModelProperty(value = "省")
    private String province;


    @ApiModelProperty(value = "市")
    private String city;


    @ApiModelProperty(value = "区")
    private String county;


    @Tolerate
    public UserPageBean() {
    }
}
