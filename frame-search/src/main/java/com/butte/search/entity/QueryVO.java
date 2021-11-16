package com.butte.search.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;

import java.util.List;

/**
 * 查询操作对象
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryVO extends BaseVO {

    /**
     * 索引ID
     */
    private String indexId ;

    /**
     * 索引ID集合
     */
    private List<String> indexIdList ;

    /**
     * 超时限制，默认20S时长
     */
    private TimeValue timeout = TimeValue.timeValueSeconds(20);

    /**
     * 分组查询字段
     */
    private String groupField ;

    public QueryVO(String indexName) {
        super(indexName);
    }

    /**
     * 分组查询构造方法
     */
    public QueryVO(String indexName,String groupField) {
        super(indexName);
        this.groupField = groupField ;
    }

    public QueryVO(String indexName, String type, RequestOptions options) {
        super(indexName, type, options);
    }

}
