package flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for CardOrganizer implementations.
 */
public class CardOrganizerTest {

    @Test
    public void testRandomSorterReturnsSameCards() {
        FlashCard a = new FlashCard("Q1", "A1");
        FlashCard b = new FlashCard("Q2", "A2");
        FlashCard c = new FlashCard("Q3", "A3");
        List<FlashCard> cards = List.of(a, b, c);

        RandomSorter sorter = new RandomSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(cards));
    }

    @Test
    public void testWorstFirstSorter() {
        FlashCard good = new FlashCard("Good", "A");
        FlashCard bad = new FlashCard("Bad", "A");
        FlashCard ugly = new FlashCard("Ugly", "A");

        good.recordCorrect();
        bad.recordIncorrect();
        bad.recordIncorrect();
        ugly.recordIncorrect();

        List<FlashCard> cards = List.of(good, bad, ugly);
        WorstFirstSorter sorter = new WorstFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        // bad has 2 incorrect, ugly has 1, good has 0
        assertEquals(bad, result.get(0));
        assertEquals(ugly, result.get(1));
        assertEquals(good, result.get(2));
    }

    @Test
    public void testRecentMistakesFirstSorterPreservesOrder() {
        FlashCard a = new FlashCard("A", "a");
        FlashCard b = new FlashCard("B", "b");
        FlashCard c = new FlashCard("C", "c");
        FlashCard d = new FlashCard("D", "d");

        // b and d had mistakes
        b.setMistakeInLastRound(true);
        d.setMistakeInLastRound(true);

        List<FlashCard> cards = List.of(a, b, c, d);
        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        // b and d come first (in original relative order), then a and c
        assertEquals(b, result.get(0));
        assertEquals(d, result.get(1));
        assertEquals(a, result.get(2));
        assertEquals(c, result.get(3));
    }

    @Test
    public void testRecentMistakesFirstSorterNoMistakes() {
        FlashCard a = new FlashCard("A", "a");
        FlashCard b = new FlashCard("B", "b");
        List<FlashCard> cards = List.of(a, b);

        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(2, result.size());
        assertEquals(a, result.get(0));
        assertEquals(b, result.get(1));
    }
}
