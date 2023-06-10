package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path = "/initial")
public class InitialController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    @Authorized(roles = {Role.ADMIN, Role.GM})
    public Response create(HttpServletResponse response) {
       customerService.initial_create();
       productService.initialcreate();
       accountService.initialcreate();
       return Response.buildSuccess();
    }


}
