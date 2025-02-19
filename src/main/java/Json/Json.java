package Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

//utility methods to convert json using jackson
public class Json {
    private final static ObjectMapper mapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        //do not break when encountering extra fields (this may be unsafe? idk)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static JsonNode parse (String string) throws IOException {
        return mapper.readTree(string);
    }


    //transforms json into a class
    public static <G> G fromJson(JsonNode jsonNode, Class<G> aClass) throws JsonProcessingException {
        return mapper.treeToValue(jsonNode, aClass);
    }

    //transforms an object into json
    public static JsonNode toJson(Object object){
        return mapper.valueToTree(object);
    }

    public static String makeString (Object object) throws JsonProcessingException{
        return generateJson(object, false);
    }

    public static String makeStringIndented (Object object) throws JsonProcessingException{
        return generateJson(object, true);
    }

    private static String generateJson(Object object, boolean indent) throws JsonProcessingException{
        ObjectWriter objectWriter = mapper.writer();
        if(indent){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(object);
    }
}
