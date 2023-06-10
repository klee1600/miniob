package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.Credit.CreditVO;
import com.nju.edu.erp.service.CreditService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/Credit")
public class CreditController {

    @Autowired
    private CreditService Creditservice;


    /**
     * 财物人员制定收款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(@RequestBody CreditVO vo) {
        Creditservice.makeSheet(vo);
        return Response.buildSuccess();
    }

    /**
     * 总经理审批
     *
     * @param SKDId 收款单id
     * @param state 修改后的状态("审批失败"/"审批成功")
     */
    @GetMapping(value = "/approval")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response approval(@RequestParam("SKDId") String SKDId,
                             @RequestParam("state") AccountSheetState state) {
        if (state.equals(AccountSheetState.FAILURE) || state.equals(AccountSheetState.SUCCESS)
            || state.equals(AccountSheetState.DONE)) {
            Creditservice.approval(SKDId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("500", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看收款单
     */
    @GetMapping(value = "/sheet-show")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response showSheetByState(@RequestParam(value = "state", required = false) AccountSheetState state) {
        return Response.buildSuccess(Creditservice.findSheetByState(state));
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
        return Response.buildSuccess(Creditservice.getCreditSheetByTime(beginDateStr, endDateStr));
    }


}
