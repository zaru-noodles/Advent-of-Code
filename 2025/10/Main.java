import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.UncheckedIOException;
import java.util.stream.*;
import java.util.*;

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
        long res = 0;
        for (String s : getData().toList()) {
            String[] tmp = s.split(" ");
            int n = tmp[0].length() - 2;

            int lights = tmp[0].substring(1, tmp[0].length()-1).chars().boxed()
                .map(x -> x == '#' ? 1 : 0)
                .reduce(0, (x, y) -> x * 2 + y);

            Integer[][] buttons = Arrays.stream(Arrays.copyOfRange(tmp, 1, tmp.length-1))
                .map(x -> Arrays.stream(x.substring(1, x.length()-1).split(",")).map(Integer::valueOf).toArray(Integer[]::new))
                .toArray(Integer[][]::new);

            Map<Integer, Integer> cache = new HashMap<>();
            Deque<Integer> queue = new LinkedList<>();
            queue.add(lights);
            cache.put(lights, 0);

            while (queue.size() != 0) {
                int cur = queue.pop();

                if (cur == 0) {
                    break;
                }
                
                for (Integer[] button : buttons) {
                    int next = cur;
                    for (Integer i : button) {
                        next ^= (int) Math.pow(2, n - i - 1);
                    }

                    if (!cache.containsKey(next)) {
                        queue.add(next);
                        cache.put(next, cache.get(cur) + 1);
                    }
                }
            }
            res += cache.get(0);
        }

        return res;
    }

    public static long part2() {
        return 0L;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}