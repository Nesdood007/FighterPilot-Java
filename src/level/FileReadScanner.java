package level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReadScanner {

    public static void main(String[] args) throws FileNotFoundException{

	testParse();

	xmlParse("hello1.txt");

	Scanner scan = new Scanner(new File("hello1.txt"));

	while(scan.hasNextLine()){
	    System.out.println(scan.nextLine());

	}
	scan.close();

    }

    public static void xmlParse(String fname) throws FileNotFoundException{

	System.out.println("XMLParse Started");

	Scanner scan = new Scanner(new File(fname));

	while(scan.hasNextLine()){

	    String line = scan.nextLine();

	    for(int i=0;i<line.length();i++){

		if(line.substring(i, i+1).equals("<")){

		    int endParse = i+1;
		    while(endParse < line.length() && !line.substring(endParse, endParse + 1).equals(">")){
			System.out.println("ok" + endParse + line.substring(i+1,endParse));

			if(line.substring(i+1,endParse).equals("test")){

			    System.out.println("Found Test.");   
			}
			endParse++;
		    }   
		}
	    }
	}
	scan.close();
	System.out.println("XMLParse Ended");
    }

    public static void testParse(){

	String xml = "Parse this stuff and do something //////with it";

	int startTag = 0, endTag = 0;

	while(startTag < xml.length() && endTag < xml.length() && !(xml.substring(endTag,endTag+1) == ">")){

	    if(xml.substring(startTag, endTag).equals("this")){
		startTag = endTag;
		System.out.println("Found .THIS.");

	    }
	    endTag++;

	}

    }

    /**
     * Go through xml file, parse each tag.
     * At each type of tag, such as <chunk>, create a new object, for instance a chunk.
     * 
     * Go through xml code recursively, call respective function until the level is built
     * 
     * Will need to find beginning and end tags of xml and pass that block of code onto the next parser.
     * 
     * 
     * 
     * @param fname FileName
     */

    public static void xmlLevelParse(String fname) throws FileNotFoundException{

	Scanner lvlfile = new Scanner(new File(fname));

	String levelxml = "";

	while(lvlfile.hasNextLine()){

	    levelxml += lvlfile.nextLine();

	}

	lvlfile.close();

	if(levelxml.indexOf("<level") != -1 && levelxml.indexOf("</level>") != -1){

	    levelxml = levelxml.substring(levelxml.indexOf("<level"), levelxml.indexOf("</level>"));

	    System.out.println(levelxml);

	    parseLevel(levelxml);
	}else{

	    System.out.println(levelxml + "\n>>This Is not a good level<<");
	}


    }

    //"<level name="something" chunksizex="5" chunksizey="5"

    public static Level parseLevel(String xml){

	//Parse information in <level> tag, then parse <chunk>

	int endTag = 0;

	while(endTag < xml.length() && xml.substring(endTag,endTag+1) == ">"){

	    endTag++;

	}




	return new Level();
    }

    public static Chunk parseChunk(String xml){

	//Parse information in <chunk> tag, then parse <layer>

	return new Chunk();
    }

    public static Layer parseLayer(String xml){

	//Parse information in <layer> tag, whether it is a BlockLayer, SpriteLayer, or BGLayer, then parse Block or Sprite

	return new Layer();
    }

    public static Sprite parseSprite(String xml){

	//Parse information in <sprite> tag, whatever sprite it will be, if that is possible

	return null;
    }

    public static Block parseBlock(String xml){

	//parse information in <block> tag

	return new Block();
    }



}
