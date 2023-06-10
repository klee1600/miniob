package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.SaleDetailService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/saleDetail")
public class SaleDetailController {

    private final SaleDetailService saleDetailService;

    @Autowired
    public SaleDetailController(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    /**
     * 根据以下参数筛选销售明细
     * @param salesman 销售员
     * @param customer 客户名
     * @param pname 商品名
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return
     * 要求导出excel
     */
    @GetMapping(value = "/getSaleDetail")
    @Authorized(roles =  {Role.FINANCIAL_STAFF, Role.ADMIN,Role.GM})
    public Response getSaleDetailList(@RequestParam String salesman, @RequestParam String customer, @RequestParam String pname, @RequestParam String beginDateStr, @RequestParam String endDateStr) {
        return Response.buildSuccess(saleDetailService.getSaleDetailList(salesman, customer, pname, beginDateStr, endDateStr));
    }

//    /**
//     * 销售明细导出
//     */
//    @PostMapping(value = "/getSaleDetail/download")
//    @Authorized(roles =  {Role.FINANCIAL_STAFF, Role.ADMIN})
//    public void download(HttpServletResponse response, @RequestParam String salesman, @RequestParam String customer, @RequestParam String pname, @RequestParam String beginDateStr, @RequestParam String endDateStr) {
//        saleDetailService.download(response, salesman, customer, pname, beginDateStr, endDateStr);
//    }

}
