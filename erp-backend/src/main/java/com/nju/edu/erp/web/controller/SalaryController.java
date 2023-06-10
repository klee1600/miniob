package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.salary.SalaryFullVO;
import com.nju.edu.erp.service.SalaryService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/salary")
public class SalaryController {

    @Autowired
    @Qualifier("SalaryServiceImpl")
    private SalaryService salaryService;


    /**
     * 查询所有已制定的薪酬
     */
    @GetMapping(value = "/getAll")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response getAll() {
        return Response.buildSuccess(salaryService.getAllSalary());
    }

    /**
     * 制定新的薪酬
     */
    @PostMapping(value = "/upd")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response upd(@RequestBody SalaryFullVO vo) {
        salaryService.upd(vo);
        return Response.buildSuccess();
    }

}
