package test;

import java.util.ArrayList;

public class Board {
    private final Tile[][] _board;
    private final int BOARD_SIZE = 15;
    private ArrayList<Word> _wordList;
    enum squareType{
        REGULAR,
        DOUBLE_LETTER,
        TRIPLE_LETTER,
        DOUBLE_WORD,
        TRIPLE_WORD
    };
    private squareType[][] squareValue;

    public Board() {
        _board = new Tile[BOARD_SIZE][BOARD_SIZE];
        squareValue = new squareType[BOARD_SIZE][BOARD_SIZE];
        initSquareValues();
        _wordList = new ArrayList<Word>();
    }

    private void initSquareValues() {
        //init REGULAR
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                squareValue[i][j] = squareType.REGULAR;
            }
        }
        //init DOUBLE LETTER
        for (int i = 0; i < _board.length; i++) { // TODO
            squareValue[0][3] = squareType.DOUBLE_LETTER;
            squareValue[2][6] = squareType.DOUBLE_LETTER;
            squareValue[3][0] = squareType.DOUBLE_LETTER;
            squareValue[3][7] = squareType.DOUBLE_LETTER;
            squareValue[6][2] = squareType.DOUBLE_LETTER;
            squareValue[6][6] = squareType.DOUBLE_LETTER;
            squareValue[7][3] = squareType.DOUBLE_LETTER;
        }
        //init TRIPLE LETTER
        for (int i = 1; i < BOARD_SIZE; i+=4) {
            for (int j = 1; j < BOARD_SIZE; j+=4) {
                squareValue[i][i] = squareType.TRIPLE_LETTER;
            }
        }
        //init DOUBLE WORD
        for (int i = 1; i < BOARD_SIZE; i++) {
            squareValue[i][i] = squareType.DOUBLE_WORD;
            squareValue[14-i][i] = squareType.DOUBLE_WORD;
        }
        squareValue[(BOARD_SIZE-1)/2][(BOARD_SIZE)-1/2] = squareType.DOUBLE_WORD;
        //init TRIPLE WORD
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                squareValue[(BOARD_SIZE-1)*i][(BOARD_SIZE-1)*j] = squareType.TRIPLE_WORD;
                squareValue[((BOARD_SIZE-1)*i)/2][((BOARD_SIZE-1)*j)/2] = squareType.TRIPLE_WORD;
            }
        }
    }

    public Tile[][] getTiles() {
        return _board.clone();
    }

    public boolean boardLegal(Word w) {
        if (!isOnBoard(w)) {
            return false;
        }
        if(_wordList.isEmpty()){ //Check first word on board
            if(!checkFirstWord(w)){
                return false;
            }
            return true;
        }
        return (isValidPlacement(w));
    }

    private boolean checkFirstWord(Word w) {
        for (int i = 0; i < w.getTile().length; i++) {
            if(w.isVertical() && w.getCol() == 7) {                
                if(w.getRow()+i == 7) {
                    return true;
                }
            }else if(!w.isVertical() && w.getRow() == 7){
                if(w.getCol()+i == 7) {
                    return true;
                }                
            }            
        }
        return false;
    }

    private boolean isOnBoard(Word word) {
        if (word.getCol() < BOARD_SIZE && word.getCol() > 0 && word.getRow() < BOARD_SIZE && word.getRow() > 0) { // Checks
                                                                                                                  // Start
                                                                                                                  // Position
            if (word.isVertical()) {
                if (word.getCol() + word.getTile().length - 1 < BOARD_SIZE) { // Checks End Position - Vertical
                    return true;
                }
            } else {
                if (word.getRow() + word.getTile().length - 1 < BOARD_SIZE) { // Checks End Position - Horizontal
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
            if(word.isVertical()) {
                if(word.getTile()[i].letter == '_' && !(_board[word.getRow()][word.getCol()+i] instanceof Tile)) {
                    return false;
                }
            }
            else{
                if(word.getTile()[i].letter == '_' && !(_board[word.getRow()+i][word.getCol()] instanceof Tile)) {
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
                if (CheckTileHasAdjacent(word.getRow() + i, word.getCol(), word.isVertical())) {
                    return true;
                }
            }
            if (CheckTileHasAdjacent(word.getRow(), word.getCol() + i, word.isVertical())) {
                return true;
            }
        }
        return false;
    }

    private boolean CheckTileHasAdjacent(int row, int col, boolean vertical) {
        if (vertical) { // Vertical Case
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
                    if (_board[row + 1][col] instanceof Tile || _board[row - 1][col] instanceof Tile) {
                        return true;
                    }
                    break;
            }
        } else { // Horizontal Case
            switch (col) {
                case 0:
                    if (_board[row][col + 1] instanceof Tile) {
                        return true;
                    }
                    break;
                case 14:
                    if (_board[row][col - 1] instanceof Tile) {
                        return true;
                    }
                    break;
                default:
                    if (_board[row][col + 1] instanceof Tile || _board[row][col - 1] instanceof Tile) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public boolean dictionaryLegal(){ //TODO
        return true;
    }

}