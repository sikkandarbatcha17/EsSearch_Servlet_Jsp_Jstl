import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.client.RequestOptions;
import java.util.concurrent.TimeUnit;

public class Connection implements IConnection{

	@Override
	public List<EventLogs> findEventLogs(int currentPage, int recordsPerPage,String eventId,String logEvent) {
		
		 List<EventLogs> eventLogs=new ArrayList<EventLogs>();

	     int start = currentPage * recordsPerPage - recordsPerPage;
	        
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(logEvent);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("EventId", eventId)); 
        searchSourceBuilder.from(start); 
        searchSourceBuilder.size(recordsPerPage);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        Map<String, Object> map=null;
        
        try {
            SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    map = hit.getSourceAsMap();
                    Object list[]= new Object[5];
                    EventLogs obj=new EventLogs();
                    int count=0;
                    for (Map.Entry<String,Object> entry : map.entrySet()){
                    	 list[count++]=entry.getValue();  
                    }
                    obj.setEventType(list[0]);
                    obj.setTimeGenerated(list[1]);
                    obj.setMessage(list[2]);
                    obj.setEventId(list[3]);
                    obj.setSource(list[4]);
                    eventLogs.add(obj);
                }
        }
     
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
		return eventLogs;
	}

	@Override
	public long getNumberOfRows(String eventId, String logEvent) {

		long numOfRows = 0;
		
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(new HttpHost("localhost", 9200, "http")));
		
		SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(logEvent);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("EventId", eventId)); 
        //searchSourceBuilder.from(0); 
        //searchSourceBuilder.size(10000);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        Map<String, Object> map=null;
        long numHits = 0;
        try {
            SearchResponse searchResponse = null;
            searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
            	SearchHits hits= searchResponse.getHits();
            	TotalHits totalHits = hits.getTotalHits();
            	numHits = totalHits.value;
            }
            
            numOfRows=numHits;
        }
        catch (IOException e) {
           e.printStackTrace();
        }
		
		return numOfRows;
	}

}