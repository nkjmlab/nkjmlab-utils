package org.nkjmlab.util.thymeleaf;

import java.util.Map;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ThymeleafTemplateProcessor {

  private final TemplateEngine engine;

  public ThymeleafTemplateProcessor(TemplateEngine engine) {
    this.engine = engine;
  }

  public ThymeleafTemplateProcessor() {
    this(ThymeleafTemplateEngineBuilder.builder().build());
  }

  public String process(String pathToTemplate, Map<String, Object> model) {
    return process(engine, pathToTemplate, model);
  }

  public static String process(TemplateEngine engine, String pathToTemplate,
      Map<String, Object> model) {
    Context ctx = new Context();
    ctx.setVariables(model);
    return engine.process(pathToTemplate, ctx);
  }

}
