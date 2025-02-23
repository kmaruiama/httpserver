package Core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public final static Logger logger = LoggerFactory.getLogger(ServerListenerThread.class);

    public ServerListenerThread (int port, String webroot) throws IOException
    {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run(){
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                //essa classe só vai servir para lidar com as sockets e passar elas pro
                Socket socket = serverSocket.accept();
                logger.info("Connection accepted, Ip: " + socket.getInetAddress());
                ConnectionHandlerThread connectionHandlerThread = new ConnectionHandlerThread(socket);
                connectionHandlerThread.start();
            }
        } catch (IOException e) {
            logger.error("Error at ServerListenerThread: " + e.getCause() + "  " + e.getMessage());
        }
    }
}
