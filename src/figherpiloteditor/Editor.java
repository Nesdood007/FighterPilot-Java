package figherpiloteditor;

import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//import javax.swing.JComponent;
//import javax.swing.JFrame;
import javax.swing.JPanel;

//import fighterpilot.Player;
import graphics.Camera;
import level.BGLayer;
import level.BlockLayer;
//import level.Level;
import level.SpriteLayer;

public class Editor extends JPanel implements Runnable{

    private static final long serialVersionUID = 1L;//So Java Cannot Complain

    public static final int SPEED = 10;//Wait time for all threads

    private int width = 0, height = 0; //Level Width and Height

    private int[][] coords = new int[100][2];
    private int coordPos = 0;

    /**Default Constructor
     * 
     */
    public Editor() {
        //Gives the JPanel actual Dimensions: The Size of the Entire Level
        if (LevelEditor.level != null) {
            height = LevelEditor.level.chunks.length * LevelEditor.level.s.getSpriteSize() * LevelEditor.level.chunks[0][0].getBlockSize()[0];
            width = LevelEditor.level.chunks[0].length * LevelEditor.level.s.getSpriteSize() * LevelEditor.level.chunks[0][0].getBlockSize()[1];
        }

        System.out.println("Dimensions of Level JPanel:" + width + ", " + height);

        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);

        //Sets Background Color
        setBackground(Color.BLUE);

