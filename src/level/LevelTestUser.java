package level;

public class LevelTestUser {

    public static void main(String args[]){

	Level lvl = new Level();

	lvl.name = "Test Level";

	System.out.println("Chunks:: R: " + lvl.chunks.length + " C: " + lvl.chunks[0].length);

	System.out.println("Layers:");

	for(int i=0; i<lvl.chunks.length; i++){
	    for(int j=0; j<lvl.chunks[0].length; j++){

		System.out.print(lvl.chunks[i][j] + "\t");

	    }
	    System.out.println();
	}

	System.out.println(lvl.chunks[0][0].layers.get(0));

	//Test Part 2
	System.out.println("\n\n\nBEGINNING NEXT PART OF TEST.....\n");

	lvl = new Level("New Level",0,2,2);

	//Fix Chunk Rendering... Renders same blocklayer for all chunks

	BlockLayer bl = (BlockLayer) lvl.chunks[0][0].layers.get(0);

	bl.setBlock(1, 1, new Block("Test",false,1,0));
	bl.setBlock(0, 0, new Block("Test",false,2,0));
	bl.setBlock(2, 2, new Block("Test",false,3,0));

	lvl.chunks[0][0].layers.set(0,bl);

	lvl.chunks[1][1].layers.add(new BGLayer(0));

	bl = (BlockLayer) lvl.chunks[1][1].layers.get(0);

	bl.setBlock(4, 4, new Block("Poop",false,1,0));
	bl.setBlock(5, 4, new Block("Poop",false,1,0));
	bl.setBlock(4, 5, new Block("Poop",false,1,0));
	bl.setBlock(5, 5, new Block("Poop",false,1,0));

	lvl.chunks[1][1].layers.set(0,bl);

	bl = (BlockLayer) lvl.chunks[1][0].layers.get(0);

	bl.setBlock(2, 3, new Block("Test2",false,1,0));
	bl.setBlock(3, 3, new Block("Test2",false,1,0));
	bl.setBlock(4, 3, new Block("Test2",false,1,0));
	bl.setBlock(5, 3, new Block("Test2",false,1,0));

	lvl.chunks[1][0].layers.set(0, bl);

	for(int a=0;a < lvl.chunks.length;a++){
	    for(int b=0;b < lvl.chunks[0].length;b++){

		System.out.println("Chunk::{" + a + " ," + b + " }");

		for(int c=0;c < lvl.chunks[a][b].layers.size();c++){

		    if(lvl.chunks[a][b].layers.get(c) instanceof BlockLayer){

			System.out.println("\tBlockLayer::{" + c + "}");
			//Goes through Blocks
			bl = (BlockLayer) lvl.chunks[a][b].layers.get(c);

			System.out.println();

			for(int d=0;d < bl.blocks.length;d++){
			    for(int e=0; e < bl.blocks[0].length;e++){

				System.out.print(bl.blocks[d][e].id + "\t");

			    }
			    System.out.println();
			}







		    }else if(lvl.chunks[a][b].layers.get(c) instanceof SpriteLayer){

			System.out.println("\tSpriteLayer::{" + c + "}");
		    }else if(lvl.chunks[a][b].layers.get(c) instanceof BGLayer){

			System.out.println("\tBGLayer::{" + c + "}");
		    }else{
			System.out.println("\tUnknown Layer Type.");
		    }

		}


	    }



	}


    }


}
