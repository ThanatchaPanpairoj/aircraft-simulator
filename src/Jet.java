import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.io.*;

/**
 * Write a description of class Shape here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Jet extends Shape
{
    private ArrayList<Point> points;
    private ArrayList<Face> faces;
    private double x, y, z;
    private int decomposing;

    public Jet(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.decomposing = 2;
        this.points = new ArrayList<Point>();
        this.faces = new ArrayList<Face>();
        points.add(new Point(x, y, z, 1));
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("aircraft data/f16.obj")));
            String line = null;
            for(int i = 0; (line = bufferedReader.readLine()) != null; i++) {
                String type = line.substring(0, 2);
                if(type.equals("v ")) {
                    int space1 = line.indexOf(' ', 3);
                    int space2 = line.indexOf(' ', space1 + 1);
                    points.add(new Point(Double.parseDouble(line.substring(2, space1)), Double.parseDouble(line.substring(space1 + 1, space2)), Double.parseDouble(line.substring(space2 + 1))));
                } else if(type.equals("f ")) {
                    int space1 = line.indexOf(' ', 3);
                    int space2 = line.indexOf(' ', space1 + 1);
                    int space3 = line.indexOf(' ', space2 + 1);
                    int space4 = line.indexOf(' ', space3 + 1);
                    int space5 = line.indexOf(' ', space4 + 1);
                    int space6 = line.indexOf(' ', space5 + 1);
                    int space7 = line.indexOf(' ', space6 + 1);
                    int space8 = line.indexOf(' ', space7 + 1);
                    int space9 = line.indexOf(' ', space8 + 1);
                    if(space3 == -1)
                        faces.add(new Face(points.get((Integer.parseInt(line.substring(2, line.indexOf('/'))))), 
                                points.get(Integer.parseInt(line.substring(space1 + 1, line.indexOf('/', space1)))),
                                points.get(Integer.parseInt(line.substring(space2 + 1, line.indexOf('/', space2))))));
                    else if(space4 == -1)
                        faces.add(new Face(points.get((Integer.parseInt(line.substring(2, line.indexOf('/'))))), 
                                points.get(Integer.parseInt(line.substring(space1 + 1, line.indexOf('/', space1)))),
                                points.get(Integer.parseInt(line.substring(space2 + 1, line.indexOf('/', space2)))),
                                points.get(Integer.parseInt(line.substring(space3 + 1, line.indexOf('/', space3))))));
                    else if(space5 == -1)
                        faces.add(new Face(points.get((Integer.parseInt(line.substring(2, line.indexOf('/'))))), 
                                points.get(Integer.parseInt(line.substring(space1 + 1, line.indexOf('/', space1)))),
                                points.get(Integer.parseInt(line.substring(space2 + 1, line.indexOf('/', space2)))),
                                points.get(Integer.parseInt(line.substring(space3 + 1, line.indexOf('/', space3)))),
                                points.get(Integer.parseInt(line.substring(space4 + 1, line.indexOf('/', space4))))));
                    else if(space8 == -1)
                        faces.add(new Face(points.get((Integer.parseInt(line.substring(2, line.indexOf('/'))))), 
                                points.get(Integer.parseInt(line.substring(space1 + 1, line.indexOf('/', space1)))),
                                points.get(Integer.parseInt(line.substring(space2 + 1, line.indexOf('/', space2)))),
                                points.get(Integer.parseInt(line.substring(space3 + 1, line.indexOf('/', space3)))),
                                points.get(Integer.parseInt(line.substring(space4 + 1, line.indexOf('/', space4)))),
                                points.get(Integer.parseInt(line.substring(space5 + 1, line.indexOf('/', space5)))),
                                points.get(Integer.parseInt(line.substring(space6 + 1, line.indexOf('/', space6)))),
                                points.get(Integer.parseInt(line.substring(space7 + 1, line.indexOf('/', space7))))));
                    else if(space9 == -1)
                        faces.add(new Face(points.get((Integer.parseInt(line.substring(2, line.indexOf('/'))))), 
                                points.get(Integer.parseInt(line.substring(space1 + 1, line.indexOf('/', space1)))),
                                points.get(Integer.parseInt(line.substring(space2 + 1, line.indexOf('/', space2)))),
                                points.get(Integer.parseInt(line.substring(space3 + 1, line.indexOf('/', space3)))),
                                points.get(Integer.parseInt(line.substring(space4 + 1, line.indexOf('/', space4)))),
                                points.get(Integer.parseInt(line.substring(space5 + 1, line.indexOf('/', space5)))),
                                points.get(Integer.parseInt(line.substring(space6 + 1, line.indexOf('/', space6)))),
                                points.get(Integer.parseInt(line.substring(space7 + 1, line.indexOf('/', space7)))),
                                points.get(Integer.parseInt(line.substring(space8 + 1, line.indexOf('/', space8))))));
                }
            }
            faces.remove(0);
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'f16.obj'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file 'f16.obj'");                  
            ex.printStackTrace();
        }

        transform(new double[] {Math.cos(Math.PI), Math.sin(Math.PI), 0, 0,
                -Math.sin(Math.PI), Math.cos(Math.PI), 0, 0, 
                0, 0,                    1, 0,        
                0, 0,                    0, 1});
        transform(new double[] {Math.cos(Math.PI * 0.5), 0, -Math.sin(Math.PI * 0.5), 0,
                0, 1,                    0, 0,
                Math.sin(Math.PI * 0.5), 0, Math.cos(Math.PI * 0.5), 0, 
                0, 0,                    0, 2});
        for(int i = 1007; i < 1095; i++) {
            faces.get(i).setOrange(true);
        }
    }

    public void draw(Graphics2D g2) {
        boolean draw = false;
        for(Point p : points) {
            if(p.getZ() > 0) {
                draw = true;
                break;
            }
        }

        if(draw) {   
            faces.sort(new FaceDistanceComparator());
            for(Face t : faces) {
                t.draw(g2);
            }

            //             for(Point p : points) {
            //                 g2.drawString((int)p.getX() + "," + (int)p.getY() + "," + (int)p.getZ(), (int)p.get2Dx(), (int)p.get2Dy());
            //             }
            //             for(int i = 0; i < 8; i++) {
            //                 g2.drawString("" + i, (int)points[i].get2Dx(), (int)points[i].get2Dy());
            //             }
            //g2.drawString("" + Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)), (int)(new Point(x, y, z, 1).get2Dx()), (int)(new Point(x, y, z, 1).get2Dy()));
        }
    }

    public void transform(double[] transformationMatrix) {
        for(Point p : points) {
            p.transform(transformationMatrix);
        }

        for(Face f : faces) {
            f.transform(transformationMatrix);
        }

        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
    }

    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {
        for(Face f : faces) {
            f.calculateNewlightingScale(gravityX, gravityY, gravityZ);
        }
    }

    public void decompose() {
        if(decomposing-- > 0)
            for(Point p : points)
                p.transform(new double[] {1, 0, 0,      2 * Math.random() - 1, 
                        0, 1, 0,      2 * Math.random() - 1, 
                        0, 0, 1,      2 * Math.random() - 1, 
                        0, 0, 0,      1});
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
