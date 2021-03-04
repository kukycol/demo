package com.ezm.entity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@ApiModel(description = "添加权限dto")
@Data
@Builder
public class UpdateRuleBean {

    @ApiModelProperty(value = "权限标识号")
    private Integer id;


    @ApiModelProperty(value = "权限",required = true)
    @NotBlank
    @Pattern(regexp = "^[/]{1}[A-Za-z1-9]{2,30}$",message = "由/开头、英文字母和数字组成")
    private String rule;


    @ApiModelProperty(value = "权限名称",required = true)
    @NotBlank
    @Pattern(regexp = "^[\u4e00-\u9fa5A-Za-z1-9]{2,30}$",message = "不允许特殊符号")
    private String ruleName;


    @ApiModelProperty(value = "启用",required = true)
    @NotBlank
    @Pattern(regexp = "^[01]{1}$",message = "只能是1或0")
    private String enabled;


    @ApiModelProperty(value = "权限添加时间")
    private Date createDate;


    @ApiModelProperty(value = "权限更新时间")
    private Date updateDate;


    @Tolerate
    public UpdateRuleBean() {
    }

}
