package engine;
/**This class keep track of Time for use by the game.
 * 
 * @author Brady O'Leary
 *
 */
public class Time {

    private static long lastTime = 0L;
    private static long deltaTime;
    private static long avgDeltaTime;

    /**Updates the time from which DeltaTime is measured
     * 
     */
    public static long updateDeltaTime() {
        deltaTime = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(avgDeltaTime == 0L) {
            avgDeltaTime = deltaTime;
        } else {
            avgDeltaTime += deltaTime;
            avgDeltaTime /= 2;
        }
        return avgDeltaTime;
    }

    public static long getDeltaTime() {
        return deltaTime;
    }


}
