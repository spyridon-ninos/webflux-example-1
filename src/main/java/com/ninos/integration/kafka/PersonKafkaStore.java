package com.ninos.integration.kafka;

import com.ninos.business.model.Person;
import com.ninos.business.spi.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

/**
 * Kafka backed person repository
 */
@Repository
public class PersonKafkaStore implements PersonRepository {

  private final KafkaTemplate<String, Person> personKafkaTemplate;
  private final KafkaConfiguration.KafkaConfig kafkaConfig;

  @Autowired
  public PersonKafkaStore(
      KafkaTemplate<String, Person> personKafkaTemplate,
      KafkaConfiguration.KafkaConfig kafkaConfig
  ) {
    this.personKafkaTemplate = personKafkaTemplate;
    this.kafkaConfig = kafkaConfig;
  }

  /*
   * saves the person object to each of the topics configured
   * for the producer
   *
   * returns the id (uuid) of the person
   */
  @Override
  public String save(Person person) {
    kafkaConfig
        .getProducer()
        .getTopics()
        .forEach(topic -> personKafkaTemplate.send(topic, person));

    return person.getUuid();
  }
}
