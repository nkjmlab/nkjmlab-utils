package org.nkjmlab.util.thymeleaf;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateEngineBuilder {

  private final TemplateResolverBuilder templateResolverBuilder =
      TemplateResolverBuilder.builder();

  private final List<IDialect> dialects = new ArrayList<>();

  public static TemplateEngineBuilder builder() {
    return new TemplateEngineBuilder();
  }

  public TemplateEngineBuilder setPrefix(String prefix) {
    templateResolverBuilder.setPrefix(prefix);
    return this;
  }

  public TemplateEngineBuilder setSuffix(String suffix) {
    templateResolverBuilder.setSuffix(suffix);
    return this;
  }

  public TemplateEngineBuilder setCacheTtlMs(long cacheTtlMs) {
    templateResolverBuilder.setCacheTtlMs(cacheTtlMs);
    return this;
  }

  public TemplateEngineBuilder setTemplateResolver(
      AbstractConfigurableTemplateResolver templateResolver) {
    templateResolverBuilder.setTemplateResolver(templateResolver);
    return this;
  }

  public TemplateEngineBuilder addDialect(IDialect dialect) {
    dialects.add(dialect);
    return this;
  }

  public TemplateEngine build() {
    TemplateEngine engine = new TemplateEngine();
    engine.setTemplateResolver(templateResolverBuilder.build());
    dialects.forEach(d -> engine.addDialect(d));
    return engine;
  }

  /**
   * @see <a
   *     href="https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-resolvers">Tutorial:
   *     Using Thymeleaf</a>
   */
  public static class TemplateResolverBuilder {

    private String prefix = "/templates/";
    private String suffix = ".html";
    private Long cacheTtlMs = Long.valueOf(-1);
    private TemplateMode templateMode = TemplateMode.HTML;
    private AbstractConfigurableTemplateResolver templateResolver =
        new ClassLoaderTemplateResolver();

    public static TemplateResolverBuilder builder() {
      return new TemplateResolverBuilder();
    }

    public AbstractConfigurableTemplateResolver build() {
      templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
      templateResolver.setTemplateMode(templateMode);
      templateResolver.setPrefix(prefix);
      templateResolver.setSuffix(suffix);
      if (cacheTtlMs > 0) {
        templateResolver.setCacheable(true);
        templateResolver.setCacheTTLMs(cacheTtlMs);
      } else {
        templateResolver.setCacheable(false);
      }
      return templateResolver;
    }

    public TemplateResolverBuilder setPrefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    public TemplateResolverBuilder setSuffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    /**
     * @param cacheTtlMs if the value is 0 or less the cache is not used.
     * @return
     */
    public TemplateResolverBuilder setCacheTtlMs(Long cacheTtlMs) {
      this.cacheTtlMs = cacheTtlMs;
      return this;
    }

    public TemplateResolverBuilder setTemplateMode(TemplateMode templateMode) {
      this.templateMode = templateMode;
      return this;
    }

    public TemplateResolverBuilder setTemplateResolver(
        AbstractConfigurableTemplateResolver templateResolver) {
      this.templateResolver = templateResolver;
      return this;
    }
  }
}
