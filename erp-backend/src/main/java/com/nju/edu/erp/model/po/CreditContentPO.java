package com.nju.edu.erp.model.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nju.edu.erp.enums.AccountSheetState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("credit_sheet_content")
public class CreditContentPO {
    @TableId
    private Long id;

    private String creditId;

    private Long bankId;

    private BigDecimal amount;

    private String remark;
}
