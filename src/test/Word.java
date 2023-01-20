package test;


public class Word {
    private final Tile[] tile;
    private final int row,col;
    private final boolean vertical;

    public Word(Tile[] t,int r,int c,boolean v){
        tile=t;
        row = r;
        col = c;
        vertical = v;
    }

    public Tile[] getTile() {
        return tile;
    }


    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }


    public boolean isVertical() {
        return vertical;
    }




	
}
