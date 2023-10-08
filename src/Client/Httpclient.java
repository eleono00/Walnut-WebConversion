package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Httpclient {
	
	public static void sendHttpPostRequest(String fileName) throws Exception {
	    try {
	        URL url = new URL("http://localhost:8080/data");
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setDoOutput(true);

	        String postData = fileName;
	        try (OutputStream os = connection.getOutputStream();
	             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
	            osw.write(postData);
	            osw.flush();
	        }

	        int responseCode = connection.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            handleResponse(connection);
	        } else {
	            handleErrorResponse(responseCode);
	        }

	        connection.disconnect();
	    } catch (ConnectException e) {
	        handleConnectException();
	    } catch (Exception e) {
	        handleOtherException(e);
	    }
	}

	private static void handleResponse(HttpURLConnection connection) throws IOException {
	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String inputLine;
	    StringBuilder response = new StringBuilder();

	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();

	    System.out.println("Risposta dalla servlet: " + response.toString());
	}

	private static void handleErrorResponse(int responseCode) {
	    System.err.println("Errore nella richiesta HTTP: " + responseCode);
	}

	private static void handleConnectException() {
	    System.err.println("Impossibile connettersi al server. Assicurarsi che il server sia acceso.");
	    // Fornire istruzioni per avviare il server se necessario
	    // ...
	}

	private static void handleOtherException(Exception e) {
	    e.printStackTrace();
	}

}
