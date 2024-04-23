package org.nkjmlab.util.java.beans;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BeanUtilsTest {

  @Test
  public void testCopyProperties() {
    TestBean source = new TestBean();
    source.setName("John Doe");
    source.setAge(30);

    TestBean destination = new TestBean();
    BeanUtils.copyProperties(source, destination);

    assertEquals(source.getName(), destination.getName(), "Name should be copied");
    assertEquals(source.getAge(), destination.getAge(), "Age should be copied");
  }

  @Test
  public void testCopyPropertiesIfAbsent() {
    TestBean source = new TestBean();
    source.setName("John Doe");
    source.setAge(30);

    TestBean destination = new TestBean();
    destination.setName("Jane Doe");

    BeanUtils.copyPropertiesIfAbsent(source, destination);

    assertEquals("Jane Doe", destination.getName(), "Name should not be overwritten");
    assertEquals(source.getAge(), destination.getAge(), "Age should be copied because it was null");
  }

  public static class TestBean {
    private String name;
    private Integer age;

    public TestBean() {}

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }
  }
}
