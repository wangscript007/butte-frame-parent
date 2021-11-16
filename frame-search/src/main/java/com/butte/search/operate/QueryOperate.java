package com.butte.search.operate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.butte.search.entity.QueryVO;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询操作基础封装
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:16
 */
@Component
public class QueryOperate {

    @Resource
    private RestHighLevelClient client ;

    /**
     * 索引ID查询
     * @since 2021-08-07 19:00
     */
    public Map<String,Object> getById (QueryVO entry){
        GetRequest getReq = new GetRequest(entry.getIndexName(), entry.getType(), entry.getIndexId()) ;
        try {
            return client.get(getReq, entry.getOptions()).getSourceAsMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    /**
     * 批量索引ID查询
     * @since 2021-08-07 19:00
     */
    public List<Map<String,Object>> getByIds (QueryVO entry){

        // 响应参数
        List<Map<String,Object>> hitList = new ArrayList<>() ;

        // 查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termsQuery("id", entry.getIndexIdList()));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(queryBuilder);

        // 查询构建
        SearchRequest searchRequest = new SearchRequest(entry.getIndexName());
        searchRequest.types(entry.getType());
        searchRequest.source(sourceBuilder);

        try {

            // 查询结果
            SearchResponse searchResponse = client.search(searchRequest, entry.getOptions());
            SearchHit[] searchHits = searchResponse.getHits().getHits() ;
            if (ArrayUtil.isEmpty(searchHits)){
                return hitList ;
            }
            for (SearchHit searchHit : searchHits){
                Map<String, Object> hitMap = searchHit.getSourceAsMap() ;
                hitMap.put("id",searchHit.getId()) ;
                hitList.add(hitMap) ;
            }
            return hitList ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hitList ;
    }

    /**
     * 指定字段分组查询
     * @since 2021-10-07 19:00
     */
    public Map<String,Object> groupByField (QueryVO entry){
        Map<String,Object> groupMap = new HashMap<>() ;
        // 分组API
        String groupName = entry.getGroupField()+"_group" ;
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.size(0) ;
        TermsAggregationBuilder termAgg = AggregationBuilders.terms(groupName)
                                                             .field(entry.getGroupField()) ;
        sourceBuilder.aggregation(termAgg);
        // 查询API
        SearchRequest searchRequest = new SearchRequest(entry.getIndexName());
        searchRequest.source(sourceBuilder) ;
        try {
            // 执行API
            SearchResponse response = client.search(searchRequest, entry.getOptions());
            // 响应结果
            Terms groupTerm = response.getAggregations().get(groupName) ;
            if (CollUtil.isNotEmpty(groupTerm.getBuckets())){
                for (Terms.Bucket bucket:groupTerm.getBuckets()){
                    groupMap.put(bucket.getKeyAsString(),bucket.getDocCount()) ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupMap ;
    }
}
