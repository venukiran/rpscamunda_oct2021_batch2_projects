package com.boa.foodyapi.delegates;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.boa.foodyapi.models.Order;
import com.github.wnameless.json.flattener.JsonFlattener;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("saveorder")
public class SaveOrderDelegate implements JavaDelegate {
@Autowired
 private RestTemplate restTemplate;
@Value("${url}")
private String url;
  private final Logger LOGGER = Logger.getLogger(SaveOrderDelegate.class.getName());
  
  public void execute(DelegateExecution execution) throws Exception {
    
    LOGGER.info("\n\n  ... Save Order Delegate invoked by "
            + "activityName='" + execution.getCurrentActivityName() + "'"
            + ", activityId=" + execution.getCurrentActivityId()
            + ", processDefinitionId=" + execution.getProcessDefinitionId()
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + ", variables=" + execution.getVariables()
            + " \n\n");
    Order order =new Order();
    order.setItemId(Long.parseLong(execution.getVariable("itemId").toString()));
    order.setQty(Integer.parseInt(execution.getVariable("qty").toString()));
    order.setDor(LocalDate.parse(execution.getVariable("dor").toString()));
    order.setTime(execution.getVariable("time").toString());
//call the rest service
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity request = new HttpEntity<>(order,headers);
    //delegate to rest service
    ResponseEntity<?> response=restTemplate.
		      postForEntity(url+"/orders",request, String.class);
    LOGGER.info("SavedOrder-->"+response.toString());
    
    Map<String,Object> data = parseString(response.getBody().toString());
    for(String key:data.keySet()) {
			System.out.println(data.get(key));
		}
		
		execution.setVariable("orderId", data.get("orderId"));
    
  }
  private Map<String,Object> parseString(String response)
 	{
 		JSONParser parser = new JSONParser(); 
 		Map<String, Object> flattenedJsonMap=null;
 		String token="";
 	  	try {
 	  		 
 			// Put above JSON content to crunchify.txt file and change path location
 			Object obj = parser.parse(response);
 			JSONObject jsonObject = (JSONObject) obj;

 			// JsonFlattener: A Java utility used to FLATTEN nested JSON objects
 			String flattenedJson = JsonFlattener.flatten(jsonObject.toString());
 			//log("\n=====Simple Flatten===== \n" + flattenedJson);

 		flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObject.toString());
 		 	
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	 return flattenedJsonMap;

 	}
}
