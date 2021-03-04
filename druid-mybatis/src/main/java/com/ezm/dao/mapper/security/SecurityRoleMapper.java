package com.ezm.dao.mapper.security;

import com.ezm.dao.base.BaseMapper;
import com.ezm.entity.bean.AddRoleBean;
import com.ezm.entity.bean.UpdateRoleBean;
import com.ezm.entity.table.SecurityRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SecurityRoleMapper extends BaseMapper<SecurityRole> {



    @Select("SELECT sr.* from security_role sr\n" +
            "left join security_role_user sru\n" +
            "on sr.id = sru.role_id \n" +
            "WHERE sru.user_id = #{userId}")
    List<SecurityRole> findByUserId(int userId);


    @Select("SELECT sr.* from security_role sr\n" +
            "left join security_role_rule srr\n" +
            "on sr.id = srr.role_id \n" +
            "WHERE srr.rule_id = #{ruleId}")
    List<SecurityRole> findByRuleId(int ruleId);


    @Select("select * from security_role where id = #{id}")
    SecurityRole findById(int id);

    @Select("select * from security_role where id = #{id} and enabled = #{enabled}")
    SecurityRole findByIdAndEnabled(int id,String enabled);

    @Insert("INSERT INTO security_role(role,role_name,create_date,update_date,enabled) VALUES(#{role},#{roleName},#{createDate},#{updateDate},#{enabled})")
    int addRole(AddRoleBean addRoleBean);

    @Select("SELECT * FROM security_role WHERE role_name = #{roleName}")
    SecurityRole findByRoleName(String roleName);

    @Select("SELECT * FROM security_role WHERE role = #{role}")
    SecurityRole findByRole(String role);

    @Delete("DELETE FROM security_role WHERE id = #{roleId} limit 2")
    int delRole(int roleId);

    @Update("update security_role set enabled = #{enabled} WHERE id= #{id}")
    int stopRole(SecurityRole securityRole);

    @Update("update security_role set enabled = #{enabled},role = #{role},role_name = #{roleName},update_date = #{updateDate} WHERE id= #{id}")
    int editRole(UpdateRoleBean updateRoleBean);
}
