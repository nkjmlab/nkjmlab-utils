package org.nkjmlab.util.thymeleaf;

import java.util.ArrayList;
import java.util.List;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;

public class ThymeleafTemplateEnginBuilder {

  private ThymeleafTemplateResolverBuilder templateResolverBuilder =
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

  public ThymeleafTemplateEnginBuilder setTemplateResolver(
      AbstractConfigurableTemplateResolver templateResolver) {
    templateResolverBuilder.setTemplateResolver(templateResolver);
    return this;
  }

  public ThymeleafTemplateEnginBuilder addDialect(IDialect dialect) {
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
