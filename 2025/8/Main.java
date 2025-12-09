import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.UncheckedIOException;
import java.util.stream.*;
import java.util.*;

class UnionFind {
    private int[] parent; 
    private int[] rank;  
    private int[] size; 

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;  
            size[i] = 1; 
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i; 
        }

        parent[i] = find(parent[i]);
        return parent[i];
    }

    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
                size[rootI] = 0;
            } else if (rank[rootJ] < rank[rootI]) {
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
                size[rootJ] = 0;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
                size[rootI] += size[rootJ];
                size[rootJ] = 0;         
            }
            return true;
        }
        return false; 
    }

    public int[] getSize() {
        return size;
    } 
}

class Point {
    public long x;
    public long y;
    public long z;

    public Point(String s) {
        String[] coords = s.split(",");
        this.x = Long.valueOf(coords[0]);
        this.y = Long.valueOf(coords[1]);
        this.z = Long.valueOf(coords[2]);
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
    }
};


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
        List<Point> points = getData().map(Point::new).toList();
        int n = points.size();
        List<int[]> edges = IntStream.range(0, n).boxed()
            .flatMap(x -> IntStream.range(x+1, n).boxed().map(y -> new int[] {x, y}))
            .collect(Collectors.toList());
        edges.sort((x, y) -> Double.compare(points.get(x[0]).distanceTo(points.get(x[1])), points.get(y[0]).distanceTo(points.get(y[1]))));

        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < 1000; i++) {
            uf.union(edges.get(i)[0], edges.get(i)[1]);
        }

        int[] sizes = uf.getSize();
        Arrays.sort(sizes);
        return sizes[n-1] * sizes[n-2] * sizes[n-3];
    }

    public static long part2() {
        List<Point> points = getData().map(Point::new).toList();
        int n = points.size();
        List<int[]> edges = IntStream.range(0, n).boxed()
            .flatMap(x -> IntStream.range(x+1, n).boxed().map(y -> new int[] {x, y}))
            .collect(Collectors.toList());
        edges.sort((x, y) -> Double.compare(points.get(x[0]).distanceTo(points.get(x[1])), points.get(y[0]).distanceTo(points.get(y[1]))));

        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            int[] sizes = uf.getSize();
            if (sizes[uf.find(edge[0])] + sizes[uf.find(edge[1])] == n) {
                return points.get(edge[0]).x * points.get(edge[1]).x;
            }
            uf.union(edge[0], edge[1]);
        }

        return -1L;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}