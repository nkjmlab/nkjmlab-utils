package org.nkjmlab.util.java.collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

  @Test
  void testToStringWithType() {

    System.out.println("boolean=>" + new boolean[0]);
    System.out.println("byte=>" + new byte[0]);
    System.out.println("char=>" + new char[0].toString());
    System.out.println("short=>" + new short[0]);
    System.out.println("int=>" + new int[0]);
    System.out.println("long=>" + new long[0]);
    System.out.println("float=>" + new float[0]);
    System.out.println("double=>" + new double[0]);
    System.out.println("String=>" + new String[0]);

    System.out.println(new int[0]);
    System.out.println(new int[0][0]);
    System.out.println(new int[0][0][0]);

    assertThat(ArrayUtils.toStringWithType((Object[]) null)).isEqualTo("null");
    assertThat(ArrayUtils.toStringWithType((Object) null)).isEqualTo("[null]");
    assertThat(ArrayUtils.toStringWithType("1", "2")).isEqualTo("[1 (String), 2 (String)]");
    assertThat(ArrayUtils.toStringWithType(1, 2)).isEqualTo("[1 (Integer), 2 (Integer)]");
    assertThat(ArrayUtils.toStringWithType(LocalDate.of(2022, 04, 04)))
        .isEqualTo("[2022-04-04 (LocalDate)]");

    assertThat(ArrayUtils.toStringWithType(new int[] {1, 2})).isEqualTo("[[1, 2] (int[])]");
    assertThat(ArrayUtils.toStringWithType((Object) new Integer[] {1, 2}))
        .isEqualTo("[[1, 2] (Integer[])]");
  }

  @Test
  void testToObjectArrayBooleanArray() {
    assertThat(ArrayUtils.toObjectArray(new boolean[] {true, false}))
        .isEqualTo(new Boolean[] {true, false});
  }

  @Test
  void testToObjectArrayByteArray() {
    assertThat(ArrayUtils.toObjectArray(new byte[] {(byte) 1, (byte) 2}))
        .isEqualTo(new Byte[] {(byte) 1, (byte) 2});
  }

  @Test
  void testToObjectArrayCharArray() {
    assertThat(ArrayUtils.toObjectArray(new char[] {'1', '2'}))
        .isEqualTo(new Character[] {'1', '2'});
  }

  @Test
  void testToObjectArrayDoubleArray() {
    assertThat(ArrayUtils.toObjectArray(new double[] {0.1, 0.2}))
        .isEqualTo(new Double[] {0.1, 0.2});
  }

  @Test
  void testToObjectArrayFloatArray() {
    assertThat(ArrayUtils.toObjectArray(new float[] {0.1f, 0.2f}))
        .isEqualTo(new Float[] {0.1f, 0.2f});
  }

  @Test
  void testToObjectArrayIntArray() {
    assertThat(ArrayUtils.toObjectArray(new int[] {1, 2})).isEqualTo(new Integer[] {1, 2});
  }

  @Test
  void testToObjectArrayLongArray() {
    assertThat(ArrayUtils.toObjectArray(new long[] {1L, 2L})).isEqualTo(new Long[] {1L, 2L});
  }

  @Test
  void testToObjectArrayShortArray() {
    assertThat(ArrayUtils.toObjectArray(new short[] {(short) 1, (short) 2}))
        .isEqualTo(new Short[] {(short) 1, (short) 2});
  }

  @Test
  void testConvertToObjectArray() {

    assertThat(ArrayUtils.convertToObjectArray(new int[] {1})).isEqualTo(new Integer[] {1});
    assertThat(ArrayUtils.convertToObjectArray(new int[][] {{1, 2, 3}, {4, 5, 6}}))
        .isEqualTo(new Integer[][] {{1, 2, 3}, {4, 5, 6}});
    assertThat(
            ArrayUtils.convertToObjectArray(
                new int[][][] {{{1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {10, 11, 12}}}))
        .isEqualTo(new Integer[][][] {{{1, 2, 3}, {4, 5, 6}}, {{7, 8, 9}, {10, 11, 12}}});
  }

  @Test
  public void testAdd_TArray_T() {
    Integer[] original = {1, 2, 3};
    Integer[] result = ArrayUtils.add(original, 4);
    assertThat(result).containsExactly(1, 2, 3, 4);
  }

  @Test
  public void testAddAll_TArray_TArray() {
    Integer[] original = {1, 2, 3};
    Integer[] addition = {4, 5};
    Integer[] result = ArrayUtils.addAll(original, addition);
    assertThat(result).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  public void testAddAll_intArray_intArray() {
    int[] original = {1, 2, 3};
    int[] addition = {4, 5};
    int[] result = ArrayUtils.addAll(original, addition);
    assertThat(result).containsExactly(1, 2, 3, 4, 5);
  }

  @Test
  public void testAdd_intArray_int() {
    int[] original = {1, 2, 3};
    int[] result = ArrayUtils.add(original, 4);
    assertThat(result).containsExactly(1, 2, 3, 4);
  }

  @Test
  public void testSplit() {
    Integer[] array = {1, 2, 3, 4, 5};
    List<Integer[]> result = ArrayUtils.split(2, array);
    assertThat(result).hasSize(3);
    assertThat(result.get(0)).containsExactly(1, 2);
    assertThat(result.get(1)).containsExactly(3, 4);
    assertThat(result.get(2)).containsExactly(5);
  }

  @Test
  public void testToObjectArray() {
    int[] intArray = {1, 2, 3};
    Integer[] integerArray = ArrayUtils.toObjectArray(intArray);
    assertThat(integerArray).containsExactly(1, 2, 3);

    // Test other primitive types similarly...
  }

  @Test
  public void testConvertSqlArrayToArray() {
    // This method requires a SQL Array, which usually comes from a SQL database.
    // Mocking or integration testing is required to test this method properly.
  }

  @Test
  public void testConvertToObjectArray_Class_Object() {
    Integer[] original = {1, 2, 3};
    Object[] result = ArrayUtils.convertToObjectArray(Integer.class, original);
    assertThat(result).containsExactly(1, 2, 3);
  }

  @Test
  public void testConvertToObjectArray_Object() {
    int[] original = {1, 2, 3};
    Object[] result = ArrayUtils.convertToObjectArray(original);
    assertThat(result).containsExactly(1, 2, 3);

    // Test other primitive types similarly...
  }

  @Test
  public void testToString() {
    Integer[] array = {1, 2, 3};
    String result = ArrayUtils.toString(array);
    assertThat(result).isEqualTo("[1, 2, 3]");
  }

  @Test
  public void testToStringWithType1() {
    Integer[] array = {1, 2, 3};
    String result = ArrayUtils.toStringWithType(array, "Test", null);
    assertThat(result).isEqualTo("[[1, 2, 3] (Integer[]), Test (String), null]");
  }
}
