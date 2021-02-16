
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

public class Board extends JPanel{
    private final int padHorizontal = 25;
    private final int padVertical = 10;
    private final int fontSize = 40;
    ArrayList<JLabel[]> list = new ArrayList<>();
    GridBagConstraints gbc = new GridBagConstraints();
    public Board(int[][] board){
        this.setBackground(Color.BLACK);
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
        list.get(row)[col].setForeground(Color.WHITE);
    }
    public void fill(int[][] board){
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                JLabel temp = new JLabel(String.valueOf(board[i][j]));
                list.get(i)[j] = temp;
                temp.setSize(30,30);
                temp.setFont(new Font("Calibri",Font.PLAIN,fontSize));
                temp.setForeground(Color.WHITE);
                gbc.insets = new Insets(padVertical,padHorizontal,padVertical,padHorizontal);
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
        lines.offset(35,20);
        lines.paintComponent(g);
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
