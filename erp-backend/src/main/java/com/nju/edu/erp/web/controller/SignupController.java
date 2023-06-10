package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.service.SignupService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/signup")
public class SignupController {

    @Autowired
    private SignupService signupService;

    /**
     * 员工打卡
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.INVENTORY_MANAGER,Role.FINANCIAL_STAFF,Role.HR})
    @GetMapping(value = "/tick")
    public Response tick(@RequestParam Integer uid)  {
        signupService.tick(uid);
        return Response.buildSuccess();
    }


    /**
     * 记录查询
     */
    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.INVENTORY_MANAGER,Role.FINANCIAL_STAFF,Role.HR})
    @GetMapping(value = "/query")
    public Response query(@RequestParam(value = "uid", required = false) Integer uid)  {
        return Response.buildSuccess(signupService.findByuid(uid));
    }
}
