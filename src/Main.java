
import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException {
        //setting the difficulty of the program
        Board board = new Board(Difficulty.EASY);
        board.printBoard();
        board.solveBoard();
        board.printBoard();
    }
}
