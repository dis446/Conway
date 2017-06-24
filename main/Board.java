package main;

import javafx.scene.layout.BorderPane;

import java.lang.reflect.Array;
import java.util.LinkedList;

/**
 * Created by dis446 on 6/20/17.
 */
public class Board {

    /**
     * The number of rows and coloums
     */
    private int DIM;

    /**
     * A 2-D array of the cells, represented as Booleans. True means alive, False means dead.
     */
    private Boolean[][] board;

    /**
     * Initilize the Board of Boolean cells given a dimension
     * @param DIM The number of rows and coloums
     */
    public Board(int DIM){
        this.DIM = DIM;
        this.board = new Boolean[DIM][DIM];
        for (int x = 0; x < DIM; x ++) {
            for (int y = 0; y < DIM; y++) {
                board[x][y] = false;
            }
        }
    }

    /**
     * Getter for the Dimensions
     */
     public int getDIM() {
         return DIM;
     }

     public boolean cellAlive(int x, int y){
         return this.board[x][y];
     }

    /**
     * Perform one iteration of Conway's life.
     * Rules:
     * 1 If a live cell has less than two live neighbours it dies.
     * 2 If a live cell has more than 3 neighbours it dies.
     * 3 If a dead cell had three neighbours it becomes alive.
     * 4 If a live cell has two or three live neighbours it stays alive
     */
    public void next(){
        //Make a copy of the board so that all of the boolean cells' next states can be calculated independently,
        //without affecting the cells that are processed after it.
        Boolean[][] newBoard = new Boolean[DIM][DIM];
        for (int x = 0; x < DIM; x++){
            for (int y = 0; y < DIM; y++){
                int count = this.getNeighbours(x, y);
                if (this.board[x][y]) {
                    if (count < 2 || count > 3) {
                        newBoard[x][y] = false;
                    }
                    else{
                        newBoard[x][y] = true;
                    }
                }
                else{
                    if (count == 3){
                        newBoard[x][y] = true;
                    }
                }
            }
        }
        this.board = newBoard;
    }

    /**
     * Get the status of a given cell
     * @param x
     * @param y
     * @return Boolean. Cell alive or dead
     */
    public boolean getCell(int x, int y){
        return this.board[x][y];
    }

    /**
     * Flip a given cell's Boolean state.
     * @param x
     * @param y
     */
    public void switchCell(int x, int y){
        this.board[x][y] = !this.board[x][y];
    }

    /**
     * How many live cells is a given cell next to?
     * @param x
     * @param y
     * @return
     */
    public int getNeighbours(int x, int y){
        int count = 0;
        if (x < DIM - 1){
            if (this.board[x + 1][y]){
                count++;
            }
        }
        if (x > 0){
            if (this.board[x - 1][y]){
                count++;
            }
        }
        if (y < DIM - 1){
            if (this.board[x][y + 1]){
                count++;
            }
        }
        if (y > 0){
            if (this.board[x][y - 1]){
                count++;
            }
        }
        return count;
    }
}
