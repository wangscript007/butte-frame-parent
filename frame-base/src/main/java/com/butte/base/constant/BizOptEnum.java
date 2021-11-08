package com.butte.base.constant;
/**
 * 业务操作标识
 * @author 公众号:知了一笑
 * @since 2021-09-22 22:52
 */
public enum BizOptEnum {
    INSERT ("insert","新增"),
    QUERY ("query","查询"),
    UPDATE ("update","更新"),
    DELETE ("delete","删除");

    private String opt ;
    private String desc ;

    BizOptEnum(String opt, String desc) {
        this.opt = opt;
        this.desc = desc;
    }

    public String getOpt() {
        return opt;
    }
    public String getDesc() {
        return desc;
    }
}