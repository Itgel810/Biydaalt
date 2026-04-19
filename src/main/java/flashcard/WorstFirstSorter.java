package flashcard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Organizes flashcards so that cards with the most incorrect answers appear first.
 */
public class WorstFirstSorter implements CardOrganizer {

    /**
     * Returns cards sorted by incorrect count descending (worst first).
     *
     * @param cards the list of cards to organize
     * @return sorted list of cards
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(FlashCard::getIncorrectCount).reversed());
        return sorted;
    }
}
