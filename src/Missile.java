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
    private Point thrust, velocity;
    private ArrayList<Point> points;

    public Missile() {
        fired = false;
        thrust = new Point(0, 0, 1);
        points = new ArrayList<Point>();
    }

    public void add(Point p) {
        points.add(p);
    }

    public void fire(double jetX, double jetY, double jetZ) {
        fired = true;
        velocity = new Point(jetX, jetY, jetZ);
    }

    public void fly() {
        if(Math.sqrt(Math.pow(thrust.getX(), 2) + Math.pow(thrust.getZ(), 2) + Math.pow(thrust.getZ(), 2)) < 150)
            thrust.transform(new double[] {1, 0, 0, 0.01 * thrust.getX(),
                    0, 1, 0, 0.01 * thrust.getY(),
                    0, 0, 1, 0.01 * thrust.getZ(),
                    0, 0, 0, 1});
        for(Point p : points) {
            p.transform(new double[] {1, 0, 0, thrust.getX() - velocity.getX(),
                    0, 1, 0, thrust.getY() - velocity.getY(),
                    0, 0, 1, thrust.getZ() - velocity.getZ(),
                    0, 0, 0, 1});
        }
        //System.out.println(points.get(0).getZ() + "  " +  velocity.getX()  + "  " +  velocity.getY() + "  " +  velocity.getZ());
    }

    public void transform(double[] transformationMatrix, boolean transformThrust) {
        for(Point p : points) {
            p.transform(transformationMatrix);
        }
        if(transformThrust) {
            thrust.transform(transformationMatrix);
            if(velocity != null)
                velocity.transform(transformationMatrix);
        }
    }

    public boolean getFired() {
        return fired;
    }
}
