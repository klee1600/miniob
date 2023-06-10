package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/findByType")
    public Response findByType(@RequestParam CustomerType type) {
        return Response.buildSuccess(customerService.getCustomersByType(type));
    }

    @GetMapping("/findByInitialType")
    public Response findByInitialType(@RequestParam CustomerType type) {
        return Response.buildSuccess(customerService.getInitialCustomersByType(type));
    }

    @GetMapping("/delete")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.SALE_STAFF})
    public Response delete(@RequestParam Integer id) {
        customerService.delete(id);
        return Response.buildSuccess();
    }


    @PostMapping("/add")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.SALE_STAFF})
    public Response add(@RequestBody CustomerVO customerVO) {
        customerService.add(customerVO);
        return Response.buildSuccess();
    }

    @PostMapping("/initial_add")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.SALE_STAFF})
    public Response initial_add(@RequestBody CustomerVO customerVO) {
        customerService.initial_add(customerVO);
        return Response.buildSuccess();
    }

    @PostMapping("/modify")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.SALE_STAFF})
    public Response modify(@RequestBody CustomerVO customerVO) {
        customerService.modify(customerVO);
        return Response.buildSuccess();
    }

}
