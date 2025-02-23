package Core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static Core.ServerListenerThread.logger;

public class ConnectionHandlerThread extends Thread {
    private Socket socket;

    public ConnectionHandlerThread (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            logger.info("processing connection in thread: " + Thread.currentThread().getName());
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<html><h1>Oi mundo :)</h1></html>";
            final String CRLF = "\n\r";
            //como eu nao estou explicitamente dizendo o que servir, o navegador vai
            //tentar adivinhar. adicionar no header o tipo
            String response =
                    "HTTP/1.1 200 OK " + CRLF // status
                            + "Content Length: " + html.getBytes().length + CRLF // header
                            + CRLF + html + CRLF + CRLF;

            outputStream.write(response.getBytes());
        } catch (IOException e){
            logger.error("Error at ConnectionHandlerThread: " + e.getCause() + "  " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e){
                    logger.error("Error when trying to close inputStream at ConnectionHandlerThread: " + e.getCause());
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e){
                    logger.error("Error when trying to close outputStream at ConnectionHandlerThread: " + e.getCause());
                }
            }

            if (!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e){
                    logger.error("Error when trying to close socket at ConnectionHandlerThread: " + e.getCause());
                }
            }
        }
    }
}
