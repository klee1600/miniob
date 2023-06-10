package com.nju.edu.erp.model.vo.Wage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateVO {
    private Integer year;
    private Integer month;
}
