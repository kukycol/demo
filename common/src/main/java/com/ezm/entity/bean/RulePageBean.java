package com.ezm.entity.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@ApiModel(description = "取消授权列表dto")
@Data
@Builder
public class RulePageBean {

    @ApiModelProperty(value = "角色标识号")
    private int roleId;


    @ApiModelProperty(value = "偏移量",required = true)
    @NotNull
    @Range(max = 10000000,min = 1)
    private Integer page = 1;


    @ApiModelProperty(value = "页条数",required = true)
    @NotNull
    @Range(max = 100,min = 1)
    private Integer limit = 10;


    @ApiModelProperty(value = "权限")
    private String rule;


    @ApiModelProperty(value = "权限名称",required = true)
    private String ruleName;



    @Tolerate
    public RulePageBean() {
    }
}
