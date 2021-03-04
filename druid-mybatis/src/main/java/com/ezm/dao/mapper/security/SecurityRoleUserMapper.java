package com.ezm.dao.mapper.security;

import com.ezm.dao.base.BaseMapper;
import com.ezm.entity.bean.RoleUserBean;
import com.ezm.entity.table.SecurityRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SecurityRoleUserMapper extends BaseMapper<SecurityRoleUser> {


    @Select("SELECT * FROM security_role_user WHERE user_id = #{userId}")
    List<SecurityRoleUser> findByUserId(int userId);

    @Delete("DELETE  FROM security_role_user WHERE user_id = #{userId} and role_id = #{roleId}")
    int delRole(RoleUserBean userBean);

    @Delete("DELETE  FROM security_role_user WHERE user_id = #{userId} ")
    int delByUserId(int userId);

    @Insert("INSERT INTO security_role_user(user_id,role_id,create_date) VALUES(#{userId},#{roleId},#{createDate})")
    int addRole(RoleUserBean userBean);

    @Select("select * from security_role_user where user_id = #{userId} and role_id = #{roleId}")
    SecurityRoleUser findByUserIdAndRoleId(RoleUserBean userBean);

    @Select("SELECT * from security_role_user WHERE role_id = #{roleId} limit 2")
    List<SecurityRoleUser> findByRoleId(int roleId);

    @Update("update security_role_user set enabled = #{enabled} WHERE id = #{id}")
    int stopUser(SecurityRoleUser securityRoleUser);

}
