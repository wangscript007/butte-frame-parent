package com.butte.search.operate;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 模板操作基础封装
 * @author 公众号:知了一笑
 * @since 2021-08-15 19:21
 */
@Component
public class TemplateOperate {

    @Resource
    private ElasticsearchRestTemplate template ;

    /**
     * 创建索引和结构
     * @param clazz 基于注解类实体
     * @return java.lang.Boolean
     * @since 2021-08-15 19:25
     */
    public <T> Boolean createPut (Class<T> clazz){
        boolean createIf = template.createIndex(clazz) ;
        if (createIf){
            return template.putMapping(clazz) ;
        }
        return Boolean.FALSE ;
    }

}
