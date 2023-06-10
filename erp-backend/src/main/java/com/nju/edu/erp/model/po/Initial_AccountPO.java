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
@TableName("initial_account")
public class Initial_AccountPO {
    @TableId
    private Long id;

    private String name;

    private BigDecimal amount;
}
