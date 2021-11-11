package com.butte.jdbc.operate;

import com.butte.jdbc.entity.ConnectVO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC基础操作，连接配置之外数据源
 * @author 公众号:知了一笑
 * @since 2021-08-03 21:18
 */
@Component
public class JdbcOperate {

    /**
     * Jdbc连接主体
     */
    private ConnectVO entry ;

    private JdbcOperate (){ }

    /**
     * 构造方法
     */
    public JdbcOperate (ConnectVO entry){
        this.entry = entry ;
    }

    /**
     * Hikari配置项
     */
    private HikariConfig hikariConfig(){
        HikariConfig hikariConfig = new HikariConfig() ;
        String driver = DatabaseDriver.fromProductName(entry.getDbName()).getDriverClassName();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(entry.getJdbcUrl());
        hikariConfig.setUsername(entry.getUserName());
        hikariConfig.setPassword(entry.getPassWord());
        return hikariConfig ;
    }

    /**
     * Hikari数据源
     */
    private HikariDataSource hikariDataSource(){
        return new HikariDataSource(hikariConfig ());
    }

    /**
     * 获取连接
     */
    public Connection connection(){
        try {
            return hikariDataSource().getConnection() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }

    /**
     * 获取JdbcTemplate操作
     */
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(hikariDataSource()) ;
    }

    /**
     * 资源批量关闭
     */
    public void close (Object... closeObjs){
        for (Object closeObj :closeObjs) {
            if (closeObj instanceof AutoCloseable){
                try {
                    ((AutoCloseable) closeObj).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
