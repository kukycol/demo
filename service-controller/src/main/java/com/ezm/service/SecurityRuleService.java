package com.ezm.service;

import com.ezm.common.response.Result;
import com.ezm.entity.bean.*;
import com.ezm.entity.table.SecurityRule;

public interface SecurityRuleService {

    Result findAll(RulePageBean rulePageBean);

    Result stopRule(int ruleId);

    Result addRule(AddRuleBean addRuleBean);

    Result delRule(int ruleId);

    Result editRule(UpdateRuleBean addRuleBean);

    Result findMenuList();

    Result addMenu(int menuId);

    Result updateMenuName(UpdateRuleNameBean updateRuleNameBean);

    Result delMenu(int menuId);

    Result startRule(Integer ruleId);

    Result updateMenuAddr(UpdateRuleAddrBean updateRuleAddrBean);
}
