package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.SaleDetailVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class SaleDetailServiceTest {

    @Autowired
    SaleDetailService saleDetailService;

    @Test
    @Transactional
    @Rollback(value = true)
    public void getSaleDetailTest() { //测试查看销售明细表
        String salesman = "xiaoshoujingli";
        String customer = "lxs";
        String pname = "戴尔电脑";
        String beginDateStr = "2022-05-24 00:04:00";
        String endDateStr = "2022-05-24 00:05:00";
        List<SaleDetailVO> actual = saleDetailService.getSaleDetailList(salesman, customer, pname, beginDateStr, endDateStr);
        Assertions.assertEquals(2, actual.size());

        SaleDetailVO actual_0 = actual.get(0);
        Assertions.assertEquals("2022-05-24", actual_0.getCreateDate());
        Assertions.assertEquals("戴尔电脑", actual_0.getPname());
        Assertions.assertEquals("戴尔(DELL)Vostro笔记本电脑5410 123色 戴尔成就3500Vostro1625D", actual_0.getType());
        Assertions.assertEquals(600, actual_0.getQuantity());
        Assertions.assertEquals(3500.00, actual_0.getUnitPrice().doubleValue());
        Assertions.assertEquals(2100000.00, actual_0.getTotalPrice().doubleValue());
        Assertions.assertEquals("Sale", actual_0.getDetailType());

        SaleDetailVO actual_1 = actual.get(1);
        Assertions.assertEquals("2022-05-24", actual_1.getCreateDate());
        Assertions.assertEquals("戴尔电脑", actual_1.getPname());
        Assertions.assertEquals("戴尔(DELL)Vostro笔记本电脑5410 123色 戴尔成就3500Vostro1625D", actual_1.getType());
        Assertions.assertEquals(600, actual_1.getQuantity());
        Assertions.assertEquals(3500.00, actual_1.getUnitPrice().doubleValue());
        Assertions.assertEquals(2100000.00, actual_1.getTotalPrice().doubleValue());
        Assertions.assertEquals("SaleReturns", actual_1.getDetailType());
    }

}
