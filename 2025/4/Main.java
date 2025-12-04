import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        char[][] data = getData().map(String::toCharArray).toArray(char[][]::new);
        int[][] directions = new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int res = 0;
        int n = data.length;
        int m = data[0].length;

        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (data[y][x] == '.') {
                    continue;
                }

                int count = 0;
                for (int[] dir : directions) {
                    int y1 = y + dir[1];
                    int x1 = x + dir[0];

                    if (0 <= x1 && x1 < m && 0 <= y1 && y1 < n && data[y1][x1] == '@') {
                        count += 1;
                    }  
                }

                if (count < 4) {
                    res++;
                }
            }
        }
        
        return res;
    }

    public static long part2() {
        char[][] data = getData().map(String::toCharArray).toArray(char[][]::new);
        int[][] directions = new int[][] {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        int res = 0;
        int accessible = -1;
        int n = data.length;
        int m = data[0].length;

        while (accessible != 0) {
            accessible = 0;

            for (int y = 0; y < n; y++) {
                for (int x = 0; x < m; x++) {
                    if (data[y][x] == '.') {
                        continue;
                    }

                    int count = 0;
                    for (int[] dir : directions) {
                        int y1 = y + dir[1];
                        int x1 = x + dir[0];

                        if (0 <= x1 && x1 < m && 0 <= y1 && y1 < n && data[y1][x1] == '@') {
                            count += 1;
                        }  
                    }

                    if (count < 4) {
                        accessible++;
                        res++;
                        data[y][x] = '.';
                    }
                }
            }
        }
        
        return res;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}