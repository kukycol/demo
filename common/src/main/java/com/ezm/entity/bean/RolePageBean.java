package com.ezm.entity.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@ApiModel(description = "搜索条件和分页条件dto")
@Data
@Builder
public class RolePageBean {


    @ApiModelProperty(value = "偏移量",required = true)
    @NotNull
    @Range(max = 10000000,min = 1)
    private Integer page = 1;


    @ApiModelProperty(value = "页条数",required = true)
    @NotNull
    @Range(max = 100,min = 1)
    private Integer limit = 10;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "角色名称")
    private String roleName;


    @Tolerate
    public RolePageBean() {
    }
}
