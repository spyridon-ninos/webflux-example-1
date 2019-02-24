package com.ninos.business.spi;

import com.ninos.business.model.Person;

/**
 * should be implemented by the persistence code
 */
public interface PersonRepository {
    /**
     * should take a person object and store it in a persistence construct
     *
     * @param person the object to persist
     *
     * @return the saved person
     */
    Person save(Person person);
}
