
import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException {
        //setting the difficulty of the program
        Board board = new Board(Difficulty.HARD);
        board.printBoard();
        if(board.solveBoard())board.printBoard();
    }
}
