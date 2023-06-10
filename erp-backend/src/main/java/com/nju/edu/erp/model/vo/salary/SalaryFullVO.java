package com.nju.edu.erp.model.vo.salary;

import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryFullVO {

    private Integer id;

    private Role role;
    private BigDecimal baseSalary;
    private BigDecimal rankSalary;   //每高一级多多少钱
    private BigDecimal bonusSalary;  //提成多少钱
    private String way;              //发工资方式

    public static SalaryFullVO PO2VO(Salary salary){
        return SalaryFullVO.builder()
                .id(salary.getId())
                .role(salary.getRole())
                .baseSalary(salary.getBaseSalary())
                .rankSalary(salary.getRankSalary())
                .bonusSalary(salary.getBonusSalary())
                .way(salary.getWay().toString()).build();
    }


}

