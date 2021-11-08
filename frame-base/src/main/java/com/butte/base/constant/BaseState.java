package com.butte.base.constant;

/**
 * 基础状态描述
 * @author 公众号:知了一笑
 * @since 2021-09-05 17:44
 */
public enum BaseState {

    ENABLE(1, "启用"),
    DISABLE(2, "禁用"),
    DELETE(3, "删除");

    private int state;
    private String desc;

    BaseState(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

}
