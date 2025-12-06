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
        List<String> data = getData().toList();
        List<String[]> nums = data.subList(0, data.size()-1).stream().map(s -> s.strip().split(" +")).toList();
        String[] ops = data.get(data.size()-1).split(" +");
        long res = 0;

        for (int i = 0; i < ops.length; i++) {
            long total;

            if (ops[i].equals("+")) {total = 0;}
            else {total = 1;}

            for (int j = 0; j < nums.size(); j++) {
                if (ops[i].equals("+")) {total += Long.parseLong(nums.get(j)[i]);}
                else {total *= Long.parseLong(nums.get(j)[i]);}
            }
            res += total;
        }

        return res;
    }

    public static long part2() {
        char[][] data = getData().map(x -> x.toCharArray()).toArray(char[][]::new);
        long res = 0;
        long total = 0;
        char op = '+';

        for (int i = 0; i < data[0].length; i++) {
            if (!(data[data.length-1][i] == ' ')) {
                op = data[data.length-1][i];
                if (op == '+') {total = 0;}
                else {total = 1;}
            }

            int num = 0;
            for (int j = 0; j < data.length-1; j++) {
                if (data[j][i] == ' ') {continue;}

                num *= 10;
                num += Integer.parseInt(Character.toString(data[j][i]));
            }

            if (num == 0) {res += total; total = 0;}
            else if (op == '+') {total += num;}
            else {total *= num;}
        }

        return res + total;
    }

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }
}