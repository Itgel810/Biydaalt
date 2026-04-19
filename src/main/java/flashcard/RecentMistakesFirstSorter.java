package flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Organizes flashcards so that cards with mistakes in the last round appear first.
 * The relative order within each group (mistake / no-mistake) is preserved.
 */
public class RecentMistakesFirstSorter implements CardOrganizer {

    /**
     * Returns cards where recent-mistake cards come first,
     * preserving internal order within each group.
     *
     * @param cards the list of cards to organize
     * @return sorted list of cards
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> mistakes = new ArrayList<>();
        List<FlashCard> correct = new ArrayList<>();

        for (FlashCard card : cards) {
            if (card.isMistakeInLastRound()) {
                mistakes.add(card);
            } else {
                correct.add(card);
            }
        }

        List<FlashCard> result = new ArrayList<>(mistakes);
        result.addAll(correct);
        return result;
    }
}
