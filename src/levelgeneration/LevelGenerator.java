package levelgeneration;

import java.util.Random;

import fighterpilot.Enemy;
import fighterpilot.LargeFlyingPanel;
import fighterpilot.Player;
import fighterpilot.PongShip;
import graphics.SpriteSheet;
import level.BGLayer;
import level.BlockLayer;
import level.Chunk;
import level.Level;
import level.Sprite;
import level.SpriteLayer;
/**Generates a Level for FighterPilot2.
 * 
 * @author Brady O'Leary
 *
 */
public class LevelGenerator {

    private boolean includeBlocks, includeBackgrounds, includeSprites;
    String levelName = "";
    Random rand = new Random();

    //Player Spawn
    private int[] playerSpawn = {0,0};
    private int playerBuffer = 3;

    //For Difficulty of Level
    private int minEnemiesPerChunk = 1;
    private int maxEnemiesPerChunk = 1;//1

    /**Creates a new LevelGenerator Object
     * 
     * @param levelName Name of all Randomly Generated Levels
     * @param includeBlocks If true, it will include blocks in randomly generated level
     * @param includeBackgrounds If true, it will include backgrounds in randomly generated level
     * @param includeSprites If true, it will include sprites in randomly generated level
     */
    public LevelGenerator(String levelName, boolean includeBlocks, boolean includeBackgrounds, boolean includeSprites){
        this.includeBackgrounds = includeBackgrounds;
        this.includeBlocks = includeBlocks;
        this.includeSprites = includeSprites;
        this.levelName = levelName;
    }

    /**Default Constructor. 
     * 
     */
    public LevelGenerator(){
        this.includeBackgrounds = true;
        this.includeBlocks = true;
        this.includeSprites = true;
        this.levelName = "RandomLevelGen";
    }

    /**Sets the Difficulty of the Level to be generated
     * 
     * @param min Minimum Number of Enemies per Chunk
     * @param max Maximum number of enemies per chunk
     */
    public void setDifficulty(int min, int max) {
        if (min < 0) {min = 0;}
        if (max < 0) {max = 0;}
        this.minEnemiesPerChunk = min;
        this.maxEnemiesPerChunk = max;
    }

    /**Sets the Chunk that the player will spawn in
     * 
     * @param row Chunk Row
     * @param col Chunk Col
     */
    public void setPlayerSpawnChunk(int row, int col) {
        playerSpawn[0] = row;
        playerSpawn[1] = col;
    }

    /**Generates a Random Level.
     * 
     * @param row number of rows that the level will have
     * @param col number of columns that the level will have
     * @return RandomLy Generated Level.
     */
    public Level generateLevel(int row, int col){
        System.out.println("Generating Level (LevelGenerator)...");
        //@TODO Finish the algorithm
        Level toReturn = new Level(levelName, -1, row, col, new SpriteSheet(SpriteSheet.TRANSPARENT));

        //Select BGM
        if (includeSprites) {
            toReturn.audioFileName = "Music/gameplay" + (rand.nextInt(4) + 1) + ".wav";
        } else {
            toReturn.audioFileName = "Music/gameplay.peaceful1.wav";
        }
        System.out.println("BGM: " + toReturn.audioFileName);

        //Generate Background Layer
        toReturn.bg = new BGLayer[1];//Global Background
        toReturn.bg[0] = new BGLayer(1,"FighterPilotImages/Background.png",0.0,0.0,true,true);//FighterPilotImages/Background.png

        toReturn.fg = new BGLayer[0];//Global Foreground

        for(int crow = 0; crow < toReturn.chunks.length; crow++) {
            for(int ccol = 0; ccol < toReturn.chunks[0].length; ccol++) {
                toReturn.chunks[crow][ccol] = generateChunk(crow, ccol);
            }
        }
        System.out.println("Done.");
        return toReturn;
    }
    /**Generates a chunk. Is usually called on by the generateLevel method.
     * 
     * @param chunkRow the chunk row number
     * @param chunkCol the chunk col number
     * @return Randomly Generated Chunk
     */
    private Chunk generateChunk(int chunkRow, int chunkCol){
        System.out.print("\n" + chunkRow + " " + chunkCol + ":\t");
        Chunk toReturn = new Chunk();

        //Generates The Level
        if(chunkRow == playerSpawn[0] && chunkCol == playerSpawn[1]) {
            addPlayer(toReturn);
        } else if(includeSprites && chunkCol > playerSpawn[1] + playerBuffer) {
            addSprites(toReturn, rand.nextInt(maxEnemiesPerChunk - minEnemiesPerChunk + 1) + minEnemiesPerChunk);
        }
        if(includeBlocks) {
            addBlocks(toReturn, rand.nextInt(toReturn.getBlockSize()[0] * toReturn.getBlockSize()[1]) / 2, 3);
        }
        if(includeBackgrounds) {

        }
        return toReturn;
    }

