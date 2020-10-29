package tigersgoats;

public class Board
{
    // An enumated type for the three possibilities
    private enum Piece {GOAT, TIGER, VACANT};
    // 1-D Array representation of the board. Top left is 0, 
    // then goes anti-clockwise spiraling inward until 23
    Piece[] board;

    /**
     * Constructor for objects of class Board.
     * Initializes the board VACANT.
     */
    public Board()
    {
        // TODO 3
        board=new Piece[24];
        for (int i = 0; i < 24; i++) {
            setVacant(i);
        }
        
    }

            
    /**
     * Checks if the location a is VACANT on the board
     */
    public boolean isVacant(int a)
    {
        //TODO 4
        if (board[a]==Piece.VACANT) {
            return true;
        }
        return false;
    }
    
    /**
     * Sets the location a on the board to VACANT
     */
    public void setVacant(int a)
    {
        //TODO 5
        board[a]=Piece.VACANT;
    }
    
    /**
     * Checks if the location a on the board is a GOAT
     */
    public boolean isGoat(int a)
    {
        //TODO 6
        if (board[a]==Piece.GOAT) {
            return true;
        }
        return false;
    }
    
    /**
     * Sets the location a on the board to GOAT
     */
    public void setGoat(int a)
    {
        //TODO 7
        board[a]=Piece.GOAT;
    }
    
    /**
     * Sets the location a on the board to TIGER
     */
    public void setTiger(int a)
    {
        //TODO 8
        board[a]=Piece.TIGER;
    }
    
    /**
     * Moves a piece by swaping the contents of a and b
     */
    public void swap(int a, int b)
    {
        //TODO 9
        Piece tmp;
        tmp=board[a];
        board[a]=board[b];
        board[b]=tmp;
        
        
    }
}
