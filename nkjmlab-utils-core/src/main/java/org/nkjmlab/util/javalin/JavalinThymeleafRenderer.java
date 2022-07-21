package org.nkjmlab.util.javalin;

import java.util.Map;
import org.nkjmlab.sorm4j.util.table_def.annotation.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import io.javalin.http.Context;
import io.javalin.plugin.rendering.FileRenderer;

public class JavalinThymeleafRenderer implements FileRenderer {

  private final TemplateEngine templateEngine;

  public JavalinThymeleafRenderer(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public String render(@NotNull String filePath, @NotNull Map<String, Object> model,
      @NotNull Context ctx) {
    WebContext context =
        new WebContext(ctx.req, ctx.res, ctx.req.getServletContext(), ctx.req.getLocale());
    context.setVariables(model);
    return templateEngine.process(filePath, context);
  }

}
