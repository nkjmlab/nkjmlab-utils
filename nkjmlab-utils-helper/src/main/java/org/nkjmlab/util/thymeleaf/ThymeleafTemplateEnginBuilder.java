package org.nkjmlab.util.thymeleaf;

import java.util.ArrayList;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;

public class ThymeleafTemplateEnginBuilder {

  private final ThymeleafTemplateResolverBuilder templateResolverBuilder =
      ThymeleafTemplateResolverBuilder.builder();

  private final List<IDialect> dialects = new ArrayList<>();


  public static ThymeleafTemplateEnginBuilder builder() {
    return new ThymeleafTemplateEnginBuilder();
  }

  public ThymeleafTemplateEnginBuilder setPrefix(String prefix) {
    templateResolverBuilder.setPrefix(prefix);
    return this;
  }

  public ThymeleafTemplateEnginBuilder setSuffix(String suffix) {
    templateResolverBuilder.setSuffix(suffix);
    return this;
  }

  public ThymeleafTemplateEnginBuilder setTtlMs(long cacheTtlMs) {
    templateResolverBuilder.setCacheTtlMs(cacheTtlMs);
    return this;
  }

  public TemplateEngine build() {
    TemplateEngine engine = new TemplateEngine();
    engine.setTemplateResolver(templateResolverBuilder.build());
    dialects.forEach(d -> engine.addDialect(d));
    return engine;
  }

  public ThymeleafTemplateEnginBuilder addDialect(IDialect dialect) {
    dialects.add(dialect);
    return this;
  }



}
