package com.nju.edu.erp.model.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("payment_sheet_content")
public class PaymentContentPO {
    @TableId
    private Long id;

    private String paymentId;

    private Integer cid;

    private BigDecimal amount;

    private String remark;
}
