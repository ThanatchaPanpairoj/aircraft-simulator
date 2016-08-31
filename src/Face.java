import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;

import java.awt.image.BufferedImage;

import java.awt.Toolkit;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Face {
    private int distance, bay, cby, acy, bax, cbx, acx;
    private boolean orange;
    private double lightingScaleConstant, lightingScale;
    private Point p1, p2, p3, p4, p5, p6, p7, p8, p9, normal;
    private static final Color EXHUAST_COLOR = new Color(255, 230, 180), MISSILE_BACK_COLOR = new Color(194, 195, 195);;

    private static final int WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5);
    private static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5);

    public Face(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        double a1 = p2.getX() - p1.getX();
        double a2 = p2.getY() - p1.getY();
        double a3 = p2.getZ() - p1.getZ();
        double b1 = p3.getX() - p2.getX();
        double b2 = p3.getY() - p2.getY();
        double b3 = p3.getZ() - p2.getZ();
        normal = new Point(a2*b3-a3*b2,a3*b1-a1*b3,a1*b2-a2*b1);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }


    public void draw(BufferedImage canvas) {
        if((p2.getX() * normal.getX() + p2.getY() * (normal.getY()) + p2.getZ() * (normal.getZ())) < 0) {
            // g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
                    // new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));
            bay = (p2.get2Dy() - p1.get2Dy());
            cby = (p3.get2Dy() - p2.get2Dy());
            acy = (p1.get2Dy() - p3.get2Dy());
            bax = (p2.get2Dx() - p1.get2Dx());
            cbx = (p3.get2Dx() - p2.get2Dx());
            acx = (p1.get2Dx() - p3.get2Dx());

            drawTriangle(canvas, p1, p2, p3);
            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        }
        //else {
            //g2.setColor(Color.RED);
            //g2.drawLine(p2.get2Dx(), p2.get2Dy(), p2.get2Dx() + normal.get2Dx(), p2.get2Dy() + normal.get2Dy());
            //                 g2.drawLine(p1.get2Dx(), p1.get2Dy(), p2.get2Dx(), p2.get2Dy());
            //                 g2.drawLine(p2.get2Dx(), p2.get2Dy(), p3.get2Dx(), p3.get2Dy());
            //                 g2.drawLine(p3.get2Dx(), p3.get2Dy(), p1.get2Dx(), p1.get2Dy());
        //}
    }

    public void setOrange(boolean orange) {
        this.orange = orange;
    }

    public void transform(double[] transformationMatrix) {
        normal.transform(transformationMatrix);
    }

    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {
        lightingScale = Math.max((gravityX * normal.getX() + gravityY * normal.getY() + gravityZ * normal.getZ()) * lightingScaleConstant, -0.4);
    }

    public void drawTriangle(BufferedImage canvas, Point pa, Point pb, Point pc) {
        int maxX = (int)(Math.max(Math.max(pa.get2Dx(), pb.get2Dx()), pc.get2Dx()) + 1);
        int minX = (int)(Math.min(Math.min(pa.get2Dx(), pb.get2Dx()), pc.get2Dx()));
        int maxY = (int)(Math.max(Math.max(pa.get2Dy(), pb.get2Dy()), pc.get2Dy()) + 1);
        int minY = (int)(Math.min(Math.min(pa.get2Dy(), pb.get2Dy()), pc.get2Dy()));
       
        //Color RGB = (new Color(134 + (int)(101 * lightingScale), 135 + (int)(100 * lightingScale), 145 + (int)(90 * lightingScale)));
        int R = (134 + (int)(101 * lightingScale));
        int G = (135 + (int)(100 * lightingScale));
        int B = (145 + (int)(90 * lightingScale));
        int color = (R << 16) | (G << 8) | B;
        
        for (int pX = minX; pX <= maxX; pX+=1) {
            for (int pY = minY; pY <= maxY; pY+=1) {
                if (pixelContained(pX, pY, pa, pb, pc)) {
                    try {
                        canvas.setRGB(pX+WIDTH, pY+HEIGHT, color);
                    } catch (Exception ayylmao) {
                        System.out.println(R + " " + G + " " + B);
                    }
                }
            }
        }
    }

    public boolean pixelContained(double pX, double pY, Point pa, Point pb, Point pc) {
        double edge1 = (pX - pa.get2Dx()) * bay - (pY - pa.get2Dy()) * bax;
        double edge2 = (pX - pb.get2Dx()) * cby - (pY - pb.get2Dy()) * cbx;
        double edge3 = (pX - pc.get2Dx()) * acy - (pY - pc.get2Dy()) * acx;
        return (edge1 >= 0 && edge2 >= 0 && edge3 >= 0);
    }

    public int getDistance() {
        return distance;
    }
}
