package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.Payment.PaymentVO;
import com.nju.edu.erp.service.PaymentService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/Payment")
public class PaymentController {

    @Autowired
    private PaymentService Paymentservice;


    /**
     * 财物人员制定付款单
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(@RequestBody PaymentVO vo) {
        Paymentservice.makeSheet(vo);
        return Response.buildSuccess();
    }


    /**
     * 红冲
     */
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    @PostMapping(value = "/red-make")
    public Response makeRedOrder(@RequestBody PaymentVO vo) {
        Paymentservice.makeRedSheet(vo);
        return Response.buildSuccess();
    }

    /**
     * 总经理审批
     *
     * @param FKDId 付款单id
     * @param state 修改后的状态("审批失败"/"审批成功")
     */
    @GetMapping(value = "/approval")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response approval(@RequestParam("FKDId") String FKDId,
                             @RequestParam("state") AccountSheetState state) {
        if (state.equals(AccountSheetState.FAILURE) || state.equals(AccountSheetState.SUCCESS)
            || state.equals(AccountSheetState.DONE)) {
            Paymentservice.approval(FKDId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("500", "操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看付款单
     */
    @GetMapping(value = "/sheet-show")
    @Authorized(roles = {Role.FINANCIAL_STAFF, Role.GM, Role.ADMIN})
    public Response showSheetByState(@RequestParam(value = "state", required = false) AccountSheetState state) {
        return Response.buildSuccess(Paymentservice.findSheetByState(state));
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
        return Response.buildSuccess(Paymentservice.getPaymentSheetByTime(beginDateStr, endDateStr));
    }

}
