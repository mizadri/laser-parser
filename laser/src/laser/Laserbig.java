package laser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Laserbig {

	public static void main(String[] args) throws IOException {
	
		try(PrintWriter writer = new PrintWriter("lasermap.nc", "UTF-8");
				FileInputStream inputStream = new FileInputStream("data.in");
				Scanner sc = new Scanner(inputStream, "UTF-8");) {
		    boolean endOfFile = false;
		    boolean numberBlock = false;
		    int currentLine = 0;
		    int wLines = 1;
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        
		        if(currentLine > 15 && !endOfFile){
		        	
		        	if(line.equalsIgnoreCase("z0")){
			    		endOfFile = true;
			    		writer.println(line);
			    		wLines++;
			    	}else{
			    		String[] lineByPieces = line.split("z");
			
				    	if(!lineByPieces[1].equalsIgnoreCase("0")){
				    		
				    		if(!numberBlock){
				    			numberBlock = true;
				    			writer.println(lineByPieces[0]+"z0");
				    			wLines++;
				    		}
				    		
				    		writer.println(line);
				    		wLines++;
				    		
				    	}else{
				    		if(numberBlock){
				    			writer.println("z0");
				    		}
				    		numberBlock = false;		    		
				    	}
			    	}
		        	
		    	}else{//imprime las primeras y las ultimas
		    		if(line.contains("Total File Lines")){
		    			writer.println("(Total File Lines = "+wLines+" )");
		    		}else{
		    			writer.println(line);
		    			wLines++;	
		    		}		    			
		    	}   
		        currentLine++;
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
