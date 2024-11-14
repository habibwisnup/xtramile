package org.example.xtramile_test;

import java.util.*;

public class Task1 {
    public static List<Integer> findUniqueNumbers(List<Integer> numbers) {
        Set<Integer> uniqueSet = new HashSet<>();
        List<Integer> uniqueList = new ArrayList<>();

        for (int num : numbers) {
            if (!uniqueSet.contains(num)) {
                uniqueSet.add(num);
                uniqueList.add(num);
            } else {
                uniqueList.remove((Integer) num);
            }
        }
        return uniqueList;
    }
}
