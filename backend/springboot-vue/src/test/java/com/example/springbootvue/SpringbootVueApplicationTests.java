package com.example.springbootvue;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.KnnQuery;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.ReindexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootvue.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootVueApplicationTests {

	@Test
	void contextLoads() {
	}
/*
	@Autowired
	private ElasticsearchClient elasticsearchClient;

	@Test
	public void searchTest() throws Exception {
		KnnQuery knnQuery = KnnQuery.of(m -> m
				//Required
				.field("text_embedding.predicted_value")
				.k(20)
				//num_candidates = Math.min(1.5 * size, 10_000) -> 默认值
				.numCandidates(300)
				//Required
				.queryVectorBuilder(b -> b.textEmbedding(t -> t.modelId("all-minilm-l6-v2").modelText("联合军演"))));
		SearchResponse<JSONObject> search = elasticsearchClient.search(s -> s
				.index("test").size(20).knn(knnQuery)  //size默认10？ 需调大才能获取更多
			, JSONObject.class);
		System.out.println(search);

		List<JSONObject> result = new ArrayList<>();

		for(Hit<JSONObject> hit : search.hits().hits()){
			//JSONObject source = hit.source();
			//source.put("score", hit.score());
			Event event = new Event();
			event.setId(hit.id());
			event.setDescription((String) hit.source().get("description"));
			event.setTitle((String) hit.source().get("title"));
			event.setTypeId((String) hit.source().get("typeId"));
			event.setTypeName((String) hit.source().get("typeName"));
			event.setThemeId((String) hit.source().get("themeId"));
			event.setThemeName((String) hit.source().get("themeName"));
			event.setArguments((List<String>) hit.source().get("arguments"));

			//System.out.println(hit.source().get("arguments"));
		}
	}

	@Test
	public void createPipeline() throws Exception{
		//System.out.println(elasticsearchClient.ingest().getPipeline(p->p.id("text-embeddings")).result().size());
		if(elasticsearchClient.ingest().getPipeline(p->p.id("event-text-embedding")).result().size() == 0) {
			elasticsearchClient.ingest().putPipeline(pipeline -> pipeline.id("event-text-embedding").description("Event Text embedding pipeline.")
					.processors(p -> p.inference(i -> i.modelId("all-minilm-l6-v2").targetField("text_embedding").fieldMap("description", JsonData.fromJson("\"text_field\"")))));
		}
	}

	@Test
	public void createIndexForVector() throws Exception {
		Map<String, Property> map = new HashMap<String, Property>();
		JsonData message = JsonData.fromJson("{\"type\": \"text\"}");
		Property propertyMessage = new Property("message", message);

		JsonData textEmbedding = JsonData.fromJson("{\"type\": \"dense_vector\", \"dims\": 384, \"index\": true, \"similarity\": \"cosine\"}");
		Property propertyTextEmbedding = new Property("message", textEmbedding);

		map.put("message", propertyMessage);
		map.put("text_embedding.predicted_value", propertyTextEmbedding);

		//System.out.println(elasticsearchClient.indices().exists(e -> e.index("events")).value());
		elasticsearchClient.indices().create(c -> c.mappings(m -> m.properties(map)).index("event-test"));
	}

	@Test
	public void createReindex() throws Exception{
		ReindexResponse reindexResponse = elasticsearchClient.reindex(r -> r.waitForCompletion(false)
				.source(s -> s.index("events"))
				.dest(d -> d.index("event-test").pipeline("event-text-embedding")));
		System.out.println(reindexResponse.task());
	}

	@Test
	public void deleteIndex() throws Exception {
		elasticsearchClient.delete(d -> d.index("event-temp"));


	}

	@Test
	public void insertSingleEvent() throws Exception{
		Event event = new Event();
		event.setDescription("This is a temp event test.");
		event.setTitle("Test.");
		event.setThemeName("test");
		elasticsearchClient.index(idx -> idx.index("event-temp").document(event).id("1").pipeline("event-text-embedding"));

		//elasticsearchClient.index(idx -> idx.document("222").index("event-temp"));
	}

	@Test
	public void createIndex() throws Exception{
		Event event = new Event();
		event.setTitle("2333");
		event.setDescription("ssssssssss");
		elasticsearchClient.create(c -> c.index("test").id("2333").document(event));
	}
*/




}
