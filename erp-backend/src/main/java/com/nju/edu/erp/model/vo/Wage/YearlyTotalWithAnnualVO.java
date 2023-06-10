package com.nju.edu.erp.model.vo.Wage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearlyTotalWithAnnualVO {

    private Integer uid;
    private String name;
    private BigDecimal totalSalary;
    private BigDecimal annualSalary;

}

