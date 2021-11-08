package com.butte.base.page;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页响应
 * @author 公众号:知了一笑
 * @since 2021-08-22 15:49
 */
@Data
public class PageVO <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 当前页
     */
    private long page = 1;
    /**
     * 每页条数，默认 10
     */
    private long pageSize = 10;
    /**
     * 数据集合
     */
    private List<T> records ;

    public PageVO (long page,long pageSize,long total,List<T> data) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.records = data ;
    }

    public PageVO (Page<?> page,Class<T> clazz) {
        this.page = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        if (ObjectUtil.isNotEmpty(page.getRecords())){
            this.records = new ArrayList<>() ;
            page.getRecords().forEach(data -> records.add(BeanUtil.copyProperties(data,clazz)));
        }
    }

}
