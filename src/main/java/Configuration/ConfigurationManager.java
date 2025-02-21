package Configuration;

import CustomException.CustomException;
import Json.Json;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.BufferedReader;
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
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            JsonNode configuration = Json.parse(stringBuilder.toString());
            currentConfiguration = Json.fromJson(configuration, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Configuration getCurrentConfiguration(){
        if (currentConfiguration == null){
            throw new CustomException.HttpConfigurationException("Erro ao configurar servidor");
        }
        return currentConfiguration;
    }
}
