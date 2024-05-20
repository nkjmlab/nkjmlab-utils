package org.nkjmlab.util.java.json.jsonrpc;

public interface Service {

  public static class Impl implements Service {

    public String getString() {
      return "stringVal";
    }

    public String convertString(int i) {
      return "" + i;
    }
  }
}
