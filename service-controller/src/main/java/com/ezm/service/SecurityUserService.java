package com.ezm.service;

import com.ezm.common.response.Result;
import com.ezm.entity.bean.AddUserBean;
import com.ezm.entity.bean.RoleUserBean;
import com.ezm.entity.bean.UpdateUserBean;
import com.ezm.entity.bean.UserPageBean;

public interface SecurityUserService {

    Result findAll(UserPageBean u);

    Result addUser(AddUserBean a);

    Result delUser(int userId);

    Result stopUser(int userId);

    Result updatePassword(int userId);

    Result editUser(UpdateUserBean a);

    Result startUser(int userId);
}
