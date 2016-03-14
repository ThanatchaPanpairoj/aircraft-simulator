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
    private double speed;
    private Color LIGHT_BLUE = new Color(153, 204, 255);
    private Point gravity, thrust, velocity;
    private Ocean ocean;
    private Shape aircraft;
    private ArrayList<Line> grid;
    private ArrayList<Shape> shapes;

    public AircraftSimulatorComponent(int width, int height) {
        this.width = width;
        this.height = height;
        this.halfW = width / 2;
        this.halfH = height / 2;
        sixthW = width / 6;
        fourthH = height / 4;
        intro = 100;
        speed = 0;

        gravity = new Point(0, -1, 0);
        thrust = new Point(0, 0, -5);
        velocity = new Point(0, 0, -40);

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
        g2.translate(width / 2, height / 2);

        if(intro > 0)
            transformAll(new double[] {Math.cos(intro * Math.PI / 90), 0, -Math.sin(intro * Math.PI / 90), 0,
                    0, 1,                    0, 0,
                    Math.sin(intro * Math.PI / 90), 0, Math.cos(intro * Math.PI / 90), 0, 
                    0, 0,                    0, 1});

        transformAll(new double[] {1, 0, 0,     0, 
                0, 1, 0,     1, 
                0, 0, 1, 7.5 + intro / 10.0, 
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

        transformAll(new double[] {1, 0, 0,     -0, 
                0, 1, 0,     -1, 
                0, 0, 1, -7.5 - intro / 10.0, 
                0, 0, 0,     1});

        if(intro > 0) {
            transformAll(new double[] {Math.cos(-intro * Math.PI / 90), 0, -Math.sin(-intro * Math.PI / 90), 0,
                    0, 1,                    0, 0,
                    Math.sin(-intro * Math.PI / 90), 0, Math.cos(-intro * Math.PI / 90), 0, 
                    0, 0,                    0, 1});
            intro--;
        }

        velocity.transform(new double[] {0.9, 0, 0,      thrust.getX() + gravity.getX(), 
                0, 0.9, 0,      thrust.getY() + gravity.getY() + speed * 0.02, 
                0, 0, 0.9,      thrust.getZ() + gravity.getZ() + Math.random() * 0.1 - 0.5, 
                0, 0, 0,      1});

        translate(new double[] {1, 0, 0,      velocity.getX(), 
                0, 1, 0,      velocity.getY(), 
                0, 0, 1,      velocity.getZ(), 
                0, 0, 0,      1});

        speed = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2) + Math.pow(velocity.getZ(), 2));

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

    public double getAltitude() {
        Point pA = ocean.getP1();
        Point pB = ocean.getP2();
        Point pC = ocean.getP3();
        double Ax = pA.getX();
        double Bx = pB.getX();
        double Cx = pC.getX();
        double Ay = pA.getY();
        double By = pB.getY();
        double Cy = pC.getY();
        double Az = pA.getZ();
        double Bz = pB.getZ();
        double Cz = pC.getZ();
        double a = (By - Ay) * (Cz - Az) - (Cy - Ay) * (Bz - Az);
        double b = (Bz - Az) * (Cx - Ax) - (Cz - Az) * (Bx - Ax);
        double c = (Bx - Ax) * (Cy - Ay) - (Cx - Ax) * (By - Ay);
        double d = -(a * Ax + b * Ay + c * Az);
        double distance = (Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2));
        double altitude = Math.sqrt(Math.pow(a * d, 2) + Math.pow(b * d, 2) + Math.pow(c * d, 2)) / distance;
        if(altitude < 20)
            System.exit(0);
        return altitude;
    }

    public void translate(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
    }

    public void rotate(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        gravity.transform(transformationMatrix);
        velocity.transform(transformationMatrix);
    }

    public void transformAll(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
        ocean.transform(transformationMatrix);
        for(Shape s : shapes) {
            s.transform(transformationMatrix);
        }
    }
    
    public void updateThrust(int newT) {
        thrust = new Point(0, 0, -newT);
    }

    public void click() {
        //
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
