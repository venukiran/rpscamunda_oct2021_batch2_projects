package com.boa.foodyapi.configurations;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.boa.foodyapi.models.Order;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

@Configuration
public class KafkaConsumerConfig{
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	//1. Send string to Kafka
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    return new DefaultKafkaConsumerFactory<>(props);
	}
	 
	/*
	 * @Bean public KafkaTemplate<String, String> kafkaTemplate() { return new
	 * KafkaTemplate<>(producerFactory()); }
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListner() {
		ConcurrentKafkaListenerContainerFactory<String, String> obj = new ConcurrentKafkaListenerContainerFactory<>();
		obj.setConsumerFactory(consumerFactory());
		return obj;
	}
	
}
