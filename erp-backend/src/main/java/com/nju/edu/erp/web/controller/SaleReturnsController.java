package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.CustomerPurchaseAmountPO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetVO;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/sale-returns")
public class SaleReturnsController {

    private final SaleReturnsService saleReturnsService;

    @Autowired
    public SaleReturnsController(SaleReturnsService saleReturnsService) {
        this.saleReturnsService = saleReturnsService;
    }

    /**
     * 销售人员制定销售退货单
     */
    @Authorized (roles = {Role.SALE_STAFF, Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(UserVO userVO, @RequestBody SaleReturnsSheetVO saleReturnsSheetVO)  {
        saleReturnsService.makePurchaseReturnsSheet(userVO, saleReturnsSheetVO);
        return Response.buildSuccess();
    }

    /**
     * 销售经理审批
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state 修改后的状态("审批失败"/"待二级审批")
     */
    @GetMapping(value = "/first-approval")
    @Authorized (roles = {Role.SALE_MANAGER, Role.GM, Role.ADMIN})
    public Response firstApproval(@RequestParam("purchaseReturnsSheetId") String purchaseReturnsSheetId,
                                  @RequestParam("state") SaleReturnsSheetState state)  {
        if(state.equals(SaleReturnsSheetState.FAILURE) || state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
            saleReturnsService.approval(purchaseReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("400","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 总经理审批
     * @param purchaseReturnsSheetId 进货退货单id
     * @param state 修改后的状态("审批失败"/"审批完成")
     */
    @Authorized (roles = {Role.GM, Role.ADMIN})
    @GetMapping(value = "/second-approval")
    public Response secondApproval(@RequestParam("purchaseReturnsSheetId") String purchaseReturnsSheetId,
                                   @RequestParam("state") SaleReturnsSheetState state)  {
        if(state.equals(SaleReturnsSheetState.FAILURE) || state.equals(SaleReturnsSheetState.SUCCESS)) {
            saleReturnsService.approval(purchaseReturnsSheetId, state);
            return Response.buildSuccess();
        } else {
            return Response.buildFailed("400","操作失败"); // code可能得改一个其他的
        }
    }

    /**
     * 根据状态查看进货退货单
     */
    @GetMapping(value = "/sheet-show")
    public Response showSheetByState(@RequestParam(value = "state", required = false) SaleReturnsSheetState state)  {
        return Response.buildSuccess(saleReturnsService.getSalesReturnsSheetByState(state));
    }

    /**
     * 根据时间查看进货退货单
     */
    @GetMapping(value = "/sheet-show-time")
    @Authorized(roles =  {Role.FINANCIAL_STAFF, Role.ADMIN,Role.GM})
    public Response getSheetByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        return Response.buildSuccess(saleReturnsService.getSaleReturnsSheetByTime(beginDateStr, endDateStr));
    }

}
