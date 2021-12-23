package org.nkjmlab.util.commons.math3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.collections4.IteratorUtils;

public class Frequency<T extends Comparable<T>> {
  private Class<T> clazz;
  private org.apache.commons.math3.stat.Frequency freq;

  public static void main(String[] args) {
    {
      Frequency<Integer> freq = createIntegerFrequency();
      freq.addValue(1);
      freq.addValue(2);
      freq.addValue(1);
      freq.addValue(1);
      freq.addValue(3);
      System.out.println(freq.getCount(1));
      System.out.println(freq.getCount(2));
      System.out.println(freq.getCount(3));
      System.out.println("---");

    }
    {
      Frequency<String> freq = createStringFrequency();
      freq.addValue("s");
      freq.addValue("u");
      freq.addValue("u");
      freq.addValue("t");
      System.out.println(freq.getCount("S"));
      System.out.println(freq.getCount("s"));
      System.out.println(freq.getCount("u"));
    }

  }

  public Frequency(Class<T> clazz) {
    this.freq = new org.apache.commons.math3.stat.Frequency();
    this.clazz = clazz;
  }


  public void merge(Frequency<T> other) {
    freq.merge(other.getFreq());
  }

  public org.apache.commons.math3.stat.Frequency getFreq() {
    return freq;
  }

  public void addValue(T v) {
    freq.addValue(v);
  }

  public long getCount(T v) {
    return freq.getCount(v);
  }

  public static Frequency<Integer> createIntegerFrequency() {
    return new Frequency<>(Integer.class);
  }

  public static Frequency<String> createStringFrequency() {
    return new Frequency<>(String.class);
  }

  @SuppressWarnings("unchecked")
  public List<Entry<T, Long>> entrySet() {
    Map<T, Long> map = new HashMap<>();
    List<Entry<Comparable<?>, Long>> list = IteratorUtils.toList(freq.entrySetIterator());
    list.forEach(e -> {
      Comparable<?> key = e.getKey();
      if (clazz.isAssignableFrom(Integer.class) && key instanceof Long) {
        map.put((T) Integer.valueOf(((Long) key).intValue()), e.getValue());
      } else {
        map.put((T) key, e.getValue());
      }
    });
    List<Entry<T, Long>> result = map.entrySet().stream()
        .sorted(Comparator.comparing(e -> e.getKey())).collect(Collectors.toList());
    return result;
  }

  @Override
  public String toString() {
    return "Frequency [clazz=" + clazz + ", freq=" + freq + "]";
  }
}
