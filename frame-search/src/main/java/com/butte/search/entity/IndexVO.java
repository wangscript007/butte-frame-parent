package com.butte.search.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.elasticsearch.client.RequestOptions;
import java.util.Map;

/**
 * 索引操作对象
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IndexVO extends BaseVO {

    /**
     * 索引结构
     */
    private String mapping ;

    /**
     * 索引结构
     */
    private Map<String,Object> properties ;

    /**
     * 字段数组
     */
    private String[] fields ;

    public IndexVO(String indexName) {
        super(indexName);
    }

    public IndexVO(String indexName, String type, RequestOptions options) {
        super(indexName, type, options);
    }

}
