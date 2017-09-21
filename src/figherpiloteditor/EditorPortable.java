package figherpiloteditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EditorPortable extends JPanel implements Runnable{

    private static final long serialVersionUID = 1L;//So Java Cannot Complain

    public static final int SPEED = 10;//Wait time for all threads

    private int[][] coords = new int[3][500];//X, Y, color
    private int currentPos = 0;
    private boolean enterMode = false;
    private int enterThreshold = 0;//Set, then subtract until zero
    private int enterSpeed = 5;
    private int[] enterPosition = new int[2];
    private boolean dragging = false;
    private boolean click = false;

    /**Default Constructor
     * 
     */
    public EditorPortable() {
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
            doMovement();
            repaint();
        }
    }

    private void doMovement() {
        //Do the Enter Threshold so that enter Mode will eventually end.
        if(enterThreshold > 0) {
            enterThreshold--;
            if(enterThreshold <= 0) enterMode = false;
        }
        if (enterMode) {
            for (int i = 0; i < coords[0].length; i++) {
                if (coords[0][i] != enterPosition[0] || coords[1][i] != enterPosition[1]) {
                    coords[2][i] = 2;
                    if(Math.sqrt(Math.pow(Math.abs(coords[0][i] - enterPosition[0]), 2) + Math.pow(Math.abs(coords[1][i] - enterPosition[1]), 2)) > enterSpeed) {
                        //Move at Slope * enterSpeed
                        int slope = 0;
                        if (coords[0][i] - enterPosition[0] != 0) {
                            slope = (coords[1][i] - enterPosition[1]) / (coords[0][i] - enterPosition[0]);
                        }
                        coords[0][i] -= enterSpeed * slope;
                        coords[1][i] -= enterSpeed * slope;
                    } else {
                        coords[0][i]--;
                        coords[1][i]--;
                    }
                }
            }
        }

        if (click) {
            for (int i = 0; i < coords[0].length; i++) {
                if (coords[1][i] > 0) {
                    coords[1][i]--;
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < coords[0].length; i++) {
            if (coords[2][i] == 1) {
                g2d.setColor(Color.BLUE);
            } else if (coords[2][i] == 2) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLACK);
            }
            g2d.drawRect(coords[0][i], coords[1][i], 2, 2);
        }
    }

    private void addCoord(int x, int y, boolean blue) {
        if(currentPos >= coords[0].length) {
            currentPos = 0;
        }
        coords[0][currentPos] = x;
        coords[1][currentPos] = y;
        if (blue) {
            coords[2][currentPos] = 1;
        } else {
            coords[2][currentPos] = 0;
        }
        currentPos++;
    }

    private class Input implements MouseMotionListener, MouseListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            addCoord(e.getX(), e.getY(), true);
            doMovement();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            System.out.println(e);
            addCoord(e.getX(), e.getY(), false);
            doMovement();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            enterMode = true;
            enterPosition[0] = e.getX();
            enterPosition[1] = e.getY();
            enterThreshold = 100;
            
            doMovement();

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            click = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            click = false;
        }
    }
}
