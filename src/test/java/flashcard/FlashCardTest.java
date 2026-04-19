package flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for FlashCard.
 */
public class FlashCardTest {

    @Test
    public void testInitialState() {
        FlashCard card = new FlashCard("Q", "A");
        assertEquals("Q", card.getQuestion());
        assertEquals("A", card.getAnswer());
        assertEquals(0, card.getCorrectCount());
        assertEquals(0, card.getIncorrectCount());
        assertFalse(card.isMistakeInLastRound());
    }

    @Test
    public void testRecordCorrect() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordCorrect();
        assertEquals(1, card.getCorrectCount());
        assertEquals(0, card.getIncorrectCount());
        assertFalse(card.isMistakeInLastRound());
    }

    @Test
    public void testRecordIncorrect() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordIncorrect();
        assertEquals(0, card.getCorrectCount());
        assertEquals(1, card.getIncorrectCount());
        assertTrue(card.isMistakeInLastRound());
    }

    @Test
    public void testCorrectAfterIncorrectClearsMistake() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordIncorrect();
        card.recordCorrect();
        assertFalse(card.isMistakeInLastRound());
    }

    @Test
    public void testTotalAnswers() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordCorrect();
        card.recordIncorrect();
        card.recordCorrect();
        assertEquals(3, card.getTotalAnswers());
    }
}
