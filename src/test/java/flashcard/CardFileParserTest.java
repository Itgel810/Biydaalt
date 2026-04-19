package flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for CardFileParser.
 */
public class CardFileParserTest {

    @TempDir
    Path tempDir;

    @Test
    public void testParseBasicCards() throws IOException {
        Path file = tempDir.resolve("cards.txt");
        Files.writeString(file,
            "What is 2+2?\n4\n\nCapital of Mongolia?\nUlaanbaatar\n");

        CardFileParser parser = new CardFileParser();
        List<FlashCard> cards = parser.parse(file.toString());

        assertEquals(2, cards.size());
        assertEquals("What is 2+2?", cards.get(0).getQuestion());
        assertEquals("4", cards.get(0).getAnswer());
        assertEquals("Capital of Mongolia?", cards.get(1).getQuestion());
        assertEquals("Ulaanbaatar", cards.get(1).getAnswer());
    }

    @Test
    public void testParseSkipsComments() throws IOException {
        Path file = tempDir.resolve("cards.txt");
        Files.writeString(file,
            "# This is a comment\nQuestion?\nAnswer\n");

        CardFileParser parser = new CardFileParser();
        List<FlashCard> cards = parser.parse(file.toString());

        assertEquals(1, cards.size());
        assertEquals("Question?", cards.get(0).getQuestion());
    }

    @Test
    public void testParseEmptyFile() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.writeString(file, "");

        CardFileParser parser = new CardFileParser();
        List<FlashCard> cards = parser.parse(file.toString());

        assertEquals(0, cards.size());
    }

    @Test
    public void testParseNonExistentFileThrows() {
        CardFileParser parser = new CardFileParser();
        assertThrows(IOException.class,
            () -> parser.parse("/nonexistent/path/cards.txt"));
    }
}
