package com.ezm.entity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@ApiModel(description = "更新ruleName dto")
@Data
@Builder
public class UpdateRuleAddrBean {

    @ApiModelProperty(value = "权限标识号")
    @NotNull
    private Integer id;


    @ApiModelProperty(value = "权限",required = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_/]{2,30}$",message = "权限只允许英文、数组、/、下划线组成的2-30的字符")
    private String rule;

    @ApiModelProperty(value = "权限更新时间")
    private Date updateDate;



    @Tolerate
    public UpdateRuleAddrBean() {
    }

}
