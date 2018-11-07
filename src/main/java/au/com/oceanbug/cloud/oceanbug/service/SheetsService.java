package au.com.oceanbug.cloud.oceanbug.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"

@Service
public class SheetsService {

    public String readSheet(){
        RestTemplate restTemplate = new RestTemplate();
        String url  = "";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        String result = null;
        try {
            root = mapper.readTree(response.getBody());
            //result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return walkTree(root);
    }

    public String walkTree(JsonNode root)
    {
        String result = "";

        JsonNode rootNode = readObject(root);

        Iterator<JsonNode> arraysUnderRoot = rootNode.elements();

        while (arraysUnderRoot.hasNext()) {
            JsonNode nextArrayUnderRoot = arraysUnderRoot.next();
            result = result + "\n" + nextArrayUnderRoot.toString();

//            Iterator<JsonNode> valuesInArray = nextArrayUnderRoot.elements();
//            while (valuesInArray.hasNext()) {
//                JsonNode nextValueInArray = valuesInArray.next();
//                System.out.println(readValue(nextValueInArray));
//            }
        }

        return result;
    }

    private JsonNode readObject(JsonNode node)
    {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> next =  iterator.next();

                String name = next.getKey();

                if("values".equalsIgnoreCase(name))
                    return next.getValue();;
            }
        }

        return null;
    }
}
