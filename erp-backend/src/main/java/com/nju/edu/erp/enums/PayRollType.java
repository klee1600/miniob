package com.nju.edu.erp.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum PayRollType implements IEnum<Integer> {
    MONTHLY(0, "月薪"),            // 月薪
    ANNUAL(1, "年薪"),             // 年薪
    BASE(2, "基本工资+提成"),       // 基本工资+提成

    ;
    private final int value;
    private final String desc;

    PayRollType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return this.desc;
    }

    public static PayRollType parse(String desc){
        for (PayRollType type : PayRollType.values()) {
            if (type.toString().equals(desc))
                return type;
        }
        return null;
    }

}
