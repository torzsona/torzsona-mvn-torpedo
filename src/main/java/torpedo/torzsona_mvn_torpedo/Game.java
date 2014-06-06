package torpedo.torzsona_mvn_torpedo;

import java.util.Random;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zsolt Nandor Torok
 * @version 0.1
 *
 */

/**
 * Main class of {@code Game}.<br>
 * This class implements the Torpedo game.<br>
 *
 */
public class Game {
	
	/**
	 * Matrix of the {@code gameBoard}.<br>
	 * This variable holds the game board.<br>
	 * Populated in {@link #main(String[])}
	 *
	 */
	public static int[][] gameBoard;
	
	/**
	 * Matrix of {@code ships}.<br>
	 * This variable holds the randomly placed ships.<br>
	 * Populated in {@link #main(String[])}
	 *
	 */
    public static int[][] ships;
    
    /**
	 * Array of {@code shootCoor}.<br>
	 * This variable holds the guessed board coordinates.<br>
	 * Populated in {@link #main(String[])}
	 *
	 */
	public static int[] shootCoor;
    
	/**
	 * Integer of {@code tries}.<br>
	 * This variable holds the number of tries.<br>
	 * Populated in {@link #main(String[])}
	 *
	 */
    public static int tries;
    
    /**
	 * Integer of {@code hits}.<br>
	 * This variable holds the number of hits.<br>
	 * Populated in {@link #main(String[])}
	 *
	 */
    public static int hits;
    
    /**
	 * Integer of {@code size}.<br>
	 * This variable holds the size of the board and the number of the ships.<br>
	 * Populated in {@link #main(String[])}
	 *
	 */
    public static int size;
    
    /**
	 * Logger class of {@code logger}.<br>
	 * This class is used for logging purposes.<br>
	 *
	 */
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    
	/**
	 * Function of {@code main}.<br>
	 * Main function of the {@code Game} class.
	 * 
	 * @param args Array of string arguments
	 *
	 */
    public static void main(String[] args) {
        
    	// Initialize tries and hits
        tries = 0;
        hits = 0;
        size = 5;
        
        // Prepare the 5x5 game board
        gameBoard = new int[size][size];
        prepareGameBoard(gameBoard);
        
        // Randomly place ships
        ships = new int[size][2];
        placeShips(ships);
        
        // Initialize variable that will hold the coordinates
        shootCoor = new int[2];
        
        System.out.println();
        
        // Loop until all ships are hit
        do {
        	// First show the game board
            drawGameBoard(gameBoard);
            
            // Then ask the player to shoot a ship
            shootShip(shootCoor);
            
            // Increase the number of tries
            tries++;
            System.out.println("\nNumber of tries so far: " + tries);
            
            // Update the game board
            updateGameBoard(shootCoor,ships,gameBoard);
        } while ( hits != size );

        // Game is over
        if ( tries == size ) {
        	logger.info("You hit all the ships in " + tries + " tries! You are GOOD!");
        } else if (tries <= size*2) {
        	logger.info("You hit all the ships in " + tries + " tries!");
        } else {
        	logger.info("You hit all the ships in " + tries + " tries! I am sure you can do better...");
        }
        
        // Show the board at last
        drawGameBoard(gameBoard);
    }
    
    /**
     * Function of {@code prepareGameBoard}.<br>
     * Initializes the game board of Torpedo.
     * Called in {@link #main(String[])}.
     * 
     * @param gameBoard Matrix of integers
     *
     */
    public static void prepareGameBoard(int[][] gameBoard) {
    	// Initially set each and every cell to -1
        for(int row=0 ; row < size ; row++ ) {
            for(int column=0 ; column < size ; column++ ) {
                gameBoard[row][column] = -1;
            }
        }
        logger.debug("Game board has been prepared");
    }
    
