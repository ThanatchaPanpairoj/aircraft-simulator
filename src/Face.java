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
        double a1 = p2.getX() - p1.getX();
        double a2 = p2.getY() - p1.getY();
        double a3 = p2.getZ() - p1.getZ();
        double b1 = p3.getX() - p2.getX();
        double b2 = p3.getY() - p2.getY();
        double b3 = p3.getZ() - p2.getZ();
        normal = new Point(a2*b3-a3*b2,a3*b1-a1*b3,a1*b2-a2*b1);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }


    public void draw(Graphics2D g2) {
        if((p2.getX() * normal.getX() + p2.getY() * (normal.getY()) + p2.getZ() * (normal.getZ())) < 0) {
            g2.setColor(new Color(134 + (int)(101 * lightingScale), 135 + (int)(100 * lightingScale), 145 + (int)(90 * lightingScale)));
            g2.fillPolygon(new Polygon(new int[] {p1.get2Dx(), p2.get2Dx(), p3.get2Dx()}, 
                    new int[] {p1.get2Dy(), p2.get2Dy(), p3.get2Dy()}, 3));
            //drawTriangle(p1, p2, p3, g2);
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

    public void drawTriangle(Point pa, Point pb, Point pc, Graphics2D g2){
        if(pa.get2Dy() > pb.get2Dy()) {
            Point temp = pa;
            pa = pb;
            pb = temp;
        }
        if(pa.get2Dy() > pc.get2Dy()) {
            Point temp = pa;
            pa = pc;
            pc = temp;
        }
        if(pb.get2Dx() > pc.get2Dx()) {
            Point temp = pb;
            pb = pc;
            pc = temp;
        }
        int p1x = Math.round((float)pa.get2Dx() * 16.0f);
        int p2x = Math.round((float)pb.get2Dx() * 16.0f);
        int p3x = Math.round((float)pc.get2Dx() * 16.0f);
        int p1y = Math.round((float)pa.get2Dy() * 16.0f);
        int p2y = Math.round((float)pb.get2Dy() * 16.0f);
        int p3y = Math.round((float)pc.get2Dy() * 16.0f);
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
        if(dy12 < 0 || (dy12 == 0 && dx12 > 0)) c1++;
        if(dy23 < 0 || (dy23 == 0 && dx23 > 0)) c2++;
        if(dy31 < 0 || (dy31 == 0 && dx31 > 0)) c3++;
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
