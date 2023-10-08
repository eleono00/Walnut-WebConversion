package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResultRepository {
	public static void checkFileExists(String filePath) throws Exception {
	    File fileToCheck = new File(filePath);
	    if (!fileToCheck.exists()) {
	        throw new Exception("Il file " + fileToCheck.getName() + " non esiste nella cartella result.");
	    }
	}
	public static String buildFilePath(String fileName) {
	    String projectDirectory = System.getProperty("user.dir");
	    String resultFolderPath = projectDirectory + File.separator + "Result";
	    return resultFolderPath + File.separator + fileName + ".gv";
	}
	
	  public static String readGVFilepredicato(String filePath) throws IOException {
	        Path path = Paths.get(filePath);
	        byte[] bytes = Files.readAllBytes(path);
	        return new String(bytes);
	    }
	    
	    public static String readGVFile(String filePath) throws IOException {
	        StringBuilder content = new StringBuilder();
	        
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                content.append(line);
	            }
	        }
	        System.out.println(content.toString());
	        return content.toString();
	        
	    }
}