        addMouseMotionListener(new Input());
        addMouseListener(new Input());
        (new Thread(this)).start();
    }

    /**Begin the Painting Loop
     * 
     */
    public void run() {
        while(true){
            try {
                Thread.sleep(SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            updateDimensions();
        }
    }

    /**Will update the width and height of the JFrame if it has changed, otherwise it will do nothing.
     * 
     */
    private void updateDimensions() {
        int currWidth = 0;
        int currHeight = 0;

        if (LevelEditor.level != null) {
            currHeight = LevelEditor.level.chunks.length * LevelEditor.level.s.getSpriteSize() * LevelEditor.level.chunks[0][0].getBlockSize()[0];
            currWidth = LevelEditor.level.chunks[0].length * LevelEditor.level.s.getSpriteSize() * LevelEditor.level.chunks[0][0].getBlockSize()[1];
        }

        if (currWidth != this.width || currHeight != this.height) {
            width = currWidth;
            height = currHeight;
            setMinimumSize(new Dimension(width, height));
            setMaximumSize(new Dimension(width, height));
            setPreferredSize(new Dimension(width, height));
            setSize(width, height);
            System.out.println("Dimensions of Level JPanel:" + width + ", " + height);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if (LevelEditor.level != null) {
            //Draw Level

            for(int i = 0; i < LevelEditor.level.bg.length; i++){//BGLayer to render
                for(int j = 0; j < width; j += LevelEditor.level.bg[i].getBufferedImage().getWidth()){
                    for(int k = 0; k < height; k += LevelEditor.level.bg[i].getBufferedImage().getHeight()){
                        g2d.drawImage(LevelEditor.level.bg[i].getBufferedImage() , j , k,null);
                        if(!LevelEditor.level.bg[i].isYRepeating()){//Ends if not repeating. Done so that it draws the first image then will stop
                            //k = height;
                            continue;
                        }
                    }
                    if(!LevelEditor.level.bg[i].isXRepeating()){//Ends if not repeating
                        //j = width;
                        continue;
                    }
                }
            }
            for(int i=0; i < LevelEditor.level.chunks.length ;i++){//row
                for(int j=0; j < LevelEditor.level.chunks[0].length; j++){//col
                    //Makes sure chunk is inside Viewing Area before rendering
                    {
                        LevelEditor.level.chunks[i][j].isActive = true;

                        //Layer
                        for(int k=0; k < LevelEditor.level.chunks[i][j].layers.size();k++){
                            if(LevelEditor.level.chunks[i][j].layers.get(k) instanceof BlockLayer){//==========================================================================================================

                                //Draws Blocks
                                BlockLayer blayer = (BlockLayer) LevelEditor.level.chunks[i][j].layers.get(k);//Had to do this to make it work

                                for(int l=0; l < blayer.blocks.length; l++){//row
                                    for(int m=0; m < blayer.blocks[0].length;m++){//col

                                        //Draws Blocks, checks to make sure inside bounds
                                        //Computes Block size:: <Current Row "l">*<BlockSize> + <Current Chunk Row "i">*<BlockSize>
                                        {                            

                                            g2d.drawImage(LevelEditor.level.s.getBufferedImage(blayer.blocks[l][m].id), (m*LevelEditor.level.s.getSpriteSize()) + (j*LevelEditor.level.s.getSpriteSize()*blayer.blocks.length), (l*LevelEditor.level.s.getSpriteSize()) + (i*LevelEditor.level.s.getSpriteSize()*blayer.blocks[0].length), null);
                                        }
                                    }   
                                }   
                            }else if(LevelEditor.level.chunks[i][j].layers.get(k) instanceof SpriteLayer){

                                //Draws Sprites
                                SpriteLayer splyr = (SpriteLayer) LevelEditor.level.chunks[i][j].layers.get(k);

                                for(int spt=0;spt<splyr.sprites.size();spt++){

                                    {
                                        g2d.drawImage(splyr.sprites.get(spt).getImage(), (int)((j*LevelEditor.level.s.getSpriteSize()*LevelEditor.level.chunks[i][j].getBlockSize()[0]) + ((double)LevelEditor.level.s.getSpriteSize()*splyr.sprites.get(spt).loc.x)),  (int)((i*LevelEditor.level.s.getSpriteSize()*LevelEditor.level.chunks[i][j].getBlockSize()[1]) + ((double)LevelEditor.level.s.getSpriteSize()*splyr.sprites.get(spt).loc.y)), null);
                                    }
                                }
                            }else if(LevelEditor.level.chunks[i][j].layers.get(k) instanceof BGLayer){

                                //Draws BG, takes care of positioning
                                BGLayer bglyr = (BGLayer) LevelEditor.level.chunks[i][j].layers.get(k);

                                //Does not render before origin
                                for(int bglj = (j*LevelEditor.level.s.getSpriteSize()*LevelEditor.level.chunks[i][j].getBlockSize()[0]); bglj < width; bglj=bglj+bglyr.getBufferedImage().getWidth()){
                                    for(int bglk = (i*LevelEditor.level.s.getSpriteSize()*LevelEditor.level.chunks[i][j].getBlockSize()[1]); bglk<height; bglk=bglk+bglyr.getBufferedImage().getHeight()){
                                        {
                                            g2d.drawImage(bglyr.getBufferedImage(),bglj + bglyr.XOffset ,bglk + bglyr.YOffset ,null);

                                            if(!bglyr.isYRepeating()){//Ends if not repeating
                                                bglk = height;
                                            }
                                        }
                                    }
                                    if(!bglyr.isXRepeating()){//Ends if not repeating
                                        bglj = width;
                                    }
                                }
                            }else{
                                System.out.println("WARN:: unknown Layer type at " + k);
                            }
                        }
                    }
                }
            }
            //==============================<<Paints Level Global BG>>====================================
            for(int i=0;i<LevelEditor.level.fg.length;i++){

                //Does not render before origin
                for(int j=0;j<width; j=j+LevelEditor.level.fg[i].getBufferedImage().getWidth()){

                    for(int k=0;k<height;k=k+LevelEditor.level.fg[i].getBufferedImage().getHeight()){
                        g2d.drawImage(LevelEditor.level.fg[i].getBufferedImage(),j,k,null);
                        if(!LevelEditor.level.fg[i].isYRepeating()){//Ends if not repeating
                            k = height;
                        }
                    }
                    if(!LevelEditor.level.fg[i].isXRepeating()){//Ends if not repeating
                        j = width;
                    }
                }
            }
        } else {
            g2d.drawString("Create a New Level First", 10, 10);
        }

        g2d.setColor(Color.RED);

        //Draws Test Markers
        for (int i = 0; i < coords.length; i++) {
            g2d.drawRect(coords[i][0], coords[i][1], 2, 2);
        }

    }

    /**Places a marker when clicked
     * 
     * @param x X Coord
     * @param y Y Coord
     */
    private void placeMarker(int x, int y) {
        if (coordPos >= coords.length) {
            coordPos = 0;
        }
        
        coords[coordPos][0] = x;
        coords[coordPos][1] = y;
        coordPos++;
    }

    private class Input implements MouseMotionListener, MouseListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            placeMarker(e.getX(), e.getY());
        }

        @Override
        public void mouseEntered(MouseEvent e) {


        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }
}
