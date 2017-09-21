package level;

import graphics.SpriteSheet;

public class Level {

    public Chunk[][] chunks;
    public String name;
    int id;

    public SpriteSheet s;

    //Maybe this will fix our issue with scrolling BGS and foregrounds...
    public BGLayer[] bg;//Scrolling Background
    public BGLayer[] fg;//Scrolling Foreground
    
    //For Audio
    public String audioFileName = "";


    public Level(){

        chunks = new Chunk[4][16];
        name = "Nothing";
        id = 0;
        populateLevel();

        bg = new BGLayer[0];
        fg = new BGLayer[0];
        populateBGs();
        s = new SpriteSheet(SpriteSheet.TEST); 
    }

    public Level(SpriteSheet sprites){

        chunks = new Chunk[4][16];
        name = "Nothing";
        id = 0;
        populateLevel();

        bg = new BGLayer[0];
        fg = new BGLayer[0];
        populateBGs();
        s = sprites; 
    }

    //writes to all chunks
    private void populateLevel() {
        /*********************************************************************************
         * Just a note...
         * 
         * making all chunks equal to each other somehow makes them all share the same memory allocation, so all chunks would be the same...
         * 
         * So It has been fixed.
         * 
         *********************************************************************************/
        int tmpid=0;
        for(int i=0; i<chunks.length; i++){
            for(int j=0; j<chunks[0].length; j++){
                chunks[i][j] = new Chunk(tmpid);
                tmpid++;
            }
        }

    }

    private void populateBGs(){
        /**********************************************************************
         * Same thing as above, will populate BGS and FGS listed in Level class.
         * Does not affects BGs in Chunks
         * 
         * SHOULD ONLY BE CALLED IN CONSTRUCTORS THAT DO NOT USE GLOBAL BGS AND FGS
         *************************************************************************/

        int tmpid=0;
        for(int i=0; i<bg.length;i++){
            bg[i]= new BGLayer(tmpid);
            tmpid++;
        }
        for(int j=0; j<bg.length;j++){
            fg[j]= new BGLayer(tmpid);
            tmpid++;
        }

    }
    /**Creates a New Level
     * 
     * @param n Name of Level
     * @param i ID of Level
     * @param cr Chunk Rows to create
     * @param cc Chunk columns to create
     */
    public Level(String n, int i ,int cr, int cc){

        chunks = new Chunk[cr][cc];
        name = n;
        id = i;
        populateLevel();

        bg = new BGLayer[0];
        fg = new BGLayer[0];
        populateBGs();
        s = new SpriteSheet(SpriteSheet.TEST);

    }

    /**Creates a New Level
     * 
     * @param n Name of Level
     * @param i ID of Level
     * @param cr Chunk Rows to create
     * @param cc Chunk columns to create
     * @param sprites SpriteSheet to use
     */
    public Level(String n, int i ,int cr, int cc, SpriteSheet sprites){

        chunks = new Chunk[cr][cc];
        name = n;
        id = i;
        populateLevel();

        bg = new BGLayer[0];
        fg = new BGLayer[0];
        populateBGs();
        s = sprites;

    }

    /**Creates a New Level
     * 
     * @param n Name of Level
     * @param i ID of Level
     * @param cr Number of Chunk Rows
     * @param cc Number of Chunk Columns
     * @param bgs Array of BGS to insert
     * @param fgs Array of FGs to Insert
     */
    public Level(String n, int i ,int cr, int cc, BGLayer[] bgs, BGLayer[] fgs){

        chunks = new Chunk[cr][cc];
        name = n;
        id = i;
        populateLevel();

        bg = bgs;
        fg = fgs;
        s = new SpriteSheet(SpriteSheet.TEST);

    }

    public Level(String n, int i ,int cr, int cc, BGLayer[] bgs, BGLayer[] fgs, SpriteSheet spritesheet){

        chunks = new Chunk[cr][cc];
        name = n;
        id = i;
        populateLevel();

        bg = bgs;
        fg = fgs;
        s = spritesheet;

    }


