package com.nju.edu.erp.model.vo.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryVO {

    private BigDecimal baseSalary;
    private BigDecimal rankSalary;  //这里指的是这个人是多少钱 而不是每一级是多少钱
    private String type;            //薪资发放方式


}

