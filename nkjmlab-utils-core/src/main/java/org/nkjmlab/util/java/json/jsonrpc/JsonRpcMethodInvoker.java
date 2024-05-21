package org.nkjmlab.util.java.json.jsonrpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface JsonRpcMethodInvoker {

  /**
   * Finds a method in the specified class with the given name and parameter.
   *
   * @param clazz the class to search for the method
   * @param methodName the name of the method to find
   * @param params the parameters to match the method signature
   * @return the found method
   * @throws IllegalArgumentException if the method is not found
   */
  Method findMethod(Class<?> clazz, String methodName, Object[] params);

  /**
   * Invokes the specified method on the given instance with the provided parameters.
   *
   * @param instance the target instance
   * @param method the method to be invoked
   * @param params the parameters to be passed to the method
   * @return the result of the method invocation
   * @throws IllegalAccessException if this Method object is enforcing Java language access control
   *     and the underlying method is inaccessible
   * @throws IllegalArgumentException if the method is passed an inappropriate argument
   * @throws InvocationTargetException if the underlying method throws an exception
   */
  Object invokeMethod(Object target, Method method, Object[] params)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
