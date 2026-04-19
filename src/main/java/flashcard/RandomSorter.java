package flashcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Organizes flashcards in a random order.
 */
public class RandomSorter implements CardOrganizer {

    /**
     * Returns cards in a randomly shuffled order.
     *
     * @param cards the list of cards to organize
     * @return shuffled list of cards
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}
