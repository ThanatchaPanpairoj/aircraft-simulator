import java.awt.Toolkit;

/**
 * Write a description of class Point here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Point
{
    private float x, y, z, s, depthScale;
    private int twoDX, twoDY;
    private static final float WIDTH = (float)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()) * 0.5f;

    public Point(float x, float y, float z, float s) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.s = s;
        depthScale = s * WIDTH / (10 + z);
        twoDX = (int)(depthScale * x);
        twoDY = (int)(depthScale * y);
    }

    public Point(double x, double y, double z) {
        this((float)x, (float)y, (float)z, 1);
    }
    
    public Point(float x, float y, float z) {
        this(x, y, z, 1);
    }

    public void transform(float[] transformationMatrix) {
        float newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + s * transformationMatrix[3];
        float newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + s * transformationMatrix[7];
        float newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + s * transformationMatrix[11];
        float newS = x * transformationMatrix[12] + y * transformationMatrix[13] + z * transformationMatrix[14] + s * transformationMatrix[15];
        x = newX;
        y = newY;
        z = newZ;
        s = newS;
        depthScale = s * WIDTH / (10 + z);
        twoDX = (int)(depthScale * x);
        twoDY = (int)(depthScale * y);
    }

    public int get2Dx() {
        return twoDX;
    }

    public int get2Dy() {
        return twoDY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
