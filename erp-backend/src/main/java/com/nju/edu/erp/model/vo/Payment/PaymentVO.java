package com.nju.edu.erp.model.vo.Payment;


import com.nju.edu.erp.enums.AccountSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentVO {
    private Long id;

    private String paymentId;

    private Long bankId;

    private String operator;

    private AccountSheetState state;

    private BigDecimal amount;

    private List<PaymentContentVO> list;
}
