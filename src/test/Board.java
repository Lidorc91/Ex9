package test;

public class Board {
    private final Tile[][] _board;

    public Board() {
        _board = new Tile[15][15];
    }

    public Tile[][] getTiles() {
        Tile[][] t = new Tile[15][15];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (_board[i][j] instanceof Tile) {
                    t[i][j] = _board[i][j];
                } else {
                    t[i][j] = null;
                }
            }
        }
        return t;
    }

    public boolean boardLegal(Word w){        
        return (CheckOnBoard(w) && CheckUseExisting(w) && CheckNoSubstitution(w));
    }
    
    private boolean CheckOnBoard(Word word) { //TODO - check if word position actual range and change accordingly
        if (word.getCol() < 16 && word.getCol() > 0 && word.getRow() < 16 && word.getRow() > 0) { //Checks Start Position
            if(word.isVertical()) {
                if(word.getCol()+word.getTile().length-1 < 16){ //Checks End Position - Vertical
                    return true;
                }
            }else{
                if(word.getRow()+word.getTile().length-1 < 16){ //Checks End Position - Horizontal
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean CheckNoSubstitution(Word word) {
        
        return false;
    }

    private boolean CheckUseExisting(Word word) {
        if(hasAdjacent(word) || useExisting(word)) {
            return true;
        }
        return false;
    }

    private boolean useExisting(Word word) { //Checks if Words relies on another word's tile
        return false;
    }

    private boolean hasAdjacent(Word word) { //Checks if word has adjacent - checks vertical and horizontal case
        
        return false;
    }


}