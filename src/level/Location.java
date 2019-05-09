package level;

public class Location {

    public double x,y;


    public Location(double a, double b){
	x = a;
	y = b;

    }


    //Debug
    public String toString(){
	return "Location::x: " + x + "\ty:" + y;

    }

}
