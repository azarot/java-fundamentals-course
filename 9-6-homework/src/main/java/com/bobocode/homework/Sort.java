package com.bobocode.cs;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sort {
    public static void main(String[] args) {
        List<Integer> list = Stream.of(6, 2, 3, 4, 5, 6).collect(Collectors.toList());
        sort(list);
        System.out.println(list);

    }

    private static<T extends Comparable<T>> void sort(List<T> list) {
        divideAndSort(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<T>> void divideAndSort(List<T> list, int start, int end) {
        if (start == end) return;

        var middle = (start + end) / 2;

        divideAndSort(list, start, middle);
        divideAndSort(list, middle + 1, end);

        var leftIndex = start;
        var rightIndex = middle + 1;

        while (leftIndex < rightIndex && rightIndex <= end) {
            if (list.get(leftIndex).compareTo(list.get(rightIndex)) > 0) {
                T rightValue = list.get(rightIndex);
                list.remove(rightIndex);
                list.add(leftIndex, rightValue);
                leftIndex++;
                rightIndex++;
            } else {
                leftIndex++;
            }
        }
    }
}
