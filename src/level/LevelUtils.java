package level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LevelUtils {


    public static void main(String[] args){
	readLevel("");
    }

    public static Level readLevel(String fileName){

	Scanner fileIn = null;
	Level level = new Level();

	/*
	try{
	    fileIn = new Scanner(new File(fileName));
	}catch(IOException e){
	    e.printStackTrace();
	}
	 */

	//Debug
	fileIn = new Scanner("<level>\n<hello>\n<world>\n</world>\n</hello>\n</level>");

	String current;
	int currentIndex, endIndex;

	while(fileIn.hasNextLine()){

	    current = fileIn.nextLine();
	    if(current.indexOf("<") != -1){
		currentIndex = current.indexOf("<")+1;

		//Read until space. see what that string equals, the parse according to that
		endIndex = current.indexOf(" ",currentIndex);
		if(endIndex != -1){
		    //Parse parts of Tag
		    System.out.println(current.substring(currentIndex, endIndex));
		}

		//Go to end of line
		endIndex = current.indexOf(">",currentIndex);
		System.out.println(current.substring(currentIndex, endIndex));
	    }




	}

	return level;
    }



    public static void writeLevel(String fileName, Level level){

	FileWriter writer = null;

	try{
	    writer = new FileWriter(fileName, false);


	    writer.write("#This is a Test of the LevelWriter\n");
	    writer.write("<level" + "chunkSizeRow=" + level.chunks.length + " chunkSizeCol=" + level.chunks[0].length + ">\n");

	    for(int i = 0; i < level.chunks.length; i++){
		for(int j = 0; j < level.chunks[0].length; j++){

		    writer.write("<chunk locationRow=" + i + " locationCol=" + j + ">\n");

		    //Code to parse other stuff
		    for(int k = 0; k < level.chunks[i][j].layers.size(); k++){

			if(level.chunks[i][j].layers.get(k) instanceof BlockLayer){

			    for(int l = 0; l < ((BlockLayer) level.chunks[i][j].layers.get(k)).blocks.length; l++){
				for(int m = 0; m < ((BlockLayer) level.chunks[i][j].layers.get(k)).blocks[0].length; m++){

				    writer.write("<block locationRow=" + l + " locationCol=" + m + ">\n");
				    writer.write("<id>" + ((BlockLayer) level.chunks[i][j].layers.get(k)).blocks[l][m].id + "</id>\n");
				    writer.write("");//Finish

				}

			    }

			}



		    }


		    writer.write("</chunk>");
		}
	    }
	}catch(IOException e){
	    e.printStackTrace();
	}
    }
}
