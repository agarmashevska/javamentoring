package com.epam.exercises.circularbuffer;

import java.util.Comparator;
import com.epam.exercises.circularbuffer.CircularBuffer;

public class StringCircularBufferComparator<T extends String> implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        T object1 = (T) o1;
        T object2 = (T) o2;
        int l1 = object1.length();
        int l2 = object2.length();
        int lmin = Math.min(l1, l2);
        for (int i = 0; i < lmin; i++) {
            int char1 = object1.charAt(i);
            int char2 = object2.charAt(i);
            if (char1 != char2) {
                return char1 - char2 > 0 ? 1 : -1;
            }
        }

        if (l1 != l2) {
            return l1 - l2 > 0 ? 1 : -1;
        }

        return 0;
    }
}
