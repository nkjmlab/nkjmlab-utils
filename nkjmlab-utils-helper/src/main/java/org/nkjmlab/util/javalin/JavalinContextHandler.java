package org.nkjmlab.util.javalin;

import java.util.function.Consumer;
import java.util.function.Function;
import org.nkjmlab.util.java.net.UrlUtils;
import org.nkjmlab.util.javax.servlet.ViewModel;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class JavalinContextHandler implements Handler {

  private final Function<Context, Function<String, Consumer<ViewModel.Builder>>> handler;
  private final Function<Context, ViewModel.Builder> modelBuilder;

  public JavalinContextHandler(Function<Context, ViewModel.Builder> modelBuilder,
      Function<Context, Function<String, Consumer<ViewModel.Builder>>> handler) {
    this.handler = handler;
    this.modelBuilder = modelBuilder;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    String filePath = getFilePath(ctx);
    ViewModel.Builder model = modelBuilder.apply(ctx);
    handler.apply(ctx).apply(filePath).accept(model);;
  }


  public static String getFilePath(Context ctx) {
    return UrlUtils.of(ctx.url()).getPath().replaceFirst("^/app/", "");
  }


}
