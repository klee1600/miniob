package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.CustomerType;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.Initial_CustomerPO;
import com.nju.edu.erp.model.vo.CustomerVO;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    /**
     * 根据id更新客户信息
     *
     * @param customerPO 客户信息
     */
    void updateCustomer(CustomerPO customerPO);

    /**
     * 根据type查找对应类型的客户
     *
     * @param type 客户类型
     * @return 客户列表
     */
    List<CustomerPO> getCustomersByType(CustomerType type);

    List<Initial_CustomerPO> getInitialCustomersByType(CustomerType type);


    CustomerPO findCustomerById(Integer supplier);

    void delete(Integer id);

    void add(CustomerVO customerVO);

    void initial_add(CustomerVO customerVO);

    void modify(CustomerVO customerVO);

    void customerAddReceivable(Integer cid, BigDecimal amount);

    void customerAddPayable(Integer cid, BigDecimal amount);

    void initial_create();
}
