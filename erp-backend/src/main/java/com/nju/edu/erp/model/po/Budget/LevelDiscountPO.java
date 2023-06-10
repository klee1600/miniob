package com.nju.edu.erp.model.po.Budget;


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
@TableName("user_discount")
public class LevelDiscountPO {

    private Long level;

    private BigDecimal discount;
}