    public void manageChunks(){
        /************************************************************
         * 
         * Moves all Sprites (and eventually Layers) between Chunks
         * 
         ************************************************************/

        for(int i=0;i<chunks.length;i++){
            for(int j=0;j<chunks[0].length;j++){

                if(chunks[i][j].isActive){

                    for(int k=0;k<chunks[i][j].layers.size();k++){

                        if(chunks[i][j].layers.get(k) instanceof SpriteLayer){

                            SpriteLayer sLayer = (SpriteLayer) chunks[i][j].layers.get(k);

                            for(int l=0;l<sLayer.sprites.size();l++){

                                if(sLayer.sprites.get(l).isDead()){

                                    //System.out.println("DeadSprite|" + sLayer.sprites.get(l));
                                    sLayer.sprites.remove(l);
                                    l--;
                                }else{

                                    //Taking a simplified approach to moving things around: will use ints (Range: 1 to -1) to move between chunks
                                    int moveX = 0, moveY = 0;
                                    boolean canMove = false, outOfBounds = false;


                                    //Checks if Sprite is outside chunk boundaries, and will then move the sprite to the appropriate neighboring chunk
                                    //PosX
                                    if(sLayer.sprites.get(l).loc.x > (double) chunks[i][j].getBlockSize()[0]){

                                        //System.out.println("moveX=1");

                                        //Makes sure Sprite is not outside of the Level
                                        if(j+1 < chunks[0].length){

                                            //Moves Sprite 1 chunk forward
                                            moveX = 1;
                                        }else{
                                            outOfBounds = true;
                                        }
                                        canMove = true;

                                    }
                                    //PosY
                                    if(sLayer.sprites.get(l).loc.y > (double) chunks[i][j].getBlockSize()[1]){

                                        //System.out.println("moveY=1, i=" +i);

                                        //Makes sure Sprite is not outside of the Level
                                        if(i+1 < chunks.length){

                                            //Moves Sprite 1 chunk forward
                                            moveY = 1;
                                        }else{
                                            outOfBounds = true;
                                        }
                                        canMove = true;
                                    }
                                    //NegX
                                    if(sLayer.sprites.get(l).loc.x < 0.0){// (double) chunks[i][j].getBlockSize()[0]

                                        //System.out.println("moveX=-1, j=" + j);

                                        //Makes sure Sprite is not outside of the Level
                                        if(j-1 >= 0){

                                            //Moves Sprite 1 chunk forward
                                            moveX = -1;
                                        }else{
                                            outOfBounds = true;
                                        }
                                        canMove = true;
                                    }
                                    //NegY
                                    if(sLayer.sprites.get(l).loc.y < 0.0){//(double) chunks[i][j].getBlockSize()[1]

                                        //System.out.println("moveY=-1, i=" +i);

                                        //Makes sure Sprite is not outside of the Level
                                        if(i-1 >= 0){

                                            //Moves Sprite 1 chunk forward
                                            moveY = -1;
                                        }else{
                                            outOfBounds = true;
                                        }
                                        canMove = true;
                                    }




                                    if(canMove){

                                        //System.out.println("moveX: " + moveX + " moveY: "+ moveY);
                                        //System.out.println("Will Move " + sLayer.sprites.get(l) + " [" + moveX + " " + moveY + "] OutOfBounds = " + outOfBounds);
                                        //System.out.println("moveX: " + moveX + " moveY: "+ moveY);

                                        //Checks to see if next Chunk has a Spritelayer, if not, then it will create one and place the sprite inside of it
                                        if(!outOfBounds && chunks[i+moveY][j+moveX].hasSpriteLayer()){

                                            //gets Sprite to be moved, changes the location, and then adds to existing SpriteLayer
                                            Sprite s = sLayer.sprites.get(l);

                                            //Changes location so that sprite will appear to seamlessly go from one chunk to another
                                            if(moveX == 1){
                                                s.loc.x -= (double) chunks[i][j].getBlockSize()[1];
                                            }else if(moveX == -1){
                                                s.loc.x += (double) chunks[i+moveY][j+moveX].getBlockSize()[1];
                                            }

                                            if(moveY == 1){
                                                s.loc.y -= (double) chunks[i][j].getBlockSize()[0];
                                            }else if(moveY == -1){
                                                s.loc.y = (double) chunks[i+moveY][j+moveX].getBlockSize()[0];
                                            }


                                            //k is Layer
                                            int sloc = chunks[i+moveY][j+moveX].getIndexOfSpriteLayer();
                                            SpriteLayer tmp = (SpriteLayer) chunks[i+moveY][j+moveX].layers.get(sloc);
                                            tmp.sprites.add(s);

                                            //System.out.println((i + moveY) + " " + (j + moveX) + "\t" + this.chunks.length + " " + this.chunks[0].length + "\t" + k + " " + this.chunks[i][j].layers.size());

                                            chunks[i+moveY][j+moveX].layers.remove(k);
                                            chunks[i+moveY][j+moveX].layers.add(k,tmp);

                                        }else if(!outOfBounds){

                                            //System.out.println("Adding SpriteLayer to chunk[" +(i + moveY) + " " +(j + moveX) + "]");

                                            //gets Sprite to be moved, changes the location
                                            Sprite s = sLayer.sprites.get(l);

                                            //Changes location so that sprite will appear to seamlessly go from one chunk to another
                                            if(moveX == 1){
                                                s.loc.x = 0.0;
                                            }else if(moveX == -1){
                                                s.loc.x = (double) chunks[i+moveY][j+moveX].getBlockSize()[0];
                                            }

                                            if(moveY == 1){
                                                s.loc.y = 0.0;
                                            }else if(moveY == -1){
                                                s.loc.y = (double) chunks[i+moveY][j+moveX].getBlockSize()[1];
                                            }

                                            Sprite[] tmp = new Sprite[1];
                                            tmp[0] = s;

                                            chunks[i+moveY][j+moveX].layers.add(new SpriteLayer(1000,tmp));	
                                        }

                                        //Removes Sprite from Old Layer
                                        sLayer.sprites.remove(l);
                                        chunks[i][j].layers.remove(k);
                                        chunks[i][j].layers.add(k,sLayer);	
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }




}
