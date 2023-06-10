package com.nju.edu.erp.model.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusPO {
    @TableId
    private Long id;

    private String name;
    private Date time;


}

