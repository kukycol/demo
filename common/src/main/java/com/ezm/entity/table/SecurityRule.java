package com.ezm.entity.table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;


/**
 * (SecurityRule)实体类
 *
 * @author makejava
 * @since 2021-01-22 22:24:14
 */
@ApiModel(description = "权限dto")
@Data
@Builder
@Table(name = "security_rule")
public class SecurityRule implements Serializable {
    private static final long serialVersionUID = -70718259544060432L;


    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "权限标识号")
    private Integer id;



    @ApiModelProperty(value = "权限",required = true)
    @Column(name = "rule")
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_/]{2,30}$",message = "权限只允许英文、数组、/、下划线组成的2-30的字符")
    private String rule;


    @ApiModelProperty(value = "权限名称",required = true)
    @Column(name = "rule_name")
    @NotBlank
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]{2,30}$",message = "用户名只能是中文、英文、数字包括下划线只允许2-30个汉字")
    private String ruleName;


    @ApiModelProperty(value = "权限登录")
    @Column(name = "level")
    private Integer level;


    @ApiModelProperty(value = "启用",required = true)
    @Column(name = "enabled")
    @NotBlank
    @Pattern(regexp = "^[0-1]{0,1}$",message = "只允许0和1")
    private String enabled;



    @ApiModelProperty(value = "权限创建时间")
    @Column(name = "create_date")
    private Date createDate;


    @ApiModelProperty(value = "权限更新时间")
    @Column(name = "update_date")
    private Date updateDate;

    @Transient
    private boolean flag;


    @Tolerate
    public SecurityRule() {
    }


}