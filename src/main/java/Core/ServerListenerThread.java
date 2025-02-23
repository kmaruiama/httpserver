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
        //como há apenas uma thread, as requests são processadas individualmente, uma após a outra
        //not good!!!
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                logger.info("Connection accepted, Ip: " + socket.getInetAddress());
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                String html = "<html><h1>Oi mundo :)</h1></html>";
                final String CRLF = "\n\r";
                //como eu nao estou explicitamente dizendo o que servir, o navegador vai
                //tentar adivinhar. adicionar no header o tipo
                String response =
                        "HTTP/1.1 200 OK " + CRLF // status
                                + "Content Length: " + html.getBytes().length + CRLF // header
                                + CRLF + html + CRLF + CRLF;

                outputStream.write(response.getBytes());
                inputStream.close();
                outputStream.close();
                socket.close();
            }
            // serverSocket.close();
        } catch (IOException e){
            logger.error("Error at ServerListenerThread: " + e.getCause() + "  " + e.getMessage());
        }
    }
}
