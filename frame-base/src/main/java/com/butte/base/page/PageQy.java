package com.butte.base.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询基础
 * @author 公众号:知了一笑
 * @since 2021-08-22 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQy {

    /**
     * 默认加载首页
     */
    private Integer page = 1 ;

    /**
     * 每页默认10条数据
     */
    private Integer pageSize = 10 ;
}
