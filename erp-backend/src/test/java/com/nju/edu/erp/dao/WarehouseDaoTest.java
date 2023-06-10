package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.WarehouseIODetailPO;
import com.nju.edu.erp.model.po.WarehousePO;
import com.nju.edu.erp.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WarehouseDaoTest {

    @Autowired
    WarehouseDao warehouseDao;

    @Autowired
    WarehouseInputSheetDao warehouseInputSheetDao;

    @Autowired
    WarehouseOutputSheetDao warehouseOutputSheetDao;

    @Test
    void deductQuantity() {
//        warehouseDao.deductQuantity(WarehousePO.builder().batchId(1).pid("0000000000400000").quantity(-56).build());
    }

    @Test
    void findAllNotZeroByPidSortedByBatchId() {
        List<WarehousePO> allNotZeroByPidSortedByBatchId = warehouseDao.findAllNotZeroByPidSortedByBatchId("0000000000500000");
        System.out.println(allNotZeroByPidSortedByBatchId);
    }

    @Autowired
    WarehouseService warehouseService;

    @Test
    void getWarehouseInputProductQuantityByTime() throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date b = simpleDateFormat.parse("2021-05-22 23:17:41");
        Date e = simpleDateFormat.parse("2022-05-24 00:00:00");
        Integer cnt = warehouseInputSheetDao.getWarehouseInputProductQuantityByTime(b, e);
        System.out.println(cnt);
    }

    @Test
    void getWarehouseInputProductQuantityByTimeDao() {
        String b = "2021-05-22 23:17:41";
        String e = "2023-05-24 23:17:41";
        Integer cnt = warehouseService.getWarehouseInputProductQuantityByTime(b, e);
        System.out.println(cnt);
    }

    @Test
    void getWarehouseOutputProductQuantityByTime() {
        String b = "2021-05-22 23:17:41";
        String e = "2023-05-24 23:17:41";
        Integer cnt = warehouseService.getWarehouseOutProductQuantityByTime(b, e);
        System.out.println(cnt);
    }

    @Test
    void getWarehouseInputDetailByTime() throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date b = simpleDateFormat.parse("2021-05-22 23:17:41");
        Date e = simpleDateFormat.parse("2022-05-24 00:00:00");
        List<WarehouseIODetailPO> arr = warehouseInputSheetDao.getWarehouseInputDetailByTime(b, e);
        System.out.println(arr);
    }

    @Test
    void getWarehouseOutputDetailByTime() throws ParseException {
        String b = "2021-05-22 23:17:41";
        String e = "2023-05-24 23:17:41";
        List<WarehouseIODetailPO> arr = warehouseService.getWarehouseIODetailByTime(b, e);
        System.out.println(arr);
    }

    @Test
    void getWarehouseIODetailByTime() throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date b = simpleDateFormat.parse("2021-05-22 23:17:41");
        Date e = simpleDateFormat.parse("2022-05-24 00:00:00");
        List<WarehouseIODetailPO> arr = warehouseOutputSheetDao.getWarehouseOutputDetailByTime(b, e);
        System.out.println(arr);
    }

}