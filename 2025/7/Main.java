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
        String[] grid = getData().toArray(String[]::new);
        Set<Integer> positions = new HashSet<>();
        positions.add(grid[0].indexOf("S"));
        long res = 0;

        for (String s : grid) {
            Set<Integer> newPostitions = new HashSet<>();
            for (int pos : positions) {
                if (s.charAt(pos) == '^') {
                    newPostitions.add(pos-1);
                    newPostitions.add(pos+1);
                    res++;
                }
                else {
                    newPostitions.add(pos);
                }
            }
            positions = newPostitions;
        }

        return res;
    }

    public static long part2() {
        String[] grid = getData().toArray(String[]::new);
        long[] positions = new long[grid[0].length()];
        Arrays.fill(positions, 0);
        positions[grid[0].indexOf("S")] = 1;

        for (String s : grid) {
            long[] newPostitions = new long[grid[0].length()];
            Arrays.fill(newPostitions, 0);

            for (int pos = 0; pos < grid[0].length(); pos++) {
                if (s.charAt(pos) == '^') {
                    newPostitions[pos-1] += positions[pos];
                    newPostitions[pos+1] += positions[pos];
                }
                else {
                    newPostitions[pos] += positions[pos];
                }
            }
            positions = newPostitions;
        }

        return Arrays.stream(positions).reduce(0L, (x, y) -> x + y);
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}