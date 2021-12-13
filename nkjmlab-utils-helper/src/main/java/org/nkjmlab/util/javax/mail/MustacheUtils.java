package org.nkjmlab.util.javax.mail;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheUtils {

  public static void main(String[] args) {

  }


  public static String bindParametersToMustacheTemplate(File template, Object params) {
    try (Reader reader = new FileReader(template, StandardCharsets.UTF_8);
        StringWriter writer = new StringWriter()) {
      return aux(reader, writer, params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String bindParametersToMustacheTemplate(String template, Object params) {
    try (Reader reader = new StringReader(template); StringWriter writer = new StringWriter()) {
      return aux(reader, writer, params);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static String aux(Reader reader, StringWriter writer, Object parames) {
    final String name = "MustacheUtils";
    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile(reader, name);
    mustache.execute(writer, parames);
    writer.flush();
    return writer.toString();
  }

}
