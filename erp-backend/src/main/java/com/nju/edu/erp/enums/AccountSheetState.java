package com.nju.edu.erp.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum AccountSheetState implements IEnum<Integer> {
    WAIT(0, "WAIT"),         // 待审批
    SUCCESS(1, "SUCCESS"),   // 已审批
    FAILURE(2, "FAILURE"),   // 失败
    DONE(3, "DONE"),         // 已发钱


    ;
    private final int value;
    private final String desc;

    AccountSheetState(int value, String desc) {
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
}
