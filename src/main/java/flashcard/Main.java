package flashcard;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Flashcard application.
 *
 * <p>Usage: flashcard &lt;cards-file&gt; [options]
 *
 * <p>Options:
 * <ul>
 *   <li>--help                   Show help information</li>
 *   <li>--order &lt;order&gt;    Card ordering: random, worst-first,
 *                                recent-mistakes-first (default: random)</li>
 *   <li>--repetitions &lt;num&gt; Number of correct answers required (default: 1)</li>
 *   <li>--invertCards            Swap question and answer</li>
 * </ul>
 */
public class Main {

    private static final String USAGE = "Usage: flashcard <cards-file> [options]\n"
        + "\nOptions:\n"
        + "  --help                      Show this help message\n"
        + "  --order <order>             Card ordering strategy (default: random)\n"
        + "                              Choices: random, worst-first,"
        + " recent-mistakes-first\n"
        + "  --repetitions <num>         Number of correct answers required per"
        + " card (default: 1)\n"
        + "  --invertCards               Swap question and answer (default: false)\n";

    /**
     * Main method.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // --help can appear anywhere; always takes priority
        for (String arg : args) {
            if ("--help".equals(arg)) {
                System.out.print(USAGE);
                System.exit(0);
            }
        }

        if (args.length < 1) {
            System.err.println("Error: No cards file specified.");
            System.err.println(USAGE);
            System.exit(1);
        }

        String cardsFile = args[0];
        String order = "random";
        int repetitions = 1;
        boolean invertCards = false;

        // Parse options starting from index 1
        int i = 1;
        while (i < args.length) {
            switch (args[i]) {
                case "--order":
                    if (i + 1 >= args.length) {
                        System.err.println("Error: --order requires an argument.");
                        System.exit(1);
                    }
                    order = args[++i];
                    if (!order.equals("random")
                        && !order.equals("worst-first")
                        && !order.equals("recent-mistakes-first")) {
                        System.err.println("Error: Invalid --order value: " + order);
                        System.err.println(
                            "  Valid options: random, worst-first,"
                            + " recent-mistakes-first");
                        System.exit(1);
                    }
                    break;

                case "--repetitions":
                    if (i + 1 >= args.length) {
                        System.err.println("Error: --repetitions requires an argument.");
                        System.exit(1);
                    }
                    try {
                        repetitions = Integer.parseInt(args[++i]);
                        if (repetitions < 1) {
                            System.err.println(
                                "Error: --repetitions must be a positive integer.");
                            System.exit(1);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println(
                            "Error: --repetitions must be a number, got: " + args[i]);
                        System.exit(1);
                    }
                    break;

                case "--invertCards":
                    invertCards = true;
                    break;

                default:
                    System.err.println("Error: Unknown option: " + args[i]);
                    System.err.println(USAGE);
                    System.exit(1);
            }
            i++;
        }

        // Load cards
        CardFileParser parser = new CardFileParser();
        List<FlashCard> cards;
        try {
            cards = parser.parse(cardsFile);
        } catch (IOException e) {
            System.err.println("Error reading cards file: " + e.getMessage());
            System.exit(1);
            return;
        }

        if (cards.isEmpty()) {
            System.err.println("Error: No cards found in file: " + cardsFile);
            System.exit(1);
        }

        // Select organizer
        CardOrganizer organizer = createOrganizer(order);

        // Run session
        FlashCardSession session = new FlashCardSession(
            cards, organizer, repetitions, invertCards);
        Scanner scanner = new Scanner(System.in);
        session.start(scanner);
    }

    /**
     * Creates a CardOrganizer based on the order string.
     *
     * @param order the order strategy name
     * @return the corresponding CardOrganizer
     */
    private static CardOrganizer createOrganizer(String order) {
        switch (order) {
            case "worst-first":
                return new WorstFirstSorter();
            case "recent-mistakes-first":
                return new RecentMistakesFirstSorter();
            default:
                return new RandomSorter();
        }
    }
}
