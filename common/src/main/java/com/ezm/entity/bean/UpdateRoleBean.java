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

@ApiModel(description = "编辑角色dto")
@Data
@Builder
public class UpdateRoleBean {

    @ApiModelProperty(value = "角色标识号", required = true)
    @NotNull
    private Integer id;


    @ApiModelProperty(value = "角色", required = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z1-9]{2,30}$", message = "由英文字母和数字组成")
    private String role;


    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    @Pattern(regexp = "^[\u4e00-\u9fa5A-Za-z1-9]{2,30}$", message = "不允许特殊符号")
    private String roleName;


    @ApiModelProperty(value = "启用",required = true)
    @NotBlank
    @Pattern(regexp = "^[01]{1}$", message = "只能是1或0")
    private String enabled;

    @ApiModelProperty(value = "角色添加时间")
    private Date createDate;


    @ApiModelProperty(value = "角色更新时间")
    private Date updateDate;


    @Tolerate
    public UpdateRoleBean() {
    }

}
