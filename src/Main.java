
import Enums.Difficulty;
import Enums.Speed;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main{
    public Difficulty difficulty = Difficulty.EASY;
    public Speed speed = Speed.FAST;

    private int[][] board;
    private Board gui;
    private Thread solver;

    JFrame frame = new JFrame();
    public static void main(String[] args) throws IOException {
       new Main();
    }
    public Main() throws IOException {
        board = makeBoard(difficulty);
        gui = new Board(board);
        initSolverThread();
        createGUI();
    }

    private void createGUI() {
        frame.setTitle("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setVisible(true);
        frame.setContentPane(gui);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Click to solve");
        changeDefaultFormat(startButton);
        startButton.addActionListener((event) -> {
            if(!solver.isAlive())startSolverThread();
        });
        buttons.add(startButton);

        JButton renewButton = new JButton("Restart");
        startButton = changeDefaultFormat(startButton);
        renewButton.addActionListener((event) -> {
            try {
                renewSudoku();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttons.add(renewButton);

//        JPanel buttons = new JPanel(new FlowLayout());
//        buttons.add(startButton);

        /*
        This is for future improvements
         */
        gui.add(buttons);
    }
    private void renewSudoku() throws IOException {
        if(!solver.isAlive() && !solver.isInterrupted()){
            int[][] newBoard = makeBoard(difficulty);
            board = newBoard;
            gui.refresh(board);
            initSolverThread();
        }
    }

    private JButton changeDefaultFormat(JButton startButton) {
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.white);
        return startButton;
    }

    private void initSolverThread(){
        solver = new Thread(()->{
            Solver solver = new Solver(board,gui,speed);
            try {
                if(solver.solve())this.solver.interrupt();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }
    private void startSolverThread() {
        solver.start();
    }

    /*
    This is for future improvements
     */
//    private void stopSolverThread() throws InterruptedException {
//        solver.wait();
//    }
    public static int[][] makeBoard(Difficulty difficulty) throws IOException {
        Document doc = Jsoup
                .connect("https://sugoku.herokuapp.com/board?difficulty="+difficulty.toString())
                .ignoreContentType(true)
                .get();
        return getBoardFromDoc(doc);
    }
    private static int[][] getBoardFromDoc(Document doc) {
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
}
