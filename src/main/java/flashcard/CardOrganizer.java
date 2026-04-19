package flashcard;

import java.util.List;

/**
 * Interface for organizing (sorting) flashcards.
 */
public interface CardOrganizer {

    /**
     * Organizes the given list of flashcards and returns a new ordered list.
     *
     * @param cards the list of cards to organize
     * @return organized list of cards
     */
    List<FlashCard> organize(List<FlashCard> cards);
}
