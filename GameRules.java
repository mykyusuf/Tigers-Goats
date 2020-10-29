package tigersgoats;

public class GameRules
{
    // Instance variables to maintain whose move it is
    private boolean moveStage; 
    private boolean goatsTurn;
    private int numGoats; //the number of goats on the board
    private int numTigers; //the number of tigers on the board
    private final int MAXGOATS = 12;

    /**
     * Constructor for objects of class GameRules
     */
    public GameRules()
    {              
        moveStage = false;
        goatsTurn = true;
        numGoats = 0;
        numTigers = 0;
    }       
    
    /**
     * returns moveStage
     */
    public boolean isMoveStage()
    {
        return moveStage;
    }
    
    /**
     * returns true iff it is goats turn
     */
    public boolean isGoatsTurn()
    {
        return goatsTurn;
    }    
    
    
    /**
     * Adds (+1 or -1) to goat numbers.
     * Changes the goatsTurn and moveStage as per rules.
     */
    public void addGoat(int n)
    {
        //TODO 12
        if(moveStage){
            numGoats+=n;
        }
        else{
            if (numGoats<MAXGOATS) {            
                numGoats+=n;
                if(numGoats%4==0){
                    goatsTurn=false;
                }            
            }
            else{
                moveStage=true;
                goatsTurn=false;
            }
        }
        
        
        
    }
    
    /**
     * returns number of goats
     */
    public int getNumGoats()
    {
        return numGoats;
    }
    
    /**
     * increments tigers and gives turn back to goats
     */
    public void incrTigers()
    {
        //TODO 16
        numTigers++;
        goatsTurn=true;
        if (numGoats==MAXGOATS){
            moveStage=true;
            goatsTurn=false;
        }
    }
        
    /**
     * Returns the nearest valid location (0-23) on the board to the x,y mouse click.
     * Locations are described in project description on LMS.
     * You will need bkSize & GameViewer.locs to compute the distance to a location.
     * If the click is close enough to a valid location on the board, return 
     * that location, otherwise return -1 (when the click is not close to any location).
     * Choose a threshold for proximity of click based on bkSize.
     */    
    public int nearestLoc(int x, int y, int bkSize)
    {
        int[][] loctmps = {{1,1},{1,4},{1,7},{4,7},{7,7},{7,4},{7,1},{4,1},
                                 {2,2},{2,4},{2,6},{4,6},{6,6},{6,4},{6,2},{4,2},
                                 {3,3},{3,4},{3,5},{4,5},{5,5},{5,4},{5,3},{4,3}};
        
        int tmpx=x/bkSize;
        int tmpx2=x%bkSize;
        int tmpy=y/bkSize;
        int tmpy2=y%bkSize;
        
        if(tmpx2>(bkSize/2)){
            tmpx++;
        }
        if(tmpy2>(bkSize/2)){
            tmpy++;
        }
        
        if(tmpx>=1 || tmpy>=1){
            
            for (int i = 0; i < 24; i++) {
                if(loctmps[i][0]==tmpx && loctmps[i][1]==tmpy){
                    return i;
                }
            }
            
        }
        // TODO 11    
        return -1;    
    }
    
    /**
     * Returns true iff a move from location a to b is legal, otherwise returns false.
     * For example: a,b = 1,2 -> true; 1,3 -> false; 7,0 -> true. Refer to the 
     * project description for details.
     * Throws an exception for illegal arguments.
     */
    public boolean isLegalMove(int a, int b)
    {
        //TODO 19 
        int[][] legalMoves = {{0,7},{0,8},{0,1},{1,0},{1,9},{1,2},{2,1},{2,10},
                                 {2,3},{3,2},{3,11},{3,4},{4,3},{4,12},{4,5},{5,4},
                                 {5,13},{5,6},{6,5},{6,14},{6,7},{7,6},{7,15},{7,0},
                                 {8,0},{8,15},{8,16},{8,9},{9,8},{9,17},{9,10},
                                 {9,1},{10,9},{10,18},{10,11},{10,2},{11,10},{11,19},{11,12},
                                 {11,3},{12,11},{12,4},{12,13},{12,20},{13,12},{13,5},{13,14},
                                 {13,21},{14,13},{14,6},{14,15},{14,22},{15,14},{15,7},
                                 {15,8},{15,23},{16,23},{16,8},{16,17},{17,16},{17,9},{17,18},
                                 {18,17},{18,10},{18,19},{19,18},{19,11},{19,20},{20,19},{20,12},
                                 {20,21},{21,20},{21,13},{21,22},{22,21},{22,14},{22,23},
                                 {23,22},{23,15},{23,16}};
        
        for (int i = 0; i < legalMoves.length; i++) {
            if (legalMoves[i][0]==a && legalMoves[i][1]==b) {
                 return true;
            }
        }
        
        return false;
    }
}
