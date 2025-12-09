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

    public Point(String s) {
        String[] coords = s.split(",");
        this.x = Long.valueOf(coords[0]);
        this.y = Long.valueOf(coords[1]);
    }

    public Point dirTo(Point p) {
        long dx = p.x - this.x;
        long dy = p.y - this.y;
        dx = dx != 0 ? dx / Math.abs(dx) : 0; 
        dy = dy != 0 ? dy / Math.abs(dy) : 0; 
        return new Point(dx, dy);
    }

    public boolean inRectangle(Point p1, Point p2) {
        return Math.min(p1.x, p2.x) <= x && x <= Math.max(p1.x, p2.x) && Math.min(p1.y, p2.y) <= y && y <= Math.max(p1.y, p2.y);
    }

    public long size(Point p) {
        return (Math.abs(p.x - x) + 1) * (Math.abs(p.y - y) + 1);
    }

    @Override
    public String toString(){
        return this.x + " " + this.y;
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
        Point[] points = getData().map(Point::new).toArray(Point[]::new);
        long res = 0; 
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                res = Math.max(points[i].size(points[j]), res);        
            }
        }

        return res;
    }

    public static long part2() {
        Point[] points = getData().map(Point::new).toArray(Point[]::new);
        int n = points.length;
        List<Point> walls = new ArrayList<>();

        long x = 1;
        long y = -1;
        for (int i = 0; i < n; i++) {
            Point dir1 = points[((i-1) % n + n) % n].dirTo(points[i]);
            Point dir2 = points[i].dirTo(points[(i+1) % n]);
            long curX = points[((i-1) % n + n) % n].x + x;
            long curY = points[((i-1) % n + n) % n].y + y;

            if (dir1.x == 0) {
                y = x == dir2.x ? -dir1.y : dir1.y;
            } else {
                x = y == dir2.y ? -dir1.x : dir1.x;
            }

            while (curX != points[i].x + x || curY != points[i].y + y) {
                if ((curX + curY) % 10000 == 0) {  // for optimisation
                    walls.add(new Point(curX, curY));
                }
                curX += dir1.x;
                curY += dir1.y;
            }

            walls.add(new Point(points[i].x + x, points[i].y + y));
        }

        List<int[]> pairs = new ArrayList<int[]>();
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                pairs.add(new int[] {i, j});        
            }
        }

        pairs.sort((i, j) -> -Long.compare(points[i[0]].size(points[i[1]]), points[j[0]].size(points[j[1]])));
        for (int[] p : pairs) {
            if (walls.stream().allMatch(z -> !z.inRectangle(points[p[0]], points[p[1]]))) {
                return points[p[0]].size(points[p[1]]);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}