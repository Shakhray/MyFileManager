package sh;

import java.util.List;

/**
 * Created by Шерхан on 24.12.2015.
 */
public class Viewer {
    public void print(List<String> values) {
        values.forEach(this::println);
    }

    public void print(Object value) {
        System.out.print(value.toString());
    }

    public void println(Object value) {
        System.out.println(value.toString());
    }
}
