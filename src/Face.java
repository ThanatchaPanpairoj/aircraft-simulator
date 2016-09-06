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
    private Vertex p1, p2, p3;
    private Point normal;
    private static final int WIDTH = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
    private static final int hWIDTH = WIDTH / 2;
    private static final int HEIGHT = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    private static final int hHEIGHT = HEIGHT / 2;

    public Face(Vertex p1, Vertex p2, Vertex p3) {
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
	p1.addNormal(normal);
	p2.addNormal(normal);
	p3.addNormal(normal);
        lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2) + Math.pow(normal.getZ(), 2))); 
    }


    public void draw(int[] pixels, int[] zBuffer) {
        if((p2.getX() * normal.getX() + p2.getY() * (normal.getY()) + p2.getZ() * (normal.getZ())) < 0) {
            drawTriangle(pixels, zBuffer, p1, p2, p3);

            distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2) 
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        }
    }

    public void drawTriangle(int[] pixels, int[] zBuffer, Vertex pa, Vertex pb, Vertex pc) {
	// Bounding box
        int maxX = (int)(Math.min(hWIDTH, (int)(Math.max(Math.max(pa.get2Dx(), pb.get2Dx()), pc.get2Dx()))));
        int minX = (int)(Math.max(-hWIDTH, (int)(Math.min(Math.min(pa.get2Dx(), pb.get2Dx()), pc.get2Dx()))));
        int maxY = (int)(Math.min(hHEIGHT, (int)(Math.max(Math.max(pa.get2Dy(), pb.get2Dy()), pc.get2Dy()))));
        int minY = (int)(Math.max(-hHEIGHT, (int)(Math.min(Math.min(pa.get2Dy(), pb.get2Dy()), pc.get2Dy()))));
	
	// Delta Precalculations
        double bay = (p2.get2Dy() - p1.get2Dy());
        double cby = (p3.get2Dy() - p2.get2Dy());
        double acy = (p1.get2Dy() - p3.get2Dy());
        double bax = (p2.get2Dx() - p1.get2Dx());
        double cbx = (p3.get2Dx() - p2.get2Dx());
        double acx = (p1.get2Dx() - p3.get2Dx());

	// Color
        int color = (255 << 24) | ((134 + (int)(101 * lightingScale)) << 16) | ((135 + (int)(100 * lightingScale)) << 8) | (145 + (int)(90 * lightingScale));
    
	// Interpolation Precalculations	
	double invArea = 1 / ((pc.get2Dx() - pa.get2Dx()) * (pb.get2Dy() - pa.get2Dy()) 
		- (pc.get2Dy() - pa.get2Dy()) * (pb.get2Dx() - pa.get2Dx()));
	double invZ1 = 1 / pa.getZ();
	double invZ2 = 1 / pb.getZ();
	double invZ3 = 1 / pc.getZ();
 
        // Column Precalculations
	double xaxbay = (minX - pa.get2Dx()) * bay;
	double xbxcby = (minX - pb.get2Dx()) * cby;
	double xcxacy = (minX - pc.get2Dx()) * acy;
	
	for (int pX = minX; pX <= maxX; pX+=1, xaxbay += bay, xbxcby += cby, xcxacy += acy) {
	    boolean drawn = false;

	    // Row Precalculations
	    double yaybax = (maxY - pa.get2Dy()) * bax;
	    double ybycbx = (maxY - pb.get2Dy()) * cbx;
	    double ycyacx = (maxY - pc.get2Dy()) * acx;

            for (int pY = maxY; pY >= minY; pY-=1, yaybax -= bax, ybycbx -= cbx, ycyacx -= acx) {
		try {
		    double edge1, edge2, edge3;
		    if ((edge1 = invArea * edgeFunction(xaxbay, yaybax)) >= 0 
			&& (edge2 = invArea * edgeFunction(xbxcby, ybycbx)) >= 0
			&& (edge3 = invArea * edgeFunction(xcxacy, ycyacx)) >= 0) {
       			int index = WIDTH * (pY+hHEIGHT) + (pX+hWIDTH); 	        
			double z = (1 / (edge1 * invZ1 + edge2 * invZ2 + edge3 * invZ3)); 
			//System.out.println(pa.getZ() + ", " + pb.getZ() + ", " + pc.getZ() + " : " + z);
			if (true || zBuffer[index] > z) {
			    pixels[index] = color;
			    zBuffer[index] = (int)(z + 0.5);
			}
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

    public double edgeFunction(double xpxppy, double ypyppx) {
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
