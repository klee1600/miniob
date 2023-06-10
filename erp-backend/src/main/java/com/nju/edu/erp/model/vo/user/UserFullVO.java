package com.nju.edu.erp.model.vo.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.po.Salary;
import com.nju.edu.erp.model.vo.salary.SalaryVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserFullVO {


    private Integer id;

    private String name;

    private Role role;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String phone;


    private Long bankId;


    private Integer level;


    private SalaryVO salary;



}
