package sh.console;

import java.util.List;

/**
 * @author Sherhan
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
