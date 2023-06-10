package com.nju.edu.erp.model.vo.Credit;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreditContentVO {
    private Long id;

    private String creditId;

    private Long bankId;

    private BigDecimal amount;

    private String remark;
}
