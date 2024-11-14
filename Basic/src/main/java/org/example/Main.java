package org.example;

import java.util.Arrays;
import java.util.List;

import static org.example.xtramile_test.Task1.findUniqueNumbers;
import static org.example.xtramile_test.Task2.findMaxSum;

public class Main {
    public static void main(String[] args) {

        //TASK 1
        List<Integer> numbersT1 = Arrays.asList(1, 2, 1, 3);
        System.out.println(findUniqueNumbers(numbersT1));

        //TASK 2
        List<Integer> numbersT2 = Arrays.asList(5);
        System.out.println(findMaxSum(numbersT2));
    }
}