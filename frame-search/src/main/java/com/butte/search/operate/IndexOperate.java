package com.butte.search.operate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.butte.search.entity.IndexVO;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 索引操作基础封装
 * @author 公众号:知了一笑
 * @since 2021-08-07 18:16
 */
@Component
public class IndexOperate {

    @Resource
    private RestHighLevelClient client ;

    /**
     * 判断索引是否存在
     * @return boolean
     * @since 2021-08-07 18:57
     */
    public boolean exists (IndexVO entry) {
        GetIndexRequest getReq = new GetIndexRequest (entry.getIndexName()) ;
        try {
            return client.indices().exists(getReq, entry.getOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE ;
    }

    /**
     * 创建索引
     * @return boolean
     * @since 2021-08-07 18:57
     */
    public boolean create (IndexVO entry){
        try {
            if(!exists(entry)){
                CreateIndexRequest createReq = new CreateIndexRequest(entry.getIndexName());
                return client.indices().create(createReq, entry.getOptions()).isAcknowledged() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 添加索引结构
     * @return boolean
     * @since 2021-08-07 18:57
     */
    public boolean putMapping (IndexVO entry){
        try {
            if (exists(entry) && CollUtil.isNotEmpty(entry.getProperties())){
                PutMappingRequest putReq = new PutMappingRequest(entry.getIndexName());
                putReq.source(entry.getProperties()) ;
                AcknowledgedResponse ackResp = client.indices().putMapping(putReq,entry.getOptions()) ;
                return ackResp.isAcknowledged() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 删除索引
     * @return boolean
     * @since 2021-08-07 18:57
     */
    public boolean delete (IndexVO entry){
        try {
            if (exists(entry)){
                DeleteIndexRequest delReq = new DeleteIndexRequest(entry.getIndexName()) ;
                AcknowledgedResponse ackResp = client.indices().delete(delReq,entry.getOptions()) ;
                return ackResp.isAcknowledged() ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 查询索引字段结构
     * @since 2021-08-07 18:58
     */
    public Map<String,Object> getMapping (IndexVO entry){
        GetFieldMappingsRequest fieldReq = new GetFieldMappingsRequest();
        fieldReq.indices(entry.getIndexName());
        Map<String,Object> properties = new HashMap<>() ;
        if (ArrayUtil.isEmpty(entry.getFields())){
            return properties ;
        }
        fieldReq.fields(entry.getFields());
        try {
            GetFieldMappingsResponse fieldResp = client.indices().getFieldMapping(fieldReq,entry.getOptions());
            for (String key : fieldResp.mappings().keySet()){
                fieldResp.mappings().get(key).forEach((key1, value) -> properties.putAll(value.sourceAsMap()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return properties ;
    }

}
