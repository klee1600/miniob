package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.dao.CustomerDao;
import com.nju.edu.erp.dao.Initial_AccountDao;
import com.nju.edu.erp.dao.Initial_CustomerDao;
import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.Initial_CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;
import com.nju.edu.erp.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private Initial_CustomerDao initial_customerDao;

    /**
     * 根据id更新客户信息
     *
     * @param customerPO 客户信息
     */
    @Override
    public void updateCustomer(CustomerPO customerPO) {
        customerDao.updateOne(customerPO);
    }

    /**
     * 根据type查找对应类型的客户
     *
     * @param type 客户类型
     * @return 客户列表
     */
    @Override
    public List<CustomerPO> getCustomersByType(CustomerType type) {

        return customerDao.findAllByType(type);
    }

    @Override
    public List<Initial_CustomerPO> getInitialCustomersByType(CustomerType type) {

        return initial_customerDao.findAllByType(type);
    }


    @Override
    public void initial_create() {
        initial_customerDao.initial_create();
    }


    @Override
    public CustomerPO findCustomerById(Integer supplier) {
        return customerDao.findOneById(supplier);
    }

    @Override
    public void delete(Integer id) {
        try {
            customerDao.deleteById(id);
        } catch (Exception e) {
            throw new MyServiceException("500", "参数错误!");
        }
    }

    @Override
    public void add(CustomerVO customerVO) {
        CustomerPO customerPO = new CustomerPO();
        BeanUtils.copyProperties(customerVO, customerPO);
        customerPO.setLineOfCredit(calLineOfCredit(customerPO.getLevel()));
        customerPO.setPayable(BigDecimal.valueOf(0));
        customerPO.setReceivable(BigDecimal.valueOf(0));
        customerDao.insert(customerPO);
    }

    @Override
    public void initial_add(CustomerVO customerVO) {
        Initial_CustomerPO customerPO = new Initial_CustomerPO();
        BeanUtils.copyProperties(customerVO, customerPO);
        customerPO.setLineOfCredit(calLineOfCredit(customerPO.getLevel()));
        customerPO.setPayable(BigDecimal.valueOf(0));
        customerPO.setReceivable(BigDecimal.valueOf(0));
        initial_customerDao.insert(customerPO);
    }

    /**
     * 表驱动计算应收额度
     */
    private BigDecimal calLineOfCredit(Integer l) {
        int[] level = {1, 2, 3, 4, 5};
        BigDecimal[] line = {BigDecimal.valueOf(0), BigDecimal.valueOf(1000), BigDecimal.valueOf(10000),
                BigDecimal.valueOf(100000), BigDecimal.valueOf(99999999)};
        for (int i = 0; i < level.length; i++) {
            if (l == level[i])
                return line[i];
        }
        throw new MyServiceException("500","级别异常");
    }

    @Override
    public void modify(CustomerVO customerVO) {
        CustomerPO customerPO = new CustomerPO();
        BeanUtils.copyProperties(customerVO, customerPO);
        try {
            customerDao.updateById(customerPO);
        } catch (Exception e) {
            throw new MyServiceException("500", "参数错误!");
        }
    }

    @Override
    public void customerAddReceivable(Integer cid, BigDecimal amount) {

    }

    @Override
    public void customerAddPayable(Integer cid, BigDecimal amount) {

    }
}
