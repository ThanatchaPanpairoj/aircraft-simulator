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


    public void draw(int[] pixels, double[] zBuffer, Graphics2D g2, int[] textureMap) {
        if((p2.getX() * normal.getX() + p2.getY() * (normal.getY()) + p2.getZ() * (normal.getZ())) < 0) {
            drawTriangle(pixels, zBuffer, g2, textureMap);
        //      g2.setColor(Color.RED);
        //      g2.drawLine((int)p1.get2Dx(), (int)p1.get2Dy(), (int)p2.get2Dx(), (int)p2.get2Dy());
        //      g2.drawLine((int)p2.get2Dx(), (int)p2.get2Dy(), (int)p3.get2Dx(), (int)p3.get2Dy());
        //      g2.drawLine((int)p3.get2Dx(), (int)p3.get2Dy(), (int)p1.get2Dx(), (int)p1.get2Dy());
        //      g2.setColor(Color.BLACK);
        //      g2.drawLine((int)p1.get2Dx(), (int)p1.get2Dy(), (int)(p1.getNormal().get2Dx() + 1 * p1.get2Dx()), (int)(p1.getNormal().get2Dy() + 1 * p1.get2Dy()));
        //      g2.drawLine((int)p2.get2Dx(), (int)p2.get2Dy(), (int)(p2.getNormal().get2Dx() + 1 * p2.get2Dx()), (int)(p2.getNormal().get2Dy() + 1 * p2.get2Dy()));
        //      g2.drawLine((int)p3.get2Dx(), (int)p3.get2Dy(), (int)(p3.getNormal().get2Dx() + 1 * p3.get2Dx()), (int)(p3.getNormal().get2Dy() + 1 * p3.get2Dy()));
        }
    }

    public void drawTriangle(int[] pixels, double[] zBuffer, Graphics2D g2, int[] textureMap) {
        // Bounding box
        int maxX = (int)(Math.min(hWIDTH, (int)(Math.max(Math.max(p1.get2Dx(), p2.get2Dx()), p3.get2Dx()))));
        int minX = (int)(Math.max(-hWIDTH, (int)(Math.min(Math.min(p1.get2Dx(), p2.get2Dx()), p3.get2Dx()))));
        int maxY = (int)(Math.min(hHEIGHT, (int)(Math.max(Math.max(p1.get2Dy(), p2.get2Dy()), p3.get2Dy()))));
        int minY = (int)(Math.max(-hHEIGHT, (int)(Math.min(Math.min(p1.get2Dy(), p2.get2Dy()), p3.get2Dy()))));

        // Interpolation Precalculations
        double area = (p3.get2Dx() - p1.get2Dx()) * (p2.get2Dy() - p1.get2Dy())
                - (p3.get2Dy() - p1.get2Dy()) * (p2.get2Dx() - p1.get2Dx());
        double invZ1 = 1 / (area * p1.getZ());
        double invZ2 = 1 / (area * p2.getZ());
        double invZ3 = 1 / (area * p3.getZ());

        // Delta Precalculations
        double bay = (p2.get2Dy() - p1.get2Dy()) * invZ3;
        double cby = (p3.get2Dy() - p2.get2Dy()) * invZ1;
        double acy = (p1.get2Dy() - p3.get2Dy()) * invZ2;
        double bax = (p2.get2Dx() - p1.get2Dx()) * invZ3;
        double cbx = (p3.get2Dx() - p2.get2Dx()) * invZ1;
        double acx = (p1.get2Dx() - p3.get2Dx()) * invZ2;

        // Color
        //int color = (255 << 24) | ((134 + (int)(101 * lightingScale)) << 16) | ((135 + (int)(100 * lightingScale)) << 8) | (145 + (int)(90 * lightingScale));
        
        // Column Precalculations
        double xaxbay = (minX - p1.get2Dx()) * bay;
        double xbxcby = (minX - p2.get2Dx()) * cby;
        double xcxacy = (minX - p3.get2Dx()) * acy;

        // Row Precalculations
        double yaybax0 = (maxY - p1.get2Dy()) * bax;
        double ybycbx0 = (maxY - p2.get2Dy()) * cbx;
        double ycyacx0 = (maxY - p3.get2Dy()) * acx;

        // Edge function values, row precalculations, drawing completion
        double edge1, edge2, edge3, yaybax, ybycbx, ycyacx;
        boolean drawn;

        for (int pX = minX; pX <= maxX; pX+=1, xaxbay += bay, xbxcby += cby, xcxacy += acy) {
            drawn = false;

            // Row Precalculations
            yaybax = yaybax0;
            ybycbx = ybycbx0;
            ycyacx = ycyacx0;

            for (int pY = maxY; pY >= minY; pY-=1, yaybax -= bax, ybycbx -= cbx, ycyacx -= acx) {
                try {
                    if ((edge1 = edgeFunction(xbxcby, ybycbx)) >= 0
                    && (edge2 = edgeFunction(xcxacy, ycyacx)) >= 0
                    && (edge3 = edgeFunction(xaxbay, yaybax)) >= 0) {
                    //&& (edge3 = 1 - edge1 - edge2) >= 0) {
                        int index = WIDTH * (pY+hHEIGHT) + (pX+hWIDTH);
                        double z = 1 / (edge1 + edge2 + edge3);
                        //System.out.println(p1.getZ() + ", " + p2.getZ() + ", " + p3.getZ() + " : " + z);
                        if (zBuffer[index] > z) {
                            int st1 = (int) (z * (edge1 * p1.getST1() + edge2 * p2.getST1() + edge3 * p3.getST1()));
                            int st2 = (int) (z * (edge1 * p1.getST2() + edge2 * p2.getST2() + edge3 * p3.getST2()));                            
                            //    r = (int)((p1.getR() + p2.getR() + p3.getR()) / 3);
                            //    g = (int)((p1.getG() + p2.getG() + p3.getG()) / 3);
                            //    b = (int)((p1.getB() + p2.getB() + p3.getB()) / 3);
                            //    System.out.println(p1.getR() + ", " + p2.getR() + ", " + p3.getR() + ": " + r); 
                            int rgb = textureMap[1500 * st1 + st2];
                            int r = (rgb >> 16) & 0xFF;
                            int g = (rgb >> 8) & 0xFF;
                            int b = rgb & 0xFF;
                            //if (rgb == -1315861) {
                            if (r > 200 && g > 200 && b > 200) {
                                r = (int) (z * (edge1 * p1.getR() + edge2 * p2.getR() + edge3 * p3.getR()));
                                g = (int) (z * (edge1 * p1.getG() + edge2 * p2.getG() + edge3 * p3.getG()));
                                b = (int) (z * (edge1 * p1.getB() + edge2 * p2.getB() + edge3 * p3.getB()));
                                //r = 80;
                                //g = 80;
                                //b = 85;
                            }
                            int lightingScale = (int) (z * (edge1 * p1.getLightingScale()
                                                            + edge2 * p2.getLightingScale()
                                                            + edge3 * p3.getLightingScale()));
                            r += lightingScale;
                            g += lightingScale;
                            b += lightingScale;
                            r = Math.min(255, Math.max(0, r));
                            g = Math.min(255, Math.max(0, g));
                            b = Math.min(255, Math.max(0, b));
                            int color = (255 << 24) | (r << 16) | (g << 8) | b;
                            //if (r > 255 || g > 255 || b > 255)
                            //System.out.println(r + ", " + g + ", " + b);
                            //    if (!(p1.getZ() > z || p2.getZ() >z || p3.getZ() > z) ||
                            //  !(p1.getZ() < z || p2.getZ() < z || p3.getZ() < z))
                            //  System.out.println(p1.getZ() + " " + p2.getZ() + " " + p2.getZ() + " " + z);

                            pixels[index] = color;
                            zBuffer[index] = z;
                        }
                        drawn = true;
                    } else if (drawn) {
                        break;
                    }
                } catch (Exception e) {
            //      System.out.println(-WIDTH + "<" + pX + "<" + WIDTH + ", " + -HEIGHT + "<" + pY + "<" + HEIGHT);
                }
            }
        }
    }

    public double edgeFunction(double xpxppy, double ypyppx) {
        return xpxppy - ypyppx;
    }

    public void setRGB(int r, int g, int b) {
        p1.setRGB(r, g, b);
        p2.setRGB(r, g, b);
        p3.setRGB(r, g, b);
    }

    public void transform(double[] transformationMatrix) {
        normal.transform(transformationMatrix);
    }
}
