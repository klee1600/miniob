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
public class AnnualVO {

    private Integer uid;
    private BigDecimal annualSalary;
    private Integer year;

}

