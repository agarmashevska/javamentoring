package com.epam.exercises.circularbuffer;

import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

public class CircularBuffer<T> {
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final Object[] buffer;
    private final int length;
    private int head = 0;
    private int tail = 0;
    private T[] helper;
    private Comparator comparator;

    public CircularBuffer(int length) {
        this.buffer = (T[]) new Object[length];
        this.length = length;
    }

    public void put(T t) {
        printMsg("Adding next value to buffer: ", t, ANSI_BLUE_BACKGROUND);

        if (!isEmpty() & isFull()) {
            throw new RuntimeException("The buffer is totally full!");
        }

        buffer[tail++ % buffer.length] = t;
        printStats();
    }

    public T get() {
        if(isEmpty()) {
            throw new RuntimeException("The buffer is empty!");
        }

        T item = (T) buffer[head % buffer.length];
        buffer[head++ % buffer.length] = null;

        printMsg("Getting next value from buffer: ", item, ANSI_RED_BACKGROUND);

        printStats();
        return item;
    }

    public Object[] toObjectArray() {
        if (isEmpty()) return new Object[]{};

        int headIndex = getIndex(head);
        int tailIndex = getIndex(tail);
        boolean isHeadGreaterThenTail = headIndex > getIndex(tail - 1);

        int size = isHeadGreaterThenTail ?
                buffer.length + (tailIndex - headIndex) : tail - head;

        Object[] temp = new Object[size];
        int index = 0;

        if(isHeadGreaterThenTail) {
            for (int i = headIndex; i < buffer.length; i++) {
                temp[index++] = buffer[i];
            }

            for (int i = 0; i < tailIndex; i++) {
                temp[index++] = buffer[i];
            }
        } else {
            for (int i = head; i < tail; i++) {
                temp[index++] = buffer[i];
            }
        }

        return temp;
    }

    public T[] toArray() {
        return (T[]) toObjectArray();
    }

    public List<T> asList() {
        return Arrays.asList(toArray());
    }

    public void addAll(List<? extends T> toAdd) {
        System.out.println(size());
       if (!isEmpty() & (length - size() < toAdd.size())) {
           throw new RuntimeException("The size of list is too big!");
       }
       toAdd
           .stream()
           .forEach(item -> put(item));
    }

    public void sort(Comparator<? super T> comparator) {
        this.comparator = comparator;
        helper = (T[]) new Object[length];
        mergeSort(0, length - 1);
    }

    private void mergeSort(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(low, middle);
            mergeSort(middle + 1, high);
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            helper[i] = (T) buffer[i];
        }
        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if (comparator.compare(helper[i], helper[j]) < 0) {
                buffer[k] = helper[i];
                i++;
            } else {
                buffer[k] = helper[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            buffer[k] = helper[i];
            k++;
            i++;
        }
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void print() {
        System.out.println(Arrays.toString(buffer));
    }

    private int size() {
        return toObjectArray().length;
    }

    private boolean isFull() {
        return head == tail % buffer.length;
    }

    private int getIndex(int real) {
        return real & buffer.length;
    }

    private void printStats() {
        printMsg(ANSI_PURPLE + "HEAD points to: " + head + ", TAIL points to: " + tail + ANSI_RESET);
        printMsg(ANSI_PURPLE + "Allocated buffer size " + size() + " out of " + length + ANSI_RESET);
    }

    private void printMsg(String text) {
        System.out.println(text);
    }

    private void printMsg(String text, T value, String color) {
        System.out.println(color + ANSI_WHITE + text + value + ANSI_RESET);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.buffer);
    }
}