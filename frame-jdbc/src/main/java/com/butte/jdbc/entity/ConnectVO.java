package com.butte.jdbc.entity;

import lombok.Data;
/**
 * JDBC连接信息
 * @author 公众号:知了一笑
 * @since 2021-08-03 21:16
 */
@Data
public class ConnectVO {
    /**
     * 数据库名称
     * 驱动枚举和转换
     * @see org.springframework.boot.jdbc.DatabaseDriver
     */
    private String dbName ;
    /**
     * 连接地址
     */
    private String jdbcUrl ;
    /**
     * 用户名
     */
    private String userName ;
    /**
     * 密码
     */
    private String passWord ;
}
