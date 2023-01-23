package test;

import java.util.ArrayList;

public class Board {
    private final Tile[][] _board;
    private final int BOARD_SIZE = 15;
    private ArrayList<Word> _wordList;

    enum squareType {
        REGULAR,
        DOUBLE_LETTER,
        TRIPLE_LETTER,
        DOUBLE_WORD,
        TRIPLE_WORD
    };

    private squareType[][] _squareValue;

    public Board() {
        _board = new Tile[BOARD_SIZE][BOARD_SIZE];
        _squareValue = new squareType[BOARD_SIZE][BOARD_SIZE];
        initSquareValues();
        _wordList = new ArrayList<Word>();
    }

    private void initSquareValues() {
        // init REGULAR
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                _squareValue[i][j] = squareType.REGULAR;
            }
        }
        // init DOUBLE LETTER
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (j == 3 || j == 11) {
                _squareValue[0][j] = squareType.DOUBLE_LETTER;
                _squareValue[14][j] = squareType.DOUBLE_LETTER;
                _squareValue[7][j] = squareType.DOUBLE_LETTER;
            }
            if (j == 6 || j == 8) {
                _squareValue[2][j] = squareType.DOUBLE_LETTER;
                _squareValue[12][j] = squareType.DOUBLE_LETTER;
            }
            if (j == 0 || j == 7 || j == 14) {
                _squareValue[3][j] = squareType.DOUBLE_LETTER;
                _squareValue[11][j] = squareType.DOUBLE_LETTER;
            }
            if (j == 2 || j == 6 || j == 8 || j == 12) {
                _squareValue[6][j] = squareType.DOUBLE_LETTER;
                _squareValue[8][j] = squareType.DOUBLE_LETTER;
            }
        }
        // init TRIPLE LETTER
        for (int i = 1; i < BOARD_SIZE; i += 4) {
            for (int j = 1; j < BOARD_SIZE; j += 4) {
                _squareValue[i][j] = squareType.TRIPLE_LETTER;
            }
        }
        // init DOUBLE WORD
        for (int i = 1; i < BOARD_SIZE; i++) {
            _squareValue[i][i] = squareType.DOUBLE_WORD;
            _squareValue[14 - i][i] = squareType.DOUBLE_WORD;
        }
        // init TRIPLE WORD
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                _squareValue[(BOARD_SIZE - 1) * i][(BOARD_SIZE - 1) * j] = squareType.TRIPLE_WORD;
                _squareValue[((BOARD_SIZE - 1) * i) / 2][((BOARD_SIZE - 1) * j) / 2] = squareType.TRIPLE_WORD;
            }
        }
        _squareValue[(BOARD_SIZE - 1) / 2][(BOARD_SIZE - 1) / 2] = squareType.DOUBLE_WORD; // put last double word in
                                                                                           // the middle
    }

    public Tile[][] getTiles() {
        return _board.clone();
    }

    public boolean boardLegal(Word w) {
        if (!isOnBoard(w)) {
            return false;
        }
        if (_wordList.isEmpty()) { // Check first word on board
            if (!checkFirstWord(w)) {
                return false;
            }
            return true;
        }
        return (isValidPlacement(w));
    }

    private boolean checkFirstWord(Word w) {
        for (int i = 0; i < w.getTile().length; i++) {
            if (w.isVertical() && w.getCol() == 7) {
                if (w.getRow() + i == 7) {
                    return true;
                }
            } else if (!w.isVertical() && w.getRow() == 7) {
                if (w.getCol() + i == 7) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOnBoard(Word word) {
        if (word.getCol() < BOARD_SIZE && word.getCol() >= 0 && word.getRow() < BOARD_SIZE && word.getRow() >= 0) { // Checks
                                                                                                                    // Start
                                                                                                                    // Position
            if (word.isVertical()) {
                if ((word.getRow() + word.getTile().length - 1) < BOARD_SIZE) { // Checks End Position - Vertical
                    return true;
                }
            } else {
                if ((word.getCol() + word.getTile().length - 1) < BOARD_SIZE) { // Checks End Position - Horizontal
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPlacement(Word word) {
        if (CheckWordHasAdjacent(word) || useExisting(word)) {
            return true;
        }
        return false;
    }

    private boolean useExisting(Word word) { // Checks if Words relies on another word's tile
        for (int i = 0; i < word.getTile().length; i++) {
            if (word.isVertical()) {
                if (word.getTile()[i].letter == '_' && !(_board[word.getRow()][word.getCol() + i] instanceof Tile)) {
                    return false;
                }
            } else {
                if (word.getTile()[i].letter == '_' && !(_board[word.getRow() + i][word.getCol()] instanceof Tile)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean CheckWordHasAdjacent(Word word) { // Checks if word has adjacent - checks vertical and horizontal
                                                      // case
        for (int i = 0; i < word.getTile().length; i++) {
            if (word.isVertical()) {
                // Check sides
                if (CheckTileHasAdjacent(word.getRow() + i, word.getCol(), word.isVertical())) {
                    return true;
                }
                // Check connected word //!Check if needed
                /*
                 * if(word.getRow()>0){
                 * if(_board[word.getRow()-1][word.getCol()] instanceof Tile){
                 * int startingTileRow =word.getRow()-1;
                 * while(startingTileRow>0){
                 * startingTileRow--;
                 * if(_board[startingTileRow][word.getCol()] instanceof Tile){
                 * continue;
                 * }
                 * }
                 * startingTileRow++;
                 * 
                 * }
                 * }
                 */

            }
            // Check sides
            if (CheckTileHasAdjacent(word.getRow(), word.getCol() + i, word.isVertical())) {
                return true;
            }
            // Check connected word //!Check if needed
        }
        return false;
    }

    private boolean CheckTileHasAdjacent(int row, int col, boolean vertical) {
        if (vertical) { // Vertical Case
            switch (col) {
                case 0:
                    if (_board[row][col+1] instanceof Tile) {
                        return true;
                    }
                    break;
                case 14:
                    if (_board[row][col-1] instanceof Tile) {
                        return true;
                    }
                    break;
                default:
                    if (_board[row][col+1] instanceof Tile || _board[row][col-1] instanceof Tile) {
                        return true;
                    }
                    break;
            }
        } else { // Horizontal Case
            switch (row) {
                case 0:
                    if (_board[row + 1][col] instanceof Tile) {
                        return true;
                    }
                    break;
                case 14:
                    if (_board[row - 1][col] instanceof Tile) {
                        return true;
                    }
                    break;
                default:
                    if (_board[row+1][col] instanceof Tile || _board[row-1][col] instanceof Tile) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public boolean dictionaryLegal() {
        return true;
    }

    public ArrayList<Word> getWords(Word word) { // Get new Words from each placement
        ArrayList<Word> newWords = new ArrayList<>(); // returned array of new words
        Tile[] newWordTiles = new Tile[word.getTile().length]; // Tile array to add word incase the word is based on
                                                               // other tiles
        for (int i = 0; i < word.getTile().length; i++) {
            Tile t = word.getTile()[i];
            if (t == null) {
                if (word.isVertical()) {
                    char letter = _board[word.getRow() + i][word.getCol()].letter;
                    int score = _board[word.getRow() + i][word.getCol()].score;
                    newWordTiles[i] = new Tile(letter, score);
                } else {
                    char letter = _board[word.getRow()][word.getCol() + i].letter;
                    int score = _board[word.getRow()][word.getCol() + i].score;
                    newWordTiles[i] = new Tile(letter, score);
                }
                continue;
            } else {
                newWordTiles[i] = new Tile(t.letter, t.score);
                if (word.isVertical()) {
                    if (CheckTileHasAdjacent(word.getRow() + i, word.getCol(), true)) {
                        newWords.add(GetNeighbourWord(word, i));
                        continue;
                    }
                    continue;
                } else {
                    if (CheckTileHasAdjacent(word.getRow(), word.getCol() + i, false)) {
                        newWords.add(GetNeighbourWord(word, i));
                        continue;
                    }
                    continue;
                }
            }
        }
        newWords.add(new Word(newWordTiles, word.getRow(), word.getCol(), word.isVertical()));
        return newWords;
    }

    private Word GetNeighbourWord(Word word, int tilePlace) { // get new word
        int newWordRow, newWordCol;
        int startingTileRow, startingTileCol, endingTileloc;
        if (word.isVertical()) { // check for horizontal words
            startingTileRow = word.getRow() + tilePlace;
            startingTileCol = word.getCol();
            newWordRow = startingTileRow;
            newWordCol = startingTileCol;
            while (newWordCol >= 0) { // get starting Tile loc
                if (_board[startingTileRow][newWordCol] != null) {
                    newWordCol--;
                } else {
                    break;
                }
            }
            endingTileloc = startingTileCol;
            while (endingTileloc < 15) { // get ending tile loc
                if (_board[startingTileRow][endingTileloc] != null) {
                    endingTileloc++;
                } else {
                    break;
                }
            }
            Tile[] t = new Tile[endingTileloc - startingTileCol - 1];
            int counter = 0;
            for (int i = startingTileCol; i <= endingTileloc; i++) {
                char letter = _board[startingTileRow][i].letter;
                int score = _board[startingTileRow][i].score;
                t[counter] = new Tile(letter, score);
                counter++;
            }
            return new Word(t, newWordRow, newWordCol, false);
        } else {// check for vertical words
            startingTileRow = word.getRow();
            startingTileCol = word.getCol() + tilePlace;
            newWordRow = startingTileRow;
            newWordCol = startingTileCol;
            while (newWordRow >= 0) { // get starting Tile loc
                if (_board[newWordRow][startingTileCol] != null) {
                    newWordRow--;
                } else {
                    break;
                }
            }
            endingTileloc = startingTileRow;
            while (endingTileloc < 15) { // get ending tile loc
                if (_board[endingTileloc][startingTileCol] != null) {
                    endingTileloc++;
                } else {
                    break;
                }
            }
            Tile[] t = new Tile[endingTileloc - startingTileRow - 1];
            int counter = 0;
            for (int i = startingTileRow; i <= endingTileloc; i++) {
                char letter = _board[i][startingTileCol].letter;
                int score = _board[i][startingTileCol].score;
                t[counter] = new Tile(letter, score);
                counter++;
            }
            return new Word(t, newWordRow, newWordCol, false);
        }
    }

    public int getScore(Word w) {
        squareType square;
        int totalScore = 0;
        int wordMultiplier = 1;
        for (int i = 0; i < w.getTile().length; i++) {
            if(!(w.getTile()[i] instanceof Tile)){
                continue;
            }
            if (w.isVertical()) {
                square = _squareValue[w.getRow() + i][w.getCol()];
            } else {
                square = _squareValue[w.getRow()][w.getCol() + i];
            }
            switch (square) {
                case REGULAR:
                    totalScore += w.getTile()[i].score;
                    break;
                case DOUBLE_LETTER:
                    totalScore += w.getTile()[i].score * 2;
                    break;
                case DOUBLE_WORD:
                    wordMultiplier *= 2;
                    totalScore += w.getTile()[i].score;
                    break;
                case TRIPLE_LETTER:
                    totalScore += w.getTile()[i].score * 3;
                    break;
                case TRIPLE_WORD:
                    wordMultiplier *= 3;
                    totalScore += w.getTile()[i].score;
                    break;
                default:
                    break;
            }
        }
        totalScore *= wordMultiplier;
        return totalScore;
    }

    public int tryPlaceWord(Word w) {
        // Calcualte score
        int sum = 0;
        ArrayList<Word> wordList = getWords(w);
        for (Word word : wordList) {
            sum += getScore(word);
        }
        // Place on Board
        for (int i = 0; i < w.getTile().length; i++) {
            if (w.getTile()[i] == null) {
                continue;
            }
            if (w.isVertical()) {
                _board[w.getRow() + i][w.getCol()] = new Tile(w.getTile()[i].letter, w.getTile()[i].score);
            } else {
                _board[w.getRow()][w.getCol() + i] = new Tile(w.getTile()[i].letter, w.getTile()[i].score);
            }
        }
        return sum;
    }
}