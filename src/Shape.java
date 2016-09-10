import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.image.BufferedImage;

/**
 * Abstract class Shape - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Shape
{
    public abstract void draw(int[] pixels, double[] zBuffer, Graphics2D g2); 

    public abstract void transform(double[] transformationMatrix);

    public abstract double getX();

    public abstract double getY();

    public abstract double getZ();
    
    public abstract void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ);
    
    public abstract void decompose();
}
