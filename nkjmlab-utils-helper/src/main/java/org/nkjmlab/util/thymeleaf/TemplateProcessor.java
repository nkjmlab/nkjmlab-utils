package org.nkjmlab.util.thymeleaf;

import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class TemplateProcessor {

  private final TemplateEngine engine;

  public TemplateProcessor(TemplateEngine engine) {
    this.engine = engine;
  }

  public TemplateProcessor() {
    this(TemplateEngineBuilder.builder().build());
  }

  public String process(String pathToTemplate, Context ctx) {
    return engine.process(pathToTemplate, ctx);
  }

  public String process(String pathToTemplate, Map<String, Object> variables) {
    Context ctx = new Context();
    ctx.setVariables(variables);
    return process(pathToTemplate, ctx);
  }
}
