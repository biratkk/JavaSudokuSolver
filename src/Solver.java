import Enums.Speed;

import java.util.Arrays;



public class Solver{
    int[][] board;
    Board gui;
    Speed speed;
    public Solver(int[][] board, Board gui, Speed speed){
        this.speed = speed;
        this.board = board;
        this.gui = gui;
    }

    /*
            prints the board as green until the current row and column which it highlights as red
            The rest of the board is white.
            Therefore, when everything is white - board is unsolved
            When everything is green - board is completely solved.
             */

    /*
    Recursive backtracking to solve a sudoku board.
    Intuitive color coded input in console when Main.java is run
     */
    public boolean solve() throws InterruptedException {
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(this.board[i][j]==0){
                    for(int num = 1;num<10;num++){
                        boolean check = isPossible(i,j,num);
                        this.board[i][j] = num;
                        gui.update(i,j,num);
                        gui.highlightCurrent(i,j);
                        Thread.sleep(speed.toInt());
                        if(check){
                            if(solve()){
                                Thread.sleep(speed.toInt());
                                gui.update(i,j,num);
                                return true;
                            }
                            else{
                                Thread.sleep(speed.toInt());
                                gui.update(i,j,0);
                                gui.unhighlight(i,j);
                                this.board[i][j] = 0;
                            }
                        }
                        else{
                            Thread.sleep(speed.toInt());
                            gui.update(i,j,0);
                            gui.unhighlight(i,j);
                            this.board[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        gui.finishHighlight();
        return true;
    }
    /*
    Checks if the value on the board is possible with index i and j for number num
     */
    private boolean isPossible(int i, int j, int num) {
        int[] arr = Arrays.copyOf(this.board[i],9);
        boolean check = Arrays.stream(arr).anyMatch(x->x==num);
        if(check)return false;
        //checks row using streams to check if there are any of the same numbers
        for(int ind = 0;ind<9;ind++){
            arr[ind] = this.board[ind][j];
        }
        check = Arrays.stream(arr).anyMatch(x->x==num);
        if(check) return false;
        //checks columns using streams to check if there are any of the same numbers
        int modRow = (int)(3*Math.floor((double)i/3));
        int modCol = (int)(3*Math.floor((double)j/3));
        for(int k = 0;k<3;k++){
            for(int l = 0;l<3;l++){
                if(board[modRow+k][modCol+l]==num){
                    return false;
                }
            }
        }
        //check subgrid iteratively to check if there are any of the same numbers
        return true;
    }
}
