package com.example.springbootvue.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.KnnQuery;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.core.ReindexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootvue.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventTextEmbeddingService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public void createPipeline(String descriptionFieldName, String pipelineId) throws Exception{
        if(elasticsearchClient.ingest().getPipeline(p->p.id(pipelineId)).result().size() == 0){
            elasticsearchClient.ingest().putPipeline(pipeline -> pipeline.id(pipelineId).description("Text embedding pipeline.")
                    .processors(p->p.inference(i->i.modelId("all-minilm-l12-v2").targetField("text_embedding").fieldMap(descriptionFieldName, JsonData.fromJson("\"text_field\"")))));
        }
    }

    public void createIndexMapping(String indexName) throws Exception {
        Map<String, Property> map = new HashMap<String, Property>();
        //JsonData message = JsonData.fromJson("{\"type\": \"text\"}");
        //Property propertyMessage = new Property("message", message);
        //map.put("message", propertyMessage);

        JsonData textEmbedding = JsonData.fromJson("{\"type\": \"dense_vector\", \"dims\": 384, \"index\": true, \"similarity\": \"cosine\"}");
        Property propertyTextEmbedding = new Property("message", textEmbedding);
        map.put("text_embedding.predicted_value", propertyTextEmbedding);
        //es会自动识别为date类型，所以将日期指定为string类型，
        JsonData time = JsonData.fromJson(("{\"type\":\"text\"}"));
        Property propertyTime = new Property("time", time);
        map.put("time", propertyTime);
        //使用ik分词器
        JsonData description = JsonData.fromJson("{\"type\":\"text\", \"analyzer\":\"ik_max_word\", \"search_analyzer\":\"ik_smart\"}");
        Property propertyDes = new Property("description", description);
        map.put("description", propertyDes);
        elasticsearchClient.indices().create(c -> c.mappings(m -> m.properties(map)).index(indexName));
    }

    public void createReindex(String srcIndex, String dstIndex, String pipelineId) throws Exception{
        ReindexResponse reindexResponse = elasticsearchClient.reindex(r -> r.waitForCompletion(false)
                .source(s -> s.index(srcIndex))
                .dest(d -> d.index(dstIndex).pipeline(pipelineId)));
        //System.out.println(reindexResponse.task());
    }

    public void insertSingleEventWithTextEmbedding(Event event, String dstIndex) throws Exception{
        if(!elasticsearchClient.indices().exists(e -> e.index(dstIndex)).value()){
            createIndexMapping(dstIndex);
        }
        elasticsearchClient.index(idx -> idx.index(dstIndex).document(event).pipeline("text-embeddings"));
//        if(!elasticsearchClient.indices().exists(e -> e.index(dstIndex)).value()){
//            createIndexMapping(dstIndex);
//        }
//        createReindex("event-temp", dstIndex, "event-text-embedding");
//        elasticsearchClient.delete(d -> d.index("event-temp"));
    }

    public void insertEventsWithTextEmbedding(List<Event> events, String dstIndex) throws Exception{
        if(!elasticsearchClient.indices().exists(e -> e.index(dstIndex)).value()){
            createIndexMapping(dstIndex);
            //System.out.println("插入新索引...");
        }

        for(Event event : events) {
            elasticsearchClient.index(idx -> idx.index(dstIndex).document(event).pipeline("text-embeddings"));
        }
    }

    public void insertWithBulk(List<Event> events, String dstIndex) throws Exception{
        if(!elasticsearchClient.indices().exists(e -> e.index(dstIndex)).value()){
            createIndexMapping(dstIndex);
            //System.out.println("插入新索引...");
        }
        List<BulkOperation> bulkOperations = new ArrayList<>();
        for(Event event : events) {
            IndexOperation<Event> indexOperation = new IndexOperation.Builder<Event>().document(event).build();
            BulkOperation bulkOperation = new BulkOperation.Builder().index(indexOperation).build();
            bulkOperations.add(bulkOperation);
        }
        elasticsearchClient.bulk(b -> b.index(dstIndex).operations(bulkOperations).pipeline("text-embeddings"));
    }

    public List<Event> searchByKNN(String index, String des, Integer k) throws Exception {
        KnnQuery knnQuery = KnnQuery.of(m -> m
                //Required
                .field("text_embedding.predicted_value")
                .k(k)
                //num_candidates = Math.min(1.5 * size, 10_000) -> 默认值
                .numCandidates(Math.min(k*10, 10000))
                //Required
                .queryVectorBuilder(b -> b.textEmbedding(t -> t.modelId("all-minilm-l12-v2").modelText(des))));
        SearchResponse<JSONObject> search = elasticsearchClient.search(s -> s
                        .index(index).size(k).knn(knnQuery)  //size默认10？ 需调大才能获取更多
                , JSONObject.class);
        //System.out.println(search);

        List<Event> result = new ArrayList<>();

        for(Hit<JSONObject> hit : search.hits().hits()){
            Event event = new Event();
            event.setId(hit.id());
            event.setDescription((String) hit.source().get("description"));
            event.setTitle((String) hit.source().get("title"));
            event.setTypeId((String) hit.source().get("typeId"));
            event.setTypeName((String) hit.source().get("typeName"));
            event.setThemeId((String) hit.source().get("themeId"));
            event.setThemeName((String) hit.source().get("themeName"));
            event.setArguments((List<String>) hit.source().get("arguments"));
            result.add(event);
        }
        return result;
    }

}
