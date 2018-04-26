package algorithms.search;

import org.junit.jupiter.api.Test;

import javax.jnlp.FileContents;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {
    private BestFirstSearch bestFS = new BestFirstSearch();

    @Test
    void addToSetNull() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bestFS.addToSet(null);
        assertEquals("Cannot add null state\r\n" ,outContent.toString());
    }

    @Test
    void getName() {
        assertEquals("BestFirstSearch",bestFS.getName());
    }

    @Test
    void solveNull() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        assertNull(bestFS.solve(null));
        assertEquals("Cannot solve null problem\r\n", outContent.toString());
    }
}