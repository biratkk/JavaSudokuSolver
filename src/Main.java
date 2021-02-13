
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

public class Main{

    public static void main(String[] args) throws IOException {
        //setting the difficulty and speed of the program
        Difficulty difficulty = Difficulty.HARD;
        Speed speed = Speed.INFINITE;
        int[][] board = makeBoard(difficulty);
        Board gui = new Board(board);
        JButton button = new JButton("Click to solve");
        button.addActionListener((event)-> {
                new Thread(()->{
                    Solver solver = new Solver(board,gui,speed);
                    try {
                        if(solver.solve())solver.printBoard();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }).start();
        });
        JFrame frame = new JFrame();
        frame.setTitle("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setVisible(true);
        frame.add(gui);
        gui.add(button);


    }
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
