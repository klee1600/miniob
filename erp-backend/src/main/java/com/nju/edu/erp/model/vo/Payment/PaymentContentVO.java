package com.nju.edu.erp.model.vo.Payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentContentVO {
    private Long id;

    private String paymentId;

    private Integer cid;

    private BigDecimal amount;

    private String remark;
}
