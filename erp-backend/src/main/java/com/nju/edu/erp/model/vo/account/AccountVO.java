package com.nju.edu.erp.model.vo.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountVO {

    private Long id;
    private String name;
    private BigDecimal amount;
}
