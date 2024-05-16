package org.nkjmlab.util.java.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClassUtilsTest {

  @Test
  public void testConvertToClass() {
    // Test primitive types
    assertEquals(boolean.class, ClassUtils.convertToClass("boolean"));
    assertEquals(char.class, ClassUtils.convertToClass("char"));
    assertEquals(byte.class, ClassUtils.convertToClass("byte"));
    assertEquals(short.class, ClassUtils.convertToClass("short"));
    assertEquals(int.class, ClassUtils.convertToClass("int"));
    assertEquals(long.class, ClassUtils.convertToClass("long"));
    assertEquals(float.class, ClassUtils.convertToClass("float"));
    assertEquals(double.class, ClassUtils.convertToClass("double"));

    // Test wrapper types
    assertEquals(Boolean.class, ClassUtils.convertToClass("java.lang.Boolean"));
    assertEquals(Character.class, ClassUtils.convertToClass("java.lang.Character"));
    assertEquals(Byte.class, ClassUtils.convertToClass("java.lang.Byte"));
    assertEquals(Short.class, ClassUtils.convertToClass("java.lang.Short"));
    assertEquals(Integer.class, ClassUtils.convertToClass("java.lang.Integer"));
    assertEquals(Long.class, ClassUtils.convertToClass("java.lang.Long"));
    assertEquals(Float.class, ClassUtils.convertToClass("java.lang.Float"));
    assertEquals(Double.class, ClassUtils.convertToClass("java.lang.Double"));
    assertEquals(Object.class, ClassUtils.convertToClass("java.lang.Object"));

    // Test fully qualified class names
    assertEquals(String.class, ClassUtils.convertToClass("java.lang.String"));
    assertEquals(java.util.List.class, ClassUtils.convertToClass("java.util.List"));

    // Test class not found
    assertThrows(
        ClassNotFoundException.class,
        () -> {
          ClassUtils.convertToClass("com.nonexistent.Class");
        });
  }

  @Test
  public void testPrimitiveToWrapper() {
    assertTrue(ClassUtils.isAssignable(int.class, Integer.class));
    assertTrue(ClassUtils.isAssignable(boolean.class, Boolean.class));
    assertTrue(ClassUtils.isAssignable(char.class, Character.class));
    assertTrue(ClassUtils.isAssignable(byte.class, Byte.class));
    assertTrue(ClassUtils.isAssignable(short.class, Short.class));
    assertTrue(ClassUtils.isAssignable(long.class, Long.class));
    assertTrue(ClassUtils.isAssignable(float.class, Float.class));
    assertTrue(ClassUtils.isAssignable(double.class, Double.class));
  }

  @Test
  public void testWrapperToPrimitive() {
    assertTrue(ClassUtils.isAssignable(Integer.class, int.class));
    assertTrue(ClassUtils.isAssignable(Boolean.class, boolean.class));
    assertTrue(ClassUtils.isAssignable(Character.class, char.class));
    assertTrue(ClassUtils.isAssignable(Byte.class, byte.class));
    assertTrue(ClassUtils.isAssignable(Short.class, short.class));
    assertTrue(ClassUtils.isAssignable(Long.class, long.class));
    assertTrue(ClassUtils.isAssignable(Float.class, float.class));
    assertTrue(ClassUtils.isAssignable(Double.class, double.class));
  }

  @Test
  public void testSameTypes() {
    assertTrue(ClassUtils.isAssignable(Integer.class, Integer.class));
    assertTrue(ClassUtils.isAssignable(int.class, int.class));
  }

  @Test
  public void testSupertypeAndSubtype() {
    assertFalse(ClassUtils.isAssignable(Number.class, Integer.class));
    assertTrue(ClassUtils.isAssignable(Integer.class, Number.class));
  }

  @Test
  public void testUnrelatedTypes() {
    assertFalse(ClassUtils.isAssignable(String.class, Integer.class));
    assertFalse(ClassUtils.isAssignable(Integer.class, String.class));
  }

  @Test
  public void testNullSrcClass() {
    assertTrue(ClassUtils.isAssignable(null, Object.class));
    assertFalse(ClassUtils.isAssignable(null, int.class));
  }

  @Test
  public void testNullDestClass() {
    assertFalse(ClassUtils.isAssignable(Integer.class, null));
  }

  @Test
  public void testPrimitiveWideningConversions() {

    assertTrue(ClassUtils.isAssignable(int.class, long.class));
    assertTrue(ClassUtils.isAssignable(int.class, float.class));
    assertTrue(ClassUtils.isAssignable(int.class, double.class));
    assertTrue(ClassUtils.isAssignable(long.class, float.class));
    assertTrue(ClassUtils.isAssignable(long.class, double.class));
    assertTrue(ClassUtils.isAssignable(float.class, double.class));
    assertFalse(ClassUtils.isAssignable(double.class, float.class));
    assertFalse(ClassUtils.isAssignable(float.class, long.class));
    assertFalse(ClassUtils.isAssignable(long.class, int.class));
  }

  @Test
  public void testWrapperWideningConversions() {
    assertFalse(ClassUtils.isAssignable(Integer.class, Long.class));
    assertFalse(ClassUtils.isAssignable(Integer.class, Float.class));
    assertFalse(ClassUtils.isAssignable(Integer.class, Double.class));
    assertFalse(ClassUtils.isAssignable(Long.class, Float.class));
    assertFalse(ClassUtils.isAssignable(Long.class, Double.class));
    assertFalse(ClassUtils.isAssignable(Float.class, Double.class));
    assertFalse(ClassUtils.isAssignable(Double.class, Float.class));
    assertFalse(ClassUtils.isAssignable(Float.class, Long.class));
    assertFalse(ClassUtils.isAssignable(Long.class, Integer.class));
  }

  @Test
  public void testOtherObjectTypes() {
    assertFalse(ClassUtils.isAssignable(Object.class, String.class));
    assertTrue(ClassUtils.isAssignable(String.class, Object.class));
  }
}
