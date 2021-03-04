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
 * (SecurityRole)实体类
 *
 * @author makejava
 * @since 2021-01-22 22:24:30
 */
@ApiModel(description = "角色dto")
@Data
@Builder
@Table(name = "security_role")
public class SecurityRole implements Serializable {
    private static final long serialVersionUID = -70537096686108911L;


    /**
     * 角色编号
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "角色标识号")
    private Integer id;


    /**
     * 角色
     */
    @Column(name = "role")
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{2,20}$",message = "只能由英文字母组成且只允许2-20个字母")
    @ApiModelProperty(value = "角色")
    private String role;


    /**
     * 角色昵称
     */
    @Column(name = "role_name")
    @NotBlank
    @Pattern(regexp = "^[\u4e00-\u9fa5]{2,10}$",message = "用户名只能是汉字且只允许2-10个汉字")
    @ApiModelProperty(value = "角色名称")
    private String roleName;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "角色添加时间")
    @Column(name = "create_date")
    private Date createDate;


    /**
     * 更新时间
     */
    @Column(name = "update_date")
    @ApiModelProperty(value = "角色更新时间")
    private Date updateDate;

    /**
     * 角色启用
     */
    @Column(name = "enabled")
    @NotBlank
    @Pattern(regexp = "^[0-1]{0,1}$",message = "只允许0和1")
    @ApiModelProperty(value = "启用状态")
    private String enabled;

    @Transient
    private boolean used;

    @Transient
    private boolean user;

    @Transient
    private boolean rule;

    @Transient
    private boolean LAY_CHECKED;


    @Tolerate
    public SecurityRole() {
    }

}