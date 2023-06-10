package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.account.AddAccountVO;
import com.nju.edu.erp.model.vo.account.UpdateAccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/add")
    @Authorized(roles = {Role.ADMIN})
    public Response add(@RequestBody AddAccountVO vo) {
        accountService.addAccount(vo);
        return Response.buildSuccess();
    }

    @GetMapping("/del")
    @Authorized(roles = {Role.ADMIN})
    public Response del(@RequestParam Long id) {
        accountService.delAccount(id);
        return Response.buildSuccess();
    }

    @PostMapping("/update")
    @Authorized(roles = {Role.ADMIN})
    public Response update(@RequestBody UpdateAccountVO vo) {
        accountService.updAccount(vo);
        return Response.buildSuccess();
    }

    @PostMapping("/getAll")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getAll() {
        return Response.buildSuccess(accountService.getAllAccount());
    }

    @PostMapping("/getInitialAll")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getInitialAll() {
        return Response.buildSuccess(accountService.getAllInitialAccount());
    }


    @PostMapping("/initial_add")
    @Authorized(roles = {Role.ADMIN})
    public Response initial_add(@RequestBody AddAccountVO vo) {
        accountService.addInitial_Account(vo);
        return Response.buildSuccess();
    }
}
