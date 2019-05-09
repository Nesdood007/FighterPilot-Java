package level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**BackGroundLayer houses a Background Image.
 * 
 * @author Brady
 *
 */
public class BGLayer extends Layer {

    private BufferedImage b;

    private boolean repeatX,scrollX,repeatY,scrollY;

    private int[] pixelSize = new int[2];

    private String fileName = "";

    public double scrollFactorX,scrollFactorY;//in terms of Blocks: .5 = moves .5 block for 1 block camera movement

    public int XOffset, YOffset;
    /**Creates a BGLayer.
     * 
     * @param layerid Layer ID
     */
    public BGLayer(int layerid) {
        super(layerid);

        try{
            b = ImageIO.read(new File("BG0.png"));
            fileName = "BG0.png";
        } catch(IOException f){
            System.out.println("@BG-Warning:Cannot find image\n" + f);
            b = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
        }


        scrollFactorX = 1.0;
        scrollFactorY = 1.0;

        repeatX = false;//chg to FALSE --testing repeating bgs in draw method
        repeatY = false;//chg to FALSE --testing repeating bgs in draw method
        scrollX = false;
        scrollY = false;

        pixelSize[0] = b.getWidth();
        pixelSize[1] = b.getHeight();

        XOffset = 0;
        YOffset = 0;
    }
    /**This Creates a new Background Layer
     * 
     * @param layerid Layer ID
     * @param fname Filename of Image
     */
    public BGLayer(int layerid,String fname) {
        super(layerid);

        fileName = fname;
        try{
            b = ImageIO.read(new File(fname));
        } catch(IOException e){
            System.out.println(e);
            try{
                b = ImageIO.read(new File("x.png"));
            } catch(IOException f){
                System.out.println("@BG-Warning:Cannot find image\n" + f);
                b = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
            }
        }

        scrollFactorX = 1.0;
        scrollFactorY = 1.0;

        repeatX = false;
        repeatY = false;
        scrollX = false;
        scrollY = false;

        pixelSize[0] = b.getWidth();
        pixelSize[1] = b.getHeight();

        XOffset = 0;
        YOffset = 0;
    }
    /**Creates Background Layer
     * 
     * @param layerid Layer ID
     * @param i BufferedImage image
     */
    public BGLayer(int layerid,BufferedImage i) {
        super(layerid);
        
        fileName = "";
        
        b = i;
        scrollFactorX = 1.0;
        scrollFactorY = 1.0;

        repeatX = false;
        repeatY = false;
        scrollX = false;
        scrollY = false;

        pixelSize[0] = b.getWidth();
        pixelSize[1] = b.getHeight();

        XOffset = 0;
        YOffset = 0;
    }
    /**Creates a Background Layer
     * 
     * @param layerid ID of Layer
     * @param i Background Image
     * @param sfx ScrollFactorX
     * @param sfy ScrollFactorY
     * @param rx RepeatX
     * @param ry RepeatY
     * @param sx ScrollX
     * @param sy ScrollY
     */
    public BGLayer(int layerid, BufferedImage i,double sfx,double sfy, boolean rx, boolean ry, boolean sx, boolean sy) {
        super(layerid);
        
        fileName = "";

        b = i;
        scrollFactorX = sfx;
        scrollFactorY = sfy;

        repeatX = rx;
        repeatY = ry;
        scrollX = sx;
        scrollY = sy;

        pixelSize[0] = b.getWidth();
        pixelSize[1] = b.getHeight();

        XOffset = 0;
        YOffset = 0;
    }
    /**Creates a new Background Layer
     * 
     * @param layerid Layer ID
     * @param fname Filename
     * @param sfx ScrollFactor x : sfx > 1 will move faster than the camera, sfx == 1 will move at-speed of cam, sfx < 1 will move slower than camera.
     * @param sfy ScrollFactor y : sfy > 1 will move faster than the camera, sfy == 1 will move at-speed of cam, sfy < 1 will move slower than camera.
     * @param rx Repeat x
     * @param ry Repeat y
     */
    public BGLayer(int layerid,String fname,double sfx,double sfy, boolean rx, boolean ry) {
        super(layerid);
        
        fileName = fname;

        try{
            b = ImageIO.read(new File(fname));
        } catch(IOException e){
            System.out.println(e);
            try{
                b = ImageIO.read(new File("x.png"));
            } catch(IOException f){
                System.out.println("@BG-Warning:Cannot find image\n" + f);
                b = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
            }
        }

        scrollFactorX = sfx;
        scrollFactorY = sfy;

        repeatX = rx;
        repeatY = ry;

        if(scrollFactorX != 1.0){
            scrollX = true;
        }else{
            scrollX = false;
        }

        if(scrollFactorY != 1.0){
            scrollY = true;
        }else{
            scrollY = false;
        }
        pixelSize[0] = b.getWidth();
        pixelSize[1] = b.getHeight();

        XOffset = 0;
        YOffset = 0;
    }
    /**Creates a new Background Layer
     * 
     * @param layerid Layer ID
     * @param fname Filename
     * @param sfx ScrollFactor x : sfx > 1 will move faster than the camera, sfx == 1 will move at-speed of cam, sfx < 1 will move slower than camera.
     * @param sfy ScrollFactor y : sfy > 1 will move faster than the camera, sfy == 1 will move at-speed of cam, sfy < 1 will move slower than camera.
     * @param rx Repeat x
     * @param ry Repeat y
     * @param XOffset Offset for Image in Pixels
     * @param YOffset Offset for Image in Pixels
     */
    public BGLayer(int layerid,String fname,double sfx,double sfy, boolean rx, boolean ry, int XOffset, int YOffset) {
        super(layerid);
        
        fileName = fname;

        try{
            b = ImageIO.read(new File(fname));
        } catch(IOException e){
            System.out.println(e);
            try{
                b = ImageIO.read(new File("x.png"));
            } catch(IOException f){
                System.out.println("@BG-Warning:Cannot find image\n" + f);
                b = new BufferedImage(8, 8, BufferedImage.OPAQUE);//8x8 is default size
            }
        }

        scrollFactorX = sfx;
        scrollFactorY = sfy;

        repeatX = rx;
        repeatY = ry;

        if(scrollFactorX != 1.0){
            scrollX = true;
        }else{
            scrollX = false;
        }

        if(scrollFactorY != 1.0){
            scrollY = true;
        }else{
            scrollY = false;
        }
        pixelSize[0] = b.getWidth();
        pixelSize[1] = b.getHeight();

        this.XOffset = XOffset;
        this.YOffset = YOffset;
    }
    /**Checks if X is Scrolling
     * 
     * @return true if scrolling
     */
    public boolean isXScrolling(){

        return scrollX;
    }
    /**Checks if Y is scrolling
     * 
     * @return true if Y is scrolling
     */
    public boolean isYScrolling(){

        return scrollY;
    }
    /**Checks if X is repeating
     * 
     * @return true if X is repeating
     */
    public boolean isXRepeating(){

        return repeatX;
    }
    /**Checks if Y is repeating
     * 
     * @return true if Y is repeating
     */
    public boolean isYRepeating(){

        return repeatY;
    }
    /**Gets BufferedImage
     * 
     * @return Background Image
     */
    public BufferedImage getBufferedImage(){

        return b;
    }
    /**Gets Image Pixel Size
     * 
     * @return array of {x, y} pixel size
     */
    public int[] getPixelSize(){

        return pixelSize;
    }
    
    public String getFileName() {
        return fileName;
    }

    //Debug
    /**Debug
     * 
     */
    public String toString(){

        return "BGLayer:: ID: " + super.id;
    }
}
