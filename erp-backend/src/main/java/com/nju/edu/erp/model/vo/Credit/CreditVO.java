package com.nju.edu.erp.model.vo.Credit;


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
public class CreditVO {
    private Long id;

    private String creditId;

    private Integer cid;

    private String operator;

    private AccountSheetState state;

    private BigDecimal amount;

    List<CreditContentVO> list;
}
