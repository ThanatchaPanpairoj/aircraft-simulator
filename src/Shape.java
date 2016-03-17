import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Abstract class Shape - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Shape
{
    public abstract void draw(Graphics2D g2); 

    public abstract void transform(float[] transformationMatrix);

    public abstract float getX();

    public abstract float getY();

    public abstract float getZ();
    
    public abstract void decompose();
}
