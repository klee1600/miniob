package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.user.EnrollmentVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.service.UserService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl")
    private  UserService userService;


    @PostMapping("/login")
    public Response userLogin(@RequestBody UserVO userVO) {
        return Response.buildSuccess(userService.login(userVO));
    }

    @PostMapping("/register")
    public Response userRegister(@RequestBody UserVO userVO) {
        userService.register(userVO);
        return Response.buildSuccess();
    }

    @GetMapping("/auth")
    public Response userAuth(@RequestParam(name = "token") String token) {
        return Response.buildSuccess(userService.auth(token));
    }

    @PostMapping("/enroll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response enroll(@RequestBody EnrollmentVO vo) {
        return Response.buildSuccess(userService.enrollment(vo));
    }

    @PostMapping("/upd")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response upd(@RequestBody EnrollmentVO vo) {
        return Response.buildSuccess(userService.update(vo));
    }

    @GetMapping("/del")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response del(@RequestParam Integer id) {
        userService.del(id);
        return Response.buildSuccess();
    }

    @PostMapping("/getAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response getAll() {
        return Response.buildSuccess(userService.getAll());
    }

    @GetMapping("/getId")
    public Response getId(@RequestParam String name) {
        return Response.buildSuccess(userService.getIdByName(name));
    }
}
