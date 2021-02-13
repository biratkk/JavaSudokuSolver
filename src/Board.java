
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Board extends JPanel{

    ArrayList<JLabel[]> list = new ArrayList<>();
    ImageIcon img = new ImageIcon("resources/Background.jpg");
    GridBagConstraints gbc = new GridBagConstraints();
    public Board(int[][] board){
        for(int i = 0;i<9;i++){
            list.add(new JLabel[9]);
        }
        setLayout(new GridBagLayout());
        fill(board);
    }

    public void highlightCurrent(int row, int col){
        list.get(row)[col].setForeground(Color.RED);
    }
    public void unhighlight(int row, int col) {
        list.get(row)[col].setForeground(Color.BLACK);
    }
    public void fill(int[][] board){
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                JLabel temp = new JLabel(String.valueOf(board[i][j]));
                list.get(i)[j] = temp;
                temp.setSize(30,30);
                temp.setBackground(Color.BLUE);
                temp.setFont(new Font("Calibri",Font.PLAIN,50));
                gbc.insets = new Insets(0,25,0,25);
                gbc.gridx = i;
                gbc.gridy = j;
                add(temp,gbc);
            }
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Lines lines = new Lines();
        drawLines(lines);
        repaint();
    }

    private void drawLines(Lines lines) {
        lines.addLine(0,200,600,200);
        lines.addLine(0,400,600,400);
        lines.addLine(200,0,200,600);
        lines.addLine(400,0,400,600);
    }

    public void update(int row,int col, int num) {
        list.get(row)[col].setText(String.valueOf(num));
    }


    public void finishHighlight() {
        for(JLabel[] arr:list){
            for(JLabel label:arr){
                label.setForeground(Color.GREEN);
            }
        }
    }
}
