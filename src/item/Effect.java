package item;

import gameEvent.Stats;

/**This is designed to add functionality to Items. Effects are a change in stats for a given amount of time.
 * 
 * @author Brady
 *
 */
public class Effect {

    private String name;
    private int id;
    private boolean isExpired = false;

    private Stats parentObject;
    private Mode mode;
    private int duration;
    private int currentDuration = 0;

    //Old Values, stored in the case that the mode.replace is selected.
    private int oldHealth;
    private int oldPower;
    private int oldDefense;
    private int oldSpecial;
    private double oldSpeed;
    //Modifiers
    private int healthMod;
    private int powerMod;
    private int defenseMod;
    private int specialMod;
    private double speedMod;

    /**Creates a new Effect. Use 0 (or 0.0) for "no Change"
     * 
     * @param parentObject The Parent Object that has effect
     * @param name Name of Effect
     * @param id ID of Effect
     * @param mode Math Mode of effect, i.e. what operation the efect will do to the parentObject's stats
     * @param duration Duration of the effect. Measured in updates
     * @param healthMod Modifier for the Health Stat. Put 0 for no change
     * @param powMod Modifier for the Power Stat. Put 0 for no change
     * @param defMod Modifier for the Defense Stat. Put 0 for no change
     * @param spcMod Modifier for the Special Stat. Put 0 for no change
     * @param spdMod Modifier for the Speed Stat. Put 0.0 for no change
     */
    public Effect(Stats parentObject, String name, int id, Mode mode,int duration, int healthMod, int powMod, int defMod, int spcMod, double spdMod){
	this.parentObject = parentObject;
	this.name = name;
	this.id = id;
	this.mode = mode;
	this.healthMod = healthMod;
	this.powerMod = powMod;
	this.defenseMod = defMod;
	this.specialMod = spcMod;
	this.speedMod = spdMod;
	//Get Old Values
	this.oldHealth = parentObject.getCurrentHealth();
	this.oldPower = parentObject.getCurrentPower();
	this.oldDefense = parentObject.getCurrentDefense();
	this.oldSpecial = parentObject.getCurrentDefense();
	this.oldSpeed = parentObject.getCurrentSpeed();
    }

    public String getName(){
	return name;
    }

    public int getID(){
	return id;
    }

    public Mode getMode(){
	return mode;
    }

    public int getDuration(){
	return duration;
    }

    public int getHealthModifier(){
	return healthMod;
    }
    public int getPowerModifier(){
	return powerMod;
    }
    public int getDefenseModifier(){
	return defenseMod;
    }
    public int getSpecialModifier(){
	return specialMod;
    }
    public double getSpeedModifier(){
	return speedMod;
    }
    
    public boolean isExpired(){
	return isExpired;
    }

    public void updateEffect(){
	switch(mode){
	case Add : //DoStuff
	    if(healthMod != 0){
		parentObject.setHealth(parentObject.getCurrentHealth() + healthMod);
	    }
	    if(powerMod != 0){
		parentObject.setPower(parentObject.getCurrentPower() + powerMod);
	    }
	    if(defenseMod != 0){
		parentObject.setDefense(parentObject.getCurrentDefense() + defenseMod);
	    }
	    if(specialMod != 0){
		parentObject.setSpecial(parentObject.getCurrentSpecial() + specialMod);
	    }
	    if(speedMod != 0.0){
		parentObject.setSpeed(parentObject.getCurrentSpeed() + speedMod);
	    }
	    break;

	case Multiply : //DoStuff
	    if(healthMod != 0){
		parentObject.setHealth(parentObject.getCurrentHealth() * healthMod);
	    }
	    if(powerMod != 0){
		parentObject.setPower(parentObject.getCurrentPower() * powerMod);
	    }
	    if(defenseMod != 0){
		parentObject.setDefense(parentObject.getCurrentDefense() * defenseMod);
	    }
	    if(specialMod != 0){
		parentObject.setSpecial(parentObject.getCurrentSpecial() * specialMod);
	    }
	    if(speedMod != 0.0){
		parentObject.setSpeed(parentObject.getCurrentSpeed() * speedMod);
	    }
	    break;

	case Replace : //DoStuff
	    if(healthMod != 0){
		parentObject.setHealth(healthMod);
	    }
	    if(powerMod != 0){
		parentObject.setPower(powerMod);
	    }
	    if(defenseMod != 0){
		parentObject.setDefense(defenseMod);
	    }
	    if(specialMod != 0){
		parentObject.setSpecial(specialMod);
	    }
	    if(speedMod != 0.0){
		parentObject.setSpeed(speedMod);
	    }
	    break;
	}
	System.out.println("Done.");
	currentDuration++;
	
	if(currentDuration >= duration){
	    if(mode == Mode.Replace){
		if(healthMod != 0){
			parentObject.setHealth(oldHealth);
		    }
		    if(powerMod != 0){
			parentObject.setPower(oldPower);
		    }
		    if(defenseMod != 0){
			parentObject.setDefense(oldDefense);
		    }
		    if(specialMod != 0){
			parentObject.setSpecial(oldSpecial);
		    }
		    if(speedMod != 0.0){
			parentObject.setSpeed(oldSpeed);
		    }
	    }
	    isExpired = true;
	}
	
    }



}
