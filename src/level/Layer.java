package level;

public class Layer {

    int id;
    private Location offset;//offset of blocks. Is in terms of block location, not pixel
    String name;

    public Layer(){

        offset = new Location(0.0,0.0);
        id = 0;
        name = "";

    }

    public Layer(int i){

        offset = new Location(0.0,0.0);
        id = i;
        name = "";

    }

    public Layer(int i,Location l){

        offset = l;
        id = i;
        name = "";

    }

    public int getUUID(){

        return id;
    }
    
    public String getName() {
        return name;
    }

    public void shift(double x,double y){

        offset = new Location(offset.x+x,offset.y+y);
    }

    public Location getOffset(){

        return offset;
    }

    //Debug
    public String toString(){

        return "Layer::ID: " + id;
    }

}
