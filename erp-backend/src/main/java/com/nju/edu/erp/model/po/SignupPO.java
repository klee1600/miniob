package com.nju.edu.erp.model.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("signup")
public class SignupPO {
    @TableId
    private Long id;

    private Integer uid;
    private Date time; //打卡时间


}

