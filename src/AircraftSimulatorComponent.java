import java.lang.Double;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
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
    private ArrayList<Shape> shapes;
    private Shape aircraft;
    private ArrayList<Line> grid;

    public AircraftSimulatorComponent(int width, int height) {
        this.width = width;
        this.height = height;
        intro = 100;

        shapes = new ArrayList<Shape>();
        shapes.add(aircraft = new Jet(0, 0, 0));
        
        grid = new ArrayList<Line>();
        for(int w = -1000000; w <= 1000000; w += 4000) {
            grid.add(new Line(new Point(w, 8000, -1000000, 1), new Point(w, 8000, 1000000, 1)));
            grid.add(new Line(new Point(-1000000, 8000, w, 1), new Point(1000000, 8000, w, 1)));
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

        g2.setColor(Color.GRAY);
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

        g2.setColor(Color.BLACK);

        g2.drawString("WASD to turn", -width / 2 + 5, - height / 2 + 17);
        g2.drawString("QE to spin", -width / 2 + 5, - height / 2 + 34);
        g2.drawString("ESC to exit", -width / 2 + 5, - height / 2 + 51);
        g2.drawString("FPS: " + fps, width / 2 - 50, - height / 2 + 17);
    }

    public void transform(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }
    }

    public void transformAll(double[] transformationMatrix) {
        for(Line l : grid) {
            l.transform(transformationMatrix);
        }

        for(Shape s : shapes) {
            s.transform(transformationMatrix);
        }
    }

    public void click() {
        //
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }
}
