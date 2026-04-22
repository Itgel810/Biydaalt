package flashcard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Organizes flashcards so that the most recently missed card appears first.
 * Cards never missed appear last in original order.
 */
public class RecentMistakesFirstSorter implements CardOrganizer {

    /**
     * Returns cards sorted by most recent mistake timestamp descending.
     * Cards never answered incorrectly appear last.
     *
     * @param cards the list of cards to organize
     * @return sorted list of cards
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> missed = new ArrayList<>();
        List<FlashCard> neverMissed = new ArrayList<>();

        for (FlashCard card : cards) {
            if (card.getLastMistakeTime() > 0) {
                missed.add(card);
            } else {
                neverMissed.add(card);
            }
        }

        // Most recently missed card comes first (largest timestamp first)
        missed.sort(Comparator.comparingLong(FlashCard::getLastMistakeTime).reversed());

        List<FlashCard> result = new ArrayList<>(missed);
        result.addAll(neverMissed);
        return result;
    }
}