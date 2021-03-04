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
 * (SecurityRoleRule)实体类
 *
 * @author makejava
 * @since 2021-01-22 22:24:26
 */
@Data
@Builder
@Table(name = "security_role_rule")
public class SecurityRoleRule implements Serializable {
    private static final long serialVersionUID = 747904875808481043L;


    @Id
    @Column(name = "id")
    private Integer id;


    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private Integer roleId;


    /**
     * 权限编号
     */
    @Column(name = "rule_id")
    private Integer ruleId;


    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;


    @Tolerate
    public SecurityRoleRule() {
    }

}