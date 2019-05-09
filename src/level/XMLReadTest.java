package level;

public class XMLReadTest {


    public static void main(String[] args){

	String xml = "<html><body><h1>Hello World</h1><p>This is Where the Magic Happens</p></body></html>";

	//Goes through xml string and looks for a given tag, and then looks for the end-tag and sends that block of xml into a recursive function until all data is processed

	XMLParse(xml,0);
    }

    public static void XMLParse(String xml,int l){

	System.out.println(l);

	int c = 0;
	int end = 0;

	@SuppressWarnings("unused")
	int lvl=0;

	String temp = "";

	c = xml.indexOf("<");

	while(c < xml.length()){

	    if(xml.substring(c,c+1).equals("<")){

		c++;

		while(c < xml.length() && !(xml.substring(c,c+1).equals(" ") || xml.substring(c,c+1).equals(">")) ){

		    temp += xml.substring(c,c+1);
		    c++;

		}

		end = c;





		/*while(end < xml.length() && (temp.length() + end + 3) < xml.length() && !(xml.substring(end,temp.length()+3).equals("</" + temp + ">"))){

					System.out.println(temp.length() + " " + xml.length() + " " + end + " " + c);
					end++;

				}*/

		System.out.print(temp + "\t" + c + "\t" + end + "\n");


		if(temp.substring(0,1).equals("/")){
		    return;
		}else{
		    XMLParse(xml.substring(c),l+1);
		}

		temp = "";//temp


	    }


	    c++;
	}




    }

}
