package CustomException;

public class CustomException {
    public static class HttpConfigurationException extends RuntimeException{
        public HttpConfigurationException(String message){
            super(message);
        }
    }
}