    /**Adds Sprites to a Chunk
     * 
     * @param c Chunk to add Sprites to
     * @param numberOfSprites Number of Sprites to be added
     */
    private void addSprites(Chunk c, int numberOfSprites){
        System.out.print("Sprites: " + numberOfSprites + "\t");
        //LevelGeneratorSprites[] enemies = LevelGeneratorSprites.values();
        SpriteLayer slyr = new SpriteLayer(0);
        Sprite temp = null;
        for(int i = 0; i < numberOfSprites; i++) {
            /*temp = enemies[rand.nextInt(enemies.length)].sprite();
            System.out.print(temp + "\t");
            temp.loc.x = rand.nextDouble()*c.getBlockSize()[1];
            temp.loc.y = rand.nextDouble()*c.getBlockSize()[0];*/

            //LevelGeneratorSprites sprite = enemies[rand.nextInt(enemies.length)];
            switch(rand.nextInt(3)) {
                case 0:
                    temp = new Enemy("RandGen", 0, 0, rand.nextDouble()*c.getBlockSize()[1], rand.nextDouble()*c.getBlockSize()[0]);
                    break;
                case 1:
                    temp = new LargeFlyingPanel("RandGen", 0, 0, rand.nextDouble()*c.getBlockSize()[1], rand.nextDouble()*c.getBlockSize()[0], 0.001);
                    break;
                case 2:
                    //Adds the PongShip Sprite
                    temp = new PongShip("RandGen", 0, 0, rand.nextDouble()*c.getBlockSize()[1], rand.nextDouble()*c.getBlockSize()[0], rand.nextInt(4), true);
                    break;
            }
            slyr.sprites.add(temp);
        }
        c.layers.add(slyr);
    }

    /**Adds a Player to a Chunk
     * 
     * @param c Chunk to add a Player to.
     */
    private void addPlayer(Chunk c) {
        System.out.print("Player\t");
        SpriteLayer slyr = new SpriteLayer(1);
        slyr.sprites.add(new Player("Player 1", 0, 0, (double) c.getBlockSize()[0]/2, (double) c.getBlockSize()[1]/2));
        c.layers.add(slyr);
    }

    /**Adds a Number of Blocks to a Chunk.
     * 
     * @param c Chunk to add Blocks to
     * @param numberOfBlocks Number of Blocks to add
     * @param maxID Maximum ID that Blocks generated can have
     */
    private void addBlocks(Chunk c, int numberOfBlocks, int maxID) {
        BlockLayer bl;
        if (!c.hasBlockLayer()) {
            System.out.println("WARN:: No BlockLayer. Adding one.");
            bl = new BlockLayer(0);
            c.layers.add(bl);
        } else {
            bl = (BlockLayer) c.layers.get(c.getIndexOfBlockLayer());
        }
        int rpos, cpos;
        for (int i = 0; i < numberOfBlocks; i++) {
            rpos = rand.nextInt(c.getBlockSize()[0]);
            cpos = rand.nextInt(c.getBlockSize()[1]);
            if (bl.blocks[rpos][cpos].id != 0) {
                for(int row = 0; row < c.getBlockSize()[0]; row++) {
                    for(int col = 0; col < c.getBlockSize()[1]; col++) {
                        if(bl.blocks[row][col].id == 0) {
                            bl.blocks[row][col].id = rand.nextInt(maxID);
                        }
                    }
                }
            } else {
                bl.blocks[rpos][cpos].id = rand.nextInt(maxID);
            }
        }
    }
}
