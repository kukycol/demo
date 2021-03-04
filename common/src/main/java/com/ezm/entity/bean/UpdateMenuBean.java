package com.ezm.entity.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel(description = "更新菜单dto")
@Data
@Builder
public class UpdateMenuBean {

    @ApiModelProperty(value = "角色标识号",required = true)
    @NotNull
    private Integer roleId;

    @ApiModelProperty(value = "权限标识号",required = true)
    private Integer ruleId;

    @ApiModelProperty(value = "勾选的菜单",required = true)
    @Size(min = 1)
    private List<MenuTreeBean> menuTreeBeanList;

    @ApiModelProperty(value = "标识(true添加 | false删除)",required = true)
    @NotNull
    private Boolean flag;

    @ApiModelProperty(value = "菜单添加时间")
    private Date createDate;

    @Tolerate
    public UpdateMenuBean() {
    }
}
