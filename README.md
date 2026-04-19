# Flashcard System

A command-line flashcard study application written in Java.

## Usage

```
flashcard <cards-file> [options]
```

### Options

| Option | Description | Default |
|---|---|---|
| `--help` | Show help information | - |
| `--order <order>` | Card ordering strategy | `random` |
| `--repetitions <num>` | Correct answers required per card | `1` |
| `--invertCards` | Swap question and answer | `false` |

### Order strategies

- `random` — Shuffle cards randomly each round
- `worst-first` — Cards with the most incorrect answers appear first
- `recent-mistakes-first` — Cards answered incorrectly in the last round appear first

### Cards file format

A plain text file where each card is a question line followed by an answer line,
separated by blank lines. Lines starting with `#` are comments.

```
# This is a comment
What is the capital of Mongolia?
Ulaanbaatar

What is 2 + 2?
4
```

## Examples

```bash
# Basic usage
java -jar flashcard.jar sample-cards.txt

# Worst-first ordering, require 3 correct answers
java -jar flashcard.jar sample-cards.txt --order worst-first --repetitions 3

# Inverted cards (answer becomes question)
java -jar flashcard.jar sample-cards.txt --invertCards

# Show help
java -jar flashcard.jar --help
```

## Achievements

| Achievement | Description |
|---|---|
| `SPEED` | Average answer time under 5 seconds in a round |
| `CORRECT` | All cards answered correctly in a round |
| `REPEAT` | Answered a single card more than 5 times |
| `CONFIDENT` | Answered a single card correctly at least 3 times |

## Building

```bash
mvn clean package
java -jar target/flashcard.jar sample-cards.txt
```

## Running tests

```bash
mvn test
```

## Generating site

```bash
mvn site
```
