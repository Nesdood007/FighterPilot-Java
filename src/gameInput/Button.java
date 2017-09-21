package gameInput;
/**This is mean to assist in the implementation of versatile, changable inputs in a game.
 * 
 * @author Brady O'Leary
 *
 */
public class Button {

    private String buttonName = "";//Name of the button
    private int buttonInputID = -1;//ID of the button
    
    private boolean currentState = false;
    private float currentValue = 0.0f;
    
    private int buttonInput;//The pointer of the button
    /**Constructor for Button Object.
     * 
     * @param name Name of button
     * @param id Integer ID of button
     * @param value Keyboard input Value that button will be checked for
     */
    public Button(String name, int id, int value){
	buttonName = name;
	buttonInputID = id;
	buttonInput = value;
    }
    /**Change the Keyboard Input Value
     * 
     * @param newValue Value to change
     * @return newValue
     */
    public int changeInput(int newValue){
	buttonInput = newValue;
	return newValue;
    }
    /**Get the name of the Button
     * 
     * @return Name of Button
     */
    public String getName(){
	return buttonName;
    }
    /**Get Integer ID of button
     * 
     * @return integer ID
     */
    public int getID(){
	return buttonInputID;
    }
    /**Get the Keyboard Input value associated with this button
     * 
     * @return
     */
    public int getInput(){
	return buttonInput;
    }
    /**Get current Boolean state of button
     * 
     * @return boolean Current State
     */
    public boolean getState(){
	return currentState;
    }
    /**Get floating point value of button (Doesn't have a lot of use yet)
     * 
     * @return float current value
     */
    public float getCurrentValue(){
	return currentValue;
    }
    /**Set the Boolean state of the button.
     * 
     * @param newState New state to be set to.
     * @return boolean new state of button.
     */
    public boolean setState(boolean newState){
	currentState = newState;
	return currentState;
    }
    
}
