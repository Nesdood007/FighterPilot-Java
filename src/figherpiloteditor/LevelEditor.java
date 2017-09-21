package figherpiloteditor;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import fighterpilot.FighterPilotGame;
import level.BGLayer;
import level.Level;
import levelgeneration.LevelGenerator;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JScrollBar;

public class LevelEditor implements ActionListener{

    private JFrame frame;
    
    public static Level level = null;

    public static final String NAME = "Level Editor Test";
    
    public enum Mode {Select, Place, Erase, None};//Level Editor Mode
    public static Mode mode;
    
    public enum SelectMode {Block, BG, Sprite};//Selection Mode
    public static SelectMode selectMode;
    
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LevelEditor window = new LevelEditor();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        //Begin Editor Logic Here.
        LevelGenerator lg = new LevelGenerator("Editor Level", true, true, true);
        lg.setDifficulty(1, 1);
        level = lg.generateLevel(4, 16);
    }

    /**
     * Create the application.
     */
    public LevelEditor() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame(NAME);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Menu Bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        
        JMenuItem mntmLoadFromFile = new JMenuItem("Load from File");
        mnFile.add(mntmLoadFromFile);
        mntmLoadFromFile.addActionListener(this);
        
        JMenuItem mntmSaveToFile = new JMenuItem("Save to File");
        mnFile.add(mntmSaveToFile);
        mntmSaveToFile.addActionListener(this);
        
        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);
        
        JMenu mnLevel = new JMenu("Level");
        menuBar.add(mnLevel);
        
        JMenuItem mntmGenerateNewLevel = new JMenuItem("Generate new Level");
        mnLevel.add(mntmGenerateNewLevel);
        mntmGenerateNewLevel.addActionListener(this);//Adds Action Listener so that something can actually happen.
        
        JMenuItem mntmTestLevel = new JMenuItem("Test Level");
        mnLevel.add(mntmTestLevel);
        mntmTestLevel.addActionListener(this);
        
        //Panes
        JSplitPane splitPane = new JSplitPane();
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        
        //Editor editor = new Editor();
        
        JScrollPane scrollPane = new JScrollPane();
        splitPane.setRightComponent(scrollPane);
        
        Editor panel = new Editor();
        scrollPane.setViewportView(panel);
        
        JLabel lblThisIsNot = new JLabel("This is not yet used.");
        splitPane.setLeftComponent(lblThisIsNot);
        
    }

    /**Action Listener to use Menus.
     * 
     * <p>Note: The "e.getActionCommand()" method will return the name of the JMenuItem.
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        if (command.equals("Generate new Level")) {
            //Add a confirmation Dialog Later
            level = null;
            
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    //level = null;
                    LevelGenerator lg = new LevelGenerator("Random Level", true, false, true);
                    lg.setDifficulty(1, 4);
                    lg.setPlayerSpawnChunk(0, 0);
                    level = lg.generateLevel(32, 32);
                    level.bg = new BGLayer[1];
                    level.bg[0] = new BGLayer(0, "FighterPilotImages/Xevious_area_all.png", 1.0, 1.0, true, true);
                }
            });
        } else if (command.equals("Test Level")) {
            //Add Confirmation and save later
            
            System.out.println("Does't Currently Work");
            
            /*EventQueue.invokeLater(new Runnable() {
                public void run() {
                    FighterPilotGame game = new FighterPilotGame(level);
                    game.start();
                }
            });*/
        }
        
    }

}
