package graphics;

/**This Class contains all variables for a "Camera" to prevent unnecessary chunks from loading and being rendered
 * 
 * @author Brady
 *
 */
public class Camera{

    private int xsize, ysize;
    private int buffer;

    private int xmin,xmax,ymin,ymax;

    /**Constructor for Camera.
     * 
     * @param x Size of X axis in Pixels
     * @param y Size of Y Axis in Pixels
     * @param b Size of Buffer in Pixels
     */
    public Camera(int x, int y, int b){

        xsize = x;
        ysize = y;

        xmin = 0;
        xmax = xsize;
        ymin = 0;
        ymax = ysize;

        buffer = b;
    }

    /**Checks to see if the given point is inside of the Screen
     * 
     * @param x X Coordinate of point
     * @param y Y Coordinate of point
     * @return true if the points are inside of the camera area
     */
    public boolean isInside(int x, int y) {

        return x >= xmin-buffer && x <= xmax+buffer && y >= ymin-buffer && y <= ymax+buffer;
    }

    /**Moves the Camera area by increasing the X and Y axis by specified number of pixels.
     * 
     * @param x Moves X axis in Pixels
     * @param y Moves Y axis in Pixels
     */
    public void move(int x, int y){

        xmin += x;
        xmax += x;

        ymin += y;
        ymax += y;
    }

    /**Moves the Camera to the specified point
     * 
     * @param x X Coordinate to set Camera to
     * @param y Y Coordinate to set Camera to
     */
    public void setOrigin(int x, int y){

        xmin = x;
        xmax = x + xsize;

        ymin = y;
        ymax = y + ysize;

    }

    /**Returns the XMin
     * 
     * @return Minimum of X Axis
     */
    public int getXmin(){
        return xmin;
    }

    /**Returns xMax
     * 
     * @return Maximum of X Axis
     */
    public int getXmax(){
        return xmax;
    }

    /**Returns yMin
     * 
     * @return Minimum of Y Axis
     */
    public int getYmin(){
        return ymin;
    }

    /**Returns YMax
     * 
     * @return Maximum of Y Axis
     */
    public int getYmax(){
        return ymax;
    }

    /**Returns the Buffer Space
     * 
     * @return Buffer Space in Pixels
     */
    public int getBuffer(){
        return buffer;
    }
}