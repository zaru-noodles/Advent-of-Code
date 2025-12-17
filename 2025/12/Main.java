import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.UncheckedIOException;
import java.util.stream.*;
import java.util.*;

class Point {
    public long x;
    public long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

class Shape {
    public Set<Point> points = new HashSet<>();
    
    public Shape(String[] pattern) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (pattern[i].charAt(j) == '#') {
                    points.add(new Point(j, i));
                }
            }
        }    
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
        List<String> data = getData().toList();
        List<Shape> shapes = new ArrayList<>();
        for (int i = 1; i < 29; i += 5) {
            shapes.add(new Shape(data.subList(i, i+3).toArray(String[]::new)));
        }
        
        long res = 0;
        for (int i = 30; i < data.size(); i++) {
            String row = data.get(i);
            int totalArea = Integer.parseInt(row.substring(0, 2)) * Integer.parseInt(row.substring(3, 5));
            int area = 0;

            for (int j = 0; j < shapes.size(); j += 1) {
                int k = 7 + j * 3;
                area += Integer.parseInt(row.substring(k, k+2)) * shapes.get(j).points.size();
            }

            if (area <= totalArea) {
                res++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(part1());
    }
}