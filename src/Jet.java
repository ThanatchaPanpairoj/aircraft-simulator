import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*;

/**
 * Object representation of the plane with attributes
 * read from the aircraft data file.
 * 
 * @author Thanatcha Panpairoj
 * @version (a version number or a date)
 */
public class Jet extends Shape
{
    private ArrayList<Vertex> points;
    private ArrayList<Face> faces;
    private Missile missile1, missile2, missile3, missile4;
    private double x, y, z;
    private int decomposing;
    private int[] textureMap;
    
    public Jet(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.decomposing = 2;
        this.points = new ArrayList<Vertex>();
        this.faces = new ArrayList<Face>();

        try {
            File texFile = new File("aircraft data/texvectorphoto.png");
            BufferedImage texMapImg = javax.imageio.ImageIO.read(texFile);
            byte[] byteMap = ((DataBufferByte) texMapImg.getRaster().getDataBuffer()).getData();
            this.textureMap = new int[texMapImg.getWidth() *  texMapImg.getHeight()];
            //int pixLen = texMapImg.getAlphaRaster() == null ? 3 : 4;
            //int len = textureMap.length;
            //for (int bytePix = 0, intPix = 0; bytePix < len; intPix++) {
            //    int argb = pixLen == 3 ? -16777216 : ((int) byteMap[bytePix++] & 0xff) << 24;
            //    argb += ((int) byteMap[bytePix++] & 0xff) << 16;
            //    argb += ((int) byteMap[bytePix++] & 0xff) << 8;
            //    argb += (int) byteMap[bytePix++] & 0xff;
            //   this.textureMap[intPix] = argb;
            //}
            for (int row = 0; row < 1500; row++)
                for (int col = 0; col < 1500; col++)
                    this.textureMap[col * 1500 + row] = texMapImg.getRGB(col, row);
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'texveotorphoto.png'");                
        } catch(IOException ex) {
            System.out.println("Error reading file 'texvectorphoto.png'");                  
            ex.printStackTrace();
        }

        ArrayList<Double> st1 = new ArrayList<Double>();// might switch to multiplied int later
        ArrayList<Double> st2 = new ArrayList<Double>();
        points.add(new Vertex(x, y, z));

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("aircraft data/f16.obj")));
            String line = null;
            int space1, space2, space3, space4, space5, space6, space7, space8, space9;
            Vertex v0, v1, v2, v3, v4, v5, v6, v7, v8;
            for(int i = 0; (line = bufferedReader.readLine()) != null; i++) {
                //System.out.println(line);
                String type = line.substring(0, 2);
                if(type.equals("v ")) {
                    space1 = line.indexOf(' ', 3);
                    space2 = line.indexOf(' ', space1 + 1);
                    points.add(new Vertex(Double.parseDouble(line.substring(2, space1)), Double.parseDouble(line.substring(space1 + 1, space2)), Double.parseDouble(line.substring(space2 + 1))));
                } else if (type.equals("vt")) {
                    space1 = line.indexOf(' ', 3);
                    st1.add(Double.parseDouble(line.substring(3, space1)));
                    st2.add(Double.parseDouble(line.substring(space1 + 1)));
                } else if(type.equals("f ")) {
                    space1 = line.indexOf(' ', 3);
                    space2 = line.indexOf(' ', space1 + 1);
                    space3 = line.indexOf(' ', space2 + 1);
                    space4 = space3 == -1 ? -1 : line.indexOf(' ', space3 + 1);
                    space5 = space4 == -1 ? -1 : line.indexOf(' ', space4 + 1);
                    space6 = space5 == -1 ? -1 : line.indexOf(' ', space5 + 1);
                    space7 = space6 == -1 ? -1 : line.indexOf(' ', space6 + 1);
                    space8 = space7 == -1 ? -1 : line.indexOf(' ', space7 + 1);
                    space9 = space8 == -1 ? -1 : line.indexOf(' ', space8 + 1);
                    v0 = points.get(Integer.parseInt(line.substring(2, line.indexOf('/'))));
                    v1 = points.get(Integer.parseInt(line.substring(space1 + 1, line.indexOf('/', space1))));
                    v2 = points.get(Integer.parseInt(line.substring(space2 + 1, line.indexOf('/', space2))));
                    String v0st = line.substring(line.indexOf('/') + 1, line.lastIndexOf('/', space1));
                    String v1st = line.substring(line.indexOf('/', space1) + 1, line.lastIndexOf('/', space2));
                    String v2st = line.substring(line.indexOf('/', space2) + 1, space3 == -1 ? line.lastIndexOf('/') : line.lastIndexOf('/', space3));
                   if (!v0st.equals("")) {
                        int v0sti = Integer.parseInt(v0st) - 1;
                        int v1sti = Integer.parseInt(v1st) - 1;
                        int v2sti = Integer.parseInt(v2st) - 1;
                        //System.out.println(v0sti + " " + v1sti + " " + v2sti);
                        v0.setST(st1.get(v0sti), st2.get(v0sti));
                        v1.setST(st1.get(v1sti), st2.get(v1sti));
                        v2.setST(st1.get(v2sti), st2.get(v2sti));
                    }
                    faces.add(new Face(v0, v1, v2));
                    if(space3 != -1) {
                        v3 = points.get(Integer.parseInt(line.substring(space3 + 1, line.indexOf('/', space3))));
                        String v3st = line.substring(line.indexOf('/', space3) + 1, space4 == -1 ? line.lastIndexOf('/') : line.lastIndexOf('/', space4));
                        if (!v3st.equals("")) {
                            int v3sti = Integer.parseInt(v3st) - 1;
                            v3.setST(st1.get(v3sti), st2.get(v3sti));  
                        } 
                        faces.add(new Face(v0, v2, v3));
                        if(space4 != -1) {
                            v4 = points.get(Integer.parseInt(line.substring(space4 + 1, line.indexOf('/', space4))));
                            String v4st = line.substring(line.indexOf('/', space4) + 1, space5 == -1 ? line.lastIndexOf('/') : line.lastIndexOf('/', space5));
                            if (!v4st.equals("")) {
                                int v4sti = Integer.parseInt(v4st) - 1;
                                v4.setST(st1.get(v4sti), st2.get(v4sti));
                            } 
                            faces.add(new Face(v0, v3, v4));
                            if(space7 != -1) {
                                v5 = points.get(Integer.parseInt(line.substring(space5 + 1, line.indexOf('/', space5))));
                                v6 = points.get(Integer.parseInt(line.substring(space6 + 1, line.indexOf('/', space6))));
                                v7 = points.get(Integer.parseInt(line.substring(space7 + 1, line.indexOf('/', space7))));
                                String v5st = line.substring(line.indexOf('/', space5) + 1, space6 == -1 ? line.lastIndexOf('/') : line.lastIndexOf('/', space6)); 
                                String v6st = line.substring(line.indexOf('/', space6) + 1, space7 == -1 ? line.lastIndexOf('/') : line.lastIndexOf('/', space7)); 
                                String v7st = line.substring(line.indexOf('/', space7) + 1, space8 == -1 ? line.lastIndexOf('/') : line.lastIndexOf('/', space8));
                                if (!v5st.equals("")) {
                                    int v5sti = Integer.parseInt(v5st) - 1;
                                    int v6sti = Integer.parseInt(v6st) - 1;
                                    int v7sti = Integer.parseInt(v7st) - 1;
                                    v5.setST(st1.get(v5sti), st2.get(v5sti));  
                                    v6.setST(st1.get(v6sti), st2.get(v6sti));  
                                    v7.setST(st1.get(v7sti), st2.get(v7sti));
                                }
                                faces.add(new Face(v0, v4, v5));
                                faces.add(new Face(v0, v5, v6));
                                faces.add(new Face(v0, v6, v7));
                                if(space8 != -1) {
                                    v8 = points.get(Integer.parseInt(line.substring(space8 + 1, line.indexOf('/', space8))));
                                    String v8st = line.substring(line.indexOf('/', space8) + 1, line.lastIndexOf('/'));
                                    int v8sti = Integer.parseInt(v8st) - 1;
                                    v8.setST(st1.get(v8sti), st2.get(v8sti));
                                    faces.add(new Face(v0, v7, v8));
                                }
                            }
                        }
                    }
                }
            }
            faces.remove(0);
            faces.remove(0);
            points.remove(0);
            points.remove(0);
            points.remove(0);
            points.remove(0);
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file 'f16.obj'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file 'f16.obj'");                  
            ex.printStackTrace();
        }
        //System.out.println(java.util.Arrays.toString(textureMap));
        rotate(new double[] {Math.cos(Math.PI), Math.sin(Math.PI), 0, 0,
                -Math.sin(Math.PI), Math.cos(Math.PI), 0, 0, 
                0, 0,                    1, 0,        
                0, 0,                    0, 1});
        rotate(new double[] {Math.cos(Math.PI * 0.5), 0, -Math.sin(Math.PI * 0.5), 0,
                0, 1,                    0, 0,
                Math.sin(Math.PI * 0.5), 0, Math.cos(Math.PI * 0.5), 0, 
                0, 0,                    0, 2});

    	missile1 = new Missile();
        for(int i = 975; i < 1023; i++) {
            missile1.add(points.get(i));
        }
        missile3 = new Missile();
        for(int i = 887; i < 975; i++) {
            missile3.add(points.get(i));
        }
        missile2 = new Missile();
        for(int i = 839; i < 887; i++) {
            missile2.add(points.get(i));
        }
        missile4 = new Missile();
        for(int i = 751; i < 839; i++) {
            missile4.add(points.get(i));
        }

	for(int i = 0; i < 272; i++) {
            points.remove(points.get(751));
        }
        //for(int i = 1007; i < 1095; i++) {
        //    faces.get(i).setOrange(true);
        //} 
    }

    public void draw(int[] pixels, double[] zBuffer, Graphics2D g2) {
        boolean draw = true;
        for(Vertex p : points) {
            if(p.getZ() > 0) {
                draw = true;
                break;
            }
        }

       if(draw) {   
            for(Face t : faces) {
                t.draw(pixels, zBuffer, g2, textureMap);
            }

            if(missile1.getFired()) {
                missile1.fly(pixels);
                if(missile2.getFired()) {
                    missile2.fly(pixels);
                    if(missile3.getFired()) {
                        missile3.fly(pixels);
                        if(missile4.getFired()) {
                            missile4.fly(pixels);
                        }
                    }
                }
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
        for(Vertex p : points) {
            p.transform(transformationMatrix);
        }

        double newX = x * transformationMatrix[0] + y * transformationMatrix[1] + z * transformationMatrix[2] + transformationMatrix[3];
        double newY = x * transformationMatrix[4] + y * transformationMatrix[5] + z * transformationMatrix[6] + transformationMatrix[7];
        double newZ = x * transformationMatrix[8] + y * transformationMatrix[9] + z * transformationMatrix[10] + transformationMatrix[11];
        x = newX;
        y = newY;
        z = newZ;
    }
    
    public void rotate(double[] transformationMatrix) {
        for(Vertex p : points) {
            p.transform(transformationMatrix);
	    p.transformNormal(transformationMatrix);
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

    public void transformMissiles(double[] transformationMatrix, boolean zoom, boolean transformVelocity) {
        if(zoom || missile1.getFired())
            missile1.transform(transformationMatrix, transformVelocity);
            if(zoom || missile2.getFired())
                missile2.transform(transformationMatrix, transformVelocity);
        	if(zoom || missile3.getFired())
            	    missile3.transform(transformationMatrix, transformVelocity);
        		if(zoom || missile4.getFired())
            		    missile4.transform(transformationMatrix, transformVelocity);
    }

    public void calculateNewlightingScale(double gravityX, double gravityY, double gravityZ) {
	for (Vertex v : points) {
	    v.calculateNewlightingScale(gravityX, gravityY, gravityZ);
}
	missile1.calculateNewlightingScale(gravityX, gravityY, gravityZ);
	missile2.calculateNewlightingScale(gravityX, gravityY, gravityZ);
	missile3.calculateNewlightingScale(gravityX, gravityY, gravityZ);
	missile4.calculateNewlightingScale(gravityX, gravityY, gravityZ);
    }

    public void fire(double jetX, double jetY, double jetZ) {
        if(!missile1.getFired()) {
            missile1.fire(jetX, jetY, jetZ);
        } else if(!missile2.getFired()) {
            missile2.fire(jetX, jetY, jetZ);
        } else if(!missile3.getFired()) {
            missile3.fire(jetX, jetY, jetZ);
        } else if(!missile4.getFired()) {
            missile4.fire(jetX, jetY, jetZ);
        } 
    }

    public void decompose() {
        if(decomposing-- > 0)
            for(Vertex p : points)
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
