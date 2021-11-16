package com.butte.search.operate;

import cn.hutool.core.collection.CollUtil;
import com.butte.search.entity.DataVO;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 数据操作基础封装
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:16
 */
@Component
public class DataOperate {

    @Resource
    private RestHighLevelClient client ;

    /**
     * 插入数据
     * @param entry 对象主体
     * @since 2021-08-07 18:16
     */
    public void insert (DataVO entry){
        IndexRequest insertReq = new IndexRequest(entry.getIndexName(),entry.getType()) ;
        insertReq.setRefreshPolicy(entry.getRefresh()) ;
        insertReq.id(String.valueOf(entry.getDataMap().get("id")));
        insertReq.source(entry.getDataMap(), XContentType.JSON) ;
        try {
            client.index(insertReq, entry.getOptions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量插入数据
     * @param entry 对象主体
     * @since 2021-08-07 18:16
     */
    public void bulkInsert (DataVO entry){
        if (CollUtil.isEmpty(entry.getDataList())){
            return ;
        }
        // 请求条件
        BulkRequest bulkInsert = new BulkRequest(entry.getIndexName(),entry.getType()) ;
        bulkInsert.setRefreshPolicy(entry.getRefresh()) ;
        entry.getDataList().forEach(dataMap -> {
            IndexRequest insertReq = new IndexRequest() ;
            insertReq.id(String.valueOf(dataMap.get("id"))) ;
            insertReq.source(dataMap) ;
            bulkInsert.add(insertReq) ;
        });
        try {
            // 执行请求
            client.bulk(bulkInsert, entry.getOptions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     * @param entry 对象主体
     * @since 2021-08-07 18:16
     */
    public void update (DataVO entry){
        String indexId = String.valueOf(entry.getDataMap().get("id")) ;
        UpdateRequest updateReq = new UpdateRequest(entry.getIndexName(),entry.getType(),indexId) ;
        updateReq.setRefreshPolicy(entry.getRefresh()) ;
        updateReq.doc(entry.getDataMap(), XContentType.JSON) ;
        try {
            client.update(updateReq, entry.getOptions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量更新数据
     * @param entry 对象主体
     * @since 2021-08-07 18:16
     */
    public void bulkUpdate (DataVO entry){
        if (CollUtil.isEmpty(entry.getDataList())){
            return ;
        }
        // 请求条件
        BulkRequest bulkUpdate = new BulkRequest(entry.getIndexName(),entry.getType()) ;
        bulkUpdate.setRefreshPolicy(entry.getRefresh()) ;
        entry.getDataList().forEach(dataMap -> {
            UpdateRequest updateReq = new UpdateRequest() ;
            updateReq.id(String.valueOf(dataMap.get("id"))) ;
            updateReq.doc(dataMap) ;
            bulkUpdate.add(updateReq) ;
        });
        try {
            // 执行请求
            client.bulk(bulkUpdate, entry.getOptions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除数据
     * @param entry 对象主体
     * @since 2021-08-07 18:16
     */
    public void delete (DataVO entry){
        // 请求条件
        DeleteRequest delReq = new DeleteRequest(entry.getIndexName()) ;
        delReq.id(entry.getIndexId());
        delReq.type(entry.getType()) ;
        delReq.setRefreshPolicy(entry.getRefresh());
        try {
            // 执行请求
            client.delete(delReq, entry.getOptions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除数据
     * @param entry 对象主体
     * @since 2021-08-07 18:16
     */
    public void bulkDelete (DataVO entry){
        if (CollUtil.isEmpty(entry.getIndexIdList())){
            return ;
        }
        // 请求条件
        BulkRequest bulkDelete = new BulkRequest(entry.getIndexName(),entry.getType()) ;
        bulkDelete.setRefreshPolicy(entry.getRefresh()) ;
        entry.getIndexIdList().forEach(indexId -> {
            bulkDelete.add(new DeleteRequest().id(indexId)) ;
        });
        try {
            // 执行请求
            client.bulk(bulkDelete, entry.getOptions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