    /**
     * Function of {@code drawGameBoard}.<br>
     * Draws the game board of Torpedo.
     * Called in {@link #main(String[])}.
     * 
     * @param gameBoard Matrix of integers
     *
     */
    public static void drawGameBoard(int[][] gameBoard) {
    	String table = "";
    	for (int ind=1 ; ind < size+1; ind++) {
    		table = table + "\t" + ind + " ";
    	}
    	System.out.println(table);
        System.out.println();
        
        for(int row=0 ; row < size ; row++ ) {
            System.out.print((row+1)+"");
            for(int column=0 ; column < size ; column++ ) {
            	// If it has not been shot
                if(gameBoard[row][column] == -1) {
                    System.out.print("\t"+"~");
                // If it has been shot but missed
                } else if(gameBoard[row][column] == 0) {
                    System.out.print("\t"+"0");
                // If it has been shot and hit
                } else if(gameBoard[row][column] == 1) {
                    System.out.print("\t"+"+");
                }
            }
            System.out.println();
        }
    }

    /**
     * Function of {@code placeShips}.<br>
     * Place ships randomly.
     * Called in {@link #main(String[])}.
     * 
     * @param ships Matrix of integers
     *
     */
    public static void placeShips(int[][] ships){
        Random random = new Random();
        int[] lastShip = new int[] {-1, -1};
        int ship = 0;
        
        // Place the ships
        do {
        	ships[ship][0]=random.nextInt(size);
            ships[ship][1]=random.nextInt(size);
            
            // Some sanity check to make sure we don't create a ship on the same coordinates
            if ( lastShip[0]!=ships[ship][0] &&
            	 lastShip[1]!=ships[ship][1] ) {
            	lastShip[0] = ships[ship][0];
                lastShip[1] = ships[ship][1];
            	ship++;
            }
        } while ( ship < size );
        logger.debug("Ships have been placed");
    }

    /**
     * Function of {@code shootShip}.<br>
     * Reads which coordinates to shoot at from the console input.
     * Called in {@link #main(String[])}.
     * 
     * @param shoot Array of integers
     *
     */
    public static void shootShip(int[] shoot){
    	// Read row and column coordinates from the console
        Scanner consoleInput = new Scanner(System.in);
        
        // Shoot row coordinate
        System.out.print("Row:    ");
        try {
            shoot[0] = consoleInput.nextInt();
            shoot[0]--;
        } catch ( java.util.InputMismatchException e ) {
        	logger.error("Not integer was entered!");
        }
        
        // Shoot column coordinate
        System.out.print("Column: ");
        try {
            shoot[1] = consoleInput.nextInt();
            shoot[1]--;
        } catch ( java.util.InputMismatchException e ) {
        	logger.error("Not integer was entered!");
        }
        
        logger.debug("Ship coordinates have been read from the inpust console: (" + shoot[0] + "," + shoot[1] + ")");
    }

    /**
     * Function of {@code updateGameBoard}.
     * Called in {@link #main(String[])}.
     * 
     * @param shootCoor Array of integers
     * @param ships Matrix of integers
     * @param gameBoard Matrix of integers
     *
     */
    public static void updateGameBoard(int[] shootCoor, int[][] ships, int[][] gameBoard){
    	// If hit
        if(hit(shootCoor,ships)) {
            gameBoard[shootCoor[0]][shootCoor[1]] = 1;
            hits++;
        // If not
        } else {
            gameBoard[shootCoor[0]][shootCoor[1]] = 0;
        }
        logger.debug("Game board has been updated");
    }
    
    /**
     * Function of {@code hit}.
     * Called in {@link #main(String[])}, {@link #updateGameBoard(int[], int[][], int[][])}.
     * 
     * @param shoot Array of integers
     * @param ships Matrix of integers
     * @return retval Boolean, true if hit, false otherwise
     *
     */
    public static boolean hit(int[] shoot, int[][] ships){
        boolean retval = false;
        for(int ship=0 ; ship < size ; ship++) {
            if( shoot[0]==ships[ship][0] &&
            	shoot[1]==ships[ship][1]) {
                System.out.printf("Nice shot! Ship was hit on ( %d , %d )!\n",shoot[0]+1,shoot[1]+1);
                retval = true;
            }
        }
        return retval;
    }
}

