import java.lang.Double;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.util.ArrayList;

import javax.swing.JComponent;

import java.awt.image.BufferedImage;

import java.awt.image.DataBufferInt;

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
    private double speed;
    private boolean crash;
    private static final double oneEightithPI = Math.PI * 0.005555555;
    private Color LIGHT_BLUE = new Color(153, 204, 255), LIGHT_GREEN = new Color(0, 225, 0);
    private Point gravity, thrust, velocity, altitudeReference1, altitudeReference2;
    private Ocean ocean;
    private Shape aircraft;
    private ArrayList<Line> grid;
    private ArrayList<Shape> shapes;
    
    private BufferedImage canvas;

    public AircraftSimulatorComponent(int width, int height) {
        this.width = width;
        this.height = height;
        this.halfW = width >> 1;
        this.halfH = height >> 1;
        this.sixthW = width / 6;
        this.fourthH = height >> 2;
        this.intro = 200;
        this.speed = 0;
        this.crash = false;


        gravity = new Point(0, -0.3266667, 0);
        thrust = new Point(0, 0, -0.5);
        velocity = new Point(0, 0, -50);
        altitudeReference1 = new Point(0, 10000, 0);
        altitudeReference2 = new Point(0, 10000.5, 0);

        ocean = new Ocean();
        shapes = new ArrayList<Shape>();
        shapes.add(aircraft = new Jet(0, 0, 0));

        grid = new ArrayList<Line>();
        for(int w = -100000; w <= 100000; w += 800) {
            grid.add(new Line(new Point(w, 10000, -100000, 1), new Point(w, 10000, 100000, 1)));
            grid.add(new Line(new Point(-100000, 10000, w, 1), new Point(100000, 10000, w, 1)));

            if(w >= 0) {
                grid.add(new Line(new Point(-100000, -90000 + w, -100000, 1), new Point(100000, -90000 + w, -100000, 1)));
                grid.add(new Line(new Point(-100000, -90000 + w, 100000, 1), new Point(100000, -90000 + w, 100000, 1)));
                grid.add(new Line(new Point(-100000, -90000 + w, -100000, 1), new Point(-100000, -90000 + w, 100000, 1)));
                grid.add(new Line(new Point(100000, -90000 + w, -100000, 1), new Point(100000, -90000 + w, 100000, 1)));
            }
            grid.add(new Line(new Point(w, 10000, -100000, 1), new Point(w, -90000, -100000, 1)));
            grid.add(new Line(new Point(w, 10000, 100000, 1), new Point(w, -90000, 100000, 1)));
            grid.add(new Line(new Point(-100000, 10000, w, 1), new Point(-100000, -90000, w, 1)));
            grid.add(new Line(new Point(100000, 10000, w, 1), new Point(100000, -90000, w, 1)));

            grid.add(new Line(new Point(w, -90000, -100000, 1), new Point(w, -90000, 100000, 1)));
            grid.add(new Line(new Point(-100000, -90000, w, 1), new Point(100000, -90000, w, 1)));
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int[] pixels = ((DataBufferInt)canvas.getRaster().getDataBuffer()).getData();

        Graphics2D g2 = (Graphics2D)g;
        g2.translate(halfW, halfH);

        aircraft.calculateNewlightingScale(gravity.getX(), gravity.getY(), gravity.getZ());

        if(intro > 0)
            rotateAll(new double[] {Math.cos(intro * oneEightithPI), 0, -Math.sin(intro * oneEightithPI), 0,
                    0, 1,                    0, 0,
                    Math.sin(intro * oneEightithPI), 0, Math.cos(intro * oneEightithPI), 0, 
                    0, 0,                    0, 1}, true, true);

        translateAll(new double[] {1, 0, 0,     0, 
                0, 1, 0,     1, 
                0, 0, 1, 7.5 + thrust.getZ() * 0.2 + intro * 0.1, 
                0, 0, 0,     1}, true, false);

        ocean.draw(pixels);

        g2.setColor(LIGHT_BLUE);
        for(Line l : grid) {
            l.draw(g2);
        }

        speed = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2) + Math.pow(velocity.getZ(), 2));

        g2.setColor(Color.BLACK);
        g2.drawString("WASD to turn", -halfW + 5, - halfH + 17);
        g2.drawString("QE to spin", -halfW + 5, - halfH + 34);
        g2.drawString("ESC to exit", -halfW + 5, - halfH + 51);
        g2.drawString("FPS: " + fps, halfW - 65, - halfH + 17);

        if(intro == 0) {
            g2.setFont (new Font (Font.SANS_SERIF, Font.BOLD, 20));
            g2.setColor(LIGHT_GREEN);
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

        shapes.sort(new DistanceComparator());
        for(Shape s : shapes) {
            s.draw(pixels);
        }

        g.drawImage(canvas, -halfW, -halfH, this);

        translateAll(new double[] {1, 0, 0,     0, 
                0, 1, 0,     -1, 
                0, 0, 1, -7.5 - thrust.getZ() * 0.2 - intro * 0.1, 
                0, 0, 0,     1}, true, false);
                
        if(intro > 0) {
            rotateAll(new double[] {Math.cos(-intro * oneEightithPI), 0, -Math.sin(-intro * oneEightithPI), 0,
                    0, 1,                    0, 0,
                    Math.sin(-intro * oneEightithPI), 0, Math.cos(-intro * oneEightithPI), 0, 
                    0, 0,                    0, 1}, true, true);
            intro--;
        }

        if(!crash) {
            velocity.transform(new double[] {0.99, 0, 0,      gravity.getX(), 
                    0, 0.99, 0,      gravity.getY() + Math.abs(thrust.getZ() * 0.6533335), 
                    0, 0, 0.99,      thrust.getZ() + gravity.getZ() + Math.random() * 0.1 - 0.05, 
                    0, 0, 0,      1});
            translate(new double[] {1, 0, 0,      velocity.getX(), 
                    0, 1, 0,      velocity.getY(), 
                    0, 0, 1,      velocity.getZ(), 
                    0, 0, 0,      1});
                    //System.out.println(velocity.getY());
        } else {
            velocity.transform(new double[] {0, 0, 0, 0, 
                    0, 0, 0, 0, 
                    0, 0, 0, 0, 
                    0, 0, 0, 0});
            aircraft.decompose();
        }
    }

    public double getAltitude() {
        double altitude = 0.25 + Math.pow(altitudeReference2.getX(), 2) + Math.pow(altitudeReference2.getY(), 2) + Math.pow(altitudeReference2.getZ(), 2) - Math.pow(altitudeReference1.getX(), 2) - Math.pow(altitudeReference1.getY(), 2) - Math.pow(altitudeReference1.getZ(), 2);
        if(altitude < 20 || altitude > 100000) {
            thrust = new Point(0, 0, 0);
            crash = true;
        }
        return altitude;
    }

    public double getRotationScale() {
        return velocity.getZ() * -0.02;
    }

    public void translate(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        ((Jet)aircraft).transformMissiles(transformationMatrix, false, false);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
    }

    public void rotate(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        gravity.transform(transformationMatrix);
        aircraft.calculateNewlightingScale(gravity.getX(), gravity.getY(), gravity.getZ());
        ((Jet)aircraft).transformMissiles(transformationMatrix, false, true);
        velocity.transform(transformationMatrix);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
    }

    public void translateAll(double[] transformationMatrix, boolean transformMissileIfNotFired, boolean transformVelocity) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        gravity.transform(transformationMatrix);
        aircraft.transform(transformationMatrix);
        ((Jet)aircraft).transformMissiles(transformationMatrix, true, false);
        velocity.transform(transformationMatrix);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
    }
    
    public void rotateAll(double[] transformationMatrix, boolean transformMissileIfNotFired, boolean transformVelocity) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        gravity.transform(transformationMatrix);
        ((Jet)aircraft).rotate(transformationMatrix);
        ((Jet)aircraft).transformMissiles(transformationMatrix, true, true);
        velocity.transform(transformationMatrix);
        altitudeReference1.transform(transformationMatrix);
        altitudeReference2.transform(transformationMatrix);
    }

    public void updateThrust(int newT) {
        thrust = new Point(0, 0, -newT * 0.1);
    }

    public void fire() {
        //((Jet)aircraft).fire(0, 0, 0);
        ((Jet)aircraft).fire(velocity.getX(), velocity.getY(), velocity.getZ());
    }

    public void click() {
        //
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
