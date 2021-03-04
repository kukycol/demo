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
@Table(name = "layui_menu")
public class LayuiMenu implements Serializable {
    private static final long serialVersionUID = 533012380735623996L;


    @Id
    @Column(name = "id")
    private Integer id;


    /**
     * 父级菜单
     */
    @Column(name = "parent")
    private Integer parent;


    /**
     * 子级菜单
     */
    @Column(name = "sun")
    private Integer sun;


    @Column(name = "create_date")
    private Date createDate;



    @Tolerate
    public LayuiMenu() {
    }

}