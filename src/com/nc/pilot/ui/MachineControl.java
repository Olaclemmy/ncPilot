package com.nc.pilot.ui;

import com.nc.pilot.config.ConfigData;
import com.nc.pilot.config.JetToolpathCutChartData;
import com.nc.pilot.lib.*;
import com.nc.pilot.lib.MDIConsole.MDIConsole;
import com.nc.pilot.lib.MotionController.MotionController;
import com.nc.pilot.lib.UIWidgets.UIWidgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MachineControl extends JFrame {

    JMenuBar menu_bar;
    Timer repaint_timer = new Timer();
    Timer poll_timer = new Timer();
    MotionController motion_controller;
    UIWidgets ui_widgets;
    GcodeViewer gcode_viewer;
    MDIConsole mdi_console;
    public MachineControl() {

        super("Xmotion Gen3");
        setSize(1100, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        motion_controller = new MotionController();
        ui_widgets = new UIWidgets();
        gcode_viewer = new GcodeViewer();
        mdi_console = new MDIConsole();
        mdi_console.inherit_MotionController(motion_controller);
        motion_controller.inherit_ui_widgets(ui_widgets);
        motion_controller.inherit_mdi_console(mdi_console);
        Layout_UI();
        createMenuBar();
        setJMenuBar(menu_bar);
        GcodeViewerPanel panel = new GcodeViewerPanel();
        add(panel);

        File f = new File("Xmotion.conf");
        if(!f.exists() && !f.isDirectory()) {
            System.out.println("Config file does not exist, creating it!");
            GlobalData.configData = new ConfigData();
            GlobalData.configData.CutChart = new ArrayList();

            JetToolpathCutChartData cut = new JetToolpathCutChartData();
            cut.Material = "1/8 Mild Steel";
            cut.Consumable = "45A Finecut";
            cut.Amperage = 38;
            cut.KerfDiameter = 0.039f;
            cut.PierceHeight = 0.150f;
            cut.PierceDelay = 1.2f;
            cut.CutHeight = 0.175f;
            cut.PostDelay = 1f;
            cut.ATHCVoltage = 100;
            cut.Feedrate = 52f;
            GlobalData.configData.CutChart.add(cut);

            cut = new JetToolpathCutChartData();
            cut.Material = "1/4 Mild Steel";
            cut.Consumable = "45A Finecut";
            cut.Amperage = 45;
            cut.KerfDiameter = 0.042f;
            cut.PierceHeight = 0.150f;
            cut.PierceDelay = 1.8f;
            cut.CutHeight = 0.175f;
            cut.PostDelay = 1f;
            cut.ATHCVoltage = 100;
            cut.Feedrate = 35f;
            GlobalData.configData.CutChart.add(cut);

            cut = new JetToolpathCutChartData();
            cut.Material = "3/8 Mild Steel";
            cut.Consumable = "45A Finecut";
            cut.Amperage = 45;
            cut.KerfDiameter = 0.048f;
            cut.PierceHeight = 0.150f;
            cut.PierceDelay = 2.5f;
            cut.CutHeight = 0.175f;
            cut.PostDelay = 1f;
            cut.ATHCVoltage = 100;
            cut.Feedrate = 28f;
            GlobalData.configData.CutChart.add(cut);

            try {
                GlobalData.pushConfig();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                GlobalData.pullConfig();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            GlobalData.configData.CurrentWorkbench = "MachineControl";
        }

        repaint_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 50);
        poll_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                motion_controller.Poll();
            }
        }, 0, 1);
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
                ui_widgets.MouseMotionStack(e.getX(), e.getY());
            }

            public void mouseDragged(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
                GlobalData.MousePositionX = e.getX();
                GlobalData.MousePositionY = e.getY();
                ui_widgets.MouseMotionStack(e.getX(), e.getY());
                repaint();
            }
        });
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                float old_zoom = GlobalData.ViewerZoom;
                if (e.getWheelRotation() < 0) {
                    GlobalData.ViewerZoom *= 1.2;
                    //System.out.println("ViewerZoom: " + GlobalData.ViewerZoom);
                    if (GlobalData.ViewerZoom > GlobalData.MaxViewerZoom) {
                        GlobalData.ViewerZoom = GlobalData.MaxViewerZoom;
                    }
                } else {
                    GlobalData.ViewerZoom *= 0.8;
                    //System.out.println("ViewerZoom: " + GlobalData.ViewerZoom);
                    if (GlobalData.ViewerZoom < GlobalData.MinViewerZoom) {
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
                        if (!GlobalData.configData.CurrentWorkbench.contentEquals("MachineControl")) return false;
                        switch (ke.getID()) {
                            case KeyEvent.KEY_PRESSED:
                                //System.out.println("(Machine Control) Key: " + ke.getKeyCode());
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    if (GlobalData.UpArrowKeyState == false) {
                                        GlobalData.UpArrowKeyState = true;
                                        motion_controller.JogY_Plus();
                                        motion_controller.JogY = true;
                                        motion_controller.JogYdir = true;
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    if (GlobalData.DownArrowKeyState == false) {
                                        GlobalData.DownArrowKeyState = true;
                                        motion_controller.JogY_Minus();
                                        motion_controller.JogY = true;
                                        motion_controller.JogYdir = false;
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                                    if (GlobalData.RightArrowKeyState == false) {
                                        GlobalData.RightArrowKeyState = true;
                                        motion_controller.JogX_Plus();
                                        motion_controller.JogX = true;
                                        motion_controller.JogXdir = true;
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                    if (GlobalData.LeftArrowKeyState == false) {
                                        GlobalData.LeftArrowKeyState = true;
                                        motion_controller.JogX_Minus();
                                        motion_controller.JogX = true;
                                        motion_controller.JogXdir = false;
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                                    if (GlobalData.PageUpKeyState == false) {
                                        GlobalData.PageUpKeyState = true;
                                        motion_controller.JogZ_Plus();
                                        motion_controller.JogZ = true;
                                        motion_controller.JogZdir = true;
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                                    if (GlobalData.PageDownKeyState == false) {
                                        GlobalData.PageDownKeyState = true;
                                        motion_controller.JogZ_Minus();
                                        motion_controller.JogZ = true;
                                        motion_controller.JogZdir = false;
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_ALT) {

                                    GlobalData.AltKeyPressed = true;
                                }
                                //repaint();
                                break;

                            case KeyEvent.KEY_RELEASED:
                                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                                    GlobalData.UpArrowKeyState = false;
                                    motion_controller.EndYJog();
                                    motion_controller.JogY = false;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                                    GlobalData.DownArrowKeyState = false;
                                    motion_controller.EndYJog();
                                    motion_controller.JogY = false;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                                    GlobalData.LeftArrowKeyState = false;
                                    motion_controller.EndXJog();
                                    motion_controller.JogX = false;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                                    GlobalData.RightArrowKeyState = false;
                                    motion_controller.EndXJog();
                                    motion_controller.JogX = false;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                                    GlobalData.PageUpKeyState = false;
                                    motion_controller.EndZJog();
                                    motion_controller.JogZ = false;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                                    GlobalData.PageDownKeyState = false;
                                    motion_controller.EndZJog();
                                    motion_controller.JogZ = false;
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {

                                    motion_controller.FeedHold();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_TAB)
                                {
                                    GcodeInterpreter g = new GcodeInterpreter("/users/admin/Documents/Projects/ncPilot/test/gcode/0.ngc");
                                    GlobalData.GcodeFile = "/users/admin/Documents/Projects/ncPilot/test/gcode/0.ngc";
                                    ArrayList<GcodeInterpreter.GcodeMove> moves = g.GetMoves();
                                    gcode_viewer.ClearStack();
                                    for (int x = 2; x < moves.size(); x ++)
                                    {
                                        if (moves.get(x).Gword == 1)
                                        {
                                            gcode_viewer.addLine(new float[]{moves.get(x-1).Xword, moves.get(x-1).Yword}, new float[]{moves.get(x).Xword, moves.get(x).Yword});
                                        }
                                        if (moves.get(x).Gword == 2)
                                        {
                                            float[] center = new float[]{moves.get(x-1).Xword + moves.get(x).Iword, moves.get(x-1).Yword + moves.get(x).Jword};
                                            float radius = new Float(Math.hypot(moves.get(x).Xword-center[0], moves.get(x).Yword-center[1]));
                                            gcode_viewer.addArc(new float[]{moves.get(x-1).Xword, moves.get(x-1).Yword}, new float[]{moves.get(x).Xword, moves.get(x).Yword}, center, radius, "CW");
                                        }
                                        if (moves.get(x).Gword == 3)
                                        {
                                            float[] center = new float[]{moves.get(x-1).Xword + moves.get(x).Iword, moves.get(x-1).Yword + moves.get(x).Jword};
                                            float radius = new Float(Math.hypot(moves.get(x).Xword-center[0], moves.get(x).Yword-center[1]));
                                            gcode_viewer.addArc(new float[]{moves.get(x-1).Xword, moves.get(x-1).Yword}, new float[]{moves.get(x).Xword, moves.get(x).Yword}, center, radius, "CCW");
                                        }
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                    motion_controller.FeedHold();
                                    motion_controller.Abort();
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_R) {

                                    if (GlobalData.AltKeyPressed == true)
                                    {
                                        motion_controller.CycleStart();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_F1) {

                                    if (GlobalData.AltKeyPressed == true)
                                    {
                                        mdi_console.show();
                                    }
                                }
                                if (ke.getKeyCode() == KeyEvent.VK_ALT) {

                                    GlobalData.AltKeyPressed = false;
                                }
                                break;
                        }
                        mdi_console.dispatchKeyEvent(ke);
                        return false;
                    }
                });
    }
    private void createMenuBar()
    {
        //Where the GUI is created:
        JMenu menu;
        JMenuItem menuItem;

        //Create the menu bar.
        menu_bar = new JMenuBar();

        //Build Wqrkbench menu
        menu = new JMenu("Workbench");
        menu.setMnemonic(KeyEvent.VK_W);
        menu.getAccessibleContext().setAccessibleDescription("Switch to different workbenches");
        menu_bar.add(menu);

        menuItem = new JMenuItem("Jet Toolpaths");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Create Toolpaths from Part Drawings");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JetToolpaths toolpaths = new JetToolpaths();
                toolpaths.setVisible(true);
            }

        });
        menuItem = new JMenuItem("JetCad");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Create Part Drawings");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JetCad jetcad = new JetCad();
                jetcad.setVisible(true);
            }

        });

        //Build Wqrkbench menu
        menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_S);
        menu.getAccessibleContext().setAccessibleDescription("Set Machine Parameters");
        menu_bar.add(menu);

        //Controller
        menuItem = new JMenuItem("Controller");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Control CNC Machine connected to Xmotion CNC Motion Controller");
        menu.add(menuItem);

        //Interface
        menuItem = new JMenuItem("Interface");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Create Toolpaths from Part Drawings");
        menu.add(menuItem);

    }
    private void Layout_UI()
    {
        ui_widgets.AddDRO();
        ui_widgets.AddMomentaryButton("Open", "bottom-right", 80, 60, 10, 10, new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("/root/Share/Post"));
                int result = fileChooser.showOpenDialog(getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    GcodeInterpreter g = new GcodeInterpreter(selectedFile.getAbsolutePath());
                    GlobalData.GcodeFile = selectedFile.getAbsolutePath();
                    ArrayList<GcodeInterpreter.GcodeMove> moves = g.GetMoves();
                    gcode_viewer.ClearStack();

                    for (int x = 2; x < moves.size(); x ++)
                    {
                        if (moves.get(x).Gword == 1)
                        {
                            gcode_viewer.addLine(new float[]{moves.get(x-1).Xword, moves.get(x-1).Yword}, new float[]{moves.get(x).Xword, moves.get(x).Yword});
                        }
                        if (moves.get(x).Gword == 2)
                        {
                            float[] center = new float[]{moves.get(x-1).Xword + moves.get(x).Iword, moves.get(x-1).Yword + moves.get(x).Jword};
                            float radius = new Float(Math.hypot(moves.get(x).Xword-center[0], moves.get(x).Yword-center[1]));
                            gcode_viewer.addArc(new float[]{moves.get(x-1).Xword, moves.get(x-1).Yword}, new float[]{moves.get(x).Xword, moves.get(x).Yword}, center, radius, "CW");
                        }
                        if (moves.get(x).Gword == 3)
                        {
                            float[] center = new float[]{moves.get(x-1).Xword + moves.get(x).Iword, moves.get(x-1).Yword + moves.get(x).Jword};
                            float radius = new Float(Math.hypot(moves.get(x).Xword-center[0], moves.get(x).Yword-center[1]));
                            gcode_viewer.addArc(new float[]{moves.get(x-1).Xword, moves.get(x-1).Yword}, new float[]{moves.get(x).Xword, moves.get(x).Yword}, center, radius, "CCW");
                        }
                    }
                }
            }
        });
        ui_widgets.AddMomentaryButton("Abort", "bottom-right", 80, 60, 100, 10, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on Abort!");
                motion_controller.Abort();
            }
        });
        ui_widgets.AddMomentaryButton("Hold", "bottom-right", 80, 60, 190, 10, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on Hold!");
                motion_controller.FeedHold();
            }
        });
        ui_widgets.AddMomentaryButton("Start", "bottom-right", 80, 60, 280, 10, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on Start!");
                motion_controller.CycleStart();
            }
        });
        ui_widgets.AddSelectButton("Torch Off","torch", true, "bottom-right", 170, 60, 10, 80, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Torch Off!");
                motion_controller.WriteBuffer("M5\n");
            }
        });
        ui_widgets.AddSelectButton("Torch On", "torch", false,"bottom-right", 170, 60, 190, 80, new Runnable() {
            @Override
            public void run() {
                //
                // System.out.println("Torch On!");
                motion_controller.WriteBuffer("M3 S5000\n");
            }
        });
        ui_widgets.AddMomentaryButton("Go Home", "bottom-right", 120, 60, 10, 150, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Go Home!");
                motion_controller.WriteBuffer("G90 G53 G0 Z0\n");
                motion_controller.WriteBuffer("G90 G53 G0 X0 Y0\n");
            }
        });
        ui_widgets.AddMomentaryButton("Probe Z", "bottom-right", 120, 60, 140, 150, new Runnable() {
            @Override
            public void run() {
                System.out.println("Probe Z!");
            }
        });
        ui_widgets.AddMomentaryButton("Home", "bottom-right", 90, 60, 270, 150, new Runnable() {
            @Override
            public void run() {
                System.out.println("Home!");
                if (GlobalData.IsHomed == false)
                {
                    GlobalData.IsHomed = true;
                    motion_controller.Home();
                }
            }
        });
        ui_widgets.AddMomentaryButton("X=0", "bottom-right", 110, 60, 250, 220, new Runnable() {
            @Override
            public void run() {
                //System.out.println("X=0");
                motion_controller.SetXzero();
            }
        });
        ui_widgets.AddMomentaryButton("Y=0", "bottom-right", 110, 60, 130, 220, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Y=0");
                motion_controller.SetYzero();
            }
        });
        ui_widgets.AddMomentaryButton("Z=0", "bottom-right", 110, 60, 10, 220, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Z=0");
                motion_controller.SetZzero();
            }
        });
        ui_widgets.AddSelectButton("0.001\"", "jog", false,"bottom-right", 60, 60, 10, 290, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on 0.001\"!");
                GlobalData.JogMode = "0.001";
            }
        });
        ui_widgets.AddSelectButton("0.01\"","jog", false, "bottom-right", 60, 60, 80, 290, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on 0.01\"!");
                GlobalData.JogMode = "0.01";
            }
        });
        ui_widgets.AddSelectButton("0.1\"", "jog", false,"bottom-right", 60, 60, 150, 290, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on 0.1\"");
                GlobalData.JogMode = "0.1";
            }
        });
        ui_widgets.AddSelectButton("Continuous","jog",true, "bottom-right", 140, 60, 220, 290, new Runnable() {
            @Override
            public void run() {
                //System.out.println("Clicked on Continuous!");
                GlobalData.JogMode = "Continuous";
            }
        });
        //void AddSlider(String text, String anchor, int width, int height, int posx, int posy, int min, int max, Runnable action){
        /*ui_widgets.AddSlider("Jog Speed", "bottom-right", 350, 60, 10, 360, 0, (int)GlobalData.X_Max_Vel , (int)(GlobalData.X_Max_Vel * 0.7), new Runnable(){
            @Override
            public void run() {
                System.out.println("New position: " + ui_widgets.getSliderPosition("Jog Speed"));
            }
        });*/
        ui_widgets.AddSlider("Jog Speed", "bottom-right", 350, 60, 10, 360, 0, (int)GlobalData.Max_linear_Vel, 300, "Inch/Min", new Runnable(){
            @Override
            public void run() {
                System.out.println("New position: " + ui_widgets.getSliderPosition("Jog Speed"));
                motion_controller.SetJogSpeed(ui_widgets.getSliderPosition("Jog Speed"));
            }
        });
    }
    // create a panel that you can draw on.
    class GcodeViewerPanel extends JPanel {
        public void paint(Graphics g) {
            Rectangle Frame_Bounds = this.getParent().getBounds();
            GlobalData.MousePositionX_MCS = (GlobalData.MousePositionX - GlobalData.ViewerPan[0]) / GlobalData.ViewerZoom;
            GlobalData.MousePositionY_MCS = ((GlobalData.MousePositionY - GlobalData.ViewerPan[1]) / GlobalData.ViewerZoom) * -1;
            Graphics2D g2d = (Graphics2D) g;
            /* Begin Wallpaper */
            g.setColor(Color.black);
            g.fillRect(0,0,Frame_Bounds.width,Frame_Bounds.height);
            /* End Wallpaper */
            gcode_viewer.RenderStack(g2d);
            ui_widgets.RenderStack(g2d, Frame_Bounds);
            mdi_console.RenderStack(g2d, Frame_Bounds);
            //Display Mouse position in MCS and Screen Cord
            //g.setColor(Color.green);
            //g.setFont(new Font("Arial", Font.BOLD, 12));
            //g.drawString(String.format("Screen-> X: %d Y: %d MCS-> X: %.4f Y: %.4f", GlobalData.MousePositionX, GlobalData.MousePositionY, GlobalData.MousePositionX_MCS, GlobalData.MousePositionY_MCS), 10, 10);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MachineControl().setVisible(true);
            }
        });
    }
}