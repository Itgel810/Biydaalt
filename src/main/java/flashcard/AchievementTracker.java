package flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks and checks achievements during a flashcard session.
 */
public class AchievementTracker {

    private static final double FAST_ANSWER_THRESHOLD_SECONDS = 5.0;
    private static final int REPEAT_THRESHOLD = 5;
    private static final int CONFIDENT_THRESHOLD = 3;

    private final List<String> earnedAchievements;
    private final List<Long> roundAnswerTimes;
    private boolean allCorrectInRound;

    /**
     * Creates a new AchievementTracker.
     */
    public AchievementTracker() {
        this.earnedAchievements = new ArrayList<>();
        this.roundAnswerTimes = new ArrayList<>();
        this.allCorrectInRound = true;
    }

    /**
     * Called at the start of a new round to reset round-level tracking.
     */
    public void startRound() {
        roundAnswerTimes.clear();
        allCorrectInRound = true;
    }

    /**
     * Records the result and time for answering a card.
     *
     * @param card          the card that was answered
     * @param correct       whether the answer was correct
     * @param elapsedMillis time taken in milliseconds
     */
    public void recordAnswer(FlashCard card, boolean correct, long elapsedMillis) {
        roundAnswerTimes.add(elapsedMillis);

        if (!correct) {
            allCorrectInRound = false;
        }

        // REPEAT achievement: answered more than 5 times total
        if (card.getTotalAnswers() > REPEAT_THRESHOLD) {
            addAchievement("REPEAT");
        }

        // CONFIDENT achievement: at least 3 correct answers for this card
        if (card.getCorrectCount() >= CONFIDENT_THRESHOLD) {
            addAchievement("CONFIDENT");
        }
    }

    /**
     * Called at the end of a round to check round-level achievements.
     */
    public void endRound() {
        // SPEED achievement: average answer time < 5 seconds
        if (!roundAnswerTimes.isEmpty()) {
            double avgSeconds = roundAnswerTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0) / 1000.0;
            if (avgSeconds < FAST_ANSWER_THRESHOLD_SECONDS) {
                addAchievement("SPEED");
            }
        }

        // CORRECT achievement: all cards correct in this round
        if (allCorrectInRound && !roundAnswerTimes.isEmpty()) {
            addAchievement("CORRECT");
        }
    }

    /**
     * Returns the list of earned achievement names.
     *
     * @return list of achievement names
     */
    public List<String> getEarnedAchievements() {
        return new ArrayList<>(earnedAchievements);
    }

    private void addAchievement(String name) {
        if (!earnedAchievements.contains(name)) {
            earnedAchievements.add(name);
            System.out.println("*** Achievement unlocked: " + name + " ***");
        }
    }
}
