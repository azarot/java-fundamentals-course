package com.bobocode.homework.h9;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class MergeSort {
    public static void main(String[] args) {
        int[] source = generate(20);
        System.out.println(Arrays.toString(source));

        int[] sorted = sort(Arrays.copyOf(source, source.length));
        System.out.println(Arrays.toString(sorted));

        var sortTask = new MergeSortTask(Arrays.copyOf(source, source.length));
        System.out.println(Arrays.toString(sortTask.compute()));

    }

    private static class MergeSortTask extends RecursiveTask<int[]> {
        private final int[] arr;

        public MergeSortTask(int[] arr) {
            this.arr = arr;
        }

        @Override
        protected int[] compute() {
            if (arr.length == 1) {
                return arr;
            }
            int half  = (arr.length) / 2;
            int[] left = new int[half];
            int[] right = new int[arr.length - half];
            System.arraycopy(arr, 0, left, 0, left.length);
            System.arraycopy(arr, left.length, right, 0, right.length);

            var sortedLeft = new MergeSortTask(left);
            var sortedRight = new MergeSortTask(right);
            sortedLeft.fork();
            sortedLeft.join();
            sortedRight.compute();

            merge(arr, left, right);
            return arr;
        }
    }

    private static int[] generate(int amount) {
        var result = new int[amount];
        var random = new Random();
        for (int i = 0; i < amount; i++) {
            result[i] = random.nextInt(amount * amount);
        }

        return result;
    }

    private static int[] sort(int[] arr) {
        if (arr.length == 1) {
            return arr;
        }
        int half  = (arr.length) / 2;
        int[] left = new int[half];
        int[] right = new int[arr.length - half];
        System.arraycopy(arr, 0, left, 0, left.length);
        System.arraycopy(arr, left.length, right, 0, right.length);

        var sortedLeft = sort(left);
        var sortedRight = sort(right);

        merge(arr, sortedLeft, sortedRight);
        return arr;
    }

    private static void merge(int[] arr, int[] sortedLeft, int[] sortedRight) {
        int rightIndex = 0, leftIndex = 0, resultIndex = 0;
        while (leftIndex < sortedLeft.length || rightIndex < sortedRight.length) {
            if (leftIndex == sortedLeft.length) {
                System.arraycopy(sortedRight, rightIndex, arr, resultIndex, sortedRight.length - rightIndex);
                return;
            } else if (rightIndex == sortedRight.length) {
                System.arraycopy(sortedLeft, leftIndex, arr, resultIndex, sortedLeft.length - leftIndex);
                return;
            } else if (sortedLeft[leftIndex] < sortedRight[rightIndex]) {
                arr[resultIndex++] = sortedLeft[leftIndex++];
            } else if (sortedLeft[leftIndex] >= sortedRight[rightIndex]) {
                arr[resultIndex++] = sortedRight[rightIndex++];
            }
        }
    }
}
