package com.ezm.common.response;


/**
 * 请求响应枚举
 */
public enum ResultEnum {

    //layui 表格响应code 0:成功/1:失败

    //查询
    querySuccessful(0,"查询成功"),
    generateSuccessful(2,"菜单生成成功！")
    ,queryFails(1,"查询失败")

    //增加
    ,addSuccessful (0,"添加成功")
    ,operationSuccessful (0,"添加成功")
    ,authorizationSuccessful (0,"授权成功")
    ,accountStartSuccessful (0,"账号启用成功")
    ,addFails (1,"添加失败")


    //更新
    ,updateSuccessful (0,"更新成功")
    ,accountStopSuccessful (0,"账号停用成功")
    ,editUser (0,"没有修改新基本信息的数据")
    ,RestPwdSuccessful (0,"密码重置成功")
    ,updateFails (1,"更新失败")

    //删除
    ,deleteSuccessful (0,"删除成功")
    ,RoleSuccessful (0,"成功取消角色")
    ,deleteFails (1,"删除失败")

    //session
    ,sessionOut(1,"会话过期")
    ,sessionExist(0,"会话未过期")

    //security
    ,accountNonLocked(1,"账号锁定")
    ,accountNonExpired(1,"账号过期")
    ,credentialsNonExpired(1,"密码过期")
    ,enabled(1,"用户未启用")
    ,wrongPassword(1,"账号或密码错误")
    ,accountNotExist(1,"账号不存在")
    ,loginSuccessful(0,"登录成功")
    ,loginFails(1,"登录失败")
    ,permissionDenied(1,"权限不足")

    //user
    ,unameExist(1,"用户名已存在")
    ,accountExist(1,"登录名已存在")
    ,roleNotExist(1,"角色不存在")
    ,roleExist(1,"role已存在")
    ,roleNameExist(1,"roleName已存在")
    ,emailExist(1,"邮箱已存在")
    ,moreEmailData(1,"您输入的邮箱地址存在异常，请联系管理员!")
    ,moreUnameData(1,"您输入的用户名存在异常，请联系管理员!")
    ,moreAccountData(1,"您输入的账号存在异常，请联系管理员!")
    ,moreMobileData(1,"您输入的手机号码存在异常，请联系管理员!")
    ,mobileExist(1,"手机号码已存在")
    ,roleUserExist(1,"请解除用户绑定的角色后再进行删除")
    ,roleRuleExist(1,"请解除权限绑定的角色后再进行删除")
    ,trueAndFalse(1,"flag参数值只能允许true and false")
    ,userRoleExist(1,"用户已存在该角色")
    ,stopUserExist(1,"用户已是停用状态")
    ,startUserExist(1,"用户已是启用状态")
    ,initialPasswordExist(1,"已是初始密码")

    ;

    //属性
    private int code;

    private String msg;

    //get..set
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //构造
    ResultEnum() {
    }

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
