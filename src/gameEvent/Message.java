package gameEvent;

public class Message {

    //Needs a location to show message, border, text (Formatted to allow for images, as well as text and also when to split the text into a new part
    String[] messagetext;
    
    //Location
    boolean snapToBottom;
    boolean snapToTop;
    boolean snapToLeft;
    boolean snapToRight;
    
    int pixelLocationX;
    int pixelLocationY;
    
    public Message(){
	
	
    }
}
