package flashcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Runs an interactive flashcard study session.
 */
public class FlashCardSession {

    private final List<FlashCard> cards;
    private final CardOrganizer organizer;
    private final int repetitions;
    private final boolean invertCards;
    private final AchievementTracker achievementTracker;

    /**
     * Creates a new FlashCardSession.
     *
     * @param cards        list of cards to study
     * @param organizer    card organizer to use
     * @param repetitions  number of correct answers required per card
     * @param invertCards  if true, swap question and answer
     */
    public FlashCardSession(
        List<FlashCard> cards,
        CardOrganizer organizer,
        int repetitions,
        boolean invertCards
    ) {
        this.cards = new ArrayList<>(cards);
        this.organizer = organizer;
        this.repetitions = repetitions;
        this.invertCards = invertCards;
        this.achievementTracker = new AchievementTracker();
    }

    /**
     * Starts the interactive session.
     *
     * @param scanner the Scanner to use for user input
     */
    public void start(Scanner scanner) {
        // Track how many times each card has been answered correctly
        Map<FlashCard, Integer> correctCounts = new HashMap<>();
        for (FlashCard card : cards) {
            correctCounts.put(card, 0);
        }

        // Cards remaining to master
        List<FlashCard> remaining = new ArrayList<>(cards);

        System.out.println("=== Flashcard Session Started ===");
        System.out.println("Cards: " + remaining.size()
            + " | Required correct: " + repetitions);
        System.out.println("Type your answer and press Enter. Type 'quit' to exit.");
        System.out.println();

        while (!remaining.isEmpty()) {
            // Reset last-round mistake flags before organizing
            for (FlashCard card : remaining) {
                // Only reset when starting a new pass
            }

            achievementTracker.startRound();
            List<FlashCard> roundOrder = organizer.organize(remaining);

            for (FlashCard card : roundOrder) {
                if (!remaining.contains(card)) {
                    continue;
                }

                String prompt = invertCards ? card.getAnswer() : card.getQuestion();
                String expectedAnswer = invertCards ? card.getQuestion() : card.getAnswer();

                System.out.println("Q: " + prompt);
                System.out.print("A: ");

                long startTime = System.currentTimeMillis();
                String userAnswer = scanner.nextLine().trim();
                long elapsed = System.currentTimeMillis() - startTime;

                if (userAnswer.equalsIgnoreCase("quit")) {
                    System.out.println("Session ended by user.");
                    printSummary();
                    return;
                }

                boolean correct = userAnswer.equalsIgnoreCase(expectedAnswer);

                if (correct) {
                    System.out.println("[CORRECT]");
                    card.recordCorrect();
                    int count = correctCounts.getOrDefault(card, 0) + 1;
                    correctCounts.put(card, count);
                    if (count >= repetitions) {
                        remaining.remove(card);
                        System.out.println("  (Card mastered! " + remaining.size()
                            + " remaining)");
                    }
                } else {
                    System.out.println("[INCORRECT] Correct answer: " + expectedAnswer);
                    card.recordIncorrect();
                    card.setMistakeInLastRound(true);
                }

                achievementTracker.recordAnswer(card, correct, elapsed);
                System.out.println();
            }

            achievementTracker.endRound();
        }

        System.out.println("=== All cards mastered! ===");
        printSummary();
    }

    private void printSummary() {
        System.out.println("\n=== Session Summary ===");
        for (FlashCard card : cards) {
            System.out.printf("  %-40s | Correct: %d | Incorrect: %d%n",
                card.getQuestion(), card.getCorrectCount(), card.getIncorrectCount());
        }

        List<String> achievements = achievementTracker.getEarnedAchievements();
        if (!achievements.isEmpty()) {
            System.out.println("\nAchievements earned: " + String.join(", ", achievements));
        }
    }
}
