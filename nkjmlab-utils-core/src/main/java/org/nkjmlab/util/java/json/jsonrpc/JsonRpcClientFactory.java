package org.nkjmlab.util.java.json.jsonrpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

import org.nkjmlab.util.java.function.Try;
import org.nkjmlab.util.java.io.ReaderUtils;
import org.nkjmlab.util.java.json.JsonMapper;

public class JsonRpcClientFactory {

  /**
   * Creates a JSON-RPC client proxy for the specified interface.
   *
   * @param <T> the type of the interface
   * @param url the URL of the JSON-RPC server
   * @param mapper the {@link JsonMapper} to use for JSON serialization and deserialization
   * @param interfaceClass the interface class
   * @return a proxy instance that implements the specified interface
   * @throws IllegalArgumentException if the interface class is not an interface
   */
  public static <T> T create(URL url, JsonMapper mapper, Class<T> interfaceClass) {
    return interfaceClass.cast(
        Proxy.newProxyInstance(
            Thread.currentThread().getContextClassLoader(),
            new Class<?>[] {interfaceClass},
            new JsonRpcInvocationHandler(url, mapper)));
  }

  public static class JsonRpcInvocationHandler implements InvocationHandler {

    private final URL url;
    private final JsonMapper mapper;

    public JsonRpcInvocationHandler(URL url, JsonMapper mapper) {
      this.url = url;
      this.mapper = mapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) {
      HttpURLConnection con = null;
      try {
        con = (HttpURLConnection) url.openConnection();

        con.setUseCaches(false);
        con.setDoOutput(true);

        writeRequest(con, method, params);

        int responseCode = con.getResponseCode();
        switch (responseCode) {
          case 404:
            throw new IllegalArgumentException(responseCode + " File not found: " + url);
          case 405:
            throw new IllegalArgumentException(responseCode + " Method Not Allowed: " + url);
          case 500:
            throw new IllegalStateException(responseCode + " Internal Server Error: " + url);
          default:
            Object result = readResponse(con, method.getReturnType());
            return result;
        }
      } catch (IOException e) {
        throw Try.rethrow(e);
      } finally {
        if (con != null) {
          con.disconnect();
        }
      }
    }

    private void writeRequest(HttpURLConnection con, final Method method, final Object[] params)
        throws IOException {
      con.setRequestProperty("Accept", "application/json-rpc");
      con.setRequestProperty("Content-type", "application/json-rpc");
      try (OutputStream os = con.getOutputStream()) {
        mapper.toJsonAndWrite(
            new JsonRpcRequest(UUID.randomUUID().toString(), method.getName(), params), os, false);
        os.flush();
      }
    }

    private Object readResponse(HttpURLConnection con, Class<?> returnType) throws IOException {
      try (InputStream is = getResponseStream(con);
          InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
        String str = ReaderUtils.readAsString(reader);
        JsonRpcResponse ret = mapper.toObject(str, JsonRpcResponse.class);
        Object result = mapper.convertValue(ret.result(), returnType);
        return result;
      }
    }

    private static InputStream getResponseStream(HttpURLConnection con) throws IOException {
      InputStream is = getInputStream(con);
      if (is == null) {
        return is;
      }
      String ce = con.getContentEncoding();
      if (ce == null) {
        return is;
      }
      if (ce.equals("gzip")) {
        return new GZIPInputStream(is);
      } else if (ce.equals("deflate")) {
        return new DeflaterInputStream(is);
      } else {
        return is;
      }
    }

    private static InputStream getInputStream(HttpURLConnection con) {
      try {
        return con.getInputStream();
      } catch (IOException e) {
        return con.getErrorStream();
      }
    }
  }
}
