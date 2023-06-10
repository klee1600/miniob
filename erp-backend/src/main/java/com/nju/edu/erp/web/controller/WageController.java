package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.Wage.DateVO;
import com.nju.edu.erp.model.vo.Wage.UserAndDateVO;
import com.nju.edu.erp.model.vo.Wage.WageVO;
import com.nju.edu.erp.service.WageService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/Wage")
public class WageController {

    @Qualifier("WageServiceImpl")
    @Autowired
    private WageService Wageservice;


    /**
     * HR制定工资单
     */
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makeSheet(@RequestBody UserAndDateVO vo) {
        return Response.buildSuccess(Wageservice.makeSheet(vo));
    }

    /**
     * 总经理审批
     *
     * @param GZDId 工资单id
     * @param state 修改后的状态("审批失败"/"审批成功")
     */
    @GetMapping(value = "/approval")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response approval(@RequestParam("GZDId") String GZDId,
                             @RequestParam("state") AccountSheetState state) {
        if (state.equals(AccountSheetState.FAILURE) || state.equals(AccountSheetState.SUCCESS)) {
            Wageservice.approval(GZDId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("500", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 财务人员发钱
     *
     * @param GZDId 工资单id
     * @param state 修改后的状态("审批失败"/"已发钱")
     */
    @GetMapping(value = "/transfer")
    @Authorized(roles = {Role.GM, Role.FINANCIAL_STAFF, Role.ADMIN})
    public Response transfer(@RequestParam("GZDId") String GZDId,
                             @RequestParam("state") AccountSheetState state) {
        if (state.equals(AccountSheetState.FAILURE) || state.equals(AccountSheetState.DONE)) {
            Wageservice.approval(GZDId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("500", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看工资单
     */
    @GetMapping(value = "/sheet-show")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response showSheetByState(@RequestParam(value = "state", required = false) AccountSheetState state) {
        return Response.buildSuccess(Wageservice.findSheetByState(state));
    }

    /**
     * 查询本月没有创建工资单的用户
     */
    @PostMapping(value = "/getUser")
    @Authorized(roles = {Role.HR, Role.GM, Role.ADMIN})
    public Response getUser(@RequestBody DateVO vo) {
        return Response.buildSuccess(Wageservice.getUserWithoutPayroll(vo));
    }

    /**
     * 查询某年某个用户的工资单
     */
    @GetMapping(value = "/getYearlyTotal")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response getYearlyTotal(@RequestParam Integer uid, @RequestParam Integer year) {
        return Response.buildSuccess(Wageservice.getYearlyTotal(uid, year));
    }



    /**
     * 根据时间筛选销售单
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return
     * 要求导出excel
     */
    @GetMapping(value = "/sheet-show-time")
    @Authorized(roles =  {Role.FINANCIAL_STAFF, Role.ADMIN,Role.GM})
    public Response getSheetByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        return Response.buildSuccess(Wageservice.getWageSheetByTime(beginDateStr, endDateStr));
    }
}
