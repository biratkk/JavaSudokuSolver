
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
    public Speed speed = Speed.SLOW;

    private final int[][] board;
    private final Board gui;
    private Thread solver;
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
        JFrame frame = new JFrame();
        frame.setTitle("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.add(gui);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton startbutton = new JButton("Click to solve");
        startbutton.addActionListener((event)-> startSolverThread());
        buttons.add(startbutton);

        /*
        This is for future improvements
         */
//        JButton stopbutton = new JButton("Reset");
//        stopbutton.addActionListener((event)->{
//            try {
//                stopSolverThread();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        gui.add(stopbutton);
        frame.add(buttons);
    }

    private void initSolverThread(){
        solver = new Thread(()->{
            Solver solver = new Solver(board,gui,speed);
            try {
                solver.solve();
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
