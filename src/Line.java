import java.awt.Graphics2D;
import java.awt.geom.Line2D.Double;

/**
 * Write a description of class Line here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Line
{
    private Point p1, p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void draw(Graphics2D g2) {
        double p1Z = p1.getZ();
        double p2Z = p2.getZ();
        if(p1Z >= 10 && p2Z >= 10) {
            g2.draw(new Double(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy()));
            //g2.drawString("" + (int)p1.getX() + "," + (int)p1.getY() + "," + (int)p1.getZ(), (int)p1.get2Dx(), (int)p1.get2Dy());
            //g2.drawString("" + (int)p2.getX() + "," + (int)p2.getY() + "," + (int)p2.getZ(), (int)p2.get2Dx(), (int)p2.get2Dy());
        } else if (p1Z >= 10) {
            Point p3 = new Point(p1.getX() + p1Z * (p2.getX() - p1.getX()) / (p1Z - p2Z), p1.getY() + p1Z * (p2.getY() - p1.getY()) / (p1Z - p2Z), 0);
            g2.draw(new Double(p1.get2Dx(), p1.get2Dy(), p3.get2Dx(), p3.get2Dy()));
        } else if (p2Z >= 10) {
            Point p3 = new Point(p2.getX() + p2Z * (p1.getX() - p2.getX()) / (p2Z - p1Z), p2.getY() + p2Z * (p1.getY() - p2.getY()) / (p2Z - p1Z), 0);
            g2.draw(new Double(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy()));
        }
    }

    public void transform(double[] transformationMatrix) {
        p1.transform(transformationMatrix);
        p2.transform(transformationMatrix);
    }

    public Point getPointOne() {
        return p1.getZ() >= 0 ? p1 : new Point(p2.getX() + p2.getZ() * (p1.getX() - p2.getX()) / (p2.getZ() - p1.getZ()), p2.getY() + p2.getZ() * (p1.getY() - p2.getY()) / (p2.getZ() - p1.getZ()), 0);
    }

    public Point getPointTwo() {
        return p2.getZ() >= 0 ? p2 : new Point(p1.getX() + p1.getZ() * (p2.getX() - p1.getX()) / (p1.getZ() - p2.getZ()), p1.getY() + p1.getZ() * (p2.getY() - p1.getY()) / (p1.getZ() - p2.getZ()), 0);
    }
}
