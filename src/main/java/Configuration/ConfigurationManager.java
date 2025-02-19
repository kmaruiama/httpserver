package Configuration;

import CustomException.CustomException;
import Json.Json;
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

    //todo: criar customs e separar trycatch
    public void loadConfigurationFile(String filepath) {
        try {
            FileReader fileReader = new FileReader(filepath);
            StringBuffer stringBuffer = new StringBuffer();
            int i = fileReader.read();
            while (i != EOF) {
                //I don't quite understand why we must explicitly cast i as a char here
                stringBuffer.append((char) i);
                i++;
            }
            JsonNode configuration = Json.parse(stringBuffer.toString());
            //instantiates the Configuration class based on the json file
            currentConfiguration = Json.fromJson(configuration, Configuration.class);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace(
            );
        }
    }

    public void getCurrentConfiguration(){
        if (currentConfiguration == null){
            throw new CustomException.HttpConfigurationException("Erro ao configurar servidor");
        }
    }
}
