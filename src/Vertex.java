import java.awt.Toolkit;

/**
 * Write a description of class Vertex here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vertex
{
    private int r, g, b, normalsAdded;
    private double x, y, z, s, depthScale, twoDX, twoDY, lightingScale, lightingScaleConstant;
    private Point normal;
    private static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5;

    public Vertex(double x, double y, double z, double s) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.s = s;
        this.depthScale = s * WIDTH / (10 + z);
        this.twoDX = (this.depthScale * x);
        this.twoDY = (this.depthScale * y);
	    this.normal = new Point(0, 0, 0);
    }

    public Vertex(double x, double y, double z) {
        this(x, y, z, 1);
    }

    public void addNormal(Point normal) {
	    this.normal = new Point((this.normal.getX() * normalsAdded + normal.getX()) / (normalsAdded + 1), 
				(this.normal.getY() * normalsAdded + normal.getY()) / (normalsAdded + 1),
				(this.normal.getZ() * normalsAdded + normal.getZ()) / (normalsAdded + 1));
	    normalsAdded += 1;
	    //double mag = Math.sqrt(Math.pow(this.normal.getX(), 2) + Math.pow(this.normal.getY(), 2) + Math.pow(this.normal.getZ(), 2));
	    //System.out.println(this.normal.getX()+ " " + this.normal.getY() + " " + this.normal.getZ() + " " + mag);
	    //this.normal = new Point(this.normal.getX() / mag, this.normal.getY() / mag, this.normal.getZ() / mag);
	    this.lightingScaleConstant = 0.7 / (0.3266667 * Math.sqrt(Math.pow(this.normal.getX(), 2) + Math.pow(this.normal.getY(), 2) + Math.pow(this.normal.getZ(), 2))); 
    }

    public void transform(double[] transformationMatrix) {
        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + s * transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + s * transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + s * transformationMatrix[11];
        double newS = x * transformationMatrix[12] + y * transformationMatrix[13] + z * transformationMatrix[14] + s * transformationMatrix[15];
        x = newX;
        y = newY;
        z = newZ;
        s = newS;
        depthScale = s * WIDTH / (10 + z);
        twoDX = (depthScale * x);
        twoDY = (depthScale * y);
    }

    public void transformNormal(double[] transformationMatrix) {
	    normal.transform(transformationMatrix);
    }

    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {
        lightingScale = Math.max((gravityX * normal.getX() + gravityY * normal.getY() + gravityZ * normal.getZ()) * lightingScaleConstant, -0.4);
	    r = 124 + (int)(111 * lightingScale);
	    g = 135 + (int)(110 * lightingScale);
	    b = 150 + (int)(100 * lightingScale);
    }

    public void setRGB(int r, int g, int b) {
	    this.r = r;
	    this.g = g;
	    this.b = b;
    }

    public Point getNormal() {
	    return normal;
    }

    public int getR() {
	    return r;
    }

    public int getG() {
	    return g;
    }

    public int getB() {
	    return b;
    }

    public double get2Dx() {
        return twoDX;
    }

    public double get2Dy() {
        return twoDY;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
