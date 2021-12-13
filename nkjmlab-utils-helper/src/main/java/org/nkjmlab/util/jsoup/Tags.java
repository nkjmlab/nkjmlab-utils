package org.nkjmlab.util.jsoup;

import java.util.Arrays;
import org.jsoup.nodes.Element;

public class Tags {

  public static Element create(String tag, Object... children) {
    return append(new Element(tag), children);
  }

  public static Element append(Element e, Object... children) {
    Arrays.stream(children).forEach(m -> e.append(m.toString()));
    return e;
  }

  public static Element ruby(String target, String ruby) {
    return new Element("ruby").appendChild(new Element("rb").text(target))
        .appendChild(new Element("rp").text("(")).appendChild(new Element("rt").text(ruby))
        .appendChild(new Element("rp").text(")"));
  }

  public static Element div(Object... children) {
    return create("div", children);
  }

  public static Element ul(Object... children) {
    return create("ul", children);
  }

  public static Element li(Object... children) {
    return create("li", children);
  }

}
