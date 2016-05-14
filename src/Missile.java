import java.util.ArrayList;

/**
 * Write a description of class Missile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Missile
{
    private boolean fired;
    private Point velocity;
    private ArrayList<Point> points;
    
    public Missile() {
        fired = false;
        velocity = new Point(0, 0, 1);
        points = new ArrayList<Point>();
    }
    
    public void add(Point p) {
        points.add(p);
    }
    
    public void fire(double jetX, double jetY, double jetZ) {
        fired = true;
        velocity.transform(new double[] {1, 0, 0, -jetX,
                0, 1, 0, -jetY,
                0, 0, 1, -jetZ,
                0, 0, 0, 1});
    }
    
    public void fly() {
        for(Point p : points) {
            p.transform(new double[] {1, 0, 0, velocity.getX(),
                0, 1, 0, velocity.getY(),
                0, 0, 1, velocity.getZ(),
                0, 0, 0, 1});
        }
        //System.out.println(points.get(0).getZ() + "  " +  velocity.getX()  + "  " +  velocity.getY() + "  " +  velocity.getZ());
    }
    
    public void transform(double[] transformationMatrix, boolean transformVelocity) {
        for(Point p : points) {
            p.transform(transformationMatrix);
        }
        if(transformVelocity) {
            velocity.transform(transformationMatrix);
        }
    }
    
    public boolean getFired() {
        return fired;
    }
}
