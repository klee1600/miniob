package com.nju.edu.erp.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.enums.PayRollType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wage_sheet")
public class WagePO {
    @TableId
    private Long id;

    private String wageId;

    private Integer uid;

    private String name;

    private Long bankId;

    private BigDecimal rawSalary;

    private Long taxId;

    private BigDecimal finalSalary;

    private AccountSheetState state;

    private Date time;

    private BigDecimal baseSalary;

    private BigDecimal rankSalary;

    private BigDecimal annualSalary;

    private BigDecimal signup;

    private BigDecimal bonus;

    private PayRollType type;
}