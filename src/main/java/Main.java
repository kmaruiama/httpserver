import Configuration.ConfigurationManager;
import Configuration.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main (String[] args){
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/configuration.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.print("Initializing HTTP Server at port: " + configuration.getPort());

        try {
            //listens to port
            ServerSocket serverSocket = new ServerSocket(configuration.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><h1>Oi mundo :)</h1></html>";
            final String CRLF = "\n\r";
            String response =
                    "HTTP/1.1 200 OK " + CRLF // status
                  + "Content Length: " + html.getBytes().length + CRLF // header
                  + CRLF + html + CRLF + CRLF;

            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e){
            System.out.print(e);
        }
    }
}
