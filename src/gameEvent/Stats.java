package gameEvent;
/**Contains all Stats, such as Health, Power, and Defense
 * 
 * @author Brady
 *
 */
public interface Stats {
    
    int getCurrentHealth();
    int getCurrentPower();
    int getCurrentDefense();
    int getCurrentSpecial();
    double getCurrentSpeed();

    //Initial Stats

    int getInitialHealth();
    int getInitialPower();
    int getInitialDefense();
    int getInitialSpecial();
    double getInitialSpeed();
    
    //Set Stats, makes implementing Effects easier.
    void setHealth(int newHealth);
    void setPower(int newPower);
    void setDefense(int newDefense);
    void setSpecial(int newSpecial);
    void setSpeed(double newSpeed);
}
