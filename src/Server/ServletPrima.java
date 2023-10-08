
package Server;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Data.ResultRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@WebServlet("/data")
public class ServletPrima extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(ServletPrima.class.getName());

   String namefile ="";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	    String filePath = ResultRepository.buildFilePath(namefile);

        String contentFile=ResultRepository.readGVFile(filePath); 
        String predicate = extractPredicateFromFile(ResultRepository.readGVFilepredicato(filePath));        
    	List<String> datiLunghi = new ArrayList<>();
        datiLunghi.add(contentFile);
        datiLunghi.add(predicate);
        System.out.println(predicate);
        
        // Serializza i dati in formato JSON
        String datiLunghiJson = new Gson().toJson(datiLunghi);
        
        // Imposta l'intestazione Content-Type
        response.setContentType("application/json");
        
        // Scrivi i dati nel corpo della risposta
        response.getWriter().write(datiLunghiJson);
        
         // Apri il browser solo se non è già stato aperto
        open(request);
         
    }
    
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Ottieni il flusso di input dalla richiesta
        BufferedReader reader = request.getReader();

        // Leggi i dati dal corpo della richiesta
        StringBuilder requestData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestData.append(line);
        }
        // Ora hai i dati inviati dal client nella variabile 'requestData'
        String clientData = requestData.toString();
        namefile=clientData;
        logger.warning(namefile);
        response.sendRedirect("/data");
    }


	
	  private void open(HttpServletRequest request) {
	    	// Apri il browser solo se non è già stato aperto
	        if (!isBrowserAlreadyOpened(request)) {
	            openBrowserWithUrl("http://localhost:8080/index.html");
	            markBrowserAsOpened(request);
	        }
	    	
	    }
	    private boolean isBrowserAlreadyOpened(HttpServletRequest request) {
	        // Verifica se il browser è già stato aperto
	        return request.getSession().getAttribute("browserOpened") != null;
	    }

	    private void markBrowserAsOpened(HttpServletRequest request) {
	        // Segna il browser come aperto in sessione
	        request.getSession().setAttribute("browserOpened", true);
	    }
	

	    private void openBrowserWithUrl(String url) {
	        try {
	            Desktop.getDesktop().browse(new URI(url));
	        } catch (IOException | URISyntaxException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static String extractPredicateFromFile(String fileContent) {
		    String[] lines = fileContent.split("\n");
		    for (String line : lines) {
		        if (line.contains("label =")) {
		            int start = line.indexOf("\"");
		            int end = line.lastIndexOf("\"");
		            if (start >= 0 && end >= 0) {
		                String predicate = line.substring(start + 1, end);
		                int colonIndex = predicate.indexOf(':');
		                if (colonIndex >= 0) {
		                    String prefix = predicate.substring(0, colonIndex + 1);
		                    String suffix = predicate.substring(colonIndex + 1).trim();

		                    // Verifica se il suffisso inizia con uno spazio e un punto interrogativo
		                    if (suffix.startsWith(" ?")) {
		                        return prefix.trim();
		                    } else {
		                        return predicate;
		                    }
		                }
		            }
		        }
		    }
		    return null;
		}
	  
	  
	    
}

