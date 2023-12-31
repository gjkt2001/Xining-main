package com.xyz66.controller;

import com.xyz66.config.SwaggerConfig;
import com.xyz66.domain.ResponseResult;
import com.xyz66.domain.entity.LoginUser;
import com.xyz66.domain.entity.Menu;
import com.xyz66.domain.entity.User;
import com.xyz66.domain.vo.AdminUserInfoVo;
import com.xyz66.domain.vo.RoutersVo;
import com.xyz66.domain.vo.UserInfoVo;
import com.xyz66.enums.AppHttpCodeEnum;
import com.xyz66.exception.SystemException;
import com.xyz66.service.LoginService;
import com.xyz66.service.MenuService;
import com.xyz66.service.RoleService;
import com.xyz66.utils.BeanCopyUtils;
import com.xyz66.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xyz66 Email:2910223554@qq.com
 */
@Api(tags = SwaggerConfig.TAG_3)
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "用户登录")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @ApiOperation(value = "用户退出")
    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }


    @ApiOperation(value = "获取路由信息")
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}
