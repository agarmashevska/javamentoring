import java.util.List;
import java.util.ArrayList;

import com.epam.exercises.circularbuffer.CircularBuffer;
import com.epam.exercises.circularbuffer.StringCircularBufferComparator;

public class Main {
    public static void main(String[] args) {
        CircularBuffer buffer = new CircularBuffer<String>(5);

        List<String> all =new ArrayList<String>();
        all.add("ergter");
        all.add("ddd");
        all.add("aaaa");
        all.add("aa");
        all.add("ghrt");
        // Error
//        all.add("book_5");
        buffer.addAll(all);
//        buffer.put("book_1");
//        buffer.put("book_2");
//        buffer.put("book_3");
//        buffer.put("book_4");
//        buffer.put("book_5");

        //Error
//        buffer.put("book_6");

//        buffer.get();
        StringCircularBufferComparator myComparator = new StringCircularBufferComparator();
        buffer.print();
        buffer.sort(myComparator);
        buffer.print();
    }
}
