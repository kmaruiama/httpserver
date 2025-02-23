import Configuration.ConfigurationManager;
import Configuration.Configuration;
import Core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    public final static Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main (String[] args){

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/configuration.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        logger.info("Initializing server at port " + configuration.getPort());
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
