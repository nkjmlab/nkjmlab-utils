package org.nkjmlab.util.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Roulette<T> {

  private final Map<T, Double> ratios;

  public Roulette(Map<T, ? extends Number> vals) {
    double sum = vals.values().stream().mapToDouble(n -> n.doubleValue()).sum();
    this.ratios =
        vals.entrySet().stream().map(e -> Map.entry(e.getKey(), e.getValue().doubleValue() / sum))
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
  }


  public T getRouletteValue() {
    return getRouletteValue(ratios);
  }

  private static <T> T getRouletteValue(Map<T, ? extends Number> ratios) {

    List<Entry<T, ? extends Number>> list = new ArrayList<>(ratios.entrySet());

    double d = ThreadLocalRandom.current().nextDouble();
    double s = 0;
    for (int i = 0; i < list.size(); i++) {
      s += list.get(i).getValue().doubleValue();
      if (d <= s) {
        return list.get(i).getKey();
      }
    }
    return list.get(list.size() - 1).getKey();
  }


  public List<T> getRouletteDistinctValues(int n) {
    if (n > ratios.size()) {
      throw new IllegalArgumentException(n + " is too large");
    }

    Map<T, Double> ratios = new HashMap<>(this.ratios);
    List<T> result = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      T obj = getRouletteValue(ratios);
      result.add(obj);
      ratios.remove(obj);
    }
    return result;
  }
}
