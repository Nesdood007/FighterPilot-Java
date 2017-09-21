package level;

import java.util.ArrayList;

public class SpriteLayer extends Layer {

    public ArrayList<Sprite> sprites;


    public SpriteLayer(int layerid) {
	super(layerid);

	sprites = new ArrayList<Sprite>(0);
    }

    public SpriteLayer(int layerid,Sprite[] s){
	super(layerid);

	sprites = new ArrayList<Sprite>(0);

	for(int i=0;i<s.length;i++){
	    sprites.add(s[i]);
	}
    }

    //Debug
    public String toString(){
	return "SpriteLayer| Contains:" + sprites;
    }






}
