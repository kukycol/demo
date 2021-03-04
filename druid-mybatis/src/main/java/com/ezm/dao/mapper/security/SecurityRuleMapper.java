package com.ezm.dao.mapper.security;

import com.ezm.dao.base.BaseMapper;
import com.ezm.entity.bean.*;
import com.ezm.entity.table.SecurityRule;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface SecurityRuleMapper extends BaseMapper<SecurityRule> {

    @Update("update security_rule set rule = #{rule},update_date = #{updateDate} where id = #{id} ")
    int updateRuleAddr(UpdateRuleAddrBean updateRuleAddrBean);

    @Update("update security_rule set rule_name = #{ruleName},update_date = #{updateDate} where id = #{id} ")
    int updateRuleName(UpdateRuleNameBean updateRuleNameBean);

    @Insert("insert into security_rule(rule_name,create_date,update_date,level,enabled) values(#{ruleName},#{createDate},#{updateDate},#{level},#{enabled})")
    int addMenu(SecurityRule securityRule);

    @Select("SELECT * from security_rule WHERE rule = #{rule}")
    SecurityRule findByRule(String rule);

    @Select("SELECT * from security_rule WHERE id = #{id}")
    SecurityRule findById(int id);

    @Select("SELECT sr.* FROM `security_role_rule` srr\n" +
            "LEFT JOIN security_rule sr on srr.rule_id = sr.id\n" +
            "WHERE sr.`level` = '3' and srr.role_id = #{roleId}")
    List<SecurityRule> findByRoleId(int roleId);


    @SelectProvider(method = "findByRoleAndApi1", type = SecurityRuleProvider.class)
    List<SecurityRule> findByRoleAndApi1(RulePageBean pageBean);

    @SelectProvider(method = "findByRoleAndApi2", type = SecurityRuleProvider.class)
    List<SecurityRule> findByRoleAndApi2(RulePageBean pageBean);

    @SelectProvider(method = "findAll", type = SecurityRuleProvider.class)
    List<SecurityRule> findAll(RulePageBean rulePageBean);

    @Update("update security_rule set enabled = #{enabled},update_date = #{updateDate} where id = #{id}")
    int stopRule(SecurityRule securityRule);

    @Select("SELECT * from security_rule WHERE rule_name = #{ruleName}")
    SecurityRule findByRuleName(String ruleName);

    @Insert("insert into security_rule(rule,rule_name,enabled,update_date,create_date) values(#{rule},#{ruleName},#{enabled},#{updateDate},#{createDate})")
    int addRule(AddRuleBean addRuleBean);

    @Delete("delete from security_rule where id = #{ruleId}")
    int delRule(int ruleId);

    @Update("update security_rule set rule = #{rule},rule_name = #{ruleName},enabled = #{enabled},update_date = #{updateDate} where id = #{id}")
    int editRule(UpdateRuleBean updateUserBean);

    class SecurityRuleProvider {

        public String findByRoleAndApi1(RulePageBean pageBean) {
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT sr.* FROM `security_role_rule` srr\n" +
                    "LEFT JOIN security_rule sr on srr.rule_id = sr.id\n" +
                    "WHERE sr.`level` = '0' and srr.role_id = #{roleId} and sr.enabled = '1'");

            if (StringUtils.isNotBlank(pageBean.getRule())) {
                sb.append(" and sr.rule = #{rule} ");
            }
            if (StringUtils.isNotBlank(pageBean.getRuleName())) {
                sb.append(" and sr.rule_name = #{ruleName} ");
            }
            return sb.toString();
        }

        public String findByRoleAndApi2(RulePageBean pageBean) {
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT * from security_rule  where `level` = 0  and  id not in (\n" +
                    "SELECT sr.id FROM `security_role_rule` srr\n" +
                    "LEFT JOIN security_rule sr on srr.rule_id = sr.id\n" +
                    "WHERE sr.`level` = '0' and srr.role_id = #{roleId}) and enabled = '1'");

            if (StringUtils.isNotBlank(pageBean.getRule())) {
                sb.append(" and rule = #{rule} ");
            }
            if (StringUtils.isNotBlank(pageBean.getRuleName())) {
                sb.append(" and rule_name = #{ruleName} ");
            }
            return sb.toString();
        }

        public String findAll(RulePageBean rulePageBean) {
            StringBuffer sb = new StringBuffer();

            sb.append("select *  from security_rule where level = 0 ");

            if (StringUtils.isNotBlank(rulePageBean.getRule())) {
                sb.append(" and rule = #{rule}");
            }

            if (StringUtils.isNotBlank(rulePageBean.getRuleName())) {
                sb.append(" and rule_name = #{ruleName}");
            }

            return sb.toString();
        }
    }

}
