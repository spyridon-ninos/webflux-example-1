package com.ninos.integration.kafka;

import com.ninos.business.model.Person;
import com.ninos.integration.kafka.serializers.PersonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * configures the kafka related functionality
 */
@Configuration
public class KafkaConfiguration {

  /**
   * configures a producer factory, based on the configuration provided by the KafkaConfig class
   */
  @Bean
  public ProducerFactory<String, Person> producerFactory() {
    KafkaConfig kafkaConfig = configuration();
    ProducerConfiguration producerConfiguration = kafkaConfig.getProducer();
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PersonSerializer.class);
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
    configProps.put(ProducerConfig.ACKS_CONFIG, producerConfiguration.getAcks());
    configProps.put(ProducerConfig.CLIENT_ID_CONFIG, producerConfiguration.getProducerId());
    configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, producerConfiguration.getMaxInFlightRequests());
    configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producerConfiguration.getRequestTimeoutMs());
    configProps.put(ProducerConfig.RETRIES_CONFIG, producerConfiguration.getRetries());
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, Person> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  /**
   * configures all producer and consumer items
   */
  @Bean
  @ConfigurationProperties(prefix = "kafka")
  public KafkaConfig configuration() {
    return new KafkaConfig();
  }

  /*
   * kafka general configuration class
   */
  public static class KafkaConfig {
    private String bootstrapServers;
    private ConsumerConfiguration consumer;
    private ProducerConfiguration producer;

    public String getBootstrapServers() {
      return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
      this.bootstrapServers = bootstrapServers;
    }

    public ConsumerConfiguration getConsumer() {
      return consumer;
    }

    public void setConsumer(ConsumerConfiguration consumer) {
      this.consumer = consumer;
    }

    public ProducerConfiguration getProducer() {
      return producer;
    }

    public void setProducer(ProducerConfiguration producer) {
      this.producer = producer;
    }
  }

  /*
   * kafka consumer configuration class
   */
  public static class ConsumerConfiguration {
    private String groupId;
    private List<String> topics;

    public String getGroupId() {
      return groupId;
    }

    public void setGroupId(String groupId) {
      this.groupId = groupId;
    }

    public List<String> getTopics() {
      return topics;
    }

    public void setTopics(List<String> topics) {
      this.topics = topics;
    }
  }

  /*
   * kafka producer configuration class
   */
  public static class ProducerConfiguration {
    private String producerId;
    private String acks;
    private String maxInFlightRequests;
    private String requestTimeoutMs;
    private String retries;
    private String metadataFetchTimeoutMs;
    private List<String> topics;

    public String getProducerId() {
      return producerId;
    }

    public void setProducerId(String producerId) {
      this.producerId = producerId;
    }

    public String getAcks() {
      return acks;
    }

    public void setAcks(String acks) {
      this.acks = acks;
    }

    public String getMaxInFlightRequests() {
      return maxInFlightRequests;
    }

    public void setMaxInFlightRequests(String maxInFlightRequests) {
      this.maxInFlightRequests = maxInFlightRequests;
    }

    public String getRequestTimeoutMs() {
      return requestTimeoutMs;
    }

    public void setRequestTimeoutMs(String requestTimeoutMs) {
      this.requestTimeoutMs = requestTimeoutMs;
    }

    public String getRetries() {
      return retries;
    }

    public void setRetries(String retries) {
      this.retries = retries;
    }

    public String getMetadataFetchTimeoutMs() {
      return metadataFetchTimeoutMs;
    }

    public void setMetadataFetchTimeoutMs(String metadataFetchTimeoutMs) {
      this.metadataFetchTimeoutMs = metadataFetchTimeoutMs;
    }

    public List<String> getTopics() {
      return topics;
    }

    public void setTopics(List<String> topics) {
      this.topics = topics;
    }
  }
}
