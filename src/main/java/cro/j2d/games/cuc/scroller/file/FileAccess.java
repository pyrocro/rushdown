/**
 * 
 */
package cro.j2d.games.cuc.scroller.file;
import java.io.*;
/**
 * @author ymc
 *
 */
public class FileAccess {
	FileReader file;	
	BufferedReader br;
	private boolean FA_EOF;        
	/**
	 * @param file
	 */
	public FileAccess(String str) throws IOException{
		this.file = new FileReader(str);		
		br = new BufferedReader(file);
		FA_EOF = false;
	}
	public String readLine(){
		String str="";
                long wait = System.currentTimeMillis()+5000;
		try{
			while(!br.ready()){
                            if (System.currentTimeMillis() > wait) break;
                            /*do nothing if the stream is not ready*/
                        }
		str = br.readLine();		
		}catch (Exception E) {System.out.println(E);}
		if (str == null) {FA_EOF = true;str = "";System.out.println("failed to read from file");}		
		return str;
	}	
	public void close(){
		
		/*try{
		while(!br.ready()){}
		br.close();
		file.close();
		}catch (Exception E) {System.out.println(E);}*/
		
	}

    public boolean isEOF() {
        return FA_EOF;
    }
}

/*String inputstring;  // string to hold input
int inputvalue;      // integer value of input
BufferedReader BR;   // object to read from stdio
BR = new BufferedReader( new InputStreamReader(System.in) );
inputstring = BR.readLine();  // reads line from stdin
// converts string to integer, e.g., from "12" to 12:
inputvalue = Integer.parseInt(inputstring);  
BR.close();  // closes BufferedReader
*/