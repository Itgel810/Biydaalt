package flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a flashcard file into a list of FlashCard objects.
 *
 * <p>File format:
 * <pre>
 * # Lines starting with # are comments and are ignored
 * # Each card is defined as two non-empty lines:
 * #   Line 1: question
 * #   Line 2: answer
 * # Cards are separated by one or more blank lines
 *
 * What is the capital of Mongolia?
 * Ulaanbaatar
 *
 * What language do Mongolians speak?
 * Mongolian
 * </pre>
 */
public class CardFileParser {

    /**
     * Parses the given file and returns a list of flashcards.
     *
     * @param filePath path to the cards file
     * @return list of parsed flashcards
     * @throws IOException if the file cannot be read
     */
    public List<FlashCard> parse(String filePath) throws IOException {
        List<FlashCard> cards = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String question = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip comments
                if (line.startsWith("#")) {
                    continue;
                }

                if (line.isEmpty()) {
                    question = null;
                    continue;
                }

                if (question == null) {
                    question = line;
                } else {
                    cards.add(new FlashCard(question, line));
                    question = null;
                }
            }
        }

        return cards;
    }
}
