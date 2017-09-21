package levelgeneration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import level.Block;
import level.BlockLayer;
import level.Chunk;
import level.Level;

public class LevelReader {
    
    public static void main(String[] args) {
        try {
            LevelReader.readLevelFromFile("ReadingTest.lvl");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private enum Mode {LEVEL, CHUNK, BLOCKLAYER, SPRITELAYER, SPRITE, BLOCK};

    /**Builds a Level from a File.
     * 
     * @param fileName name of file to read in from
     * @return Read File
     * @throws FileNotFoundException 
     */
    public static Level readLevelFromFile(String fileName) throws FileNotFoundException{
        System.out.println("Reading Level from file: " + fileName);

        Level toReturn = new Level();

        toReturn.name = "Test";
        toReturn.audioFileName = "Music/gameplay.peaceful1.wav";
        
        Mode mode = Mode.LEVEL;
        int chunkRow = 0, chunkCol = 0, blockRow = 0, blockCol = 0, currLayer = 0;//Current Position
        
        Scanner lvlfile = new Scanner(new File(fileName));
        
        while (lvlfile.hasNextLine()) {
            String currLine = lvlfile.nextLine();
            System.out.println(currLine);
            
            //Eliminates Comment Lines
            if(currLine.startsWith("#")) {
                System.out.println("-->Comment");
                continue;
            }
            
            //Gets Level Name
            if(currLine.startsWith("NAME=") && mode == Mode.LEVEL) {
                System.out.println("-->Name is: " + currLine.substring(currLine.indexOf("=") + 1));
                toReturn.name = currLine.substring(currLine.indexOf("=") + 1);
                continue;
            }
            
            //Gets Chunksize, will determine how the Level is read.
            if(currLine.startsWith("CHUNKSIZE=[") && mode == Mode.LEVEL) {
                String numbers = currLine.substring(currLine.indexOf("[") + 1, currLine.indexOf("]"));
                int row = Integer.parseInt(numbers.substring(0, numbers.indexOf(",")).trim());
                int col = Integer.parseInt(numbers.substring(numbers.indexOf(",") + 1).trim());
                System.out.println("-->ChunkSize: " + row + " " + col);
                toReturn.chunks = new Chunk[row][col];
                continue;
            }
            
            //Changes to Chunk Mode in order to read a chunk
            if(currLine.startsWith("BEGINCHUNK") && mode == Mode.LEVEL) {
                mode = Mode.CHUNK;
                System.out.println("-->Parsing Chunk: " + chunkRow + " " + chunkCol);
                toReturn.chunks[chunkRow][chunkCol] = new Chunk();
                continue;
            }
            
            //Changes Mode to BlockLayer in order to Read Blocks
            if (currLine.startsWith("BEGINBLOCKLAYER") && mode == Mode.CHUNK) {
                mode = Mode.BLOCKLAYER;
                System.out.println("-->Parsing BlockLayer");
                continue;
            }
            
            //Changes Mode to Block in order to begin Reading Block
            if (currLine.startsWith("BEGINBLOCK") && mode == Mode.BLOCKLAYER) {
                mode = Mode.BLOCK;
                System.out.println("-->Parsing Block: " + blockRow + " " + blockCol);
                ((BlockLayer)toReturn.chunks[chunkRow][chunkCol].layers.get(currLayer)).blocks[blockRow][blockCol] = new Block();
                continue;
            }
            
            //Get Block Name
            if (currLine.startsWith("NAME=") && mode == Mode.BLOCK) {
                System.out.println("-->Name is: " + currLine.substring(currLine.indexOf("=") + 1));
                ((BlockLayer)toReturn.chunks[chunkRow][chunkCol].layers.get(currLayer)).blocks[blockRow][blockCol].name = currLine.substring(currLine.indexOf("=") + 1);
                continue;
            }
            
            //Get Block ID
            if (currLine.startsWith("ID=") && mode == Mode.BLOCK) {
                int id = Integer.parseInt(currLine.substring(currLine.indexOf("=") + 1).trim());
                System.out.println("-->ID is: " + id);
                ((BlockLayer)toReturn.chunks[chunkRow][chunkCol].layers.get(currLayer)).blocks[blockRow][blockCol].id = id;
            }
            
            //Get Block DV
            if (currLine.startsWith("DV=") && mode == Mode.BLOCK) {
                int dv = Integer.parseInt(currLine.substring(currLine.indexOf("=") + 1).trim());
                System.out.println("-->DV is: " + dv);
                ((BlockLayer)toReturn.chunks[chunkRow][chunkCol].layers.get(currLayer)).blocks[blockRow][blockCol].data_value = dv;
            }
            
            //Get Block Transparency
            if (currLine.startsWith("TRANSPARENT=") && mode == Mode.BLOCK) {
                boolean tr = (currLine.substring(currLine.indexOf("=") + 1).trim()).equals("true");
                System.out.println("-->Transparency is: " + tr);
                ((BlockLayer)toReturn.chunks[chunkRow][chunkCol].layers.get(currLayer)).blocks[blockRow][blockCol].transparent = tr;
            }
            
            //End Block Mode
            if (currLine.startsWith("ENDBLOCK") && mode == Mode.BLOCK) {
                mode = Mode.BLOCKLAYER;
                System.out.println("-->End Block Parsing");
                blockCol++;
                if(blockCol > toReturn.chunks[chunkRow][chunkCol].getBlockSize()[1] - 1) {
                    blockRow++;
                    blockCol = 0;
                }
            }
            
            //End BlockLayer Mode
            if (currLine.startsWith("ENDBLOCKLAYER") && mode == Mode.BLOCKLAYER) {
                mode = Mode.CHUNK;
                System.out.println("-->End BlockLayer Parsing");
                blockRow = 0;
                blockCol = 0;
            }
            
            //End Chunk Mode
            if (currLine.startsWith("ENDCHUNK") && mode == Mode.CHUNK) {
                mode = Mode.LEVEL;
                System.out.println("-->End Chunk Parsing");
                chunkCol++;
                if (chunkCol > toReturn.chunks[0].length - 1) {
                    chunkRow++;
                    chunkCol = 0;
                }
            }
            
            
        }
        
        lvlfile.close();
        System.out.println("Done.");
        return toReturn;
    }

}
