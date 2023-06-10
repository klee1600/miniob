package com.nju.edu.erp.service.Impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.nju.edu.erp.dao.SaleDetailDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.entity.SaleDetailView;
import com.nju.edu.erp.model.vo.SaleDetailVO;
import com.nju.edu.erp.service.SaleDetailService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SaleDetailServiceImpl implements SaleDetailService {

    private final SaleDetailDao saleDetailDao;

    @Autowired
    public SaleDetailServiceImpl(SaleDetailDao saleDetailDao) {
        this.saleDetailDao = saleDetailDao;
    }

    /**
     * 根据以下参数筛选销售明细
     * @param salesman 销售员
     * @param customer 客户名
     * @param pname 商品名
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return List<SaleDetailVO>
     * 要求导出excel
     */
    @Override
    public List<SaleDetailVO> getSaleDetailList(String salesman, String customer, String pname, String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SaleDetailVO> result;
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                result = saleDetailDao.getSaleDetailList(salesman, customer, pname, beginTime, endTime);
                result.addAll(saleDetailDao.getSaleReturnsDetailList(salesman, customer, pname, beginTime, endTime));
                for (SaleDetailVO vo : result)
                    vo.setCreateDate(vo.getCreateDate().split(" ")[0]);
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 销售明细导出
     */
    @Override
    public void download(HttpServletResponse response, String salesman, String customer, String pname, String beginDateStr, String endDateStr) {
        OutputStream out = null;
        try {
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("销售明细表.xls", "UTF-8"));
            out = response.getOutputStream();
            Workbook workbook = getWorkbook(salesman, customer, pname, beginDateStr, endDateStr);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            throw new MyServiceException("503", "网络异常");
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Workbook getWorkbook(String salesman, String customer, String pname, String beginDateStr, String endDateStr) {
        List<SaleDetailVO> arr = getSaleDetailList(salesman, customer, pname, beginDateStr, endDateStr);
        List<SaleDetailView> view = new ArrayList<>();
        for (SaleDetailVO vo : arr)
            view.add(SaleDetailView.VO2View(vo));
        ExportParams params = new ExportParams("销售明细表", "销售明细表", ExcelType.HSSF);
        return ExcelExportUtil.exportExcel(params, SaleDetailView.class, view);
    }
}
