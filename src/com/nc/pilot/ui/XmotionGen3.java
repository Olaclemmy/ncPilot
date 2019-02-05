package com.nc.pilot.ui;

import com.nc.pilot.lib.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Callable;

/**
 * This program demonstrates how to draw lines using Graphics2D object.
 * @author www.codejava.net
 *
 */
public class XmotionGen3 extends JFrame {

    ArrayList<ViewerEntity> ViewerEntityStack = new ArrayList();
    private SerialIO serial;
    Timer repaint_timer = new Timer();
    MotionController motion_controller;
    UIWidgets ui_widgets;
    public XmotionGen3() {

        super("ncPilot Xmotion Gen3");
        setSize(800, 600);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        serial = new SerialIO();
        serial.initialize();
        motion_controller = new MotionController(serial);
        //motion_controller.InitMotionController();
        ui_widgets = new UIWidgets();
        Layout_UI();
        GcodeViewerPanel panel = new GcodeViewerPanel();
        add(panel);
        repaint_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                serial.write(MotionController.SatusReport);
                repaint();
            }
        }, 0, 200);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
                ui_widgets.ClickPressStack(e.getX(), e.getY());
                repaint();
            }
            public void mouseReleased(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
                ui_widgets.ClickReleaseStack(e.getX(), e.getY());
                repaint();
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {// provides empty implementation of all
            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
                GlobalData.MousePositionX = e.getX();
                GlobalData.MousePositionY = e.getY();
            }
        });
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                float old_zoom = GlobalData.ViewerZoom;
                if (e.getWheelRotation() < 0) {
                    GlobalData.ViewerZoom *= 1.1;
                    //System.out.println("ViewerZoom: " + GlobalData.ViewerZoom);
                    if (GlobalData.ViewerZoom > GlobalData.MaxViewerZoom)
                    {
                        GlobalData.ViewerZoom = GlobalData.MaxViewerZoom;
                    }
                } else {
                    GlobalData.ViewerZoom *= 0.9;
                    //System.out.println("ViewerZoom: " + GlobalData.ViewerZoom);
                    if (GlobalData.ViewerZoom < GlobalData.MinViewerZoom)
                    {
                        GlobalData.ViewerZoom = GlobalData.MinViewerZoom;
                    }
                }
                float scalechange = GlobalData.ViewerZoom - old_zoom;
                //printf("Scale change: %0.4f\n", scalechange);
                float pan_x = (GlobalData.MousePositionX_MCS * scalechange) * -1;
                float pan_y = (GlobalData.MousePositionY_MCS * scalechange);
                //System.out.println("Pan_x: " + pan_x + " Pan_y: " + pan_y);
                GlobalData.ViewerPan[0] += pan_x;
                GlobalData.ViewerPan[1] += pan_y;
                repaint();
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent ke) {
                        switch (ke.getID()) {
                            case KeyEvent.KEY_PRESSED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    if (GlobalData.UpArrowKeyState == false)
                                    {
                                        GlobalData.UpArrowKeyState = true;
                                        MotionController.JogY_Plus();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    if (GlobalData.DownArrowKeyState == false)
                                    {
                                        GlobalData.DownArrowKeyState = true;
                                        MotionController.JogY_Minus();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                                    if (GlobalData.RightArrowKeyState == false)
                                    {
                                        GlobalData.RightArrowKeyState = true;
                                        MotionController.JogX_Plus();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                    if (GlobalData.LeftArrowKeyState == false)
                                    {
                                        GlobalData.LeftArrowKeyState = true;
                                        MotionController.JogX_Minus();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                                    if (GlobalData.RightArrowKeyState == false)
                                    {
                                        GlobalData.PageUpKeyState = true;
                                        MotionController.JogZ_Plus();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                                    if (GlobalData.LeftArrowKeyState == false)
                                    {
                                        GlobalData.PageDownKeyState = true;
                                        MotionController.JogZ_Minus();
                                    }
                                }
                                //repaint();
                                break;

                            case KeyEvent.KEY_RELEASED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    GlobalData.UpArrowKeyState = false;
                                    MotionController.EndJog();

                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    GlobalData.DownArrowKeyState = false;
                                    MotionController.EndJog();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                    GlobalData.LeftArrowKeyState = false;
                                    MotionController.EndJog();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                                    GlobalData.RightArrowKeyState = false;
                                    MotionController.EndJog();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                                    GlobalData.PageUpKeyState = false;
                                    MotionController.EndJog();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                                    GlobalData.PageDownKeyState = false;
                                    MotionController.EndJog();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                                    //Load up a bunch of lines onto viewer entity stack and repaint
                                    ViewerEntity entity = new ViewerEntity();
                                    entity.setLine(new float[]{0, 0}, new float[]{10, 0});
                                    ViewerEntityStack.add(entity);

                                    entity = new ViewerEntity();
                                    entity.setLine(new float[]{10, 0}, new float[]{10, 10});
                                    ViewerEntityStack.add(entity);
                                    repaint();

                                    //serial.write("$#\n");
                                    //MotionController.FeedHold();
                                    MotionController.WriteBuffer("G0X10Y10\n");
                                }

                                break;
                        }
                        return false;
                    }
                });
    }
    private void Layout_UI()
    {
        ui_widgets.AddMomentaryButton("Open", "bottom-right", 80, 60, 10, 10, new Runnable() {
            @Override
            public void run() {
                System.out.println("Clicked on Open!");
            }
        });
        ui_widgets.AddMomentaryButton("Abort", "bottom-right", 80, 60, 100, 10, new Runnable() {
            @Override
            public void run() {
                System.out.println("Clicked on Abort!");
            }
        });
        ui_widgets.AddMomentaryButton("Hold", "bottom-right", 80, 60, 190, 10, new Runnable() {
            @Override
            public void run() {
                System.out.println("Clicked on Hold!");
                MotionController.FeedHold();
            }
        });
        ui_widgets.AddMomentaryButton("Start", "bottom-right", 80, 60, 280, 10, new Runnable() {
            @Override
            public void run() {
                System.out.println("Clicked on Start!");
                MotionController.CycleStart();
            }
        });
        ui_widgets.AddMomentaryButton("Torch Off", "bottom-right", 170, 60, 10, 80, new Runnable() {
            @Override
            public void run() {
                System.out.println("Torch Off!");
            }
        });
        ui_widgets.AddMomentaryButton("Torch On", "bottom-right", 170, 60, 190, 80, new Runnable() {
            @Override
            public void run() {
                System.out.println("Torch On!");
            }
        });
    }
    // create a panel that you can draw on.
    class GcodeViewerPanel extends JPanel {
        public void paint(Graphics g) {
            Rectangle Frame_Bounds = this.getParent().getBounds();
            GlobalData.MousePositionX_MCS = (GlobalData.MousePositionX - GlobalData.ViewerPan[0]) / GlobalData.ViewerZoom;
            GlobalData.MousePositionY_MCS = ((GlobalData.MousePositionY - GlobalData.ViewerPan[1]) / GlobalData.ViewerZoom) * -1;
            //System.out.println("Mouse X: " + GlobalData.MousePositionX + " Mouse Y: " + GlobalData.MousePositionY + " Mouse X MCS: " + GlobalData.MousePositionX_MCS + " Mouse Y MCS: " + GlobalData.MousePositionY_MCS + " Frame Width: " + Frame_Bounds.width + " Frame Height: " + Frame_Bounds.height);
            Graphics2D g2d = (Graphics2D) g;

            /* Begin Wallpaper */
            g.setColor(Color.black);
            g.fillRect(0,0,Frame_Bounds.width,Frame_Bounds.height);
            /* End Wallpaper */

            g.setColor(Color.white);

            //g.fillRect(10,10,100,100);

            /* Begin machine boundry outline */
            g.setColor(Color.red);
            //0,0,X_Extent,0
            //X_Extent,0,X_Extent,Y_Extent
            //X_Extent,Y_Extent,0,Y_Extent
            //0,Y_Extent,0,0
            g2d.draw(new Line2D.Float((0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0 * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0 * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
            g2d.draw(new Line2D.Float((GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0 * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents  * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
            g2d.draw(new Line2D.Float((GlobalData.X_Extents * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents  * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
            g2d.draw(new Line2D.Float((0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((GlobalData.Y_Extents * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], (0 * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], ((0  * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));
            g.setColor(Color.white);
            /* End machine boundry outline */

            for(int i=0;i< ViewerEntityStack.size();i++)
            {
                ViewerEntity entity = ViewerEntityStack.get(i);
                if (entity.type == 1) //We are a line move
                {
                    g.setColor(Color.white);
                    //g.drawLine(entity.start[0], entity.start[1], entity.end[0], entity.end[1]);
                    g2d.draw(new Line2D.Float(((entity.start[0] + GlobalData.work_offset[0]) * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], (((entity.start[1] + GlobalData.work_offset[1]) * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1], ((entity.end[0] + GlobalData.work_offset[0]) * GlobalData.ViewerZoom) + GlobalData.ViewerPan[0], (((entity.end[1] + GlobalData.work_offset[1]) * GlobalData.ViewerZoom) * -1) + GlobalData.ViewerPan[1]));

                    //System.out.println("(" + i + ") Drawing Line Move: start: (" + entity.start[0] + ", " + entity.start[1] + ") end (" + entity.end[0] + ", " + entity.end[1] + ")");
                }
            }

            g.setFont(new Font("Arial", Font.BOLD, 45));
            if (GlobalData.IsHomed == false)
            {
                g.setColor(Color.red);
            }
            else
            {
                g.setColor(Color.green);
            }
            int DRO_X_Offset = -30;
            g.drawString("X:", Frame_Bounds.width - 350 - DRO_X_Offset, 70);
            g.drawString("Y:", Frame_Bounds.width - 350 - DRO_X_Offset, 140);
            g.drawString("Z:", Frame_Bounds.width - 350 - DRO_X_Offset, 210);

            g.drawString(String.format("%.4f", GlobalData.dro[0]), Frame_Bounds.width - 220 - DRO_X_Offset, 70);
            g.drawString(String.format("%.4f", GlobalData.dro[1]), Frame_Bounds.width - 220 - DRO_X_Offset, 140);
            g.drawString(String.format("%.4f", GlobalData.dro[2]), Frame_Bounds.width - 220 - DRO_X_Offset, 210);


            ui_widgets.RenderStack(g2d, Frame_Bounds);

            //Display Mouse position in MCS and Screen Cord
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString(String.format("Screen-> X: %d Y: %d MCS-> X: %.4f Y: %.4f", GlobalData.MousePositionX, GlobalData.MousePositionY, GlobalData.MousePositionX_MCS, GlobalData.MousePositionY_MCS), 10, 10);

        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new XmotionGen3().setVisible(true);
            }
        });
    }
}