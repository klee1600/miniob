package com.nju.edu.erp.model.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnrollmentVO {

    private Integer id;

    private String name;

    private Role role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String phone;

    private String gender;

    private Integer level;
}
