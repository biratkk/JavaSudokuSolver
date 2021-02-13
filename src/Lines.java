import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.LinkedList;

public class Lines extends JComponent {
    private static class Line{
        final int x1,y1,x2,y2;
        final Color color;

        private Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }
    private final LinkedList<Line> lines = new LinkedList<>();

    public void addLine(int x1,int y1,int x2,int y2){
        addLine(x1, y1, x2, y2,Color.BLACK);
    }
    public void addLine(int x1,int y1,int x2,int y2,Color color){
        lines.add(new Line(x1,y1,x2,y2,color));
        repaint();
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Line line:lines){
            g.setColor(line.color);
            g.drawLine(line.x1,line.y1,line.x2,line.y2);
        }
    }
}
