package item;

public class StatItem extends Item{

    
    

    //Stats entries: health, power, def, spc, spd
    //For modification:
    private int healthMod;
    private int powerMod;
    private int defMod;
    private int specialMod;
    private double speedMod;

    //Mode and time length
    private Mode mode;
    private int lengthOfEffect;



    public StatItem(String name, int id, double weight, String desc, Mode mode, int healthMod, int powMod, int defMod, int spcMod, double spdMod){
	super(name, id, weight,desc);
    }
    
    @Override
    public String getEffects() {
	// TODO Auto-generated method stub
	return null;
    }
}
