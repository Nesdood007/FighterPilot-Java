package gameEvent;

public class GameEvent {

    //Public Declarations for GameEvents

    public static final int DO_NOTHING = 0;
    public static final int End_LEVEL = 1;


    //Class-Specific Stuff

    //I plan on making this GameEvent class as a carrier for the GameEvent commands passed on by other classes, namely sprites.

    //How will this be made so that the AI is not deaf and blind???? --the way it is designed: it cannot access location of surrounding objects...

    private GE[] evnt;



    public GameEvent(int e){
	evnt = new GE[1];
	evnt[0] = new GE(e,"");
    }

    public GameEvent(int e, String a){
	evnt = new GE[1];
	evnt[0] = new GE(e,a);
    }

    public GameEvent(int[] e, String[] a){

	int len=0;

	if(e.length > a.length){
	    len = a.length;
	}else{
	    len = e.length;
	}

	for(int i = 0; i<len;i++){

	    evnt[i] = new GE(e[i],a[i]);
	}
    }


    public int[] readEvents(){

	int[] e = new int[evnt.length];

	for(int i=0;i<e.length;i++){
	    e[i] = evnt[i].evnt;

	}

	return e;

    }

    public String[] readArgs(){

	String[] s = new String[evnt.length];

	for(int i=0;i<s.length;i++){
	    s[i] = evnt[i].arg;

	}

	return s;

    }


    private class GE{

	public String arg;
	public int evnt;


	public GE(int e,String a){
	    evnt = e;
	    arg = a;

	}


    }

}
