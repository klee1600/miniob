package com.nju.edu.erp.web.controller;


import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.salary.AnnualVO;
import com.nju.edu.erp.service.AnnualService;
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
@RequestMapping(path = "/annual")
public class AnnualController {

    @Autowired
    @Qualifier("AnnualServiceImpl")
    private AnnualService annualService;

    /**
     * 总经理制定年终奖
     */
    @PostMapping(value = "/makeAnnual")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response makeAnnual(@RequestBody AnnualVO vo) {
        annualService.makeAnnual(vo);
        return Response.buildSuccess();
    }

    /**
     * 查询所有没制定年终奖的用户
     */
    @GetMapping(value = "/getAll")
    @Authorized(roles = {Role.GM, Role.ADMIN})
    public Response getAll(@RequestParam Integer year) {
        return Response.buildSuccess(annualService.getUidWithAnnual(year));
    }
}
