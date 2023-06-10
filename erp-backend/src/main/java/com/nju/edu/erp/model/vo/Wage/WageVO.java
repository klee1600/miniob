package com.nju.edu.erp.model.vo.Wage;

import com.nju.edu.erp.enums.AccountSheetState;
import com.nju.edu.erp.model.po.TaxPO;
import com.nju.edu.erp.model.po.WagePO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WageVO {
    private Long id;

    private String wageId;

    private Integer uid;

    private String name;

    private Long bankId;

    private BigDecimal rawSalary;

    private Long taxId;

    private BigDecimal finalSalary;

    private AccountSheetState state;

    private TaxVO tax;

    private BigDecimal baseSalary;

    private BigDecimal rankSalary;

    private BigDecimal annualSalary;

    private BigDecimal signup;

    private BigDecimal bonus;

    private String type;

    private Date time;

    public static WageVO PO2VO(WagePO po, TaxPO taxPO) {
        TaxVO tax = new TaxVO();
        BeanUtils.copyProperties(taxPO, tax);

        WageVO gzdvo = new WageVO();
        BeanUtils.copyProperties(po, gzdvo);
        gzdvo.type = po.getType().toString();
        gzdvo.tax = tax;
        return gzdvo;
    }
}

