package com.nju.edu.erp.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nju.edu.erp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public class User {

    /**
     * 用户ID
     */
    @TableId
    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户身份
     */
    private Role role;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 用户生日
     */
    private Date birthday;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户银行账号
     */
    private Long bankId;

    /**
     * 用户岗位级别
     */
    private Integer level;


}