package com.nju.edu.erp.model.vo.Budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AmountOffsetVO {

    private BigDecimal amount;
    private BigDecimal offset;
}
