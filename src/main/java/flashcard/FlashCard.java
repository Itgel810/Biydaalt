package flashcard;

/**
 * Represents a single flashcard with a question and answer.
 */
public class FlashCard {

    private final String question;
    private final String answer;
    private int correctCount;
    private int incorrectCount;
    private boolean mistakeInLastRound;
    private int totalAnswers;
    private long lastMistakeTime;

    /**
     * Creates a new FlashCard.
     *
     * @param question the question text
     * @param answer   the answer text
     */
    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.mistakeInLastRound = false;
        this.totalAnswers = 0;
        this.lastMistakeTime = 0;
    }

    /**
     * Returns the question.
     *
     * @return question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the answer.
     *
     * @return answer text
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns the number of correct answers.
     *
     * @return correct count
     */
    public int getCorrectCount() {
        return correctCount;
    }

    /**
     * Returns the number of incorrect answers.
     *
     * @return incorrect count
     */
    public int getIncorrectCount() {
        return incorrectCount;
    }

    /**
     * Returns the total number of answers.
     *
     * @return total answers
     */
    public int getTotalAnswers() {
        return totalAnswers;
    }

    /**
     * Records a correct answer.
     */
    public void recordCorrect() {
        correctCount++;
        totalAnswers++;
        mistakeInLastRound = false;
    }

    /**
     * Records an incorrect answer.
     */
    public void recordIncorrect() {
        incorrectCount++;
        totalAnswers++;
        mistakeInLastRound = true;
        lastMistakeTime = System.currentTimeMillis();
    }

    /**
     * Returns the timestamp of the last mistake.
     *
     * @return last mistake time in milliseconds
     */
    public long getLastMistakeTime() {
        return lastMistakeTime;
    }

    /**
     * Returns whether this card had a mistake in the last round.
     *
     * @return true if mistake in last round
     */
    public boolean isMistakeInLastRound() {
        return mistakeInLastRound;
    }

    /**
     * Sets the mistake-in-last-round flag.
     *
     * @param mistake the flag value
     */
    public void setMistakeInLastRound(boolean mistake) {
        this.mistakeInLastRound = mistake;
    }

    @Override
    public String toString() {
        return "FlashCard{question='" + question + "', answer='" + answer + "'}";
    }
}