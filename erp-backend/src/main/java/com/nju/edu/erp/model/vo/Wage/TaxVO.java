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
public class TaxVO {

    private Long id;

    private BigDecimal tax;
    private BigDecimal insurance;
    private BigDecimal fund;
    private BigDecimal total;


}

