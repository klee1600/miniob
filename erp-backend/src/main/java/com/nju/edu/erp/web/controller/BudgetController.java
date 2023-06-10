package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.Budget.AmountDiscountVO;
import com.nju.edu.erp.model.vo.Budget.AmountOffsetVO;
import com.nju.edu.erp.model.vo.Budget.LevelDiscountVO;
import com.nju.edu.erp.model.vo.Budget.LevelOffsetVO;
import com.nju.edu.erp.model.vo.account.AddAccountVO;
import com.nju.edu.erp.model.vo.account.UpdateAccountVO;
import com.nju.edu.erp.service.AccountService;
import com.nju.edu.erp.service.BudgetService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/amount-discount")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response amountdiscount(@RequestBody AmountDiscountVO vo) {
        budgetService.amountdiscount(vo);
        return Response.buildSuccess();
    }

    @PostMapping("/amount-offset")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response amountoffset(@RequestBody AmountOffsetVO vo) {
        budgetService.amountoffset(vo);
        return Response.buildSuccess();
    }

    @PostMapping("/level-discount")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response leveldiscount(@RequestBody LevelDiscountVO vo) {
        budgetService.leveldiscount(vo);
        return Response.buildSuccess();
    }

    @PostMapping("/level-offset")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response leveloffset(@RequestBody LevelOffsetVO vo) {
        budgetService.leveloffset(vo);
        return Response.buildSuccess();
    }

    @PostMapping("/amountdiscountquery")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getamountdiscount() {
        return Response.buildSuccess(budgetService.getamountdiscount());
    }

    @PostMapping("/amountoffsetquery")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getamountoffset() {
        return Response.buildSuccess(budgetService.getamountoffset());
    }

    @PostMapping("/leveldiscount-query")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getleveldiscount() {
        return Response.buildSuccess(budgetService.getleveldiscount());
    }

    @PostMapping("/leveloffset-query")
    @Authorized(roles = {Role.ADMIN,Role.GM})
    public Response getleveloffset() {
        return Response.buildSuccess(budgetService.getleveloffset());
    }

}
