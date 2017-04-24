package us.filin.topnpaths;

import org.junit.Assert;

import org.junit.Test;

import java.io.File;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by vfilin on 4/24/17.
 */
public class TopMostPopularPathsTest {



    @Test
    public void parseLinesIgnoreWhiteSpaces() throws Exception {
        Stream<String> stream = Stream.of(
            null,
            "",
            "    ",
            "\t\t\t\t",
            "\r\r\r\r",
            "\n\n\n\n",
            "  1 ",
            " 1 2 3 ",
            "u1 2",
            "   u1 2 ",
            "     u1           2 ",
            "u1 2"
        );
        Stream<String[]> parsed = TopMostPopularPaths.parseLines(stream);
        Assert.assertNotNull(parsed);
        Predicate<String[]> predicate = i -> i != null && i.length ==2 && "u1".equals(i[0]) && "2".equals(i[1]);
        Assert.assertTrue(parsed.allMatch(predicate));
    }

    @Test
    public void parseLinesDontMiss() throws Exception {
        Stream<String> stream = Stream.of(
            "u1 2",
            "   u1 2 ",
            "     u1           2 ",
            "u1 2",
            "u2, 2"
        );
        Stream<String[]> parsed = TopMostPopularPaths.parseLines(stream);
        Assert.assertEquals(5, parsed.count());
    }


    @Test
    public void processRecordsLimits() throws Exception {
        Stream<String[]> stream = Stream.of(new String[][]{{"u","2"}});
        Assert.assertEquals(1, TopMostPopularPaths.processRecords(stream, 1, 1).count());

        stream = Stream.of(new String[][]{{"u","2"}});
        Assert.assertEquals(0, TopMostPopularPaths.processRecords(stream, 1, 3).count());

        stream = Stream.of(new String[][]{{"u","2"},{"u","3"}});
        Assert.assertEquals(0, TopMostPopularPaths.processRecords(stream, 1, 3).count());

        stream = Stream.of(new String[][]{{"u","1"},{"u","2"},{"u","3"}});
        Assert.assertEquals(1, TopMostPopularPaths.processRecords(stream, 1, 3).count());


        stream = Stream.of(new String[][]{{"u","1"},{"u","2"},{"u","3"},{"u","4"},{"u","5"} });
        Assert.assertEquals(3, TopMostPopularPaths.processRecords(stream, 999, 3).count());
    }

    @Test
    public void processRecordsTheTest() throws Exception {
        String[][] strings = new String[][]{
            {"U1", "/"},
            {"U1", "subscribers"},
            {"U2", "/"},
            {"U2", "subscribers"},
            {"U1", "filter"},
            {"U1", "export"},
            {"U2", "filter"},
            {"U2", "export"},
            {"U3", "/"},
            {"U3", "catalog"},
            {"U3", "edit"}
        };

        Stream<String[]> stream = Stream.of(strings);
        Assert.assertEquals(3, TopMostPopularPaths.processRecords(stream, 999999, 3).count());

        stream = Stream.of(strings);
        Assert.assertEquals(2, TopMostPopularPaths.processRecords(stream, 2, 3).count());

        stream = Stream.of(strings);
        Object[] result = TopMostPopularPaths.processRecords(stream, 2, 3).toArray();
        Assert.assertEquals("/ -> subscribers -> filter", result[0].toString());
        Assert.assertEquals("subscribers -> filter -> export", result[1].toString());
    }

    @Test
    public void doItFile() throws Exception {
        TopMostPopularPaths object = new TopMostPopularPaths();
        String filename = getClass().getResource("/files/test.txt").getFile();
        object.doIt(new File(filename));
    }
}