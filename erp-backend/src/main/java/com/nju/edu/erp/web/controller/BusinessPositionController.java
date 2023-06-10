package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.service.BusinessPositionService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/BusinessPosition")
public class BusinessPositionController {

    private final BusinessPositionService businesspositionService;

    @Autowired
    public BusinessPositionController(BusinessPositionService businesspositionService) {
        this.businesspositionService = businesspositionService;
    }

    /**
     * 根据以下参数筛选经营情况表
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return
     * 要求导出excel
     */
    @GetMapping(value = "/getBusinessPosition")
    @Authorized(roles =  {Role.FINANCIAL_STAFF, Role.ADMIN,Role.GM})
    public Response getBusinessPosition(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        return Response.buildSuccess(businesspositionService.getBusinessPosition(beginDateStr, endDateStr));
    }

}
