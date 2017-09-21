package fighterpilot;

import gameEvent.AI;
import gameInput.Button;
import gameInput.Controllable;
import physics.Collidable;
import graphics.Camera;
import graphics.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
//import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
//import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JPanel;
import javax.swing.JPanel;

import level.*;
import levelgeneration.LevelGenerator;
import levelgeneration.LevelReader;
import levelgeneration.LevelSaver;
import physics.Physics;
import audio.AudioManager;
import audio.Sound;


/**This is the GameLogic Portion of the program, and also where the painting of the screen happens
 * 
 * @author Brady
 *
 */
public class FighterPilotGame extends JPanel {//can extend Canvas, but there is a terrible flicker, so don't extend Canvas.

    private static final long serialVersionUID = 1L;//So Java Cannot Complain

    public static final int WIDTH = 800;//640, 1440, 800
    public static final int HEIGHT = 600;//480, 720, 600
    public static final int SCALE = 1;//1
    public static final String NAME = "FighterPilot2TestGame";

    public static final int SPEED = 1;//Wait time for all threads

    private JFrame frame;

    private Camera cam;

    //Game-specific stuff
    private Level lvl = null;

    private boolean moveUP,moveDN,moveLF,moveRT,rst,follow,bound = true;
    private int[] pointToFollow = new int[2];
    private int[] boundaries = new int[4];//{xmin,ymin,xmax,ymax}

    //Might be useful for implementing Multiplayer in the future
    private Player[] players = new Player[1]; 
    private ArrayList<Controllable> contObjects = new ArrayList<Controllable>();
    private HashSet<Controllable> contSet = new HashSet<Controllable>();

    //Sound Test
    Sound bgmusic;

    //This is for different GameModes, mainly to implement title screens, loading screns, cutscenes, as well as different rules and such reguarding a gamemode.
    private enum GameMode{
        TitleIntro, 		//The Intro before the Title 
        TitleScreen, 		//The Actual Title Screen
        ShooterLevelIntro,	//The Intro before the ShooterLevel
        ShooterLevel,		//ShooterLevel is being played
        ShooterLevelSuccess,	//End of the ShooterLevel, when finished
        ShooterLevelFail,	//End of the ShooterLevel, if failure.

    }
    private GameMode currentMode = null;
    private boolean paused = false;//If game is paused.

    //Title Screen Stuff
    private BufferedImage titleScreen;
    private int[] titleScreenSize = {WIDTH, HEIGHT};



    /**Default Constructor, acts as the Initializer for the Game
     * 
     */
    public FighterPilotGame() {//Init Routine for Game
        initialize();
    }
    
    public FighterPilotGame(Level lvl) {
        this.lvl = lvl;
        initialize();
        System.out.println("OK");
    }
    
    private void initialize() {
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);//false
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cam = new Camera(WIDTH*SCALE, HEIGHT*SCALE, 256); //Buffer will be 32

        frame.setFocusable(true);//must be true to capture keystrokes
        frame.addKeyListener(new Input());

        //Game Specific Stuff========================================
        moveUP = false;
        moveDN = false;
        moveLF = false;
        moveRT = false;
        rst = false;
        follow = false;
        pointToFollow[0] = 0;
        pointToFollow[1] = 0;
        bound = true;
        boundaries[0] = 0;
        boundaries[1] = 0;
        boundaries[2] = 0;
        boundaries[3] = 0;

        //Game Mode
        currentMode = GameMode.TitleScreen;

