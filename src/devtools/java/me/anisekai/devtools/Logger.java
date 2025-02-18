package me.anisekai.devtools;

import java.io.PrintStream;
import java.time.ZonedDateTime;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public final class Logger {

    private static int NESTED_LEVEL = 0;

    private Logger() {}

    private static void print(PrintStream ps, String message, Object... args) {

        ZonedDateTime now = ZonedDateTime.now();

        String nesting = "|  ".repeat(NESTED_LEVEL);
        String time    = String.format("[%02d:%02d:%02d] ", now.getHour(), now.getMinute(), now.getSecond());
        String log     = String.format(message, args);
        ps.println(time + nesting + log);
    }

    public static void log(String message, Object... args) {

        print(System.out, message, args);
    }

    public static void err(String message, Object... args) {

        print(System.err, message, args);
    }

    public static void logn(String message, Object... args) {

        log(String.format(message, args), args);
        nest();
    }

    public static void nest() {

        NESTED_LEVEL++;
    }

    public static void unnest() {

        NESTED_LEVEL--;
    }

}
