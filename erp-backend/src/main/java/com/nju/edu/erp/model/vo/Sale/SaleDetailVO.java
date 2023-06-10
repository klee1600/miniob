package com.nju.edu.erp.model.vo.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleDetailVO {

    private String createDate;
    //精确到天
    //eg: "2022-05-12"

    private String pname;

    private String type;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
