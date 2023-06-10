package com.nju.edu.erp.model.vo.Sale;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalesmanWithTimeVO {

    private String salesman;
    private String beginDateStr;
    private String endDateStr;
}
