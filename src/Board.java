
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
public class Board{

    int[][] board;
    public Board() throws IOException {
        Document doc = Jsoup
                .connect("https://sugoku.herokuapp.com/board?difficulty=easy")
                .ignoreContentType(true)
                .get();
        this.board = getBoardFromDoc(doc);
    }

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

    public void printBoard() {
        int[][] board = this.board;
        for(int[] temp: board){
            System.out.println(Arrays.toString(temp));
        }
        System.out.println("-----------------------------");
    }
    public void printBoard(int row, int col){
        int[][] board = this.board;
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(i == row&&j==col){
                    System.out.print(Color.RED);
                    System.out.print(board[i][j]+" ");
                }
                else if(i<row||j<col){
                    System.out.print(Color.GREEN);
                    System.out.print(board[i][j]+" ");
                }
                else{
                    System.out.print(Color.RESET);
                    System.out.print(board[i][j]+" ");
                }
            }
            System.out.println();
        }
    }
    public boolean solveBoard() {
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                if(this.board[i][j]==0){
                    for(int num = 1;num<10;num++){
                        boolean check = isPossible(i,j,num);
                        this.board[i][j] = num;
                        printBoard(i,j);
                        System.out.println();
                        if(check){
                            this.board[i][j] = num;
                            if(solveBoard()){
                                return true;
                            }
                            else{
                                this.board[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPossible(int i, int j, int num) {
        int[] arr = Arrays.copyOf(this.board[i],9);
        boolean check = Arrays.stream(arr).anyMatch(x->x==num);
        if(check)return false;
        for(int ind = 0;ind<9;ind++){
            arr[ind] = this.board[ind][j];
        }
        check = Arrays.stream(arr).anyMatch(x->x==num);
        if(check) return false;
        int modRow = (int)(3*Math.floor((double)i/3));
        int modCol = (int)(3*Math.floor((double)j/3));
        for(int k = 0;k<3;k++){
            for(int l = 0;l<3;l++){
                if(board[modRow+k][modCol+l]==num){
                    return false;
                }
            }
        }
        return true;
    }

}
