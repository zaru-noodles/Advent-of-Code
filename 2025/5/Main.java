import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.List;

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
        class Range {
            public long start;
            public long end;

            public Range(String s) {
                String[] range = s.split("-");
                this.start = Long.valueOf(range[0]);
                this.end = Long.valueOf(range[1]);
            }
        };

        List<String> data = getData().toList();
        int tmp = data.indexOf("");
        List<Range> ranges = data.subList(0, tmp).stream().map(Range::new).toList();
        int res = 0;
        
        for (int i = tmp + 1; i < data.size(); i++) {
            long num = Long.valueOf(data.get(i));
            for (Range r : ranges) {
                if (r.start <= num && num <= r.end) {
                    res++;
                    break;
                }
            }
        }

        return res;
    }

    public static long part2() {
        class Range {
            public Long start;
            public Long end;

            public Range(String s) {
                String[] range = s.split("-");
                this.start = Long.valueOf(range[0]);
                this.end = Long.valueOf(range[1]);
            }
        };

        List<String> data = getData().toList();
        int tmp = data.indexOf("");
        List<Range> ranges = data.subList(0, tmp).stream().map(Range::new).collect(Collectors.toList());
        ranges.sort(Comparator.comparingLong(r -> r.start));

        long res = 0;
        long endMax = 0;
        for (Range r : ranges) {
            res += r.end - r.start + 1;
            res -= Math.max(0, Math.min(endMax, r.end) - r.start + 1);
            endMax = Math.max(endMax, r.end);
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}