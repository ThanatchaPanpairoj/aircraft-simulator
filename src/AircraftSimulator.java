import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Toolkit;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.swing.JSlider;

/**
 * This is the GUITemplate class. This includes the JFrame, listeners, buttons, and the GUITemplateComponent which includes all the objects. The buttons and the components are all added to a panel, which is added to the frame.
 * The main method starts the comp and sets everything up. 
 *
 * @author (Thanatcha Panpairoj)
 * @version (7/25/15)
 */

public class AircraftSimulator extends JFrame
{
    private long startTime;
    private int frame;
    private float mouseX, mouseY, zRotation, yRotation, xRotation;
    private boolean xUp, xDown, yLeft, yRight, zLeft, zRight;

    public static void main(String[] args) throws Exception {
        AircraftSimulator r = new AircraftSimulator();
    }

    public AircraftSimulator() throws Exception {
        super();

        startTime = System.currentTimeMillis();

        xUp = false;
        xDown = false;
        yLeft = false;
        yRight = false;
        zLeft = false;
        zRight = false;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int)dim.getWidth(), (int)dim.getHeight() - 40);
        this.setTitle("Display");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        final int width = this.getWidth();
        final int height = this.getHeight();

        zRotation = 0;
        xRotation = 0;
        yRotation = 0;

        //System.out.println("" + width + ", " + height);
        //         frame.setUndecorated(true);
        //         frame.setShape(new Ellipse2D.float(0,0, 800, 800));//circle frame?

        JPanel panel = new JPanel();
        panel.setDoubleBuffered(true);
        JSlider slider = new JSlider(JSlider.VERTICAL, 0, 10, 5);
        slider.setBounds(width - 100, height / 3, 100, height / 3);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setOpaque(false);
        slider.setVisible(true);

        AircraftSimulatorComponent comp = new AircraftSimulatorComponent(width, height);

        class TimeListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                comp.requestFocus();
                mouseX = (float)(MouseInfo.getPointerInfo().getLocation().getX() - getLocation().getX()) - 3;
                mouseY = (float)(MouseInfo.getPointerInfo().getLocation().getY() - getLocation().getY()) - 25;

                if(zLeft && !zRight) {
                    float angle = -0.03f * comp.getRotationScale();
                    float sineA = (float)Math.sin(angle);
                    float cosineA = (float)Math.cos(angle);
                    comp.rotate(new float[] {cosineA, sineA, 0, 0,
                            -sineA, cosineA, 0, 0, 
                            0, 0,                    1, 0,        
                            0, 0,                    0, 1});
                    zRotation += angle;
                } else if(zRight && !zLeft) {
                    float angle = 0.03f * comp.getRotationScale();
                    float sineA = (float)Math.sin(angle);
                    float cosineA = (float)Math.cos(angle);
                    comp.rotate(new float[] {cosineA, sineA, 0, 0,
                            -sineA, cosineA, 0, 0, 
                            0, 0,                    1, 0,        
                            0, 0,                    0, 1});
                    zRotation += angle;
                }

                if(xDown && !xUp) {
                    float angle = -0.02f * comp.getRotationScale();
                    float sineA = (float)Math.sin(angle);
                    float cosineA = (float)Math.cos(angle);
                    comp.rotate(new float[] {1,                     0,                    0, 0, 
                            0,  cosineA, sineA, 0, 
                            0, -sineA, cosineA, 0, 
                            0,                     0,                    0, 1});
                    xRotation += angle;
                } else if(xUp && !xDown) {
                    float angle = 0.02f * comp.getRotationScale();
                    float sineA = (float)Math.sin(angle);
                    float cosineA = (float)Math.cos(angle);
                    comp.rotate(new float[] {1,                     0,                    0, 0, 
                            0,  cosineA, sineA, 0, 
                            0, -sineA, cosineA, 0, 
                            0,                     0,                    0, 1});
                    xRotation += angle;
                } 

                if(yLeft && !yRight) {
                    float angle = -0.002f * comp.getRotationScale();
                    float sineA = (float)Math.sin(angle);
                    float cosineA = (float)Math.cos(angle);
                    comp.rotate(new float[] {cosineA, 0, -sineA, 0,
                            0, 1,                    0, 0,
                            sineA, 0, cosineA, 0, 
                            0, 0,                    0, 1});
                    yRotation += angle;
                } else if(yRight && !yLeft) {
                    float angle = 0.002f * comp.getRotationScale();
                    float sineA = (float)Math.sin(angle);
                    float cosineA = (float)Math.cos(angle);
                    comp.rotate(new float[] {cosineA, 0, -sineA, 0,
                            0, 1,                    0, 0,
                            sineA, 0, cosineA, 0, 
                            0, 0,                    0, 1});
                    yRotation += angle;
                }

                comp.repaint();
                frame++;
                if(System.currentTimeMillis() - startTime >= 1000) {
                    startTime += 1000;
                    comp.updateFPS(frame);
                    frame = 0;
                }
            }
        }

        class KeyboardListener implements KeyListener {
            /**
             * Updates which keys are currently pressed.
             * 
             * @param  e  key pressed on the keyboard
             * @return    void
             */
            public void keyPressed(KeyEvent e)
            {
                int k = e.getKeyCode();
                if(k ==  KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                } else if(k == KeyEvent.VK_A) {
                    if(!yRight) {
                        if(!yLeft) {
                            float angle = 0.004f * comp.getRotationScale();
                            float sineA = (float)Math.sin(angle);
                            float cosineA = (float)Math.cos(angle);
                            comp.rotateAircraft(new float[] {cosineA, 0, -sineA, 0,
                                    0, 1,                    0, 0,
                                    sineA, 0, cosineA, 0, 
                                    0, 0,                    0, 1});
                        }
                        yLeft = true;
                    }
                } else if (k == KeyEvent.VK_D) {
                    if(!yLeft) {
                        if(!yRight) {
                            float angle = -0.004f * comp.getRotationScale();
                            float sineA = (float)Math.sin(angle);
                            float cosineA = (float)Math.cos(angle);
                            comp.rotateAircraft(new float[] {cosineA, 0, -sineA, 0,
                                    0, 1,                    0, 0,
                                    sineA, 0, cosineA, 0, 
                                    0, 0,                    0, 1});
                        }
                        yRight = true;
                    }
                } else if (k == KeyEvent.VK_W) {
                    if(!xUp)
                        xDown = true;
                } else if (k == KeyEvent.VK_S) {
                    if(!xDown)
                        xUp = true;
                } else if (k == KeyEvent.VK_Q) {
                    if(!zRight) {
                        if(!zLeft) {
                            float angle = 0.06f * comp.getRotationScale();
                            float sineA = (float)Math.sin(angle);
                            float cosineA = (float)Math.cos(angle);
                            comp.rotateAircraft(new float[] {cosineA, sineA, 0, 0,
                                    -sineA, cosineA, 0, 0, 
                                    0, 0,                    1, 0,        
                                    0, 0,                    0, 1});
                        }
                        zLeft = true;
                    }
                } else if (k == KeyEvent.VK_E) {
                    if(!zLeft) {
                        if(!zRight) {
                            float angle = -0.06f * comp.getRotationScale();
                            float sineA = (float)Math.sin(angle);
                            float cosineA = (float)Math.cos(angle);
                            comp.rotateAircraft(new float[] {cosineA, sineA, 0, 0,
                                    -sineA, cosineA, 0, 0, 
                                    0, 0,                    1, 0,        
                                    0, 0,                    0, 1});
                        }
                        zRight = true;
                    }
                } 
            }

            /**
             * Updates when a key is released.
             * 
             * @param  e  key released from the keyboard
             * @return    void
             */
            public void keyReleased(KeyEvent e) {
                int k = e.getKeyCode();
                if(k == KeyEvent.VK_A) {
                    if(yLeft) {
                        float angle = -0.004f * comp.getRotationScale();
                        float sineA = (float)Math.sin(angle);
                        float cosineA = (float)Math.cos(angle);
                        comp.rotateAircraft(new float[] {cosineA, 0, -sineA, 0,
                                0, 1,                    0, 0,
                                sineA, 0, cosineA, 0, 
                                0, 0,                    0, 1});
                    }
                    yLeft = false;
                } else if (k == KeyEvent.VK_D) {
                    if(yRight) {
                        float angle = 0.004f * comp.getRotationScale();
                        float sineA = (float)Math.sin(angle);
                        float cosineA = (float)Math.cos(angle);
                        comp.rotateAircraft(new float[] {cosineA, 0, -sineA, 0,
                                0, 1,                    0, 0,
                                sineA, 0, cosineA, 0, 
                                0, 0,                    0, 1});
                    }
                    yRight = false;
                } else if (k == KeyEvent.VK_W) {
                    xDown = false;
                } else if (k == KeyEvent.VK_S) {
                    xUp = false;
                } else if (k == KeyEvent.VK_Q) {
                    if(zLeft) {
                        float angle = -0.06f * comp.getRotationScale();
                        float sineA = (float)Math.sin(angle);
                        float cosineA = (float)Math.cos(angle);
                        comp.rotateAircraft(new float[] {cosineA, sineA, 0, 0,
                                -sineA, cosineA, 0, 0, 
                                0, 0,                    1, 0,        
                                0, 0,                    0, 1});
                    }
                    zLeft = false;
                } else if (k == KeyEvent.VK_E) {
                    if(zRight) {
                        float angle = 0.06f * comp.getRotationScale();
                        float sineA = (float)Math.sin(angle);
                        float cosineA = (float)Math.cos(angle);
                        comp.rotateAircraft(new float[] {cosineA, sineA, 0, 0,
                                -sineA, cosineA, 0, 0, 
                                0, 0,                    1, 0,        
                                0, 0,                    0, 1});
                    }
                    zRight = false;
                } 
            }

            /**
             * Updates when a key is typed.
             * 
             * @param  e  key typed on the keyboard
             * @return    void
             */
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
            }
        }

        class MousePressListener implements MouseListener {
            /**
             * Updates when the mouse button is pressed.
             * 
             * @param  event  mouse button press
             * @return        void
             */
            public void mousePressed(MouseEvent event)
            {

            }

            /**
             * Updates when the mouse button is released.
             * 
             * @param  event  mouse button is released
             * @return        void
             */
            public void mouseReleased(MouseEvent event) {
                comp.click();
            }

            public void mouseClicked(MouseEvent event) {}

            public void mouseEntered(MouseEvent event) {}

            public void mouseExited(MouseEvent event) {}
        }

        class ScrollListener implements MouseWheelListener {
            public void mouseWheelMoved(MouseWheelEvent e) {

            }
        }

        class SliderListener implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                comp.updateThrust(((JSlider)e.getSource()).getValue());
            }
        }

        slider.addChangeListener(new SliderListener());
        comp.setPreferredSize(new Dimension(width, height));
        comp.addKeyListener(new KeyboardListener());
        comp.addMouseListener(new MousePressListener());
        comp.addMouseWheelListener(new ScrollListener());
        comp.setBounds(0, 0, width, height);
        comp.setFocusable(true);
        comp.setVisible(true);

        Timer t = new Timer(0, new TimeListener());
        t.start();

        panel.setLayout(null);

        panel.add(slider);
        panel.add(comp);
        this.add(panel);

        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setVisible(true);

        setResizable(false);
    }
}
