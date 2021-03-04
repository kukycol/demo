package com.ezm.entity.bean;

import com.ezm.entity.table.SecurityRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@ApiModel(description = "用户更新dto")
@Data
@Builder
public class UpdateUserBean {

    @ApiModelProperty(value = "用户标识号",required = true)
    @NotNull
    private Integer id;


    @ApiModelProperty(value = "用户名",required = true)
    @NotBlank
    @Pattern(regexp = "^[\u4e00-\u9fa5]{2,10}$",message = "用户名只能是汉字且只允许2-10个汉字")
    private String uname;


    @ApiModelProperty(value = "账号",required = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{2,20}$",message = "只能由英文字母组成且只允许2-20个字母")
    private String account;


    @ApiModelProperty(value = "用户启用",required = true)
    @NotBlank
    @Pattern(regexp = "^[01]{1}$",message = "只能是1或0")
    private String enabled;


    @ApiModelProperty(value = "性别",required = true)
    @NotBlank
    @Pattern(regexp = "^['男女']{1}$",message = "只能是男或女")
    private String sex;


    @ApiModelProperty(value = "密码过期",required = true)
    @NotBlank
    @Pattern(regexp = "^[01]{1}$",message = "只能是1或0")
    private String credentialsNonExpired;


    @ApiModelProperty(value = "账号锁定",required = true)
    @NotBlank
    @Pattern(regexp = "^[01]{1}$",message = "只能是1或0")
    private String accountNonLocked;


    @ApiModelProperty(value = "账号过期",required = true)
    @NotBlank
    @Pattern(regexp = "^[01]{1}$",message = "只能是1或0")
    private String accountNonExpired;


    @ApiModelProperty(value = "邮箱地址",required = true)
    @NotBlank
    @Email(message = "请输入正确的邮箱地址")
    private String email;


    @ApiModelProperty(value = "手机号码",required = true)
    @NotBlank
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$",message = "请输入正确的手机号码！")
    private String mobile;



    @ApiModelProperty(value = "详细地址",required = true)
    @NotBlank(message = "详细住址不能为空")
    private String addr;


    @ApiModelProperty(value = "省",required = true)
    @NotBlank(message = "省份不能为空")
    private String province;


    @ApiModelProperty(value = "市",required = true)
    @NotBlank(message = "城市不能为空")
    private String city;


    @ApiModelProperty(value = "区",required = true)
    @NotBlank(message = "地区不能为空")
    private String county;


    @ApiModelProperty(value = "角色列表",required = true)
    @Size(min = 1)
    private List<SecurityRole> roleList;


    @ApiModelProperty(value = "初始化密码状态")
    private String state;


    @ApiModelProperty(value = "用户更新信息的时间")
    private Date updateDate;


    @Tolerate
    public UpdateUserBean() {
    }

}
