
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
enum Color{
    RESET("\033[0m"),       //Normal
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m");    // GREEN
    private final String color;

    Color(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color;
    }
}
enum Difficulty{
    EASY("easy"),MEDIUM("medium"),HARD("hard");
    private final String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String toString(){
        return difficulty;
    }
}
public class Board{

    int[][] board;
    public Board(Difficulty difficulty) throws IOException {
        Document doc = Jsoup
                .connect("https://sugoku.herokuapp.com/board?difficulty="+difficulty.toString())
                .ignoreContentType(true)
                .get();
        this.board = getBoardFromDoc(doc);
    }
    /*
    method parses board from JSON
     */
    private int[][] getBoardFromDoc(Document doc) {
        Elements elements = doc.getAllElements();
        String json = elements.text();
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("board");
        int[][] tempBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            JSONArray first = arr.getJSONArray(i);
            for (int j = 0; j < 9; j++) {
                Object num = first.get(j);
                tempBoard[i][j] = Integer.parseInt(num.toString());
            }
        }
        return tempBoard;
    }
    /*
    prints the board as green until the current row and column which it highlights as red
    The rest of the board is white.
    Therefore, when everything is white - board is unsolved
    When everything is green - board is completely solved.
     */
    public void printBoard(int row, int col){
        int[][] board = this.board;
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(i == row&&j==col){
                    System.out.print(Color.RED);
                    System.out.print(board[i][j]+" ");
                }
//                else if(j<col&&i<row) {
//                    System.out.print(Color.GREEN);
//                    System.out.print(board[i][j]+" ");
//                }
                else{
                    System.out.print(Color.RESET);
                    System.out.print(board[i][j]+" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    public void printBoard(){
        printBoard(9,9);//prints out the entire board
    }
    /*
    Recursive backtracking to solve a sudoku board.
    Intuitive color coded input in console when Main.java is run
     */
    public boolean solveBoard() {
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(this.board[i][j]==0){
                    for(int num = 1;num<10;num++){
                        boolean check = isPossible(i,j,num);
                        this.board[i][j] = num;
                        if(check){
                            if(solveBoard()){
                                return true;
                            }
                            else{
                                this.board[i][j] = 0;
                            }
                        }
                        else{
                            this.board[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
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
