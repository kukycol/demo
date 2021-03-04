package com.ezm.entity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@ApiModel(description = "更新ruleName dto")
@Data
@Builder
public class UpdateRuleNameBean {

    @ApiModelProperty(value = "权限标识号")
    @NotNull
    private Integer id;


    @ApiModelProperty(value = "权限名称",required = true)
    @NotBlank
    @Pattern(regexp = "^[\u4e00-\u9fa5A-Za-z1-9]{2,30}$",message = "不允许特殊符号")
    private String ruleName;


    @ApiModelProperty(value = "权限更新时间")
    private Date updateDate;





    @Tolerate
    public UpdateRuleNameBean() {
    }

}
