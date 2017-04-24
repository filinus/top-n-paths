package us.filin.topnpaths;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * App accepts filename via single CLI argument
 * Using constants you may limit number of top
 * and (bonus!) specify length of page. By default it's 3
 * Main routine is processRecords.
 * Logic to parse input, algorithm to find 3-paths and rendering are aparted from each other.
 *
 * I tried to keep it simple, that's why it's in single file with only one nested class.
 * App works, you may try it from CLI, check example of input file test.txt
 * or run unit tests, all they works either.
 * And last test contains exactly your data sample
 */
public class TopMostPopularPaths {
    final private static int PATH_LENGTH = 3;
    final private static String COLUMN_DELIMITER = "\\s+";
    private int topN = 10;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.print("Input file name expected");
            System.exit(1);
        }
        String inputFilePath = args[0]; // say, "/work/top3paths/test/resources/test.txt";

        new TopMostPopularPaths().doIt(new File(inputFilePath));
    }


    void doIt(File file) {
        try ( // autocloseables
              InputStream inputStream = new FileInputStream(file);
              InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
              BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            Stream<String[]> parsedLines = parseLines(bufferedReader.lines()); //parse
            Stream<String> mostPopularPath = processRecords(parsedLines, topN, PATH_LENGTH); //algorithm
            mostPopularPath.forEach(System.out::println); //rendering
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * here we abstract from syntax of input
     *
     * @param lines raw line from stream
     * @return stream with 2-element array [user, page]
     */
    static Stream<String[]> parseLines(Stream<String> lines) {
        return
            lines
                .filter(Objects::nonNull)
                .map(line -> {
                    String[] columns = line.trim().split(COLUMN_DELIMITER, 3);
                    if (columns.length != 2) {
                        return null;
                    } else if (columns[0].length() == 0) {
                        return null;
                    } else if (columns[1].length() == 0) {
                        return null;
                    }
                    return columns; // it might be an object, however, in our case, it's enough plain two element array
                })
                .filter(Objects::nonNull)
                .parallel();
    }

    /**
     *
     *
     * @param records consist of 2-element items, where 0 position is username, 1 is page
     * @param topN how many top most path we want to receive
     * @param pathLength length of path can vary
     * @return stream of most popular path
     */
    static Stream<String> processRecords(Stream<String[]> records, int topN, int pathLength) {
        ConcurrentMap<String, LongAdder> counterMap = new ConcurrentHashMap<>();

        records
            .collect(Collectors.groupingBy(i -> i[0], //user at 0
                Collectors.mapping(
                    i -> i[1], // page at 1
                    Collector.of(
                        () -> new Path(counterMap, pathLength), // supplier
                        Path::accumulate, // BiConsumer
                        (a, b) -> null // BinaryOperator is not used
                    )
                )
            ))
            .size();

        return counterMap
            .entrySet()
            .stream()
            .sorted((a, b) -> Long.compare(b.getValue().longValue(), a.getValue().longValue())) //note, the reverse order
            .limit(topN)
            .map(Map.Entry::getKey); // comment this line to have entries with counters
    }

    final static class Path {

        final private String[] values;
        final private ConcurrentMap<String, LongAdder> map;

        private Path(ConcurrentMap<String, LongAdder> map, int pathLength) {
            this.map = map;
            this.values = new String[pathLength];
        }

        void accumulate(String next) {
            System.arraycopy(values, 1, values, 0, values.length - 1);
            values[values.length - 1] = next;
            if (values[0] != null) {
                map.computeIfAbsent(String.join(" -> ", values), adder -> new LongAdder()).increment();
            }
        }
    }
}
