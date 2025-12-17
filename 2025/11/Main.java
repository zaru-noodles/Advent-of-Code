import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.UncheckedIOException;
import java.util.stream.*;
import java.util.*;

class DFS {
    private Map<String, Long> cache = new HashMap<>();
    private Map<String, String[]> adjDict;

    public static long dfs(Map<String, String[]> adjDict, String cur, String target) {
        DFS x = new DFS();
        x.adjDict = adjDict;
        return x.helper(cur, target);
    }

    public long helper(String cur, String target) {
        if (cur.equals(target)) {
            return 1;
        } else if (cache.containsKey(cur)) {
            return cache.get(cur);
        } else if (adjDict.get(cur) == null) {
            return 0;
        }

        long res = 0;
        for (String next : adjDict.get(cur)) {
            res += helper(next, target);
        }

        cache.put(cur, res);
        return res;
    }
}

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
        Map<String, String[]> adjDict = new HashMap<>();
        for (String s : getData().toList()) {
            String key = s.substring(0, 3);
            String[] val = s.substring(5).split(" ");
            adjDict.put(key, val);
        }

        return DFS.dfs(adjDict, "you", "out");
    }

    public static long part2() {
        Map<String, String[]> adjDict = new HashMap<>();
        for (String s : getData().toList()) {
            String key = s.substring(0, 3);
            String[] val = s.substring(5).split(" ");
            adjDict.put(key, val);
        }

        return DFS.dfs(adjDict, "svr", "dac") * DFS.dfs(adjDict, "dac", "ftt") * DFS.dfs(adjDict, "fft", "out") + 
            DFS.dfs(adjDict, "svr", "fft") * DFS.dfs(adjDict, "fft", "dac") * DFS.dfs(adjDict, "dac", "out");
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}