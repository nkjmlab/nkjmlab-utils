package org.nkjmlab.util.jsonrpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import org.nkjmlab.sorm4j.internal.util.Try;
import org.nkjmlab.util.java.io.IoStreamUtils;
import org.nkjmlab.util.java.json.JsonMapper;

public class JsonRpcClientFactory {


  public static <T> T create(JsonMapper mapper, Class<T> interfaceClass, URL url) {
    return create(interfaceClass, new JsonRpcInvocationHandler(url, mapper));
  }

  private static <T> T create(Class<T> interfaceClass, InvocationHandler handler) {
    return interfaceClass.cast(Proxy.newProxyInstance(
        Thread.currentThread().getContextClassLoader(), new Class<?>[] {interfaceClass}, handler));
  }

  public static class JsonRpcInvocationHandler implements InvocationHandler {

    private final JsonMapper mapper;
    private final URL url;

    public JsonRpcInvocationHandler(URL url, JsonMapper mapper) {
      this.url = url;
      this.mapper = mapper;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

      HttpURLConnection con;
      try {
        con = (HttpURLConnection) url.openConnection();
      } catch (IOException e) {
        throw Try.rethrow(e);
      }

      con.setUseCaches(false);
      con.setDoOutput(true);
      writeRequest(con, method, args);
      int sts = getResponseCode(con);
      if (sts == 404) {
        throw new IllegalArgumentException(sts + " file not found: " + url);
      } else if (sts == 405) {
        throw new IllegalArgumentException(sts + " Method Not Allowed: " + url);
      } else if (sts == 500) {
        throw new IllegalStateException(sts + " Internal Server Error: " + url);
      }
      Object result = readResponse(con, method.getReturnType());
      return result;
    }



    private int getResponseCode(HttpURLConnection con) {
      try {
        return con.getResponseCode();
      } catch (IOException e) {
        throw Try.rethrow(e);
      }
    }


    private void writeRequest(HttpURLConnection con, final Method method, final Object[] args) {
      con.setRequestProperty("Accept", "application/json-rpc");
      con.setRequestProperty("Content-type", "application/json-rpc");
      try (OutputStream os = con.getOutputStream()) {
        mapper.toJsonAndWrite(JsonRpcUtils.createRequest(method.getName(), args), os, false);
        os.flush();
      } catch (IOException e) {
        throw Try.rethrow(e);
      }
    }

    private Object readResponse(HttpURLConnection con, Class<?> returnType) {
      try (InputStream is = getResponseStream(con)) {
        String str = IoStreamUtils.readAsString(is, StandardCharsets.UTF_8);
        JsonRpcResponse ret = mapper.toObject(str, JsonRpcResponse.class);
        Object result = mapper.convertValue(ret.getResult(), returnType);
        return result;
      } catch (IOException e) {
        throw Try.rethrow(e);
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
      }
      return is;
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
