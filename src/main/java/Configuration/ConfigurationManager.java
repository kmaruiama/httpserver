package Configuration;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    //#define EOF -1 ;)
    private static final int EOF = -1;
    private Configuration currentConfiguration;
    //singleton instantiation
    private ConfigurationManager(){};

    public static ConfigurationManager configurationManager;

    public static ConfigurationManager getInstance(){
        if (configurationManager == null){
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    public void loadConfigurationFile(String filepath) {
        try {
            FileReader fileReader = new FileReader(filepath);
            StringBuffer stringBuffer = new StringBuffer();
            int i = fileReader.read();
            while (i != EOF) {
                //I don't quite understand why we must explicit cast i as a char here
                stringBuffer.append((char) i);
                i++;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode json = Json.parse();
    }

    public void getCurrentConfiguration(){

    }
}
