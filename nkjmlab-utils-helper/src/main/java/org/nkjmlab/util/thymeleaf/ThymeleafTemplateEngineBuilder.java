package org.nkjmlab.util.thymeleaf;

import java.util.ArrayList;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;

public class ThymeleafTemplateEngineBuilder {

  private final ThymeleafTemplateResolverBuilder templateResolverBuilder =
      ThymeleafTemplateResolverBuilder.builder();

  private final List<IDialect> dialects = new ArrayList<>();

  public static ThymeleafTemplateEngineBuilder builder() {
    return new ThymeleafTemplateEngineBuilder();
  }

  public ThymeleafTemplateEngineBuilder setPrefix(String prefix) {
    templateResolverBuilder.setPrefix(prefix);
    return this;
  }

  public ThymeleafTemplateEngineBuilder setSuffix(String suffix) {
    templateResolverBuilder.setSuffix(suffix);
    return this;
  }

  public ThymeleafTemplateEngineBuilder setTtlMs(long cacheTtlMs) {
    templateResolverBuilder.setCacheTtlMs(cacheTtlMs);
    return this;
  }

  public ThymeleafTemplateEngineBuilder setTemplateResolver(
      AbstractConfigurableTemplateResolver templateResolver) {
    templateResolverBuilder.setTemplateResolver(templateResolver);
    return this;
  }

  public ThymeleafTemplateEngineBuilder addDialect(IDialect dialect) {
    dialects.add(dialect);
    return this;
  }

  public TemplateEngine build() {
    TemplateEngine engine = new TemplateEngine();
    engine.setTemplateResolver(templateResolverBuilder.build());
    dialects.forEach(d -> engine.addDialect(d));
    return engine;
  }

}
