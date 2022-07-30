package com.Suresh.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher implements MessagePublisher {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private Topic topic;

	public RedisMessagePublisher() {
		// TODO Auto-generated constructor stub
	}

	public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate, Topic topic) {

		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	@Override
	public void publish(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), "hello Buddie I am from RedisMessagePublisher");
	}

}
