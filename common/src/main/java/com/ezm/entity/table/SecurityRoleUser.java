package com.ezm.entity.table;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * (SecurityRoleUser)实体类
 *
 * @author makejava
 * @since 2021-01-22 22:24:19
 */
@Data
@Builder
@Table(name = "security_role_user")
public class SecurityRoleUser implements Serializable {
    private static final long serialVersionUID = 533012380735623996L;


    @Id
    @Column(name = "id")
    private Integer id;


    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;


    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Integer userId;


    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "enabled")
    private String enabled;


    @Tolerate
    public SecurityRoleUser() {
    }

}