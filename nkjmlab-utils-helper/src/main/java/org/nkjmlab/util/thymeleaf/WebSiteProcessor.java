package org.nkjmlab.util.thymeleaf;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.io.FileUtils;
import org.nkjmlab.util.java.logging.SimpleLogger;
import org.nkjmlab.util.java.logging.SystemOutLogger;
import org.nkjmlab.util.java.time.DateTimeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

public class WebSiteProcessor {
  private static final SimpleLogger log = new SystemOutLogger();

  private final TemplateEngine templateEngine = createTemplateEngine();

  private static TemplateEngine createTemplateEngine() {
    TemplateEngine engine = new TemplateEngine();
    FileTemplateResolver r = new FileTemplateResolver();
    engine.setTemplateResolver(r);
    return engine;
  }

  public static void main(String[] args) {
    if (args.length != 2) {
      log.error("Args should be two.");
      System.out.println("[USAGE]");
      System.out.println("WebSiteProcessor srcRootDir targetRootDir");
      System.out.println("e.g. WebSiteProcessor webroot ../");
      return;
    }

    new WebSiteProcessor().process(Path.of(args[0]), Path.of(args[1]));

  }



  public void process(Path srcRootDir, Path targetRootDir) {
    log.info("srcRootDir={}, targetRootDir={}", srcRootDir, targetRootDir);
    getSrcHtmlFiles(srcRootDir).forEach(srcFile -> {
      File targetFile = createTargetFileIfAbsent(srcFile.toPath(), srcRootDir, targetRootDir);
      log.info("ProcFile srcFile={}, targetFile={}", srcFile, targetFile);
      Context ctx = new Context();
      ctx.setVariable("lastModified", DateTimeUtils.toTimestamp(new Date(srcFile.lastModified())));
      try (Writer writer = FileUtils.newBufferedWriter(targetFile.toPath())) {
        templateEngine.process(srcFile.getPath(), ctx, writer);
      } catch (IOException e) {
        Try.rethrow(e);
      }
    });
  }


  private static List<File> getSrcHtmlFiles(Path srcDir) {
    List<File> htmlFiles = FileUtils.listFiles(srcDir.toFile()).stream()
        .filter(f -> !f.getPath().contains("fragments" + File.separator))
        .filter(f -> f.getName().contains(".html")).toList();
    return htmlFiles;
  }

  private static File createTargetFileIfAbsent(Path srcFile, Path srcRootDir, Path targetRootDir) {
    Path srcRelativePath = srcRootDir.relativize(srcFile);
    File targetFile = targetRootDir.resolve(srcRelativePath).toFile();
    try {
      targetFile.getParentFile().mkdirs();
      targetFile.createNewFile();
      return targetFile;
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

}
