package tigersgoats;

import java.awt.*;
import java.awt.event.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class GameViewer implements MouseListener
{
    // instance variables
    private int bkSize; // block size - all other measurements to be derived from bkSize
    private int brdSize; // board size
    private SimpleCanvas sc; // an object of SimpleCanvas to draw 
    private GameRules rules; // an object of GameRules
    private Board bd; // an object of Board
    private AIplayer ai; //an object of AIplayer
    
    // 2D coordinates of valid locations on the board in steps of block size    
    public static final int[][] locs = {{1,1},{1,4},{1,7},{4,7},{7,7},{7,4},{7,1},{4,1},
                                 {2,2},{2,4},{2,6},{4,6},{6,6},{6,4},{6,2},{4,2},
                                 {3,3},{3,4},{3,5},{4,5},{5,5},{5,4},{5,3},{4,3}};
    // source and destination for the goat moves                             
    private int[] mov = {-1,-1}; //-1 means no selection

    /**
     * Constructor for objects of class GameViewer
     * Initializes instance variables and adds mouse listener.
     * Draws the board.
     */
    public GameViewer(int bkSize)
    {        
        this.bkSize = bkSize;
        brdSize = bkSize*8;
        sc = new SimpleCanvas("Tigers and Goats", brdSize, brdSize, Color.BLUE);
        sc.addMouseListener(this);           
        rules = new GameRules();
        bd = new Board();
        ai = new AIplayer();              
        drawBoard();                      
    }
    
    /**
     * Constructor with default block size
     */
    public GameViewer( )
    {
        this(80);
    }
    
    /**
     * Draws the boad lines and the pieces as per their locations.
     * Drawing of lines is provided, students to implement drawing 
     * of pieces and number of goats.
     */
    private void drawBoard()
    {
        sc.drawRectangle(0,0,brdSize,brdSize,Color.BLUE); //wipe the canvas

        //draw shadows of Goats and Tigers - not compulsory //////////////////////        
        
        // Draw the lines
        for(int i=1; i<9; i++)
        {
            //diagonal and middle line
            sc.drawLine(locs[i-1][0]*bkSize, locs[i-1][1]*bkSize,
                        locs[i+15][0]*bkSize, locs[i+15][1]*bkSize, Color.red);
           
            if(i==4 || i==8) continue; //no more to draw at i=4,8
            // vertical line
            sc.drawLine(i*bkSize, i*bkSize,
                        i*bkSize, brdSize-i*bkSize,Color.white);            
            // horizontal line
            sc.drawLine(i*bkSize,         i*bkSize,
                        brdSize-i*bkSize, i*bkSize, Color.white);  
            
        }
        
        // TODO 10 
        // Draw the goats and tigers. (Drawing the shadows is not compulsory)
        // Display the number of goats 
        sc.drawString("Number of Goats: "+rules.getNumGoats(), brdSize/2-60, brdSize-20, Color.GREEN);
        
        for (int i = 0; i < 24; i++) {
            if (bd.isGoat(i)) {
                sc.drawDisc(locs[i][0]*bkSize,locs[i][1]*bkSize ,bkSize/2, Color.RED);
            }
            else if (!bd.isVacant(i)) {
                sc.drawDisc(locs[i][0]*bkSize,locs[i][1]*bkSize ,bkSize/2, Color.YELLOW);
            }            
        }
        
        
    }
    
    /**
     * If vacant, place a goat at the user clicked location on board.
     * Update goat count in rules and draw the updated board
     */
    public void placeGoat(int loc) 
    {   
        //TODO 2        
        if (bd.isVacant(loc) && rules.isGoatsTurn()) {
            bd.setGoat(loc);
            rules.addGoat(1);
            drawBoard();
        }
    }
    
    /**
     * Calls the placeTiger method of AIplayer to place a tiger on the board.
     * Increments tiger count in rules.
     * Draws the updated board.
     */
    public void placeTiger() 
    {   
        //TODO 13
        rules.incrTigers();
        ai.placeTiger(bd);
        drawBoard();
    }
    
    /**
     * Toggles goat selection - changes the colour of selected goat.
     * Resets selection and changes the colour back when the same goat is clicked again.
     * Selects destination (if vacant) to move and calls moveGoat to make the move.
     */
    public void selectGoatMove(int loc) 
    {   
        //TODO 16
        
        if (bd.isGoat(loc)) {
            
            if (mov[0]==-1 ) {
                mov[0]=loc;
                sc.drawDisc(locs[loc][0]*bkSize,locs[loc][1]*bkSize ,bkSize/2, Color.PINK);
            }
            else if (mov[0]==loc) {
                mov[0]=-1;
                sc.drawDisc(locs[loc][0]*bkSize,locs[loc][1]*bkSize ,bkSize/2, Color.RED);
            }
            else{
                sc.drawDisc(locs[mov[0]][0]*bkSize,locs[mov[0]][1]*bkSize ,bkSize/2, Color.RED);
                mov[0]=loc;
                sc.drawDisc(locs[loc][0]*bkSize,locs[loc][1]*bkSize ,bkSize/2, Color.PINK);
            }
            
        }
        else if(bd.isVacant(loc) && mov[0]!=-1){            
            mov[1]=loc;  
            moveGoat();
        }
        else{
            if (mov[0]!=-1) {
                sc.drawDisc(locs[mov[0]][0]*bkSize,locs[mov[0]][1]*bkSize ,bkSize/2, Color.RED);
            }
            
            mov[0]=-1;
            mov[1]=-1;
        }
        
        
    }
    
    /**
     * Make the user selected goat move only if legal otherwise set the destination to -1 (invalid).
     * If did make a goat move, then update board, draw the updated board, reset mov to -1,-1.
     * and call tigersMove() since after every goat move, there is a tiger move.
     */
    public void moveGoat() 
    {   
        //TODO 18
        if (rules.isLegalMove(mov[0], mov[1])) {
            bd.swap(mov[0], mov[1]);
            mov[0]=-1;
            mov[1]=-1;
            drawBoard();
            tigersMove();
        }
        else{
            mov[1]=-1;
        }
        
    }
 
    /**
     * Call AIplayer to make its move. Update and draw the board after the move.
     * If Tigers cannot move, display "Goats Win!".
     * If goats are less than 6, display "Tigers Win!".
     * No need to terminate the game.
     */
    public void tigersMove()
    {
        //TODO 20
        int result=ai.makeAmove(bd);

        if (result==1) {
            rules.addGoat(-1);
            drawBoard();

            if (rules.getNumGoats()<6) {
                sc.drawRectangle(brdSize/4,brdSize/4,3*brdSize/4,3*brdSize/4,Color.RED);
                sc.drawString("TigersWin",brdSize/2 ,brdSize/2 , Color.YELLOW);
                System.out.println("Tigers Win!");
            }
        }
        else if (result==0) {
            drawBoard();
        }
        else{
            sc.drawRectangle(brdSize/4,brdSize/4,3*brdSize/4,3*brdSize/4,Color.GREEN);
            sc.drawString("Goats Win!",brdSize/2 ,brdSize/2 , Color.RED);
            System.out.println("Goats Win!");
        }
    }
        
    /**
     * Respond to a mouse click on the board. 
     * Get a valid location nearest to the click (from GameRules). 
     * If nearest location is still far, do nothing. 
     * Otherwise, call placeGoat to place a goat at the location.
     * Call this.placeTiger when it is the tigers turn to place.
     * When the game changes to move stage, call selectGoatMove to move 
     * the user selected goat to the user selected destination.
     */
    public void mousePressed(MouseEvent e) 
    {
        //TODO 1
        int tmpLoc=rules.nearestLoc(e.getX(), e.getY(), bkSize);
            
        if (!rules.isMoveStage()) {
            
            if(tmpLoc>-1){
                placeGoat(tmpLoc);
                if (!rules.isGoatsTurn()) {
                    placeTiger();
                }
            }
            
        }
        else{
            if(tmpLoc>-1){
                selectGoatMove(tmpLoc); 
            }
        }
        
        
              
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
