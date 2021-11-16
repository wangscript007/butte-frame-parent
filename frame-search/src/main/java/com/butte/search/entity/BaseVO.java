package com.butte.search.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.client.RequestOptions;
/**
 * 基础业务对象
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:13
 */
@Data
@NoArgsConstructor
public class BaseVO {

    /**
     * 索引名称
     */
    private String indexName ;

    /**
     * 类型：默认
     */
    private String type = "_doc" ;

    /**
     * 请求选项
     */
    private RequestOptions options = RequestOptions.DEFAULT ;

    public BaseVO(String indexName){
        this.indexName = indexName ;
    }

    public BaseVO(String indexName, String type, RequestOptions options){
        this.indexName = indexName ;
        this.type = type ;
        this.options = options ;
    }

}
