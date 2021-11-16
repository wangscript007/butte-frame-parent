package com.butte.search.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;

import java.util.List;
import java.util.Map;
/**
 * 数据操作对象
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataVO extends BaseVO {

    /**
     * 单数据，默认存在索引主键
     */
    private Map<String,Object> dataMap ;

    /**
     * 数据集合，默认存在索引主键
     */
    private List<Map<String,Object>> dataList ;

    private String indexId ;

    private List<String> indexIdList ;

    /**
     * 刷新机制，默认立即刷新
     */
    private WriteRequest.RefreshPolicy refresh = WriteRequest.RefreshPolicy.IMMEDIATE;

    public DataVO(String indexName) {
        super(indexName);
    }

    public DataVO(String indexName, String type, RequestOptions options) {
        super(indexName, type, options);
    }

}
