package sh;

import javafx.util.Pair;

/**
 * @author Sherhan
 */
public class InputUtils {
    public static final String EMPTY = "";

    public static Pair<String, String> parseInput(String input) {
        return new Pair<>(getCommand(input), getArgs(input));
    }

    public static String getCommand(String input) {
        return input.split(" ")[0];
    }

    public static String getArgs(String input) {
        if (hasArguments(input)) {
            return input.substring(input.indexOf(' ') + 1, input.length());
        } else {
            return EMPTY;
        }
    }

    public static String[] splitArgs(String arguments) {
        return arguments.split(" ");
    }

    private static boolean hasArguments(String input) {
        return input.split(" ").length > 1;
    }
}
