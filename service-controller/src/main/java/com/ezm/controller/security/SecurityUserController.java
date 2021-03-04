package com.ezm.controller.security;

import com.ezm.common.response.Result;
import com.ezm.entity.bean.AddUserBean;
import com.ezm.entity.bean.UpdateUserBean;
import com.ezm.entity.bean.UserPageBean;
import com.ezm.service.SecurityUserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = {"用户Controller"}, value = "用户操作接口")
@ApiSupport(author = "梁港生")
@ApiResponses({
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 404, message = "找不到请求路径"),
        @ApiResponse(code = 500, message = "服务器异常")
})
@RestController
@RequestMapping("/user")
public class SecurityUserController {

    @Resource
    private SecurityUserService securityUserService;



    @ApiOperation(value = "获取用户列表", notes = "列表、分页、搜索三合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userPageBean", value = "搜索条件和分页条件dto", dataTypeClass = UserPageBean.class)
    })
    @GetMapping(value = "/findAll")
    public Result findAll(@Valid UserPageBean userPageBean) {
        return securityUserService.findAll(userPageBean);
    }



    @ApiOperation(value = "添加用户", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addUserBean", value = "用户信息dto", dataTypeClass = AddUserBean.class)
    })
    @PostMapping("/addUser")
    public Result addUser(@RequestBody @Validated AddUserBean addUserBean) {
        return securityUserService.addUser(addUserBean);
    }



    @ApiOperation(value = "编辑用户", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", value = "更新用户信息dto", dataTypeClass = UpdateUserBean.class)
    })
    @PutMapping("/editUser")
    public Result editUser(@RequestBody @Validated UpdateUserBean a) {
        return securityUserService.editUser(a);
    }



    @ApiOperation(value = "删除用户", notes = "先删除角色再删用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识号", dataTypeClass = Integer.class, required = true)
    })
    @DeleteMapping("/delUser/{userId}")
    public Result delUser(@PathVariable("userId") Integer userId) {
        return securityUserService.delUser(userId);
    }



    @ApiOperation(value = "停用用户", notes = "停用用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识号", dataTypeClass = Integer.class, required = true)
    })
    @PutMapping("/stopUser/{userId}")
    public Result stopUser(@PathVariable("userId") Integer userId) {
        return securityUserService.stopUser(userId);
    }



    @ApiOperation(value = "启用用户", notes = "启用用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识号", dataTypeClass = Integer.class, required = true)
    })
    @PutMapping("/startUser/{userId}")
    public Result startUser(@PathVariable("userId") Integer userId) {
        return securityUserService.startUser(userId);
    }



    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识号", dataTypeClass = Integer.class, required = true)
    })
    @PutMapping("/updatePassword/{userId}")
    public Result updatePassword(@PathVariable("userId") Integer userId) {
        return securityUserService.updatePassword(userId);
    }


}


