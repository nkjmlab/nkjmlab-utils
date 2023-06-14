package org.nkjmlab.util.javalin;

import java.util.function.Consumer;
import java.util.function.Function;
import org.nkjmlab.util.java.net.UrlPath;
import org.nkjmlab.util.java.web.ViewModel;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class BasicJavalinContextHandler implements Handler {

  private final Function<Context, Function<String, Consumer<ViewModel.Builder>>> handler;
  private final Function<Context, ViewModel.Builder> modelBuilder;

  public static interface ContextFilePathModelHandler
      extends Function<Context, Function<String, Consumer<ViewModel.Builder>>> {

  }

  public BasicJavalinContextHandler(Function<Context, ViewModel.Builder> modelSupplier,
      ContextFilePathModelHandler handler) {
    this.handler = handler;
    this.modelBuilder = modelSupplier;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    String filePath = UrlPath.of(ctx.url()).removeStart("/app/").getPath();
    ViewModel.Builder model = modelBuilder.apply(ctx);
    handler.apply(ctx).apply(filePath).accept(model);;
  }

}
