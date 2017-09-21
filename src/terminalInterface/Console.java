package terminalInterface;

import java.util.Scanner;

/**MY experiment with sorcery with Classes in Java
 * 
 * @author Brady
 *
 */
public class Console {

    private final String PACKAGENAME = "terminalInterface.";
    private final String[] TITLE = {
	    "      ________      __    __",
	    "     / ______/     / /   / /_ Featuring LevelFormat.",
	    "    / /___________/ /___/ __/____  ____",
	    "   / ____/ / __  / __  / /  / __ |/ ___\\______      __",
	    "  / /   / / /_/ / / / / /__/ /_/_/ / __  /_/ /     / /_",
	    " /_/   /_/___  /_/ /_/\\___/\\____/_/ / / /_/ /_____/ __/  \\\\",
	    "        ____/ /__________________/ ____/ / / __  / /______\\.\\  >  >",
	    "       / __/ /__________________/ /___/ / / /_/ / /_______/`/  >  >",
    	    "      /_____/ Testing Version. /_/   /_/_/_____/\\___/    //"
	    };

    public void doConsole(){
	Class c;
	Object instance;

	for(int i = 0; i < TITLE.length; i++){
	    System.out.println(TITLE[i]);
	}
	
	System.out.println("Enter a Command:");
	System.out.print("> ");
	
	boolean isActive = true;
	Scanner userIn = new Scanner(System.in);
	while(userIn.hasNext() && isActive){
	    
	    String input = userIn.nextLine();
	    if(input.toUpperCase().equals("QUIT")){
		isActive = false;
		return;
	    }
	    System.out.println(input);
	    
	    try {
		c = Class.forName(input);
	    } catch (Exception e) {
		System.err.println("COMMAND NOT FOUND\n" + e);
	    }
	    //f(c instanceof Command)
	    
	    
	    
	    //Preparing for next command
	    System.out.print("> ");
	}
	
	System.out.println("Stopping...");
	
    }
    
    public int doCommand(String command){
	//Lookup Command and do it.
	return 0;
    }

    public static void main(String[] args){
	Console c = new Console();
	c.doConsole();
    }

}
