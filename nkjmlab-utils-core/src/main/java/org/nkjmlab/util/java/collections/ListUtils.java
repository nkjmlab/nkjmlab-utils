package org.nkjmlab.util.java.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ListUtils {
  private ListUtils() {}

  public static <T> List<List<T>> partition(List<T> list, int numOfPartition) {
    List<List<T>> result = new ArrayList<>();
    int partitionSize = getPartisionSize(list.size(), numOfPartition);
    for (int i = 0; i < numOfPartition; i++) {
      int fromIndex = i * partitionSize;
      int toIndex = Math.min((i + 1) * partitionSize, list.size());
      if (fromIndex > toIndex) {
        result.add(Collections.emptyList());
      } else {
        result.add(list.subList(fromIndex, toIndex));
      }
    }

    return result;
  }

  public static int getPartisionSize(int sizeOfList, int numOfPartision) {
    if (sizeOfList % numOfPartision == 0) {
      return sizeOfList / numOfPartision;
    }
    return (sizeOfList / numOfPartision) + 1;
  }

  public static <T extends Object> boolean areAllUnique(List<T> list) {
    return list.stream().allMatch(new HashSet<>()::add);
  }
}
