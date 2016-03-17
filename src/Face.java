import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Face
{
    private int distance;
    private Point p1, p2, p3, p4, p5, p6, p7, p8, p9;
    private static final Color COLOR = new Color(164, 165, 175),
    LIGHT_COLOR = new Color(174, 175, 185),
    DARK_COLOR = new Color(154, 155, 165),
    EXHUAST_COLOR = new Color(255, 240, 210), 
    MISSILE_BACK_COLOR = new Color(194, 195, 195),
    LINE_COLOR = new Color(106, 106, 106);

    public Face(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
    }

    public Face(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX()) * 0.25, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY()) * 0.25, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ()) * 0.25, 2));
    }

    public Face(Point p1, Point p2, Point p3, Point p4, Point p5) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX()) * 0.2, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY()) * 0.2, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ()) * 0.2, 2));
    }

    public Face(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6, Point p7, Point p8) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.p7 = p7;
        this.p8 = p8;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX() + p6.getX() + p7.getX() + p8.getX())* 0.125, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY() + p6.getY() + p7.getY() + p8.getY()) * 0.125, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ() + p6.getZ() + p7.getZ() + p8.getZ()) * 0.125, 2));
    }

    public Face(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6, Point p7, Point p8, Point p9) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.p7 = p7;
        this.p8 = p8;
        this.p9 = p9;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX() + p6.getX() + p7.getX() + p8.getX() + p9.getX()) * 0.11, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY() + p6.getY() + p7.getY() + p8.getY() + p9.getY()) * 0.11, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ() + p6.getZ() + p7.getZ() + p8.getZ() + p9.getZ()) * 0.11, 2));
    }

    public void draw(Graphics2D g2) {
        if(p1.getZ() > 0 && p2.getZ() > 0 && p3.getZ() > 0)
            if(p4 == null) {
                g2.setColor(LIGHT_COLOR);
                g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
                        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));

                g2.setColor(LINE_COLOR);
                g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
                g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
                g2.drawLine(p3.get2Dx(), p3.get2Dy(), p1.get2Dx(), p1.get2Dy());
                distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
                    + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
                    + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
            } else if (p5 == null) {
                g2.setColor(COLOR);
                g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx()}, 
                        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy()}, 4));

                g2.setColor(LINE_COLOR);
                g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
                g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
                g2.drawLine(p3.get2Dx(), p3.get2Dy(), p4.get2Dx(), p4.get2Dy());
                g2.drawLine(p4.get2Dx(), p4.get2Dy(), p1.get2Dx(), p1.get2Dy());
                distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX()) * 0.25, 2)
                    + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY()) * 0.25, 2) 
                    + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ()) * 0.25, 2));
            } else if (p6 == null) {
                g2.setColor(DARK_COLOR);
                g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx(), p5.get2Dx()}, 
                        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy(), p5.get2Dy()}, 5));

                g2.setColor(LINE_COLOR);
                g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
                g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
                g2.drawLine(p3.get2Dx(), p3.get2Dy(), p4.get2Dx(), p4.get2Dy());
                g2.drawLine(p4.get2Dx(), p4.get2Dy(), p5.get2Dx(), p5.get2Dy());
                g2.drawLine(p5.get2Dx(), p5.get2Dy(), p1.get2Dx(), p1.get2Dy());
                distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX()) * 0.2, 2)
                    + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY()) * 0.2, 2) 
                    + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ()) * 0.2, 2));
            } else if (p9 == null) {
                g2.setColor(MISSILE_BACK_COLOR);
                g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx(), p5.get2Dx(), p6.get2Dx(), p7.get2Dx(), p8.get2Dx()}, 
                        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy(), p5.get2Dy(), p6.get2Dy(), p7.get2Dy(), p8.get2Dy()}, 8));

                g2.setColor(LINE_COLOR);
                g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
                g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
                g2.drawLine(p3.get2Dx(), p3.get2Dy(), p4.get2Dx(), p4.get2Dy());
                g2.drawLine(p4.get2Dx(), p4.get2Dy(), p5.get2Dx(), p5.get2Dy());
                g2.drawLine(p5.get2Dx(), p5.get2Dy(), p6.get2Dx(), p6.get2Dy());
                g2.drawLine(p6.get2Dx(), p6.get2Dy(), p7.get2Dx(), p7.get2Dy());
                g2.drawLine(p7.get2Dx(), p7.get2Dy(), p8.get2Dx(), p8.get2Dy());
                g2.drawLine(p8.get2Dx(), p8.get2Dy(), p1.get2Dx(), p1.get2Dy());
                distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX() + p6.getX() + p7.getX() + p8.getX()) * 0.125, 2)
                    + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY() + p6.getY() + p7.getY() + p8.getY()) * 0.125, 2) 
                    + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ() + p6.getZ() + p7.getZ() + p8.getZ()) * 0.125, 2));
            } else {
                g2.setColor(EXHUAST_COLOR);
                g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx(), p5.get2Dx(), p6.get2Dx(), p7.get2Dx(), p8.get2Dx(), p9.get2Dx()}, 
                        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy(), p5.get2Dy(), p6.get2Dy(), p7.get2Dy(), p8.get2Dy(), p9.get2Dy()}, 9));

                g2.setColor(LINE_COLOR);
                g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
                g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
                g2.drawLine(p3.get2Dx(), p3.get2Dy(), p4.get2Dx(), p4.get2Dy());
                g2.drawLine(p4.get2Dx(), p4.get2Dy(), p5.get2Dx(), p5.get2Dy());
                g2.drawLine(p5.get2Dx(), p5.get2Dy(), p6.get2Dx(), p6.get2Dy());
                g2.drawLine(p6.get2Dx(), p6.get2Dy(), p7.get2Dx(), p7.get2Dy());
                g2.drawLine(p7.get2Dx(), p7.get2Dy(), p8.get2Dx(), p8.get2Dy());
                g2.drawLine(p8.get2Dx(), p8.get2Dy(), p9.get2Dx(), p9.get2Dy());
                g2.drawLine(p9.get2Dx(), p9.get2Dy(), p1.get2Dx(), p1.get2Dy());
                distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX() + p6.getX() + p7.getX() + p8.getX() + p9.getX()) * 0.11, 2)
                    + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY() + p6.getY() + p7.getY() + p8.getY() + p9.getY()) * 0.11, 2) 
                    + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ() + p6.getZ() + p7.getZ() + p8.getZ() + p9.getZ()) * 0.11, 2));
            }
    }

    public int getDistance() {
        return distance;
    }
}
