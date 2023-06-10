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
@TableName("user_salary")
public class UserSalaryPO {
    @TableId
    private Long id;

    private BigDecimal bonus;       //提成
    private BigDecimal signup;      //打卡
    private BigDecimal annual;      //年终奖


}

