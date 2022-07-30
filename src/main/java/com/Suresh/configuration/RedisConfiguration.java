package com.Suresh.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfiguration {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		GenericToStringSerializer<Object> serializer = new GenericToStringSerializer<>(Object.class);
		redisTemplate.setValueSerializer(serializer);

		return redisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer() {

		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
		redisMessageListenerContainer.addMessageListener(messageListenerAdapter(), getTopic());
		return redisMessageListenerContainer;

	}

	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		return new MessageListenerAdapter(new RedisMessageSubscriber(), "onMessage");
	}

	@Bean
	public Topic getTopic() {
		ChannelTopic topic = new ChannelTopic("hello topic");
		return topic;
	}

	@Bean
	MessagePublisher messagePublisher() {
		return new RedisMessagePublisher(redisTemplate(redisConnectionFactory), getTopic());
	}
}