        //Title Screen
        try {
            titleScreen = ImageIO.read(new File("FighterPilotImages/Title/" + titleScreenSize[0] + "x" + titleScreenSize[1] + ".png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**This Method actually starts the Game
     * 
     */
    public void start(){

        if (lvl == null) {
            this.loadLevel();
        }

        if(lvl.audioFileName.equals("")) {
            lvl.audioFileName = "Music/gameplay2.wav";
            System.out.println("Set Default Music.");
        }
        bgmusic = AudioManager.loadSound("Music/title.wav", true);
        bgmusic.play();

        DoGameLoop dgl = new DoGameLoop();
        (new Thread(dgl)).start();
        while(true){
            repaint();
        }

    }
    /**Loads the Level in a game, and resets the Game Variables
     * 
     */
    private void loadLevel(){
        LevelGenerator lg = new LevelGenerator("Random Generation", true, false, true);
        lg.setDifficulty(1, 4);
        lvl = lg.generateLevel(4, 16);

        /*try {
            lvl = LevelReader.readLevelFromFile("test.lvl");//ReadingTest.lvl
        } catch (FileNotFoundException e1) {
            System.err.println("Error reading Level from File");
            e1.printStackTrace();
        }

        lvl.s = new SpriteSheet(SpriteSheet.TRANSPARENT);

        lvl.bg = new BGLayer[1];
        lvl.bg[0] = new BGLayer(1,"FighterPilotImages/Background.png",0.0,0.0,true,true);

        SpriteLayer slyr = new SpriteLayer(0);
        slyr.sprites.add(new Player("Player 1", 0, 0, 4.0, 4.0));
        lvl.chunks[0][0].layers.add(slyr);*/

        //lvl = this.randomLevel(4, 16, 100, 4);
        //lvl = this.randomLevelTest(32, 32, 1);//4, 16, 1

        /*lvl = new Level("CollisionTest", -1, 1, 8, new SpriteSheet(SpriteSheet.TRANSPARENT));

        BGLayer[] bg = new BGLayer[1];//Global Background
        bg[0] = new BGLayer(1,"FighterPilotImages/Background.png",0.0,0.0,true,true);//FighterPilotImages/Background.png

        BGLayer[] fg = new BGLayer[0];//Global Foreground

        lvl = new Level("CollisionTest", -1, 4, 4,bg ,fg,new SpriteSheet(SpriteSheet.TRANSPARENT));
        SpriteLayer sLayer = new SpriteLayer(0);
        sLayer.sprites.add(new PongShip("Test", -1, 0, 4.0, 1.0, PongShip.NORTH, true));
        sLayer.sprites.add(new PongShip("Test", -1, 0, 4.0, 7.0, PongShip.SOUTH, true));
        sLayer.sprites.add(new PongShip("Test", -1, 0, 1.0, 4.0, PongShip.EAST, true));
        sLayer.sprites.add(new PongShip("Test", -1, 0, 7.0, 4.0, PongShip.WEST, true));
        lvl.chunks[2][2].layers.add(sLayer);
        sLayer = new SpriteLayer(0);
        sLayer.sprites.add(new PongShip("Test", -1, 0, 4.0, 1.0, PongShip.NORTH, false));
        sLayer.sprites.add(new PongShip("Test", -1, 0, 4.0, 7.0, PongShip.SOUTH, false));
        sLayer.sprites.add(new PongShip("Test", -1, 0, 1.0, 4.0, PongShip.EAST, false));
        sLayer.sprites.add(new PongShip("Test", -1, 0, 7.0, 4.0, PongShip.WEST, false));
        lvl.chunks[1][1].layers.add(sLayer);
        sLayer = new SpriteLayer(0);
        players[0] = new Player("Player1", 0, 0, 4.0, 4.0);
        sLayer.sprites.add(players[0]);
        lvl.chunks[0][0].layers.add(sLayer);*/

        //doControls();//Adds Controllable Objects to Controllable List

        findControllableObjects();

        /*for(Player plr : players){
            contSet.add(plr);
            contObjects.add(plr);
        }*/

        follow = true;

        bound = true;
        boundaries[0] = 0;
        boundaries[1] = 0;
        boundaries[2] = lvl.chunks[0].length * lvl.chunks[0][0].getBlockSize()[0] * lvl.s.getSpriteSize();
        boundaries[3] = lvl.chunks.length * lvl.chunks[0][0].getBlockSize()[1] * lvl.s.getSpriteSize();

        //Testing Level Saving...
        try {
            LevelSaver.saveLevelToFile(lvl, "test.lvl");
        } catch (IOException e) {
            System.err.println("Error in Saving Level to File:");
            e.printStackTrace();
        }
    }

    /**Finds all Controllable Objects in the Level to the Controllable Objects List.
     * 
     */
    private void findControllableObjects() {
        for (int crow = 0; crow < lvl.chunks.length; crow++) {
            for (int ccol = 0; ccol < lvl.chunks[0].length; ccol++) {
                for (int lyr = 0; lyr < lvl.chunks[crow][ccol].layers.size(); lyr++) {
                    if (lvl.chunks[crow][ccol].layers.get(lyr) instanceof SpriteLayer) {
                        for (int spt = 0; spt < ((SpriteLayer)lvl.chunks[crow][ccol].layers.get(lyr)).sprites.size(); spt++) {
                            if (((SpriteLayer)lvl.chunks[crow][ccol].layers.get(lyr)).sprites.get(spt) instanceof Controllable) {
                                if (!contSet.contains(((SpriteLayer)lvl.chunks[crow][ccol].layers.get(lyr)).sprites.get(spt))) {
                                    contObjects.add((Controllable) ((SpriteLayer)lvl.chunks[crow][ccol].layers.get(lyr)).sprites.get(spt));
                                    contSet.add((Controllable) ((SpriteLayer)lvl.chunks[crow][ccol].layers.get(lyr)).sprites.get(spt));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**This generates a Random Level
     * 
     * @param rowChunks how many rows of chunks to generate
     * @param ColChunks how many cols of Chunks to generate
     * @param enemyProb PRobability of enemies appearing
     * @param maxEnemies Maximum number of enemies that can be in a chunk
     * @return the Randomly Generated Level
     */
    public Level randomLevel(int rowChunks, int colChunks, int enemyProb, int maxEnemies){
        System.out.println("Generating Level...|");

        Random rand = new Random();

        BGLayer[] bg = new BGLayer[1];//Global Background
        bg[0] = new BGLayer(1,"FighterPilotImages/Background.png",0.0,0.0,true,true);//FighterPilotImages/Background.png

        BGLayer[] fg = new BGLayer[0];//Global Foreground

        Level level = new Level("RandomGen", -1, rowChunks, colChunks,bg ,fg,new SpriteSheet(SpriteSheet.TRANSPARENT));

        for(int i = 0; i < rowChunks; i++) {
            for(int j = 0; j < colChunks; j++){
                boolean empty = true;
                SpriteLayer sLayer = new SpriteLayer(0);
                if(i == 0 && j == 0){//Adds Player
                    players[0] = new Player("Player1", 0, 0, 4.0, 4.0);
                    sLayer.sprites.add(players[0]);
                    empty = false;
                }
                for(int k = 0; k < maxEnemies; k++){
                    if(rand.nextBoolean()){
                        empty = false;
                        sLayer.sprites.add(new Enemy("RandGen",0,0,rand.nextDouble()*8,rand.nextDouble()*8));//Adds Enemy
                    }
                    if(rand.nextBoolean()){
                        empty = false;
                        sLayer.sprites.add(new LargeFlyingPanel("RandGen", 0, 0, rand.nextDouble()*8, rand.nextDouble()*8, 0.001));//Adds Enemy
                    }
                }
                if(!empty){
                    //System.out.println("Adding SpriteLayer|" + sLayer);
                    level.chunks[i][j].layers.add(sLayer);//Since this is a new Level, there should not be a Spritelayer, so we don't really need to check for one, as that would be stupid
                }
                //Code to add random Blocks
            }
        }
        //level.manageChunks();
        return level;
    }
    /**Randomly Generates a Level full of Enemies
     * 
     * @param rows Rows of Chunks in Level
     * @param cols Cols of Chunks in Level
     * @param enemiesPerChunk Total Enemies that will be placed in Level
     * @return Randomly Generated Level
     */
    public Level randomLevelTest(int rows, int cols, int enemiesPerChunk){

        BGLayer[] bg = new BGLayer[1];//Global Background
        bg[0] = new BGLayer(1,"BG0.png",0.25,0.25,true,true);//FighterPilotImages/Xevious_area_all.png
        //bg[1] = new BGLayer(2, "FighterPilotImages/TestClouds.png", 0.75, 0.75, true, true);

        BGLayer[] fg = new BGLayer[0];//Global Foreground

        Level level = new Level("RandomLevel.Test", -1, rows, cols, bg, fg, new SpriteSheet(SpriteSheet.TRANSPARENT));

        for(int i = 0; i < level.chunks.length; i++){
            for(int j = 0; j < level.chunks[0].length; j++){
                SpriteLayer sLayer = new SpriteLayer(0);
                for(int k = 0; k < enemiesPerChunk; k++){
                    sLayer.sprites.add(new Enemy("RandGen", 0, 0, 4.0, 4.0));
                }
                if(i == rows - 1 && j == cols - 1){
                    players[0] = new Player("PlayerRandGen", 0, 0, 0.0, 0.0);
                    sLayer.sprites.add(players[0]);
                }
                //System.out.println(sLayer);
                level.chunks[i][j].layers.add(sLayer);
                sLayer = null;
            }
        }
        level.manageChunks();
        return level;
    }

    /**This paints the Level
     * 
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if(lvl == null){
            System.out.println("Loading Level...");
            return;
        }

        if(currentMode == GameMode.TitleScreen){
            g2d.drawImage(titleScreen, 0, 0, null);
            return;
        }
        if(currentMode == GameMode.ShooterLevelFail){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, WIDTH + 10, HEIGHT + 10);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Serif", Font.BOLD, 40));
            g2d.drawString("Game Over", (WIDTH/2) - 10, (HEIGHT/2) - 10);
            return;
        }


        //===========================================Paints Global Level BG=========================================
        //Right now, Scroll Factors over 1.0 and below 0.0 cause weird things to happen.

        for(int i = 0; i < lvl.bg.length; i++){//BGLayer to render

            //System.out.println(((int) ((cam.getXmin() - cam.getBuffer() - lvl.bg[i].getBufferedImage().getWidth()) * (lvl.bg[i].scrollFactorX)) / lvl.bg[i].getBufferedImage().getWidth()) * lvl.bg[i].getBufferedImage().getWidth() + " " + ((int) ((cam.getXmax() + cam.getBuffer() + lvl.bg[i].getBufferedImage().getWidth()) * (/*1 + */lvl.bg[i].scrollFactorX)) / lvl.bg[i].getBufferedImage().getWidth()) * lvl.bg[i].getBufferedImage().getWidth() + " | " + ((int) ((cam.getYmin() - cam.getBuffer() - lvl.bg[i].getBufferedImage().getHeight()) * (lvl.bg[i].scrollFactorY)) / lvl.bg[i].getBufferedImage().getHeight()) * lvl.bg[i].getBufferedImage().getHeight() + " " + ((int) ((cam.getYmax() + cam.getBuffer() + lvl.bg[i].getBufferedImage().getHeight()) * (/*1 + */lvl.bg[i].scrollFactorY)) / lvl.bg[i].getBufferedImage().getHeight()) * lvl.bg[i].getBufferedImage().getHeight());

            for(int j = ((int) ((cam.getXmin() - cam.getBuffer() - lvl.bg[i].getBufferedImage().getWidth()) * (lvl.bg[i].scrollFactorX)) / lvl.bg[i].getBufferedImage().getWidth()) * lvl.bg[i].getBufferedImage().getWidth(); j < ((int) ((cam.getXmax() + cam.getBuffer() + lvl.bg[i].getBufferedImage().getWidth()) * (1 + lvl.bg[i].scrollFactorX)) / lvl.bg[i].getBufferedImage().getWidth()) * lvl.bg[i].getBufferedImage().getWidth(); j = j + lvl.bg[i].getBufferedImage().getWidth()){
                for(int k = ((int) ((cam.getYmin() - cam.getBuffer() - lvl.bg[i].getBufferedImage().getHeight()) * (lvl.bg[i].scrollFactorY)) / lvl.bg[i].getBufferedImage().getHeight()) * lvl.bg[i].getBufferedImage().getHeight(); k < ((int) ((cam.getYmax() + cam.getBuffer() + lvl.bg[i].getBufferedImage().getHeight()) * (1 + lvl.bg[i].scrollFactorY)) / lvl.bg[i].getBufferedImage().getHeight()) * lvl.bg[i].getBufferedImage().getHeight(); k = k + lvl.bg[i].getBufferedImage().getHeight()){
                    g2d.drawImage(lvl.bg[i].getBufferedImage() , (int) (j - cam.getXmin() * lvl.bg[i].scrollFactorX) , (int) (k - cam.getYmin() * lvl.bg[i].scrollFactorY),null);
                    if(!lvl.bg[i].isYRepeating()){//Ends if not repeating. Done so that it draws the first image then will stop
                        k = cam.getYmax() + cam.getBuffer();
                    }
                }
                if(!lvl.bg[i].isXRepeating()){//Ends if not repeating
                    j = cam.getXmax() + cam.getBuffer();
                }
            }
        }
        //=======================================Paints Level===================================================
        for(int i=0; i < lvl.chunks.length ;i++){//row
            for(int j=0; j < lvl.chunks[0].length; j++){//col

                //Makes sure chunk is inside Viewing Area before rendering
                if(cam.isInside(  j * lvl.s.getSpriteSize() * lvl.chunks[i][j].getBlockSize()[0] ,  i * lvl.s.getSpriteSize() * lvl.chunks[i][j].getBlockSize()[1] )){
                    lvl.chunks[i][j].isActive = true;

                    //Layer
                    for(int k=0; k < lvl.chunks[i][j].layers.size();k++){
                        if(lvl.chunks[i][j].layers.get(k) instanceof BlockLayer){//==========================================================================================================

                            //Draws Blocks
                            BlockLayer blayer = (BlockLayer) lvl.chunks[i][j].layers.get(k);//Had to do this to make it work

                            for(int l=0; l < blayer.blocks.length; l++){//row
                                for(int m=0; m < blayer.blocks[0].length;m++){//col

                                    //Draws Blocks, checks to make sure inside bounds
                                    //Computes Block size:: <Current Row "l">*<BlockSize> + <Current Chunk Row "i">*<BlockSize>
                                    if(cam.isInside((m*lvl.s.getSpriteSize()) + (j*lvl.s.getSpriteSize()*blayer.blocks.length), (l*lvl.s.getSpriteSize()) + (i*lvl.s.getSpriteSize()*blayer.blocks[0].length))){							

                                        g2d.drawImage(lvl.s.getBufferedImage(blayer.blocks[l][m].id), (m*lvl.s.getSpriteSize()) + (j*lvl.s.getSpriteSize()*blayer.blocks.length) - cam.getXmin(), (l*lvl.s.getSpriteSize()) + (i*lvl.s.getSpriteSize()*blayer.blocks[0].length) - cam.getYmin(), null);
                                    }
                                }	
                            }	
                        }else if(lvl.chunks[i][j].layers.get(k) instanceof SpriteLayer){

                            //Draws Sprites
                            SpriteLayer splyr = (SpriteLayer) lvl.chunks[i][j].layers.get(k);

                            for(int spt=0;spt<splyr.sprites.size();spt++){

                                //CameraFollowMode code
                                if(follow && splyr.sprites.get(spt) instanceof Player){
                                    pointToFollow[0] = (int)((j*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[0]) + ((double)lvl.s.getSpriteSize()*splyr.sprites.get(spt).loc.x));
                                    pointToFollow[1] = (int)((i*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[1]) + ((double)lvl.s.getSpriteSize()*splyr.sprites.get(spt).loc.y));
                                }


                                if(cam.isInside((int)((j*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[0]) + ((double)lvl.s.getSpriteSize()*splyr.sprites.get(spt).loc.x)),(int)((i*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[1]) + ((double)lvl.s.getSpriteSize()*splyr.sprites.get(spt).loc.y)))){
                                    g2d.drawImage(splyr.sprites.get(spt).getImage(), (int)((j*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[0]) + ((double)lvl.s.getSpriteSize()*splyr.sprites.get(spt).loc.x)) - cam.getXmin(),  (int)((i*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[1]) + ((double)lvl.s.getSpriteSize()*splyr.sprites.get(spt).loc.y)) - cam.getYmin(), null);
                                }
                            }
                        }else if(lvl.chunks[i][j].layers.get(k) instanceof BGLayer){

                            //Draws BG, takes care of positioning
                            BGLayer bglyr = (BGLayer) lvl.chunks[i][j].layers.get(k);

                            //Does not render before origin
                            for(int bglj = (j*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[0]); bglj<cam.getXmax()+cam.getBuffer(); bglj=bglj+bglyr.getBufferedImage().getWidth()){
                                for(int bglk = (i*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[1]); bglk<cam.getYmax()+cam.getBuffer(); bglk=bglk+bglyr.getBufferedImage().getHeight()){
                                    if(cam.isInside((j*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[0]), (i*lvl.s.getSpriteSize()*lvl.chunks[i][j].getBlockSize()[1]))){
                                        g2d.drawImage(bglyr.getBufferedImage(),bglj + bglyr.XOffset - cam.getXmin(),bglk + bglyr.YOffset - cam.getYmin(),null);

                                        if(!bglyr.isYRepeating()){//Ends if not repeating
                                            bglk = cam.getYmax()+cam.getBuffer();
                                        }
                                    }
                                }
                                if(!bglyr.isXRepeating()){//Ends if not repeating
                                    bglj = cam.getXmax()+cam.getBuffer();
                                }
                            }
                        }else{
                            System.out.println("WARN:: unknown Layer type at " + k);
                        }
                    }
                }else{
                    lvl.chunks[i][j].isActive = false;
                    //If Chunk is not in Camera (is being displayed on the screen, it is inactive
                }
            }
        }
        //==============================<<Paints Level Global FG>>====================================
        for(int i=0;i<lvl.fg.length;i++){

            //Does not render before origin
            for(int j=0;j<cam.getXmax()+cam.getBuffer(); j=j+lvl.fg[i].getBufferedImage().getWidth()){

                for(int k=0;k<cam.getYmax()+cam.getBuffer();k=k+lvl.fg[i].getBufferedImage().getHeight()){

                    g2d.drawImage(lvl.fg[i].getBufferedImage(),j-cam.getXmin(),k-cam.getYmin(),null);

                    if(!lvl.fg[i].isYRepeating()){//Ends if not repeating
                        k = cam.getYmax()+cam.getBuffer();
                    }
                }
                if(!lvl.fg[i].isXRepeating()){//Ends if not repeating
                    j = cam.getXmax()+cam.getBuffer();
                }
            }
        }
        g2d.setColor(Color.GRAY);
        g2d.setFont(new Font("Sans-Serif", Font.PLAIN, 10));
        g2d.drawString("Resolution::" + WIDTH*SCALE + "x" + HEIGHT*SCALE, 10, 10);
        g2d.drawString("CameraPosition::" + "xmin: " + cam.getXmin() + " xmax: " + cam.getXmax() + " ymin: " + cam.getYmin() + " ymax: " + cam.getYmax(), 10,20);	
    }    

    /**This Takes Keyboard Input.
     * <p>
     * May be able to expand and use custom inputs using an array and have each index represent a certain keystroke, or something
     * @TODO Add Pause and Reset Buttons. Pause pauses all AI and movement. Reset reloads the level
     * 	
     * @author Brady
     *
     */
    private class Input implements KeyListener {

        /**Do this when Key is pressed
         * 
         */
        public void keyPressed(KeyEvent e){

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_UP){
                moveUP = true;
            } 
            if(key == KeyEvent.VK_DOWN){
                moveDN = true;
            }
            if(key == KeyEvent.VK_LEFT){
                moveLF = true;
            }
            if(key == KeyEvent.VK_RIGHT){
                moveRT = true;
            }
            if(key == KeyEvent.VK_SPACE){
                rst = true;

                if(currentMode == GameMode.TitleScreen){
                    currentMode = GameMode.ShooterLevel;
                    bgmusic.stop();
                    bgmusic = null;
                    bgmusic = AudioManager.loadSound(lvl.audioFileName, true);
                    bgmusic.play();
                }
                if(currentMode == GameMode.ShooterLevelFail){
                    currentMode = GameMode.TitleScreen;
                    bgmusic.stop();
                    bgmusic = null;
                    bgmusic = AudioManager.loadSound("Music/title.wav", true);
                    bgmusic.play();
                    loadLevel();
                }


            }
            if(key == KeyEvent.VK_C){

                //follow = true;
            }

            for(int i = 0; i < contObjects.size(); i++){
                Controllable cont = contObjects.get(i);
                for(Button temp : cont.getButtons()){
                    if(key == temp.getInput()){
                        temp.setState(true);
                    }
                }
            }

        }
        /**Do This when key is released
         * 
         */
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_UP){
                moveUP = false;
            } 

            if(key == KeyEvent.VK_DOWN ){
                moveDN = false;
            }
            if(key == KeyEvent.VK_LEFT){
                moveLF = false;
            }
            if(key == KeyEvent.VK_RIGHT){
                moveRT = false;
            }
            if(key == KeyEvent.VK_SPACE){
                rst = false;
            }
            if(key == KeyEvent.VK_C){
                if(follow){
                    follow = false;
                } else{
                    follow = true;
                }
            }

            for(int i = 0; i < contObjects.size(); i++){
                Controllable cont = contObjects.get(i);
                for(Button temp : cont.getButtons()){
                    if(key == temp.getInput()){
                        temp.setState(false);
                    }
                }
            }

        }
        public void keyTyped(KeyEvent arg0) {
            // TODO Auto-generated method stub

        }
    }

    private class DoGameLoop implements Runnable {

        public void run(){

            while(true){
                try {
                    Thread.sleep(SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(currentMode == GameMode.ShooterLevel){
                    lvl.manageChunks();
                    doAI();
                    //doPhysics();
                    //doControls();
                    doCamera();
                    doCollision();
                    doLevelOutcome();
                }
            }
        }
    }

    /**Does AI Method. Gets around Concurrency Issues
     * 
     */
    private void doAI(){
        for(int i=0;i<lvl.chunks.length;i++){
            for(int j=0;j<lvl.chunks[0].length;j++){

                if(lvl.chunks[i][j].isActive && lvl.chunks[i][j].hasSpriteLayer()){
                    //This is for UpdateChunk, gives 3x3 block of Chunks to Sprites to use for AI

                    //Has an issue where the row2, col2 chunks are Null. 
                    Chunk[][] temp = new Chunk[3][3];
                    //System.out.println("Now on Chunk " + i + " " + j);
                    //Stops at 2 because I am dumb, or something. Duh!
                    for(int m = i - 1; m < i + 2; m++){
                        for(int n = j - 1; n < j + 2; n++){
                            if(m >= 0 && n >= 0 && m < lvl.chunks.length && n < lvl.chunks[0].length){
                                temp[m-i+1][n-j+1] = lvl.chunks[m][n];
                                //System.out.println("NormChunk in updateChunk: " + (m-i+1) + " " + (n-j+1));
                            }else{
                                temp[m-i+1][n-j+1] = null;
                                //System.out.println("NullChunk in updateChunk: " + (m-i+1) + " " + (n-j+1));
                            }
                        }
                    }

                    for(int k=0; k<lvl.chunks[i][j].layers.size();k++){

                        if(lvl.chunks[i][j].layers.get(k) instanceof SpriteLayer){

                            /**
                             * @TODO
                             * Clean up code - Since I can get access to a spritelayer without the use of another new object, it is pointless and probably slows down the program. CLEANITUP!!
                             * 
                             * 
                             */
                            for(int l=0;l < ((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.size();l++){

                                if(((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.get(l) instanceof AI){
                                    //Does AI Methods, including methods that may add other Sprites/Blocks/Backgrounds

                                    //((AI)((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.get(l)).updateChunk(temp);//Old Method. Will not work anymore

                                    ((AI)((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.get(l)).updateChunk(temp);
                                    ((AI)((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.get(l)).doAI();

                                    Sprite tmp = ((AI)((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.get(l)).getChildren();
                                    if(tmp != null) {
                                        //System.out.println("NewChild:" + tmp);
                                        ((SpriteLayer) lvl.chunks[i][j].layers.get(k)).sprites.add(tmp);
                                    }
                                }
                            }	
                        }
                    }
                }
            }
        }
    }

    //These methods are meant to help reduce the concurrency issues present in the design that uses multiple threads
    /**Goes through level and gets Controllable Objects, and also checks to see if Controllable Objects are actually present in the Level
     * 
     */
    private void doControls(){

        HashSet<Controllable> foundCont = new HashSet<Controllable>();

        for(int i=0;i<lvl.chunks.length;i++){
            for(int j=0;j<lvl.chunks[0].length;j++){

                if(lvl.chunks[i][j].isActive && lvl.chunks[i][j].hasSpriteLayer()){
                    for(int lyr = 0; lyr < lvl.chunks[i][j].layers.size(); lyr++){

                        if(lvl.chunks[i][j].layers.get(lyr) instanceof SpriteLayer){
                            for(int spt = 0; spt < ((SpriteLayer)lvl.chunks[i][j].layers.get(lyr)).sprites.size();spt++){
                                if(((SpriteLayer)lvl.chunks[i][j].layers.get(lyr)).sprites.get(spt) instanceof Controllable){
                                    foundCont.add((Controllable)((SpriteLayer)lvl.chunks[i][j].layers.get(lyr)).sprites.get(spt));
                                    contSet.add((Controllable)((SpriteLayer)lvl.chunks[i][j].layers.get(lyr)).sprites.get(spt));
                                }
                            }
                        }
                    }
                }
            }
        }

        //Checks if Controllable Object is still in the Level

        //DoPlayerControls
    }
    /**Does Camera Movement (Since implementing Buttons directly in the Camera class would break the hierarchy, and lead to a lot of messy code)
     * 
     */
    private void doCamera(){
        if(moveUP){
            cam.move(0, -1);
        }
        if(moveDN){
            cam.move(0, 1);
        }
        if(moveLF){
            cam.move(-1, 0);
        }
        if(moveRT){
            cam.move(1, 0);
        }
        if(rst){
            cam.setOrigin(0, 0);
        }
        if(follow){//Follow Player Camera Mode

            if(!cam.isInside(pointToFollow[0], pointToFollow[1])){

                if(cam.getXmin() < pointToFollow[0]){
                    cam.move(10, 0);
                }
                if(cam.getXmax() > pointToFollow[0]){
                    cam.move(-10, 0);
                }
                if(cam.getYmin() < pointToFollow[1]){
                    cam.move(0, 10);
                }
                if(cam.getYmax() > pointToFollow[1]){
                    cam.move(0, -10);
                }
            }else{
                //Is inside Camera

                if(pointToFollow[0] - cam.getXmin() < WIDTH/2){
                    cam.move(-1, 0);
                }
                if(pointToFollow[0] - cam.getXmin() > WIDTH/2){
                    cam.move(1, 0);
                }
                if(pointToFollow[1] - cam.getYmin() < HEIGHT/2){
                    cam.move(0, -1);
                }
                if(pointToFollow[1] - cam.getYmin() > HEIGHT/2){
                    cam.move(0, 1);
                }

            }
        }

        if(bound){//Do Camera Boundaries
            if(cam.getXmin() < boundaries[0]){
                //System.out.println("Cam out of Bounds: Xmin");
                cam.setOrigin(boundaries[0], cam.getYmin());
            }
            if(cam.getYmin() < boundaries[1]){
                //System.out.println("Cam out of Bounds: Ymin");
                cam.setOrigin(cam.getXmin(), boundaries[1]);
            }
            if(cam.getXmax() > boundaries[2]){
                //System.out.println("Cam out of Bounds: Xmax");
                cam.setOrigin(boundaries[2] - WIDTH, cam.getYmin());
            }
            if(cam.getYmax() > boundaries[3]){
                //System.out.println("Cam out of Bounds: Ymax");
                cam.setOrigin(cam.getXmin(), boundaries[3] - HEIGHT);
            }
        }

    }
    /**Does Physics
     * 
     */
    private void doPhysics(){
        for(int i = 0; i < lvl.chunks.length; i++){//row
            for(int j = 0; j < lvl.chunks[0].length; j++){//col
                for(int k = 0; k < lvl.chunks[i][j].layers.size(); k++){//layers
                    if(lvl.chunks[i][j].layers.get(k) instanceof SpriteLayer){//Sprites
                        for(int l = 0; l < ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.size(); l++){
                            if(((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l) instanceof Physics){
                                ((Physics)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l)).doPhysics();
                                System.out.println("Physics done at Sprite: " + ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l));
                            }
                        }

                    }

                }
            }
        }
    }
    /**Does Collision between all Collidable Objects
     * @TODO Add Detection for Blocks(optional right now) and for Sprites in other Chunks that hang out and overlap with the current chunk.
     * 
     */
    private void doCollision(){
        //Right now, uses TEMP version of Collision detection. Will be improved later on.

        //Goes through sprites in Active Chunks and compares to other sprites in same chunk
        for(int i = 0; i < lvl.chunks.length; i++){//Row
            for(int j = 0; j < lvl.chunks[0].length; j++){//Col
                if(lvl.chunks[i][j].isActive){//If Active
                    for(int k = 0; k < lvl.chunks[i][j].layers.size(); k++){//Layers
                        if(lvl.chunks[i][j].layers.get(k) instanceof SpriteLayer){//SpriteLayer
                            for(int l = 0; l < ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.size(); l++){//Sprites
                                if(((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l) instanceof Collidable){//If Collision

                                    //System.out.println("FirstSprite: " + l + " " + ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l));

                                    Rectangle hitbox1 = ((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l)).getHitbox();
                                    hitbox1.setLocation((int) ((j * lvl.chunks[i][j].getBlockSize()[1] * lvl.s.getSpriteSize()) + ((double) ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l).loc.x * lvl.s.getSpriteSize())) ,  (int) ((i * lvl.chunks[i][j].getBlockSize()[0] * lvl.s.getSpriteSize()) + ((double) ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l).loc.y * lvl.s.getSpriteSize())));//Calc Location in Pixels
                                    //ChunkPosition.px + SpritePosition.px

                                    //Checks for Collisions
                                    for(int crow = -1; crow <= 1; crow++){//add to 'i'
                                        for(int ccol = -1; ccol <= 1; ccol++){//add to 'j'
                                            if(i + crow >= 0 && i + crow < lvl.chunks.length && j + ccol >= 0 && j + ccol < lvl.chunks[0].length){
                                                if(crow == 0 && ccol == 0 ){
                                                    for(int m = l + 1; m < ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.size(); m++){//Checking Other Sprites

                                                        //System.out.println(m);

                                                        if(((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m) instanceof Collidable){
                                                            //System.out.println("Checking " + ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m));

                                                            Rectangle hitbox2 = ((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m)).getHitbox();
                                                            hitbox2.setLocation((int) ((j * lvl.chunks[i][j].getBlockSize()[1] * lvl.s.getSpriteSize()) + ((double) ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m).loc.x * lvl.s.getSpriteSize())) ,  (int) ((i * lvl.chunks[i][j].getBlockSize()[0] * lvl.s.getSpriteSize()) + ((double) ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m).loc.y * lvl.s.getSpriteSize())));//Calc Location in Pixels

                                                            if(hitbox1.intersects(hitbox2)){
                                                                //System.out.println("PossibleCollision| " + ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l) + "\t" + ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m));
                                                                ((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m)).doCollision((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l));
                                                                ((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l)).doCollision((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(m));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    //If not same Chunk (change only where sprite is chosen by var 'l'
                                                    for(int lyr = 0; lyr < lvl.chunks[i + crow][j + ccol].layers.size(); lyr++){
                                                        if(lvl.chunks[i + crow][j + ccol].layers.get(lyr) instanceof SpriteLayer){
                                                            for(int m = 0; m < ((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.size(); m++){//Checking Other Sprites

                                                                //System.out.println(m);

                                                                if(((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m) instanceof Collidable){
                                                                    //System.out.println("Checking " + ((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m));

                                                                    Rectangle hitbox2 = ((Collidable)((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m)).getHitbox();
                                                                    hitbox2.setLocation((int) (((j + ccol) * lvl.chunks[i + crow][j + ccol].getBlockSize()[1] * lvl.s.getSpriteSize()) + ((double) ((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m).loc.x * lvl.s.getSpriteSize())) ,  (int) (((i + crow) * lvl.chunks[i + crow][j + ccol].getBlockSize()[0] * lvl.s.getSpriteSize()) + ((double) ((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m).loc.y * lvl.s.getSpriteSize())));//Calc Location in Pixels

                                                                    if(hitbox1.intersects(hitbox2)){
                                                                        //System.out.println("PossibleCollision| " + ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l) + "\t" + ((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m));
                                                                        ((Collidable)((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m)).doCollision((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l));
                                                                        ((Collidable)((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l)).doCollision((Collidable)((SpriteLayer)lvl.chunks[i + crow][j + ccol].layers.get(lyr)).sprites.get(m));

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**Handles the Level Outcome (Whether the player successfully reaches the end of the level or not.
     * 
     */
    private void doLevelOutcome(){
        for(int i = 0; i < lvl.chunks.length; i++){//Row
            for(int j = 0; j < lvl.chunks[0].length; j++){//Col
                for(int k = 0; k < lvl.chunks[i][j].layers.size(); k++){//Layers
                    if(lvl.chunks[i][j].layers.get(k) instanceof SpriteLayer){//SpriteLayer --Issue/NotReaching this code
                        for(int l = 0; l < ((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.size(); l++){//Sprites
                            if(((SpriteLayer)lvl.chunks[i][j].layers.get(k)).sprites.get(l) instanceof Player){
                                return;
                            }
                        }
                    }
                }
            }
        }
        currentMode = GameMode.ShooterLevelFail;
        bgmusic.stop();
        bgmusic = null;
        bgmusic = AudioManager.loadSound("sfx/longExplode.wav",false);
        bgmusic.play();
    }
}