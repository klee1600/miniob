package com.nju.edu.erp.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessPositionVO {

    private BigDecimal saleincome;

    private BigDecimal productincome;

    private BigDecimal saleoutcome;

    private BigDecimal productoutcome;

    private BigDecimal humanoutcome;
}
