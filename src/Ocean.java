import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;

import java.awt.image.BufferedImage;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ocean extends Shape
{
    private double x, y, z;
    private Point p1, p2, p3, p4;
    private Line l1, l2, l3, l4;
    private static final Color COLOR = new Color(72, 90, 163);

    public Ocean() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        p1 = new Point(-100000.1, 10000.1, -100000.2);
        p2 = new Point(100000.3, 10000.2, -100000.4);
        p3 = new Point(100000.5, 10000.3, 100000.6);
        p4 = new Point(-100000.7, 10000.4, 100000.8);
        this.l1 = new Line(p1, p2);
        this.l2 = new Line(p2, p3);
        this.l3 = new Line(p3, p4);
        this.l4 = new Line(p4, p1);
    }

    public void draw(int[] pixels, double[] zBuffer, Graphics2D g2) {
/*
        g2.setColor(COLOR);
//         System.out.println(l1.getPointOne().getZ()
//         + " " + l1.getPointTwo().getZ()
//         + " " + l2.getPointOne().getZ()
//         + " " + l2.getPointTwo().getZ()
//         + " " + l3.getPointOne().getZ()
//         + " " + l3.getPointTwo().getZ()
//         + " " + l4.getPointOne().getZ()
//         + " " + l4.getPointTwo().getZ());
        g2.fillPolygon(new Polygon(new int[] {(int)l1.getPointOne().get2Dx(), 
                    (int)l1.getPointTwo().get2Dx(), 
                    (int)l2.getPointOne().get2Dx(), 
                    (int)l2.getPointTwo().get2Dx(), 
                    (int)l3.getPointOne().get2Dx(), 
                    (int)l3.getPointTwo().get2Dx(), 
                    (int)l4.getPointOne().get2Dx(), 
                    (int)l4.getPointTwo().get2Dx(),},
                new int[] {(int)l1.getPointOne().get2Dy(), 
                    (int)l1.getPointTwo().get2Dy(), 
                    (int)l2.getPointOne().get2Dy(), 
                    (int)l2.getPointTwo().get2Dy(), 
                    (int)l3.getPointOne().get2Dy(), 
                    (int)l3.getPointTwo().get2Dy(), 
                    (int)l4.getPointOne().get2Dy(), 
                    (int)l4.getPointTwo().get2Dy(),},
                8));
                        g2.setColor(Color.RED);
        l1.draw(g2);
        l2.draw(g2);
        l3.draw(g2);
        l4.draw(g2);
*/
    }

    public void transform(double[] transformationMatrix) {
        p1.transform(transformationMatrix);
        p2.transform(transformationMatrix);
        p3.transform(transformationMatrix);
        p4.transform(transformationMatrix);

        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
    }
    
    public Point getP1() {
        return p1;
    }
    
    public Point getP2() {
        return p2;
    }
    
    public Point getP3() {
        return p3;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {}
    
    public void decompose(){}
}
