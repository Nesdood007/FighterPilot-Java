package level;

import java.util.ArrayList;

public class Chunk{

    public ArrayList<Layer> layers = new ArrayList<Layer>(0);
    public int id;
    private int layerid = 0; //Latest assigned Layer ID
    public boolean isActive=false;
    private int blocksize[] = new int[2];

    public Chunk(){
        id = 0;
        layers.add(new BlockLayer(layerid));
        layerid++;
        blocksize[0] = 8;
        blocksize[1] = 8;

    }

    public Chunk(int i){
        id = i;
        layers.add(new BlockLayer(layerid));
        layerid++;
        blocksize[0]=8;
        blocksize[1]=8;
    }

    public Chunk(int i, Layer[] l){
        id = i;

        blocksize[0]=0;
        blocksize[1]=0;


        for(int j=0;j<l.length;j++){
            layers.add(l[j]);

            if(l[j] instanceof BlockLayer){
                BlockLayer temp = (BlockLayer) l[j];

                if(temp.blocks.length > blocksize[0]) blocksize[0] = temp.blocks.length;
                if(temp.blocks[0].length > blocksize[1]) blocksize[1] = temp.blocks[0].length;

            }
        }
    }
    /**Creates a new Chunk from an old one. I had to use this with Collision Detection and AI
     * 
     * @param chunk Old Chunk to be replicated
     */
    public Chunk(Chunk chunk){
        this.id = chunk.id;
        this.layers = new ArrayList<Layer>(chunk.layers);
        this.layerid = chunk.layerid;
        this.blocksize = chunk.blocksize;
    }

    public void addLayer(int type){
        if(type==1){
            layers.add(new SpriteLayer(layerid));

        }else if(type == 2){
            layers.add(new BGLayer(layerid));

        }else{
            layers.add(new BlockLayer(layerid));
        }
        layerid++;

    }

    //checks to see if the Specified Layer exists in the Layer List
    public boolean hasSpriteLayer(){

        for(int i=0;i<layers.size();i++){

            if(layers.get(i) instanceof SpriteLayer) return true;
        }
        return false;
    }

    public boolean hasBlockLayer(){

        for(int i=0;i<layers.size();i++){

            if(layers.get(i) instanceof BlockLayer) return true;
        }
        return false;
    }

    public boolean hasBGLayer(){

        for(int i=0;i<layers.size();i++){

            if(layers.get(i) instanceof BGLayer) return true;
        }
        return false;
    }

    //Looks for the first instance of the given Layer and will return the index. If it cannot find one, then it will return -1.
    public int getIndexOfBGLayer(){

        for(int i=0;i<layers.size();i++){

            if(layers.get(i) instanceof BGLayer) return i;
        }
        return -1;
    }

    public int getIndexOfSpriteLayer(){

        for(int i=0;i<layers.size();i++){

            if(layers.get(i) instanceof SpriteLayer) return i;
        }
        return -1;
    }

    public int getIndexOfBlockLayer(){

        for(int i=0;i<layers.size();i++){

            if(layers.get(i) instanceof BlockLayer) return i;
        }
        return -1;
    }







    public int[] getBlockSize(){
        return blocksize;
    }

    //Moves sprites if they move out of chunk area







    //Debug
    public String toString(){

        return "Chunk|ID: " + id + " #Layers: " + layers.size() + ":" + layers;

    }




}
