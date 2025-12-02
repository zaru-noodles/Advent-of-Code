import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.Optional;
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


    public static int part1() {
        return getData()
            .map(str -> Optional.of(str.charAt(0))
                .filter(x -> x == 'R')
                .map(x -> str.substring(1))
                .orElse("-" + str.substring(1)))
            .map(x -> new int[] {Integer.valueOf(x), 0})
            .reduce(new int[] {50, 0}, (x, y) -> new int[] {((((x[0] + y[0]) % 100) + 100) % 100), Optional.of(((((x[0] + y[0]) % 100) + 100) % 100))
                .filter(z -> z == 0)
                .map(z -> x[1] + 1)
                .orElse(x[1])
            })[1];  
    }

    public static int part2() {
        return getData()
            .map(str -> Optional.of(str.charAt(0))
                .filter(x -> x == 'R')
                .map(x -> str.substring(1))
                .orElse("-" + str.substring(1)))
            .map(x -> new int[] {Integer.valueOf(x), 0})
            .reduce(new int[] {50, 0}, (x, y) -> new int[] {((((x[0] + y[0]) % 100) + 100) % 100), Optional.of(x[0] + y[0])
                .filter(z -> z <= 0 || z > 99)
                .map(z -> x[1] + Optional.of(y[0]).filter(a -> a < 0).map(a -> Math.abs(-((100 - x[0]) % 100) + y[0]) / 100).orElse((y[0] + x[0]) / 100))
                .orElse(x[1])
            })[1];
    }
    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}
