# Flashcard System

A command-line flashcard study application written in Java.

## Usage

```
flashcard <cards-file> [options]
```

### Options

| Option | Description | Default |
|---|---|---|
| `--help` | Тусламжийн мэдээллийг харуулах | - |
| `--order <order>` | Картуудыг эрэмбэлэх стратеги | `random` |
| `--repetitions <num>` | Карт бүрийг бүрэн цээжилсэнд тооцох зөв хариултын тоо | `1` |
| `--invertCards` | Асуулт болон хариултын байрыг солих | `false` |

### Order strategies

- `random` — Үе бүрт картуудыг санамсаргүй байдлаар холино.
- `worst-first` — Хамгийн олон удаа буруу хариулсан картуудыг эхэнд гаргана.
- `recent-mistakes-first` — Өмнөх үед (round) алдсан картуудыг хамгийн түрүүнд гаргана.



```
# Энэ бол тайлбар
Монгол улсын нийслэл юу вэ?
Улаанбаатар

2 + 2 хэд вэ?
4
```

## Examples

```bash
# Үндсэн хэрэглээ
java -jar flashcard.jar asuult.txt

# Хамгийн их алдсан картуудыг түрүүлж харах, карт бүр дээр 3 зөв хариулт шаардах
java -jar flashcard.jar asuult.txt --order worst-first --repetitions 3

# Асуулт, хариултын байрыг солих (хариултыг асуулт болгох)
java -jar flashcard.jar asuult.txt --invertCards

# Тусламж харах
java -jar flashcard.jar --help
```

## Achievements

| Achievement | Description |
|---|---|
| `SPEED` | Нэг үеийн дотор дунджаар 5 секундээс хурдан хариулах |
| `CORRECT` | Нэг үеийн бүх асуултад алдалгүй зөв хариулах|
| `REPEAT` | Нэг картыг 5-аас олон удаа давтаж харах |
| `CONFIDENT` | Нэг картад дор хаяж 3 удаа зөв хариулах |

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
