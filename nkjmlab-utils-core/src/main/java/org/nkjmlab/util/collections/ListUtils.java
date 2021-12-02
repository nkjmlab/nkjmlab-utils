package org.nkjmlab.util.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
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

  public static void main(String[] args) {
    List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println(partition(list, 1));
    System.out.println(partition(list, 2));
    System.out.println(partition(list, 3));
    System.out.println(partition(list, 4));
    System.out.println(partition(list, 5));
    System.out.println(partition(list, 20));
  }

  public static int getPartisionSize(int sizeOfList, int numOfPartision) {
    if (sizeOfList % numOfPartision == 0) {
      return sizeOfList / numOfPartision;
    }

    return (sizeOfList / numOfPartision) + 1;
  }


}
