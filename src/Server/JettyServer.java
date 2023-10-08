package Server;import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {
    public static void main(String[] args) throws Exception {
        startServer();
    }

    public static void startServer() throws Exception {
        int port = 8080; // Puoi impostare la porta desiderata
        Server server = new Server(port);

        // Crea un contesto servlet per la tua servlet
        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.setContextPath("/");

        // Aggiungi la servlet al contesto
        servletContext.addServlet(new ServletHolder(new ServletPrima()), "/data"); // Mappa la servlet a "/data"

        // Configura Jetty per gestire i file JSP
        ServletHolder jspServletHolder = servletContext.addServlet(DefaultServlet.class, "*.jsp");
        jspServletHolder.setInitOrder(0);
        jspServletHolder.setInitParameter("resourceBase", "WebConversionProject"); // Sostituisci con il tuo percorso dei file JSP
        jspServletHolder.setInitParameter("dirAllowed", "true");

        // Configura Jetty per gestire i file HTML dalla directory WebConversionProject
        ServletHolder htmlServletHolder = servletContext.addServlet(DefaultServlet.class, "/");
        htmlServletHolder.setInitParameter("resourceBase", "WebConversionProject"); // Sostituisci con il tuo percorso dei file HTML
        htmlServletHolder.setInitParameter("dirAllowed", "true");

        // Configura il server Jetty con il contesto servlet
        server.setHandler(servletContext);

        try {
            // Avvia il server Jetty
            server.start();
            System.out.println("Server avviato correttamente");
            server.join();
        } finally {
            // Ferma il server Jetty quando hai finito
            server.stop();
        }
    }
}