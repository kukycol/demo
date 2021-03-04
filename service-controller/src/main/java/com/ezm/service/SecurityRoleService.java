package com.ezm.service;

import com.ezm.common.response.Result;
import com.ezm.entity.bean.*;

public interface SecurityRoleService {

    Result findAll(RolePageBean rolePageBean);

    Result addRole(AddRoleBean addRoleBean);

    Result delRole(int roleId);

    Result stopRole(int roleId);

    Result editRole(UpdateRoleBean updateRoleBean);

    Result findRoleApi1(RulePageBean pageBean);

    Result findRoleApi2(RulePageBean pageBean);

    Result addRoleApi(int roleId, int apiId);

    Result delRoleApi(int roleId, int apiId);

    Result findRoleMenu(int roleId);

    Result editMenu(UpdateMenuBean updateMenuBean);

    Result findRoleList();

    Result findUserRoleList(int userId);

    Result startRole(Integer roleId);
}
