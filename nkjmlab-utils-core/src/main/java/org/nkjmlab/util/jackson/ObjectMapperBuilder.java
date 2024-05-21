package org.nkjmlab.util.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperBuilder {

  public static ObjectMapperBuilder builder() {
    return new ObjectMapperBuilder();
  }

  private boolean findAndAddModules = true;
  private boolean failOnUnkownProperties = true;

  private ObjectMapperBuilder() {}

  public ObjectMapperBuilder findAndAddModules(boolean findAndAddModules) {
    this.findAndAddModules = findAndAddModules;
    return this;
  }

  public ObjectMapperBuilder failOnUnkownProperties(boolean failOnUnkownProperties) {
    this.failOnUnkownProperties = failOnUnkownProperties;
    return this;
  }

  public ObjectMapper build() {
    ObjectMapper objectMapper =
        findAndAddModules
            ? com.fasterxml.jackson.databind.json.JsonMapper.builder().findAndAddModules().build()
            : new ObjectMapper();
    objectMapper.configure(
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnkownProperties);
    return objectMapper;
  }
}
