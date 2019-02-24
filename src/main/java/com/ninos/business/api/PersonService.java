package com.ninos.business.api;

import com.ninos.business.model.Person;
import com.ninos.business.spi.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the main actions around persons
 */
@Service
public class PersonService {
  private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(
      PersonRepository personRepository
  ) {
    this.personRepository = personRepository;
  }

  /**
   * stores a person in the persistence layer
   *
   * @param person the object to store
   *
   * @return the stored person
   */
  public Person store(Person person) {
    logger.debug("Received: {}", person);
    return personRepository.save(person);
  }
}
