
import java.io.IOException;

public class Main{

    public static void main(String[] args) throws IOException {
        Board board = new Board();
        board.printBoard(9,9);
        board.solveBoard();
        board.printBoard(9,9);
    }
}
