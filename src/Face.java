import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;

/**
 * Write a description of class Face here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Face {
    private int distance;
    private boolean orange;
    private double lightingScaleConstant, lightingScale;
    private Point p1, p2, p3, p4, p5, p6, p7, p8, p9, normal;
    private static final Color EXHUAST_COLOR = new Color(255, 230, 180), MISSILE_BACK_COLOR = new Color(194, 195, 195);;

    public Face(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double uz = p2.getZ() - p1.getZ();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();
        double vz = p3.getZ() - p2.getZ();
        normal = new Point(uy * vz - uz - vy, uz * vx - ux * vz, ux * vy - uy * vx);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }

    public Face(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX()) * 0.25, 2)
            + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY()) * 0.25, 2) 
            + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ()) * 0.25, 2));
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double uz = p2.getZ() - p1.getZ();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();
        double vz = p3.getZ() - p2.getZ();
        normal = new Point(uy * vz - uz - vy, uz * vx - ux * vz, ux * vy - uy * vx);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
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
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double uz = p2.getZ() - p1.getZ();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();
        double vz = p3.getZ() - p2.getZ();
        normal = new Point(uy * vz - uz - vy, uz * vx - ux * vz, ux * vy - uy * vx);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
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
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double uz = p2.getZ() - p1.getZ();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();
        double vz = p3.getZ() - p2.getZ();
        normal = new Point(uy * vz - uz - vy, uz * vx - ux * vz, ux * vy - uy * vx);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
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
        double ux = p2.getX() - p1.getX();
        double uy = p2.getY() - p1.getY();
        double uz = p2.getZ() - p1.getZ();
        double vx = p3.getX() - p2.getX();
        double vy = p3.getY() - p2.getY();
        double vz = p3.getZ() - p2.getZ();
        normal = new Point(uy * vz - uz - vy, uz * vx - ux * vz, ux * vy - uy * vx);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }

    public void draw(Graphics2D g2) {
        if(p4 == null) {
            g2.setColor(new Color(134 + (int)(101 * lightingScale), 135 + (int)(100 * lightingScale), 145 + (int)(90 * lightingScale)));
            //g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
            //        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));
            drawTriangle(p1, p2, p3, g2);
            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        } else if (p5 == null) {
            if(!orange)
                g2.setColor(new Color(134 + (int)(101 * lightingScale), 135 + (int)(100 * lightingScale), 145 + (int)(90 * lightingScale)));
            else
                g2.setColor(new Color(106 + (int)(20 * lightingScale), 89 + (int)(20 * lightingScale), 79 + (int)(20 * lightingScale)));
            //             g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx()}, 
            //                     new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy()}, 4));
            drawTriangle(p1, p2, p3, g2);
            drawTriangle(p3, p4, p1, g2);
            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX()) * 0.25, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY()) * 0.25, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ()) * 0.25, 2));
        } else if (p6 == null) {
            g2.setColor(new Color(134 + (int)(101 * lightingScale), 135 + (int)(100 * lightingScale), 145 + (int)(90 * lightingScale)));
            //g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx(), p5.get2Dx()}, 
            //        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy(), p5.get2Dy()}, 5));
            drawTriangle(p1, p2, p3, g2);
            drawTriangle(p3, p4, p1, g2);
            drawTriangle(p4, p5, p1, g2);
            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX()) * 0.2, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY()) * 0.2, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ()) * 0.2, 2));
        } else if (p9 == null) {
            g2.setColor(MISSILE_BACK_COLOR);
            //g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx(), p5.get2Dx(), p6.get2Dx(), p7.get2Dx(), p8.get2Dx()}, 
            //        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy(), p5.get2Dy(), p6.get2Dy(), p7.get2Dy(), p8.get2Dy()}, 8));
            drawTriangle(p1, p2, p3, g2);
            drawTriangle(p3, p4, p1, g2);
            drawTriangle(p4, p5, p1, g2);
            drawTriangle(p5, p6, p1, g2);
            drawTriangle(p6, p7, p1, g2);
            drawTriangle(p7, p8, p1, g2);
            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX() + p6.getX() + p7.getX() + p8.getX()) * 0.125, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY() + p6.getY() + p7.getY() + p8.getY()) * 0.125, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ() + p6.getZ() + p7.getZ() + p8.getZ()) * 0.125, 2));
        } else {
            g2.setColor(EXHUAST_COLOR);
            //g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx(), p4.get2Dx(), p5.get2Dx(), p6.get2Dx(), p7.get2Dx(), p8.get2Dx(), p9.get2Dx()}, 
            //        new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy(), p4.get2Dy(), p5.get2Dy(), p6.get2Dy(), p7.get2Dy(), p8.get2Dy(), p9.get2Dy()}, 9));
            drawTriangle(p1, p2, p3, g2);
            drawTriangle(p3, p4, p1, g2);
            drawTriangle(p4, p5, p1, g2);
            drawTriangle(p5, p6, p1, g2);
            drawTriangle(p6, p7, p1, g2);
            drawTriangle(p7, p8, p1, g2);
            drawTriangle(p8, p9, p1, g2);
            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX() + p4.getX() + p5.getX() + p6.getX() + p7.getX() + p8.getX() + p9.getX()) * 0.11, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY() + p4.getY() + p5.getY() + p6.getY() + p7.getY() + p8.getY() + p9.getY()) * 0.11, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ() + p4.getZ() + p5.getZ() + p6.getZ() + p7.getZ() + p8.getZ() + p9.getZ()) * 0.11, 2));
        }
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

    public void drawTriangle(Point p1, Point p2, Point p3, Graphics2D g2){
//         if(p1.get2Dy() > p2.get2Dy()) {
//             Point temp = p1;
//             p1 = p2;
//             p2 = temp;
//         }
//         if(p1.get2Dy() > p3.get2Dy()) {
//             Point temp = p1;
//             p1 = p3;
//             p3 = temp;
//         }
//         if(p2.get2Dx() > p3.get2Dx()) {
//             Point temp = p2;
//             p2 = p3;
//             p3 = temp;
//         }

        int p1x = Math.round((float)p1.get2Dx() * 16.0f);
        int p2x = Math.round((float)p2.get2Dx() * 16.0f);
        int p3x = Math.round((float)p3.get2Dx() * 16.0f);
        int p1y = Math.round((float)p1.get2Dy() * 16.0f);
        int p2y = Math.round((float)p2.get2Dy() * 16.0f);
        int p3y = Math.round((float)p3.get2Dy() * 16.0f);
        int dx12 = p1x - p2x;
        int dx23 = p2x - p3x;
        int dx31 = p3x - p1x;
        int dy12 = p1y - p2y;
        int dy23 = p2y - p3y;
        int dy31 = p3y - p1y;
        int adx12 = dx12 << 4;
        int adx23 = dx23 << 4;
        int adx31 = dx31 << 4;
        int ady12 = dy12 << 4;
        int ady23 = dy23 << 4;
        int ady31 = dy31 << 4;
        int minX = Math.min(Math.min(p1x, p2x), p3x);
        int maxX = Math.max(Math.max(p1x, p2x), p3x);
        int minY = Math.min(Math.min(p1y, p2y), p3y);
        int maxY = Math.max(Math.max(p1y, p2y), p3y);
        int c1 = dy12 * p1x - dx12 * p1y;
        int c2 = dy23 * p2x - dx23 * p2y;
        int c3 = dy31 * p3x - dx31 * p3y;
//         if(dy12 < 0 || (dy12 == 0 && dx12 > 0)) c1++;
//         if(dy23 < 0 || (dy23 == 0 && dx23 > 0)) c2++;
//         if(dy31 < 0 || (dy31 == 0 && dx31 > 0)) c3++;
        int cy1 = c1 + dx12 * minY - dy12 * minX;
        int cy2 = c2 + dx23 * minY - dy23 * minX;
        int cy3 = c3 + dx31 * minY - dy31 * minX;
        minX >>= 4;
        maxX >>= 4;
        minY >>= 4;
        maxY >>= 4;
        for(int y = minY; y < maxY; y++) {
            int cx1 = cy1;
            int cx2 = cy2;
            int cx3 = cy3;
            for(int x = minX; x < maxX; x++) {
                //                 if((p1x - p2x) * (y - p1y) / (p1y - p2y) * (x - p1x) > 0 
                //                 && (p2x - p3x) * (y - p2y) / (p2y - p3y) * (x - p2x) > 0 
                //                 && (p3x - p1x) * (y - p3y) / (p3y - p1y) * (x - p3x) > 0)
                if(cx1 >= 0 && cx2 >= 0 && cx3 >= 0)
                    g2.drawLine(x, y, x, y);
                cx1 -= ady12;
                cx2 -= ady23;
                cx3 -= ady31;
            }

            cy1 += adx12;
            cy2 += adx23;
            cy3 += adx31;
        }
    }

    public int getDistance() {
        return distance;
    }
}
