package com.ninos.service;

import com.ninos.business.api.PersonService;
import com.ninos.business.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * defines the methods that implement the routes followed by the person related web paths
 */
@Configuration
public class PersonRoutes {
  private static final Logger logger = LoggerFactory.getLogger(PersonRoutes.class);

  private final PersonService personService;

  @Autowired
  public PersonRoutes(
      PersonService personService
  ) {
    this.personService = personService;
  }

  /**
   * the route that saves a person to the persistence layer
   *
   * @return a router function
   */
  @Bean
  public RouterFunction<ServerResponse> save() {
    return route(POST("/person").and(accept(MediaType.APPLICATION_JSON_UTF8)), this::saveHandler);
  }

  /*
   * the handler that builds the response on the save action
   * produces json
   */
  private Mono<ServerResponse> saveHandler(ServerRequest request) {
    return request
        .body(toMono(Person.class))
        .doOnNext(personService::store)
        .then(ok().contentType(MediaType.APPLICATION_JSON_UTF8).build());
  }
}
