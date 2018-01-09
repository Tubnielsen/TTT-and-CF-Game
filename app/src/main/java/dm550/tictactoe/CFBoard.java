package dm550.tictactoe;

/**
 * Created by Lucas on 12/12/2017.
 */

public class CFBoard {

    /** 2-dimensional array representing the board
     * coordinates are counted from top-left (0,0) to bottom-right (size-1, size-1)
     * board[x][y] == 0   signifies free at position (x,y)
     * board[x][y] == i   for i > 0 signifies that Player i made a move on (x,y)
     */
    private int[][] board;

    /** size of the (quadratic) board */
    private int sizeX;
    private int sizeY;

    private int numPlayers;
    /** constructor for creating a copy of the board
     * not needed in Part 1 - can be viewed as an example
     */
    public CFBoard(CFBoard original) {
        this.sizeX = original.sizeX;
        this.sizeY = original.sizeY;
        this.numPlayers = original.numPlayers;
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {      //Originally the board was [y][x]
                this.board[x][y] = original.board[x][y];
            }
        }
    }

    /** constructor for creating an empty board for a given number of players */
    public CFBoard(int numPlayers) {
        this.sizeX = 7 + numPlayers -2;
        this.sizeY = 6 + numPlayers -2;
        this.numPlayers = numPlayers;
        this.board = new int[this.getSizeX()][this.getSizeY()];
    }

    /** checks whether the board is free at the given position */
    /* public boolean isFree(Coordinate c) {
        if (c.checkBoundaries(this.getSizeX(),this.getSizeY())) {
            if (this.board[c.getX()][c.getY()] == 0) {
                return true;
            } else return false;
        } else throw new IllegalArgumentException("coordinate not within game board");
    }*/

    public boolean isFree(Coordinate c) {
        if (c.checkBoundaries(this.getSizeX(),this.getSizeY())) {
            for (int y = this.getSizeY()-1; y >= 0; y--) {
                if (this.board[c.getX()][y] == 0) {
                    return true;
                }
            }
            return false;
        } else throw new IllegalArgumentException("coordinate not within game board");
    }

    /** returns the players that made a move on (x,y) or 0 if the positon is free */
    public int getPlayer(Coordinate c) {
        if (c.checkBoundaries(this.getSizeX(),this.getSizeY())) {
            if (this.board[c.getX()][c.getY()] > 0){
                return this.board[c.getX()][c.getY()];
            } else return 0;
        } else throw new IllegalArgumentException("coordinate not within game board");
    }

    /** record that a given player made a move at the given position
     * checks that the given positions is on the board
     * checks that the player number is valid
     */
    public void addMove(Coordinate c, int player) {
        if (c.checkBoundaries(this.getSizeX(),this.getSizeY())) {
            if (player <= this.getNumPlayers()){
                for (int y = this.getSizeY()-1; y >= 0; y--) {
                    if (this.board[c.getX()][y] == 0) {
                        this.board[c.getX()][y] = player;
                        break;
                    }
                }
            } else throw new IllegalArgumentException("Invalid player number");
        } else throw new IllegalArgumentException("coordinate not within game board");
    }

    /** returns true if, and only if, there are no more free positions on the board */
    public boolean checkFull() {
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                if (this.board[x][y] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /** returns 0 if no player has won (yet)
     * otherwise returns the number of the player that has three in a row
     */
    public int checkWinning() {
        int player = 0;
        for (int i = 0; i < this.getSizeY(); i++){
            player = checkSequence(new XYCoordinate(i,0),0,1);
            if (player != 0) return player;

            player = checkSequence(new XYCoordinate(0,i),1,0);
            if (player != 0) return player;

            player = checkSequence(new XYCoordinate(i,0),1,1);
            if (player != 0) return player;

            player = checkSequence(new XYCoordinate(0,i),1,1);
            if (player != 0) return player;

            player = checkSequence(new XYCoordinate(i,0),-1,1);
            if (player != 0) return player;

            player = checkSequence(new XYCoordinate(0,i),-1,1);
            if (player != 0) return player;
        }
        return 0;
    }

    /** internal helper function checking one row, column, or diagonal */
    private int checkSequence(Coordinate start, int dx, int dy) {

        int count = 0;
        int player = 0;
        while (start.checkBoundaries(this.getSizeX(),this.getSizeY())) {

            if (this.board[start.getX()][start.getY()] == player){
                count += 1;
                player = player;
            } else {
                player = this.board[start.getX()][start.getY()];
                count = 1;
            }
            if (count == 4 && player != 0) return player;
            start = start.shift(dx,dy);
        }
        return 0;
    }

    /** getter for size of the board */
    public int getSizeX() {
        return this.sizeX;
    }
    public int getSizeY() {
        return this.sizeY;
    }
    public int getNumPlayers() { return this.numPlayers; }
    /** pretty printing of the board
     * usefule for debugging purposes
     */
    public String toString() {
        String result = "";
        for (int x = 0; x < this.sizeX; x++) {
            for (int y = 0; y < this.sizeY; y++) {
                result += this.board[x][y]+" ";
            }
            result += "\n";
        }
        return result;
    }
}
