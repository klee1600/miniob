package com.nju.edu.erp.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.nju.edu.erp.enums.PayRollType;
import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Salary {
    @TableId
    private Integer id;

    private Role role;
    private BigDecimal baseSalary;
    private BigDecimal rankSalary;   //每高一级多多少钱
    private BigDecimal bonusSalary;  //提成多少钱
    private PayRollType way;         //发工资方式


}

