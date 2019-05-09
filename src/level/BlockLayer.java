package level;



public class BlockLayer extends Layer {

    public Block[][] blocks;

    public BlockLayer(int i){

        super(i);
        blocks = new Block[8][8];
        populateLayer();
    }

    //Populates Layer with Blocks
    private void populateLayer() {
        for(int i=0; i<blocks.length; i++){
            for(int j=0; j<blocks[0].length; j++){
                blocks[i][j] = new Block();
            }
        }
    }

    public Block getBlock(int r, int c){
        return blocks[r][c];
    }

    public void setBlock(int r, int c, Block b){
        blocks[r][c] = b;
    }

    public int getUUID(){

        return id;
    }

    public void combine(BlockLayer l,boolean overwrite){

        for(int i=0;i < l.blocks.length;i++){
            for(int j=0;j < l.blocks[0].length;j++){

                if(blocks[i][j].id == 0 || overwrite){

                    blocks[i][j] = l.blocks[i][j];

                }
            }
        }

    }

    //Debug
    public String toString(){

        return "BlockLayer:: ID: " + super.id;
    }

}
