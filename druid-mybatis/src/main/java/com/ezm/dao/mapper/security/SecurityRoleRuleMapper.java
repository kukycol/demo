package com.ezm.dao.mapper.security;

import com.ezm.dao.base.BaseMapper;
import com.ezm.entity.bean.UpdateMenuBean;
import com.ezm.entity.table.SecurityRoleRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SecurityRoleRuleMapper extends BaseMapper<SecurityRoleRule> {


    @Select("SELECT * from security_role_rule WHERE role_id = #{roleId} limit 2")
    List<SecurityRoleRule> findByRoleId(int roleId);

    @Insert("INSERT INTO security_role_rule(role_id,rule_id,create_date) VALUES(#{roleId},#{ruleId},#{createDate})")
    int addRoleRule(UpdateMenuBean updateMenuBean);

    @Delete("DELETE FROM security_role_rule WHERE role_id = #{roleId} and rule_id = #{ruleId}")
    int delRoleRule(UpdateMenuBean updateMenuBean);


    @Delete("DELETE FROM security_role_rule WHERE  rule_id = #{ruleId}")
    int delRoleRuleByRuleID(int ruleId);

    @Select("select * from security_role_rule where rule_id = #{ruleId}")
    List<SecurityRoleRule> findByRuleId(int ruleId);

    @Update("update security_role_rule set enabled = 0 where rule_id = #{ruleId}")
    int stopRoleRules(int ruleId);
}
