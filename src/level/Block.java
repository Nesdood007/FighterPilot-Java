package level;
public class Block {

    public String name;
    public boolean transparent;
    public int id;
    public int data_value;

    public Block(){
	name = "nothing";
	transparent = false;
	id = 0;
	data_value = 0;


    }

    public Block(String n, boolean t, int i, int dv){
	name = n;
	transparent = t;
	id = i;
	data_value = dv;

    }

    //Debug
    public String toString(){

	return "Block::Name: " + name + " Transparent: " + transparent + " ID: " + id + " Data_Value: " + data_value;
    }

}
