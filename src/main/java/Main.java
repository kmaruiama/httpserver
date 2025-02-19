import Configuration.ConfigurationManager;

public class Main {
    public static void main (String[] args){
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/configuration.json");
    }
}
