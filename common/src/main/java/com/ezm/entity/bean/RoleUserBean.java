package com.ezm.entity.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
public class RoleUserBean {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer roleId;

    @NotNull
    private Boolean flag;

    private Date createDate;

    @Tolerate
    public RoleUserBean() {
    }
}
