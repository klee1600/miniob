package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.BusinessPositionDao;
import com.nju.edu.erp.model.vo.BusinessPositionVO;
import com.nju.edu.erp.service.BusinessPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BusinessPositionServiceImpl implements BusinessPositionService {

    private final BusinessPositionDao businesspositionDao;

    @Autowired
    public BusinessPositionServiceImpl(BusinessPositionDao businesspositionDao) {
        this.businesspositionDao = businesspositionDao;
    }

    /**
     * 根据以下参数筛选经营情况表
     * @param beginDateStr 开始时间
     * @param endDateStr 结束时间
     * @return BusinessPositionVO
     * 要求导出excel
     */
    @Override
    public BusinessPositionVO getBusinessPosition(String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BusinessPositionVO result;
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                BusinessPositionVO vo=new BusinessPositionVO();
                vo.setSaleincome(businesspositionDao.getsaleincome(beginTime, endTime));
                vo.setSaleoutcome(businesspositionDao.getsaleoutcome(beginTime,endTime));
                vo.setProductincome(businesspositionDao.getproductincome(beginTime,endTime));
                vo.setProductoutcome(businesspositionDao.getproductoutcome(beginTime,endTime));
                vo.setHumanoutcome(businesspositionDao.gethumanoutcome(beginTime,endTime));
                return vo;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
