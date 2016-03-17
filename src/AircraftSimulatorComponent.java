import java.lang.Double;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * Basic GUI component GUITemplateComponent
 * 
 * @author Thanatcha Panpairoj
 * @version (a version number or a date)
 */
public class AircraftSimulatorComponent extends JComponent
{
    private int width, height, fps, intro;
    private static int halfW, halfH, sixthW, fourthH; 
    private float speed;
    private boolean crash;
    private static final float ninetiethPI = (float)(Math.PI * 0.0111111111f);
    private Color LIGHT_BLUE = new Color(153, 204, 255);
    private Point gravity, thrust, velocity, altitudeReference1, altitudeReference2;
    private Ocean ocean;
    private Shape aircraft;
    private ArrayList<Line> grid;
    private ArrayList<Shape> shapes;

    public AircraftSimulatorComponent(int width, int height) {
        this.width = width;
        this.height = height;
        this.halfW = width >> 1;
        this.halfH = height >> 1;
        this.sixthW = width / 6;
        this.fourthH = height >> 2;
        this.intro = 100;
        this.speed = 0;
        this.crash = false;

        gravity = new Point(0, -0.3266667f, 0);
        thrust = new Point(0, 0, -0.5f);
        velocity = new Point(0, 0, -50f);
        altitudeReference1 = new Point(0, 2000, 0);
        altitudeReference2 = new Point(0, 2000.5f, 0);

        ocean = new Ocean();
        shapes = new ArrayList<Shape>();
        shapes.add(aircraft = new Jet(0, 0, 0));

        grid = new ArrayList<Line>();
        for(int w = -100000; w <= 100000; w += 800) {
            grid.add(new Line(new Point(w, 2000, -100000, 1), new Point(w, 2000, 100000, 1)));
            grid.add(new Line(new Point(-100000, 2000, w, 1), new Point(100000, 2000, w, 1)));

            if(w >= 0) {
                grid.add(new Line(new Point(-100000, -98000 + w, -100000, 1), new Point(100000, -98000 + w, -100000, 1)));
                grid.add(new Line(new Point(-100000, -98000 + w, 100000, 1), new Point(100000, -98000 + w, 100000, 1)));
                grid.add(new Line(new Point(-100000, -98000 + w, -100000, 1), new Point(-100000, -98000 + w, 100000, 1)));
                grid.add(new Line(new Point(100000, -98000 + w, -100000, 1), new Point(100000, -98000 + w, 100000, 1)));
            }
            grid.add(new Line(new Point(w, 2000, -100000, 1), new Point(w, -98000, -100000, 1)));
            grid.add(new Line(new Point(w, 2000, 100000, 1), new Point(w, -98000, 100000, 1)));
            grid.add(new Line(new Point(-100000, 2000, w, 1), new Point(-100000, -98000, w, 1)));
            grid.add(new Line(new Point(100000, 2000, w, 1), new Point(100000, -98000, w, 1)));

            grid.add(new Line(new Point(w, -98000, -100000, 1), new Point(w, -98000, 100000, 1)));
            grid.add(new Line(new Point(-100000, -98000, w, 1), new Point(100000, -98000, w, 1)));
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(halfW, halfH);

        if(intro > 0)
            transformAll(new float[] {(float)Math.cos(intro * ninetiethPI), 0, -(float)Math.sin(intro * ninetiethPI), 0,
                    0, 1,                    0, 0,
                    (float)Math.sin(intro * ninetiethPI), 0, (float)Math.cos(intro * ninetiethPI), 0, 
                    0, 0,                    0, 1});

        transformAll(new float[] {1, 0, 0,     0, 
                0, 1, 0,     1, 
                0, 0, 1, (float)(7.5 + thrust.getZ() * 0.2 + intro * 0.1), 
                0, 0, 0,     1});

        ocean.draw(g2);

        g2.setColor(LIGHT_BLUE);
        for(Line l : grid) {
            l.draw(g2);
        }

        shapes.sort(new DistanceComparator());
        for(Shape s : shapes) {
            s.draw(g2);
        }

        transformAll(new float[] {1, 0, 0,     -0, 
                0, 1, 0,     -1, 
                0, 0, 1, (float)(-7.5 - thrust.getZ() * 0.2 - intro * 0.1), 
                0, 0, 0,     1});

        if(intro > 0) {
            transformAll(new float[] {(float)Math.cos(-intro * ninetiethPI), 0, -(float)Math.sin(-intro * ninetiethPI), 0,
                    0, 1,                    0, 0,
                    (float)Math.sin(-intro * ninetiethPI), 0, (float)Math.cos(-intro * ninetiethPI), 0, 
                    0, 0,                    0, 1});
            intro--;
        }

        if(!crash) {
            velocity.transform(new float[] {0.99f, 0, 0,      gravity.getX(), 
                    0, 0.99f, 0,      gravity.getY() + (float)Math.abs(thrust.getZ() * 0.6533335), 
                    0, 0, 0.99f,      thrust.getZ() + gravity.getZ() + (float)Math.random() * 0.1f - 0.05f, 
                    0, 0, 0,      1});
            translate(new float[] {1, 0, 0,      velocity.getX(), 
                    0, 1, 0,      velocity.getY(), 
                    0, 0, 1,      velocity.getZ(), 
                    0, 0, 0,      1});
        } else {
            velocity.transform(new float[] {0, 0, 0, 0, 
                    0, 0, 0, 0, 
                    0, 0, 0, 0, 
                    0, 0, 0, 0});
            aircraft.decompose();
        }

        speed = (float)Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2) + Math.pow(velocity.getZ(), 2));

