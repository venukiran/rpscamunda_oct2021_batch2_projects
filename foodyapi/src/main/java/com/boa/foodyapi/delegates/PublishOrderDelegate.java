package com.boa.foodyapi.delegates;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component("publishorder")
public class PublishOrderDelegate implements JavaDelegate {
 @Autowired
	private RestTemplate restTemplate;
 
 @Value("${url}")
 private String url;
  private final Logger LOGGER = Logger.getLogger(PublishOrderDelegate.class.getName());
  
  public void execute(DelegateExecution execution) throws Exception {
    
    LOGGER.info("\n\n  ... Publish Order Delegate invoked by "
            + "activityName='" + execution.getCurrentActivityName() + "'"
            + ", activityId=" + execution.getCurrentActivityId()
            + ", processDefinitionId=" + execution.getProcessDefinitionId()
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + ", variables=" + execution.getVariables()
            + " \n\n");
    
    long orderId=Long.parseLong(execution.getVariable("orderId")
			.toString());
	HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
  
 	
	HttpEntity request = new HttpEntity<String>(null,headers);

	ResponseEntity<?> responseEntityStr = restTemplate.
	          exchange(url+"/orders/publish/"+orderId,HttpMethod.GET, request,
			  String.class); 

	LOGGER.info("Data published");
    
  }

}
