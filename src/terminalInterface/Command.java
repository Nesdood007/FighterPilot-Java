package terminalInterface;

public interface Command {
    
    public String[] getCommandNames();
    
    public int doCommand();
    
    public String getDescription();

}