        g2.setColor(Color.BLACK);
        g2.drawString("WASD to turn", -halfW + 5, - halfH + 17);
        g2.drawString("QE to spin", -halfW + 5, - halfH + 34);
        g2.drawString("ESC to exit", -halfW + 5, - halfH + 51);
        g2.drawString("FPS: " + fps, halfW - 50, - halfH + 17);

        if(intro == 0) {
            g2.setFont (new Font (Font.SANS_SERIF, Font.BOLD, 20));
            g2.setColor(Color.GREEN);
            g2.drawString((int)(speed * 10.8) + "kph", -sixthW - 80, 0);
            g2.drawString((int)(getAltitude() * 0.05) + "m", sixthW + 5, 0);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(-sixthW, -fourthH, -sixthW + 20, -fourthH);
            g2.drawLine(-sixthW, -fourthH, -sixthW, fourthH);
            g2.drawLine(-sixthW, fourthH, -sixthW + 20, fourthH);
            g2.drawLine(sixthW, -fourthH, sixthW - 20, -fourthH);
            g2.drawLine(sixthW, -fourthH, sixthW, fourthH);
            g2.drawLine(sixthW, fourthH, sixthW - 20, fourthH);
        }
    }

    public float getAltitude() {
        float altitude = 0.25f + (float)(Math.pow(altitudeReference2.getX(), 2) + Math.pow(altitudeReference2.getY(), 2) + Math.pow(altitudeReference2.getZ(), 2) - Math.pow(altitudeReference1.getX(), 2) - Math.pow(altitudeReference1.getY(), 2) - Math.pow(altitudeReference1.getZ(), 2));
        if(altitude < 20) {
            thrust = new Point(0, 0, 0);
            crash = true;
        }
        return altitude;
    }

    public float getRotationScale() {
        return velocity.getZ() * -0.02f;
    }

    public void translate(float[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
    }

    public void rotate(float[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        gravity.transform(transformationMatrix);
        velocity.transform(transformationMatrix);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
    }

    public void rotateAircraft(float[] transformationMatrix) {
        aircraft.transform(transformationMatrix);
    }

    public void transformAll(float[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        gravity.transform(transformationMatrix);
        velocity.transform(transformationMatrix);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
        for(Shape s : shapes) {
            s.transform(transformationMatrix);
        }
    }

    public void updateThrust(int newT) {
        thrust = new Point(0, 0, -newT * 0.1f);
    }

    public void click() {
        //
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
