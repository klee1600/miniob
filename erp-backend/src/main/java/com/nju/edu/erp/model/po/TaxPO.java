package com.nju.edu.erp.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tax")
public class TaxPO {
    @TableId
    private Long id;

    private BigDecimal tax;
    private BigDecimal insurance;
    private BigDecimal fund;
    private BigDecimal total;


}

