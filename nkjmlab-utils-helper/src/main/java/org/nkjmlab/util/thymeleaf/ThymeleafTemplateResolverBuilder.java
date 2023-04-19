package org.nkjmlab.util.thymeleaf;

import java.nio.charset.StandardCharsets;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafTemplateResolverBuilder {

  private String prefix = "/templates/";
  private String suffix = ".html";
  private long cacheTtlMs = -1;
  private TemplateMode templateMode = TemplateMode.HTML;
  private AbstractConfigurableTemplateResolver templateResolver = new ClassLoaderTemplateResolver();


  public static ThymeleafTemplateResolverBuilder builder() {
    return new ThymeleafTemplateResolverBuilder();
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

  public ThymeleafTemplateResolverBuilder setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public ThymeleafTemplateResolverBuilder setSuffix(String suffix) {
    this.suffix = suffix;
    return this;
  }

  /**
   *
   * @param cacheTtlMs if the value is less than 0 cache is not used.
   * @return
   */

  public ThymeleafTemplateResolverBuilder setCacheTtlMs(long cacheTtlMs) {
    this.cacheTtlMs = cacheTtlMs;
    return this;
  }

  public ThymeleafTemplateResolverBuilder setTemplateMode(TemplateMode templateMode) {
    this.templateMode = templateMode;
    return this;
  }

  public ThymeleafTemplateResolverBuilder setTemplateResolver(
      AbstractConfigurableTemplateResolver templateResolver) {
    this.templateResolver = templateResolver;
    return this;
  }



}
