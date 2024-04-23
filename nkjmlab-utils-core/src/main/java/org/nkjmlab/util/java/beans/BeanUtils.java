package org.nkjmlab.util.java.beans;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.nkjmlab.util.java.function.Try;

public class BeanUtils {

  /**
   * Copy properties from source to destination.
   *
   * @param source
   * @param destination
   */
  public static <T> void copyProperties(T source, T destination) {
    try {
      for (PropertyDescriptor propDescriptor : Introspector.getBeanInfo(destination.getClass())
          .getPropertyDescriptors()) {
        if (propDescriptor.getWriteMethod() == null || propDescriptor.getReadMethod() == null) {
          continue;
        }
        propDescriptor.getWriteMethod().invoke(destination,
            propDescriptor.getReadMethod().invoke(source));
      }
    } catch (Exception e) {
      Try.rethrow(e);
    }
  }


  /**
   * Copy properties from source to destination if the destination properties are null.
   *
   * @param source
   * @param destination
   */
  public static <T> void copyPropertiesIfAbsent(T source, T destination) {
    try {
      for (PropertyDescriptor propDescriptor : Introspector.getBeanInfo(destination.getClass())
          .getPropertyDescriptors()) {
        if (propDescriptor.getWriteMethod() == null || propDescriptor.getReadMethod() == null) {
          continue;
        }
        if (propDescriptor.getReadMethod().invoke(destination) != null) {
          continue;
        }
        propDescriptor.getWriteMethod().invoke(destination,
            propDescriptor.getReadMethod().invoke(source));
      }
    } catch (Exception e) {
      Try.rethrow(e);
    }
  }

}

