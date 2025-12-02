import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;
import java.io.UncheckedIOException;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Main {
    private static Stream<String> getData() {
        Path path = Path.of("test.txt"); 
        try {
            return Files.readAllLines(path).stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static long part1() {
        Function<String, Long> firstHalf = x -> Optional.of(x.substring(0, x.length() / 2))
            .filter(y -> !y.equals(""))
            .map(Long::valueOf)
            .orElse(1L);

        return getData()
            .flatMap(line -> Stream.of(line.split(",")))
            .map(x -> x.split("-"))
            .flatMap(x -> Stream.iterate(firstHalf.apply(x[0]), y -> y + 1)
                .map(y -> y.toString())
                .map(y -> Long.valueOf(y + y))
                .takeWhile(y -> Long.valueOf(x[1]) >= y)
                .filter(y -> Long.valueOf(x[0]) <= y))
            .reduce(0L, (x, y) -> x + y);
    }

    public static long part2() {
        // given up on functional programming
        List<String> data = getData().flatMap(line -> Stream.of(line.split(","))).toList();
        long total = 0;
        for (String s : data) {
            String[] tmp = s.split("-");
            long l = Long.valueOf(tmp[0]);
            long r = Long.valueOf(tmp[1]);

            for (long i = l; i <= r; i++) {
                if (Pattern.matches("^(\\d+)\\1+$", String.valueOf(i))) {
                    total += i;
                }
            }
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}