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
    private int distance;
    private double lightingScaleConstant, lightingScale;
    private Point p1, p2, p3, normal;
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
        normal = new Point(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }


    public void draw(int[] pixels) {
        if((p2.getX() * normal.getX() + p2.getY() * (normal.getY()) + p2.getZ() * (normal.getZ())) < 0) {
            drawTriangle(pixels, p1, p2, p3);

            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        }
    }

    public void drawTriangle(int[] pixels, Point pa, Point pb, Point pc) {
	// Bounding box
        int maxX = (int)(Math.min(WIDTH, (int)(Math.max(Math.max(pa.get2Dx(), pb.get2Dx()), pc.get2Dx()))));
        int minX = (int)(Math.max(-WIDTH, (int)(Math.min(Math.min(pa.get2Dx(), pb.get2Dx()), pc.get2Dx()))));
        int maxY = (int)(Math.min(HEIGHT, (int)(Math.max(Math.max(pa.get2Dy(), pb.get2Dy()), pc.get2Dy()))));
        int minY = (int)(Math.max(-HEIGHT, (int)(Math.min(Math.min(pa.get2Dy(), pb.get2Dy()), pc.get2Dy()))));
	
	// Delta Precalculations
        int bay = (p2.get2Dy() - p1.get2Dy());
        int cby = (p3.get2Dy() - p2.get2Dy());
        int acy = (p1.get2Dy() - p3.get2Dy());
        int bax = (p2.get2Dx() - p1.get2Dx());
        int cbx = (p3.get2Dx() - p2.get2Dx());
        int acx = (p1.get2Dx() - p3.get2Dx());

	// Color
        int color = (255 << 24) | ((134 + (int)(101 * lightingScale)) << 16) | ((135 + (int)(100 * lightingScale)) << 8) | (145 + (int)(90 * lightingScale));
     
        // Column Precalculations
	int xaxbay = (minX - pa.get2Dx()) * bay;
	int xbxcby = (minX - pb.get2Dx()) * cby;
	int xcxacy = (minX - pc.get2Dx()) * acy;

        for (int pX = minX; pX < maxX; pX+=1, xaxbay += bay, xbxcby += cby, xcxacy += acy) {
	    boolean drawn = false;

	    // Row Precalculations
	    int yaybax = (maxY - pa.get2Dy()) * bax;
	    int ybycbx = (maxY - pb.get2Dy()) * cbx;
	    int ycyacx = (maxY - pc.get2Dy()) * acx;

            for (int pY = maxY; pY > minY; pY-=1, yaybax -= bax, ybycbx -= cbx, ycyacx -= acx) {
		try {
		    int edge1, edge2, edge3;
		    if ((edge1 = edgeFunction(xaxbay, yaybax)) >= 0 
			&& (edge2 = edgeFunction(xbxcby, ybycbx)) >= 0
			&& (edge3 = edgeFunction(xcxacy, ycyacx)) >= 0) {
        	        pixels[2 * WIDTH * (pY+HEIGHT) + (pX+WIDTH)] = color;
                        drawn = true;
                    } else if (drawn) {
			break;
		    }
		} catch (Exception e) {
	//	    System.out.println(-WIDTH + "<" + pX + "<" + WIDTH + ", " + -HEIGHT + "<" + pY + "<" + HEIGHT);
		}
            }
        }
    }

    public int edgeFunction(int xpxppy, int ypyppx) {
	return xpxppy - ypyppx;
    }

    public void transform(double[] transformationMatrix) {
        normal.transform(transformationMatrix);
    }

    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {
        lightingScale = Math.max((gravityX * normal.getX() + gravityY * normal.getY() + gravityZ * normal.getZ()) * lightingScaleConstant, -0.4);
    }

    public int getDistance() {
        return distance;
    }
}
