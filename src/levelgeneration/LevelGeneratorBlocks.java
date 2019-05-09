package levelgeneration;

import level.Block;
/**Contains all Blocks that can be used to generate a level. Enum Names should match the Block Class names.
 * 
 * @author Brady O'Leary
 *
 */
public enum LevelGeneratorBlocks {
    //Blocks go here
    Block("Block", new Block());
    
    public final String name;
    public final Block block;
    
    private LevelGeneratorBlocks(String name, Block block){
	this.name = name;
	this.block = block;
    }
    
    public String blockName(){
	return name;
    }
    
    public Block block(){
	return block;
    }
}
