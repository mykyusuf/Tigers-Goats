package tigersgoats;

import java.util.Random;
import java.util.*;

public class AIplayer
{
    private Random rn; // for random tiger or location selection
    private GameRules rul; // an instance of GameRules to check for legal moves
    private int[] tigerLocs; // location of tigers for convenience 
    private int ntigers; // number of tigers placed
    // A 2D Array that maintains legal goat eating moves that tigers can perform, e.g. {0,8,16} means 
    // a tiger can jump from location 0 to 16 (or 16 to 0) to eat the goat at location 8 
    private final int[][] legalEats = {{0,1,2},{0,7,6},{0,8,16},{1,9,17},{2,10,18},{2,3,4},{3,11,19},
                                 {4,5,6},{4,12,20},{5,13,21},{6,14,22},{7,15,23},{8,9,10},{8,15,14},
                                 {10,11,12},{12,13,14},{16,17,18},{16,23,22},{18,19,20},{20,21,22}};                              
    /**
     * Constructor for objects of class AIplayer.
     * Initializes instance variables.
     */
    public AIplayer()
    {
        // TODO 14
        rul=new GameRules();
        rn=new Random();
        tigerLocs=new int[3];
        ntigers=0;
    }

    /**
     * Place tiger in a random VACANT location on the Board
     * Update the board, the tiger count and tigerLocs.
     */
    public void placeTiger(Board bd)
    {
        //TODO 15
        int randTmp=rn.nextInt(24);
        boolean placed=false;
        
        for (int i = 0; i < legalEats.length; i++) {            
            if (bd.isGoat(legalEats[i][1])) {
                if (bd.isVacant(legalEats[i][0]) && !bd.isGoat(legalEats[i][2])) {
                    bd.setTiger(legalEats[i][0]);
                    tigerLocs[ntigers]=legalEats[i][0];
                    ntigers++;
                    placed=true;
                    i=legalEats.length+1;
                }
                else if (bd.isVacant(legalEats[i][2]) && !bd.isGoat(legalEats[i][0])) {
                    bd.setTiger(legalEats[i][2]);
                    tigerLocs[ntigers]=legalEats[i][2];
                    ntigers++;
                    placed=true;
                    i=legalEats.length+1;
                }
            }
        }
        if (!placed) {
            while(!bd.isVacant(randTmp)){
                randTmp=rn.nextInt(24);
            }
            bd.setTiger(randTmp);
            tigerLocs[ntigers]=randTmp;
            ntigers++;
        }
        
        
    }
    
    /**
     * If possible to eat a goat, must eat and return 1
     * If cannot eat any goat, make a move and return 0
     * If cannot make any move (all Tigers blocked), return -1
     */
    public int makeAmove(Board bd)
    {
        if (eatGoat(bd))  return 1; // did eat a goat
        else if (simpleMove(bd)) return 0; // made a simple move
        else return -1; // could not move
    }
    
    /**
     * Randomly choose a tiger, move it to a legal destination and return true
     * if none of the tigers can move, return false.
     * Update the board and the tiger location (tigerLocs)
     */
    private boolean simpleMove(Board bd)
    {
        //TODO 21
        boolean cont=false;
        int check[]={0,0,0};
        int randTmp;

        randTmp=rn.nextInt(3);
        check[randTmp]=1;
        
        for (int i = 0; i < 24; i++) {
            if(rul.isLegalMove(tigerLocs[randTmp], i) && bd.isVacant(i)){
                bd.swap(tigerLocs[randTmp],i);
                cont=true;
                tigerLocs[randTmp]=i;
                i=24;
            }
        }
        if (!cont) {
            while(check[randTmp]!=0){
                randTmp=rn.nextInt(3);
            }
            check[randTmp]=1;
            
            for (int i = 0; i < 24; i++) {
                if(rul.isLegalMove(tigerLocs[randTmp], i) && bd.isVacant(i)){
                    bd.swap(tigerLocs[randTmp],i);
                    cont=true;
                    tigerLocs[randTmp]=i;
                    i=24;
                }
            }                        
        }
        if (!cont) {
            while(check[randTmp]!=0){
                randTmp=rn.nextInt(3);
            }
            check[randTmp]=1;
            
            for (int i = 0; i < 24; i++) {
                if(rul.isLegalMove(tigerLocs[randTmp], i) && bd.isVacant(i)){
                    bd.swap(tigerLocs[randTmp],i);
                    cont=true;
                    tigerLocs[randTmp]=i;
                    i=24;
                }
            }                        
        }
        if (cont==false) {
            return false;
        }
            
        return true; 
    }
    
    /**
     * If possible, eat a goat and return true otherwise return false.
     * Update the board and the tiger location (tigerLocs)
     */
    private boolean eatGoat(Board bd)
    {
        //TODO 21    
        for (int i = 0; i < legalEats.length; i++) {
            for (int j = 0; j < ntigers; j++) {
                if (legalEats[i][0]==tigerLocs[j] && bd.isGoat(legalEats[i][1]) && bd.isVacant(legalEats[i][2])) {
                    bd.setVacant(legalEats[i][1]);
                    bd.swap(legalEats[i][0], legalEats[i][2]);
                    tigerLocs[j]=legalEats[i][2];
                    return true;
                }
                else if (legalEats[i][2]==tigerLocs[j] && bd.isGoat(legalEats[i][1]) && bd.isVacant(legalEats[i][0])) {
                    bd.setVacant(legalEats[i][1]);
                    bd.swap(legalEats[i][2], legalEats[i][0]);
                    tigerLocs[j]=legalEats[i][0];
                    return true;
                }
            }
        }
        
        
        return false;
    }
   
}
