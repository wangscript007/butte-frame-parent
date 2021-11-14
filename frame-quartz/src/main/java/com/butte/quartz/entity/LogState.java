package com.butte.quartz.entity;

/**
 * 日志状态
 * @author 公众号:知了一笑
 * @since 2021-08-07 21:06
 */
public enum LogState {

    LOG_SUS(1, "成功"),
    LOG_FAIL(2, "失败");

    private int status;
    private String desc;

    LogState(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
