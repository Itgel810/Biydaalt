package flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for AchievementTracker.
 */
public class AchievementTrackerTest {

    @Test
    public void testSpeedAchievement() {
        AchievementTracker tracker = new AchievementTracker();
        tracker.startRound();
        FlashCard card = new FlashCard("Q", "A");
        card.recordCorrect();
        // answer in 1 second = fast
        tracker.recordAnswer(card, true, 1000);
        tracker.endRound();

        assertTrue(tracker.getEarnedAchievements().contains("SPEED"));
    }

    @Test
    public void testNoSpeedAchievementForSlowAnswers() {
        AchievementTracker tracker = new AchievementTracker();
        tracker.startRound();
        FlashCard card = new FlashCard("Q", "A");
        card.recordCorrect();
        // answer in 10 seconds = slow
        tracker.recordAnswer(card, true, 10000);
        tracker.endRound();

        assertFalse(tracker.getEarnedAchievements().contains("SPEED"));
    }

    @Test
    public void testCorrectAchievement() {
        AchievementTracker tracker = new AchievementTracker();
        tracker.startRound();
        FlashCard card = new FlashCard("Q", "A");
        card.recordCorrect();
        tracker.recordAnswer(card, true, 10000);
        tracker.endRound();

        assertTrue(tracker.getEarnedAchievements().contains("CORRECT"));
    }

    @Test
    public void testNoCorrectAchievementIfMistake() {
        AchievementTracker tracker = new AchievementTracker();
        tracker.startRound();
        FlashCard card = new FlashCard("Q", "A");
        card.recordIncorrect();
        tracker.recordAnswer(card, false, 10000);
        tracker.endRound();

        assertFalse(tracker.getEarnedAchievements().contains("CORRECT"));
    }

    @Test
    public void testRepeatAchievement() {
        AchievementTracker tracker = new AchievementTracker();
        tracker.startRound();
        FlashCard card = new FlashCard("Q", "A");
        // Answer 6 times
        for (int i = 0; i < 6; i++) {
            card.recordCorrect();
        }
        tracker.recordAnswer(card, true, 1000);
        tracker.endRound();

        assertTrue(tracker.getEarnedAchievements().contains("REPEAT"));
    }

    @Test
    public void testConfidentAchievement() {
        AchievementTracker tracker = new AchievementTracker();
        tracker.startRound();
        FlashCard card = new FlashCard("Q", "A");
        card.recordCorrect();
        card.recordCorrect();
        card.recordCorrect();
        tracker.recordAnswer(card, true, 1000);
        tracker.endRound();

        assertTrue(tracker.getEarnedAchievements().contains("CONFIDENT"));
    }
}
