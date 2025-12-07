import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import java.io.UncheckedIOException;

public class Main {
    private static Stream<String> getData() {
        Path path = Path.of("input.txt"); 
        try {
            return Files.readAllLines(path).stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static long part1() {
        List<String> data = getData().toList();
        long out = 0;

        for (String s : data) {
            char[] chars = s.toCharArray();
            int tenth = 0;
            int ones = 0;

            for (int i = 0; i < chars.length; i++) {
                int num = Integer.parseInt(String.valueOf(chars[i]));
                if (num > tenth && i != chars.length - 1) {
                    tenth = num;
                    ones = 0;
                }
                else if (num > ones) {
                    ones = num;
                }
            }
            out += tenth * 10 + ones;
        }

        return out;
    }

    public static long part2() {
        List<String> data = getData().toList();
        long out = 0;

        for (String s : data) {
            char[] chars = s.toCharArray();
            long res = 0;

            for (int i = 0; i < chars.length; i++) {
                long num = Long.parseLong(String.valueOf(chars[i]));
                for (int j = 11; j >= 0; j--) {
                    long x = (long) Math.pow(10, j);

                    if (num > (res / x) % 10 && i < chars.length - j) {
                        res = (res / (x * 10)) * (x * 10) + num * x; 
                        break;
                    }
                }
            }
            out += res;
        }

        return out;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}