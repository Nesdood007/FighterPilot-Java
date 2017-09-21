package levelgeneration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import level.BGLayer;
import level.Block;
import level.BlockLayer;
import level.Chunk;
import level.Layer;
import level.Level;
import level.Sprite;
import level.SpriteLayer;

/**This saves a level to a file.
 * 
 * @author Brady
 *
 */
public class LevelSaver {

    private static FileWriter writer;
    
    /**Converts a Level into a file
     * 
     * @param lvl Level to save
     * @param filename Name of file to write to 
     * @throws IOException Thrown if there is an error writing to a file
     */
    public static void saveLevelToFile(Level lvl, String filename) throws IOException{
        File file = new File(filename);
        file.createNewFile();//Creates a new File.
        writer = new FileWriter(file);
        writer.write("#This is a test of the Level Saving abilities.\n");
        System.out.println("Beginning to save Level " + lvl.name + " to file: " + filename);

        //Level
        writer.write("NAME=" + lvl.name + "\n");
        writer.write("CHUNKSIZE=[" + lvl.chunks.length + " ," + lvl.chunks[0].length + "]\n");

        //Chunks
        for(int chunkr = 0; chunkr < lvl.chunks.length; chunkr++) {
            for(int chunkc = 0; chunkc < lvl.chunks[0].length; chunkc++) {
                writer.write(saveChunk(lvl.chunks[chunkr][chunkc]));
            }
        }
        //When the level is finished being saved, flush the filewriter and return.
        writer.flush();
        writer.close();
        System.out.println("Done.");
        return;
    }
    /**Converts the Chunk to a savable format
     * 
     * @param chunk Chunk to save
     * @return savable String
     */
    private static String saveChunk(Chunk chunk) {
        String toReturn = "";
        toReturn += "BEGINCHUNK\n";
        for(int lyr = 0; lyr < chunk.layers.size(); lyr++) {
            toReturn += saveLayer(chunk.layers.get(lyr));
        }
        toReturn += "ENDCHUNK\n";
        return toReturn;
    }
    
    /**Converts the Layer into a savable format
     * 
     * @param layer Layer to save
     * @return Savable String
     */
    private static String saveLayer(Layer layer) {
        String toReturn = "";
        if (layer instanceof BGLayer) {
            toReturn += "BEGINBGLAYER\n";
            
            toReturn += "FILENAME=" + ((BGLayer)layer).getFileName() + "\n";
            toReturn += "SCROLL=" + ((((BGLayer)layer).isXScrolling()) ? "X" : "") + ((((BGLayer)layer).isYScrolling()) ? "Y" : "") + "\n";
            toReturn += "REPEAT=" + ((((BGLayer)layer).isXRepeating()) ? "X" : "") + ((((BGLayer)layer).isYRepeating()) ? "Y" : "") + "\n";
            
            toReturn += "ENDBGLAYER\n";
        } else if (layer instanceof SpriteLayer) {
            toReturn += "BEGINSPRITELAYER\n";
            for(int spt = 0; spt < ((SpriteLayer)layer).sprites.size();spt++){
                toReturn += saveSprite(((SpriteLayer)layer).sprites.get(spt));
            }
            toReturn += "ENDSPRITELAYER\n";
        } else if (layer instanceof BlockLayer) {
            toReturn += "BEGINBLOCKLAYER[" + ((BlockLayer)layer).blocks.length + " ," + ((BlockLayer)layer).blocks[0].length + "]\n";
            for(int blockr = 0; blockr < ((BlockLayer)layer).blocks.length; blockr++) {
                for(int blockc = 0; blockc < ((BlockLayer)layer).blocks[0].length; blockc++) {
                    toReturn += saveBlock(((BlockLayer)layer).blocks[blockr][blockc]);
                }
            }
            toReturn += "ENDBLOCKLAYER\n";
        } else {
            System.err.println("WARN::Incompatible Layer " + layer);
        }
        return toReturn;
    }
    
    /**Converts a Block to a savable format
     * 
     * @param block Block to save
     * @return Savable String
     */
    private static String saveBlock(Block block) {
        String toReturn = "";
        toReturn += "BEGINBLOCK\n";
        
        toReturn += "NAME=" + block.name + "\n";
        toReturn += "ID=" + block.id + "\n";
        toReturn += "DV=" + block.data_value + "\n";
        toReturn += "TRANSPARENT=" + block.transparent + "\n";
        
        toReturn += "ENDBLOCK\n";
        return toReturn;
    }

    private static String saveSprite(Sprite sprite) {
        String toReturn = "";
        toReturn += "BEGINSPRITE\n";
        
        toReturn += "ENDSPRITE\n";
        return toReturn;
    }
}
