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
@TableName("annual")
public class AnnualPO {
    @TableId
    private Long id;

    private Integer uid;
    private BigDecimal annualSalary;
    private Integer year;


}

