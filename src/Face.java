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
    }


    public void draw(int[] pixels, int[] zBuffer, Graphics2D g2) {
        if((p2.getX() * normal.getX() + p2.getY() * (normal.getY()) + p2.getZ() * (normal.getZ())) < 0) {
            drawTriangle(g2, pixels, zBuffer);
//	    g2.setColor(Color.RED);
//	    g2.drawLine((int)p1.get2Dx(), (int)p1.get2Dy(), (int)p2.get2Dx(), (int)p2.get2Dy());
//	    g2.drawLine((int)p2.get2Dx(), (int)p2.get2Dy(), (int)p3.get2Dx(), (int)p3.get2Dy());
//	    g2.drawLine((int)p3.get2Dx(), (int)p3.get2Dy(), (int)p1.get2Dx(), (int)p1.get2Dy());
//	    g2.setColor(Color.BLACK);
//	    g2.drawLine((int)p1.get2Dx(), (int)p1.get2Dy(), (int)(p1.getNormal().get2Dx() + 1 * p1.get2Dx()), (int)(p1.getNormal().get2Dy() + 1 * p1.get2Dy()));
//	    g2.drawLine((int)p2.get2Dx(), (int)p2.get2Dy(), (int)(p2.getNormal().get2Dx() + 1 * p2.get2Dx()), (int)(p2.getNormal().get2Dy() + 1 * p2.get2Dy()));
//	    g2.drawLine((int)p3.get2Dx(), (int)p3.get2Dy(), (int)(p3.getNormal().get2Dx() + 1 * p3.get2Dx()), (int)(p3.getNormal().get2Dy() + 1 * p3.get2Dy()));

           distance = (int)Math.sqrt(Math.pow((p1.getX() + p2.getX() + p3.getX()) * 0.33, 2)
                + Math.pow((p1.getY() + p2.getY() + p3.getY()) * 0.33, 2)
                + Math.pow((p1.getZ() + p2.getZ() + p3.getZ()) * 0.33, 2));
        }
    }

    public void drawTriangle(Graphics2D g2, int[] pixels, int[] zBuffer) {
	    // Bounding box
        int maxX = (int)(Math.min(hWIDTH, (int)(Math.max(Math.max(p1.get2Dx(), p2.get2Dx()), p3.get2Dx()))));
        int minX = (int)(Math.max(-hWIDTH, (int)(Math.min(Math.min(p1.get2Dx(), p2.get2Dx()), p3.get2Dx()))));
        int maxY = (int)(Math.min(hHEIGHT, (int)(Math.max(Math.max(p1.get2Dy(), p2.get2Dy()), p3.get2Dy()))));
        int minY = (int)(Math.max(-hHEIGHT, (int)(Math.min(Math.min(p1.get2Dy(), p2.get2Dy()), p3.get2Dy()))));

	    // Delta Precalculations
        //double bay = (p2.get2Dy() - p1.get2Dy());
        double cby = (p3.get2Dy() - p2.get2Dy());
        double acy = (p1.get2Dy() - p3.get2Dy());
        //double bax = (p2.get2Dx() - p1.get2Dx());
        double cbx = (p3.get2Dx() - p2.get2Dx());
        double acx = (p1.get2Dx() - p3.get2Dx());

        // Color
        //int color = (255 << 24) | ((134 + (int)(101 * lightingScale)) << 16) | ((135 + (int)(100 * lightingScale)) << 8) | (145 + (int)(90 * lightingScale));

        // Interpolation Precalculations
            double invArea = 1 / ((p3.get2Dx() - p1.get2Dx()) * (p2.get2Dy() - p1.get2Dy())
                - (p3.get2Dy() - p1.get2Dy()) * (p2.get2Dx() - p1.get2Dx()));
        double invZ1 = 1 / p1.getZ();
        double invZ2 = 1 / p2.getZ();
        double invZ3 = 1 / p3.getZ();

        // Column Precalculations
        //double xaxbay = (minX - p1.get2Dx()) * bay;
        double xbxcby = (minX - p2.get2Dx()) * cby;
        double xcxacy = (minX - p3.get2Dx()) * acy;

        for (int pX = minX; pX <= maxX; pX+=1,/* xaxbay += bay,*/ xbxcby += cby, xcxacy += acy) {
            boolean drawn = false;

            // Row Precalculations
            //double yaybax = (maxY - p1.get2Dy()) * bax;
            double ybycbx = (maxY - p2.get2Dy()) * cbx;
            double ycyacx = (maxY - p3.get2Dy()) * acx;

            for (int pY = maxY; pY >= minY; pY-=1, /*yaybax -= bax,*/ ybycbx -= cbx, ycyacx -= acx) {
        		try {
		            double edge1, edge2, edge3;
		            if ((edge1 = invArea * edgeFunction(xbxcby, ybycbx)) >= 0
			        && (edge2 = invArea * edgeFunction(xcxacy, ycyacx)) >= 0
                    //&& (edge3 = edgeFunction(xaxbay, yaybax)) >= 0
                    && (edge3 = 1 - edge1 - edge2) >= 0) {
                        int index = WIDTH * (pY+hHEIGHT) + (pX+hWIDTH);
                        edge1 = edge1 * invZ1;
                        edge2 = edge2 * invZ2;
                        edge3 = edge3 * invZ3;
                        double z = (1 / (edge1 + edge2 + edge3));
                        //System.out.println(p1.getZ() + ", " + p2.getZ() + ", " + p3.getZ() + " : " + z);
                        if (true || zBuffer[index] > z) {
                            int r = (int) (z * (edge1 * p1.getR() + edge2 * p2.getR() + edge3 * p3.getR()));
                            int g = (int) (z * (edge1 * p1.getG() + edge2 * p2.getG() + edge3 * p3.getG()));
                            int b = (int) (z * (edge1 * p1.getB() + edge2 * p2.getB() + edge3 * p3.getB()));
                            //int r = (int) (1 / (edge1 / p1.getR() + edge2 / p2.getR() + edge3 / p3.getR()));
                            //int g = (int) (1 / (edge1 / p1.getG() + edge2 / p2.getG() + edge3 / p3.getG()));
                            //int b = (int) (1 / (edge1 / p1.getB() + edge2 / p2.getB() + edge3 / p3.getB()));
                            //    r = (int)((p1.getR() + p2.getR() + p3.getR()) / 3);
                            //    g = (int)((p1.getG() + p2.getG() + p3.getG()) / 3);
                            //    b = (int)((p1.getB() + p2.getB() + p3.getB()) / 3);
                            //    g = (int)p1.getG();
                            //    b = (int)p1.getB();
                            //    System.out.println(p1.getR() + ", " + p2.getR() + ", " + p3.getR() + ": " + r);
                            int color = (255 << 24) | (r << 16) | (g << 8) | b;
                            //if (r > 255 || g > 255 || b > 255)
                            //System.out.println(r + ", " + g + ", " + b);
                            //    if (!(p1.getZ() > z || p2.getZ() >z || p3.getZ() > z) ||
                            //	!(p1.getZ() < z || p2.getZ() < z || p3.getZ() < z))
                            //	System.out.println(p1.getZ() + " " + p2.getZ() + " " + p2.getZ() + " " + z);
                            pixels[index] = color;
                            zBuffer[index] = (int) (z);
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

    public int getDistance() {
        return distance;
    }
}
