package laser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class LaserMapper {

	public static void main( String[] args )
    {
		String file = getFileByExtension();
		System.out.println(file);
		String[] tokens = file.split("\\.(?=[^\\.]+$)");
		System.out.println(tokens[0]);
		try(PrintWriter writer = new PrintWriter(tokens[0]+".nc", "UTF-8");
				FileInputStream inputStream = new FileInputStream(file);
				Scanner sc = new Scanner(inputStream, "UTF-8");) {
		    boolean endOfFile = false;
		    boolean numberBlock = false;
		    int currentLine = 0;
		    int wLines = 1;
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        //The first 15 lines are just configuration info.
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
		        	
		    	}else{//Writes to file the initial and final lines(not important to the printin process.)
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
	
	public static String getFileByExtension(){
		// inner class, generic extension filter
		class GenericExtFilter implements FilenameFilter {

			private String ext;

			public GenericExtFilter(String ext) {
				this.ext = ext;
			}

			public boolean accept(File dir, String name) {
				return (name.endsWith(ext));
			}
		}
		
		String ext = ".in";
		String folder = Paths.get(".").toAbsolutePath().normalize().toString();
		String temp = null;
		
		GenericExtFilter filter = new GenericExtFilter(ext);

		File dir = new File(folder);
		
		if(dir.isDirectory()==false){
			System.out.println("Directory does not exists : " + folder);
		}
		
		// list out all the file name and filter by the extension
		String[] list = dir.list(filter);

		if (list.length == 0) {
			System.out.println("no files end with : " + ext);
		}

		//returns the first file with specified extension
		for (String file : list) {	 
			return file;
		}
		return temp;
		
	}
	
}
